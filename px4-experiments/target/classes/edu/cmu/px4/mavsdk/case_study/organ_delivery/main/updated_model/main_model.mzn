% drone from (0, 0) flying to (0, 10) at 5 m/s, with remaining battery of 30%. 
% safe landing threshold is willing to negotiate down to 20% battery remaining.
%
% some verbose description of this particular scenario
% Delivery Planning (DP)
% G[0,1](remaining_range_upon_delivery >= 0)
% Safe Landing (SL)
% G[0,1]((<<battery <= 20>>(delta_battery, 10.0)) -> (F[0,1](is_landing = 1)))
%
% one hard requirement is that the drone must have a non-negative remaining range
% upon the delivery of the organ
% 
% the last 10% battery life is reserved for landing feature only
% 
% by the current configuration, the battery has 12% left, 10 meters away from the destination
% based on the drain rate and the speed, it is able to reach exactly to the destination with no remaining range left
%
% however, if I set the constraint sig_var_battery[1] = 11.0; it no longer is able to make it to the destination,
% therefore, the solver returns UNSAT.
%
% the drone will continue flying (action = 1) or land immediately (action = 0);

% float: BATTERY_DRAIN_RATE = 2.0;
% float: SPEED = 5.0; % m/s 
% float: REFRESH_RATE_SECOND = 0.5; % seconds
% float: LANDING_TIME_REQUIRED = 10.0; % seconds

% current / constant speed
% constraint sig_var_speed[1] = 5.0;

% current location of the drone
% constraint loc_x[1] = 0.0;
% constraint loc_y[1] = 0.0;

% current battery level (30%)
% constraint sig_var_battery[1] = 24.0;

% current mission time left (seconds)
% constraint sig_var_mission_time_left[1] = 2;

% use the ego drone as the base line
% calculate the destination location of the drone
% float: DEST_X = 0.0; % east
% float: DEST_Y = 9.5; % north



/******** ABOVE IS THE INPUT FROM THE MAIN SIMULATOR TO THE SOLVER ********/


% additional constraint make sure the hard constraint is always satisfied
% bug in the STL Encoder, currently will not ensure that the hard constraint (without weakening) achieves a positive robustness
constraint rho_stl_1 > 0;

% action variables
array[1..SIGNAL_LENGTH - 1] of var -SPEED..SPEED: vel_x; % action seq
array[1..SIGNAL_LENGTH - 1] of var -SPEED..SPEED: vel_y; % action seq
array[1..SIGNAL_LENGTH - 1] of var 0..1: action; % action seq % 0: land, 1: fly

% state variables
array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: loc_x; % state variable 
array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: loc_y; % state variable 

% array[1..SIGNAL_LENGTH] of var 0..1000.0: sig_var_distance_to_hospital; % IN_AIR: 0, LANDING: 1, ON_GROUND: 2

% enforce sequence 0 -> 1 -> 2 for drone_status
% a number of models depends on the current drone status
constraint 
  forall (idx in 1..SIGNAL_LENGTH - 1) (
    sig_var_is_landing[idx] <= sig_var_is_landing[idx + 1] 
  );

  constraint
  forall (idx in 1..SIGNAL_LENGTH - 2) (
    action[idx] >= action[idx + 1]
  );

% constraint on signal values
constraint
  forall (idx in 1..SIGNAL_LENGTH) (
    sig_var_battery[idx] >= 0.0 /\ sig_var_battery[idx] <= 100.0
  );

% link action to sig_var_is_landing
% assume that at the start the drone is still flying when the resolver is called
constraint sig_var_is_landing[1] = 0;

predicate is_landing_model(var int: action, var float: sig_var_is_landing_before, var float: sig_var_is_landing_next) =
    if (action = 1 /\ sig_var_is_landing_before = 0) then (
        % keep flying
        sig_var_is_landing_next = 0
    ) elseif (action = 0 /\ sig_var_is_landing_before = 0) then (
        % landing
        sig_var_is_landing_next = 1
    ) else (
        sig_var_is_landing_next = sig_var_is_landing_before
    )
    endif;

%   constraint
%     forall (idx in 1..SIGNAL_LENGTH - 1) (
%       if (action[idx] = 1) then (
%         sig_var_is_landing[idx + 1] = 1
%       ) else (
%         sig_var_is_landing[idx] = 0
%       )
%       endif
%     );

