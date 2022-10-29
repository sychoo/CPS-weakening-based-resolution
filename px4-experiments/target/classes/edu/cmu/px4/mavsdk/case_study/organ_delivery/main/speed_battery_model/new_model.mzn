
% constant 
float: REFRESH_RATE_SECOND = 0.5; % seconds
float: SPEED = 5; % m/s

% runtime variables 
% constraint sig_var_speed[1] = 5.0; % m/s

% current battery level (30%)
constraint sig_var_battery[1] = 26.0;
constraint sig_var_mission_time_left[1] = 5;

% current location of the drone
constraint loc_x[1] = 0.0;
constraint loc_y[1] = 0.0;

% destination location with respect to current location
float: DEST_X = 0.0; % east
float: DEST_Y = 10.0; % north

% signal variables
array[1..SIGNAL_LENGTH] of var 0..1000.0: sig_var_speed;
array[1..SIGNAL_LENGTH] of var 0..1000.0: sig_var_necessary_speed;
array[1..SIGNAL_LENGTH] of var 0..1000.0: sig_var_distance_to_hospital;
array[1..SIGNAL_LENGTH] of var 0..1000.0: sig_var_mission_time_left;

% action variables
array[1..SIGNAL_LENGTH - 1] of var -1000.0..1000.0: vel_x; % action seq
array[1..SIGNAL_LENGTH - 1] of var -1000.0..1000.0: vel_y; % action seq
array[1..SIGNAL_LENGTH - 1] of var 0..1: action; % action seq % 0: land, 1: fly

% state variables
array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: loc_x; % state variable 
array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: loc_y; % state variable 

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%% MOST IMPORTANT CHANGE HERE %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% battery depends on the current speed, when speed increases, battery consumption increases exponentially
predicate battery_model(var float: curr_battery,
                        var float: next_battery,
                        var float: curr_speed) =
    % battery remain unchanged when the speed is 0
    if curr_speed = 0.0 then
        next_battery = curr_battery
    % battery decreases by 0.2 * speed * speed when the speed is not 0
    else
        next_battery = curr_battery - 0.2 * curr_speed * curr_speed
    endif;


% calculate based on the current coordinates
predicate distance_to_hospital_model(var float: curr_x, var float: curr_y, var float: distance_to_hospital) = 
    distance_to_hospital * distance_to_hospital = (curr_x - DEST_X) * (curr_x - DEST_X) + (curr_y - DEST_Y) * (curr_y - DEST_Y) /\

    % set range for the value "distance_to_hospital"
    % it should not be negative
    distance_to_hospital >= 0.0;

% generate velocity vector from the current location and the destination location, with speed
% copy follower drone model
predicate drone_model(var float: vel_x, var float: vel_y, var int: action, % actions
                var float: curr_x, var float: curr_y, 
                var float: dest_x, var float: dest_y, var float: curr_speed) = (
        % speed constraint
        % vel_x * vel_x + 
        % vel_y * vel_y = 
        % REFRESH_RATE_SECOND * REFRESH_RATE_SECOND * %curr_speed * curr_speed
        % /\
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

% caclulate the time left for the mission
% decrement by refresh_rate_second at every step of the way
predicate mission_time_left_model(var float: current_time_left, var float: next_time_left, var float: refresh_rate_second) =
    % subsequently decrement the time tick left
    next_time_left = current_time_left - refresh_rate_second;

% do not consider the battery in this case
predicate necessary_speed_model(var float: distance_to_hospital, var float: mission_time_left, var float: necessary_speed) =
    % calculate the necessary speed
    necessary_speed = distance_to_hospital / mission_time_left;
    % if (mission_time_left > 0) then (
        % necessary_speed = distance_to_hospital / mission_time_left;
    % ) else (
    %     necessary_speed = 1 % to represent infinity - no matter how fast the speed is, the drone can never get to the hospital on time
    % )
    % endif;

predicate current_speed_necessary_speed_diff_model(var float: curr_speed, var float: necessary_speed, var float: current_speed_necessary_speed_diff) =
    current_speed_necessary_speed_diff = curr_speed - necessary_speed;
    
% propagate calculation (dependencies on other signals)
constraint
    forall (idx in 1..SIGNAL_LENGTH) (
        % estimated_range_model(sig_var_battery[idx], sig_var_is_landing[idx], sig_var_estimated_range[idx]) /\
        distance_to_hospital_model(loc_x[idx], loc_y[idx], sig_var_distance_to_hospital[idx]) 
        % /\

        % speed remains constant (equal to the value at index 1)
        % speed_model(sig_var_speed[idx], sig_var_is_landing[idx], SPEED)
    );


% link the velocity vector action with the current speed
predicate speed_model(var float: vel_x, var float: vel_y, var float: curr_speed) =
    % calculate the speed based on the velocity vector
    curr_speed * curr_speed = vel_x * vel_x + vel_y * vel_y;


predicate speed_constraint(var float: curr_speed) =
    curr_speed >= 0.0 /\ curr_speed <= 5.0;

predicate is_landing_constraint(var float: is_landing) =
    is_landing = 0.0 \/ is_landing = 1.0;

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


% propagate calculation (dependencies on other signals)
constraint
    forall (idx in 1..SIGNAL_LENGTH) (
        distance_to_hospital_model(loc_x[idx], loc_y[idx], sig_var_distance_to_hospital[idx]) 
        /\
        % is landing can only be 0 or 1
        is_landing_constraint(sig_var_is_landing[idx])
        /\
        % calculate the necessary speed
        necessary_speed_model(sig_var_distance_to_hospital[idx], sig_var_mission_time_left[idx], sig_var_necessary_speed[idx])
        /\
        % calculate the difference between the current speed and the necessary speed
        current_speed_necessary_speed_diff_model(sig_var_speed[idx], sig_var_necessary_speed[idx], sig_var_current_speed_necessary_speed_diff[idx])
        % /\
        % speed_model(vel_x[idx], vel_y[idx], sig_var_speed[idx])
        /\
        speed_constraint(sig_var_speed[idx])
    );


% link previous state and the next state with the action
% propagate 
% link pre and post state with actions
constraint
    forall (idx in 1..SIGNAL_LENGTH - 1) (
        speed_model(vel_x[idx], vel_y[idx], sig_var_speed[idx])
        /\
        physical_model(vel_x[idx], vel_y[idx], action[idx],
                loc_x[idx], loc_y[idx],
                loc_x[idx + 1], loc_y[idx + 1])
        /\
        battery_model(sig_var_battery[idx], sig_var_battery[idx + 1], sig_var_speed[idx])
        /\
        drone_model(vel_x[idx], vel_y[idx], action[idx],
                loc_x[idx], loc_y[idx],
                DEST_X, DEST_Y, sig_var_speed[idx])
        /\
        is_landing_model(action[idx], sig_var_is_landing[idx], sig_var_is_landing[idx + 1])
        /\
        mission_time_left_model(sig_var_mission_time_left[idx], sig_var_mission_time_left[idx + 1], REFRESH_RATE_SECOND)
    );