% generate velocity vector from the current location and the destination location, with speed
% copy follower drone model
predicate drone_model(var float: vel_x, var float: vel_y, var int: action, % actions
                var float: curr_x, var float: curr_y, 
                var float: dest_x, var float: dest_y) = (
        % speed constraint
        vel_x * vel_x + 
        vel_y * vel_y = 
        REFRESH_RATE_SECOND * REFRESH_RATE_SECOND * SPEED * SPEED
        /\
        % make sure the velocity has the right sign
        vel_x * (dest_x - curr_x) >= 0
        /\
        vel_y * (dest_y - curr_y) >= 0
        /\
        % make sure each component velocity are proportional to the distance (cross multiplication 交叉相乘)
        vel_x * (dest_y - curr_y) = vel_y * (dest_x - curr_x)
        );

% physical model
predicate physical_model(var float: vel_x, var float: vel_y, var int: action, % actions
                var float: curr_x, var float: curr_y, 
                var float: next_x, var float: next_y) =
    if (action = 1) then (
        % location update
        next_x = curr_x + vel_x
        /\
        next_y = curr_y + vel_y
    )
    else (
        % location remain unchanged
        next_x = curr_x /\
        next_y = curr_y
    )
    endif;

% % estimated range
% % given the current battery, calculate the estimated range
% predicate estimated_range_model(var float: curr_battery, var float: sig_var_is_landing, var float: estimated_range) =
%     if (sig_var_is_landing = 0) then (
%         % flying
%         % take into account the landing battery required
%         estimated_range = ((curr_battery - LANDING_TIME_REQUIRED * BATTERY_DRAIN_RATE) / BATTERY_DRAIN_RATE) * SPEED

%     ) else (
%         % case when sig_var_is_landing = 1
%         estimated_range = 0.0
%     )
%     endif;

% distance to hospital
% pythagorean theorem
predicate distance_to_hospital_model(var float: curr_x, var float: curr_y, var float: distance_to_hospital) = 
    distance_to_hospital * distance_to_hospital = (curr_x - DEST_X) * (curr_x - DEST_X) + (curr_y - DEST_Y) * (curr_y - DEST_Y) /\

    % set range for the value "distance_to_hospital"
    distance_to_hospital >= 0.0;


% model (can depends on other states)
% battery simply decrements
predicate battery_model(var float: curr_battery,
                        var float: next_battery) =
    next_battery = curr_battery - (BATTERY_DRAIN_RATE * REFRESH_RATE_SECOND);

% speed remains constant (equal to the one at index one)
predicate speed_model(var float: curr_speed, var float: is_landing_status, var float: speed_constant) =
    if (is_landing_status = 0) then (
        curr_speed = speed_constant
    ) else (
        curr_speed = 0.0
    )
    endif;

predicate mission_time_left_model(var float: current_time_left, var float: next_time_left, var float: refresh_rate_second) =
    % subsequently decrement the time tick left
    next_time_left = current_time_left - refresh_rate_second;

% propagate calculation (dependencies on other signals)
constraint
    forall (idx in 1..SIGNAL_LENGTH) (
        % estimated_range_model(sig_var_battery[idx], sig_var_is_landing[idx], sig_var_estimated_range[idx]) /\
        distance_to_hospital_model(loc_x[idx], loc_y[idx], sig_var_distance_to_hospital[idx]) /\

        % speed remains constant (equal to the value at index 1)
        speed_model(sig_var_speed[idx], sig_var_is_landing[idx], SPEED)
    );

% propagate 
% link pre and post state with actions
constraint
    forall (idx in 1..SIGNAL_LENGTH - 1) (

        physical_model(vel_x[idx], vel_y[idx], action[idx],
                loc_x[idx], loc_y[idx],
                loc_x[idx + 1], loc_y[idx + 1])
        /\
        battery_model(sig_var_battery[idx], sig_var_battery[idx + 1])
        /\
        drone_model(vel_x[idx], vel_y[idx], action[idx],
                loc_x[idx], loc_y[idx],
                DEST_X, DEST_Y)
        /\
        is_landing_model(action[idx], sig_var_is_landing[idx], sig_var_is_landing[idx + 1])
        /\
        mission_time_left_model(sig_var_mission_time_left[idx], sig_var_mission_time_left[idx + 1], REFRESH_RATE_SECOND)
    );