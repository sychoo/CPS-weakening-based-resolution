% purpose:  location/velocity model based on cartesian plane
% problem:  given the current location and destination location,
%           find the next action (among the velocity vectors)
%           that eventually advance to the destination location.
%
% example:  calculate the sequence of actions (no optimization)
%           going from (0,0) to (10,10) with SPEED <= 10 m/s
%
% candidate result: vel_x = 0.0;
%                   vel_y = 0.0;
%                   sig_var_loc_x = [0.0, 5.0, 10.0, 10.0, 10.0];
%                   sig_var_loc_y = [0.0, 5.0, 10.0, 10.0, 10.0];
%                   action_vel_x = [5.0, 5.0, 0.0, 0.0];
%                   action_vel_y = [5.0, 5.0, 0.0, 0.0];
%
% result interpretation: at time step 1 and 2, each have a velocity vector of
%                        5 m/s north and 5 m/s east. get to the destination
%                        at step 3. remain ever after.

% constants
% int: SIGNAL_LENGTH = 5;
float: SPEED = 5.0;
% float: DURATION = 1.0;

% always start with (0,0)
float: INIT_X = 0.0;
float: INIT_Y = 0.0;

% float: DEST_X = 10.0;
% float: DEST_Y = 10.0;

% variables
var float: vel_x;
var float: vel_y;

array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: sig_var_loc_x; % signal/state seq
array[1..SIGNAL_LENGTH] of var-1000.0..1000.0: sig_var_loc_y; % signal/state seq

array[1..SIGNAL_LENGTH - 1] of var 0.0..SPEED: action_vel_x; % action seq
array[1..SIGNAL_LENGTH - 1] of var 0.0..SPEED: action_vel_y; % action seq

array[1..SIGNAL_LENGTH] of var 0..1000.0: sig_var_estimated_range; % IN_AIR: 0, LANDING: 1, ON_GROUND: 2
array[1..SIGNAL_LENGTH] of var 0..1000.0: sig_var_distance_to_hospital; % IN_AIR: 0, LANDING: 1, ON_GROUND: 2



% function distance

constraint sig_var_loc_x[1] = INIT_X;
constraint sig_var_loc_y[1] = INIT_Y;

% additional constraint: final condition
% constraint sig_var_loc_x[SIGNAL_LENGTH] = DEST_X;
% constraint sig_var_loc_y[SIGNAL_LENGTH] = DEST_Y;

% model
predicate physical_model(var float: vel_x, var float: vel_y, % actions
                var float: curr_x, var float: curr_y, 
                var float: next_x, var float: next_y, var int: curr_drone_status) =
    if (curr_drone_status = 0) then (
    next_x = curr_x + DURATION * vel_x
    /\
    next_y = curr_y + DURATION * vel_y
    )
    else (
        next_x = curr_x /\
        next_y = curr_y
    )
    endif;

% estimated range
predicate estimated_range_model(var float: curr_battery, var float: estimated_range) =
    estimated_range = (curr_battery / DRAIN_RATE) * SPEED * DURATION;

% distance to hospital
% pythagorean theorem
predicate distance_to_hospital_model(var float: curr_x, var float: curr_y, var float: distance_to_hospital) = 
    distance_to_hospital * distance_to_hospital = (curr_x - DEST_X) * (curr_x - DEST_X) + (curr_y - DEST_Y) * (curr_y - DEST_Y);

% propagate calculation (dependencies on other signals)
constraint
    forall (idx in 1..SIGNAL_LENGTH) (
        estimated_range_model(sig_var_battery[idx], sig_var_estimated_range[idx]) /\
        distance_to_hospital_model(sig_var_loc_x[idx], sig_var_loc_y[idx], sig_var_distance_to_hospital[idx]) /\
        sig_var_remaining_range_upon_delivery[idx] = sig_var_estimated_range[idx] - sig_var_distance_to_hospital[idx]
    );

% propagate 
constraint
    forall (idx in 1..SIGNAL_LENGTH - 1) (
        % speed constraint
        action_vel_x[idx] * action_vel_x[idx] + 
        action_vel_y[idx] * action_vel_y[idx] <= 
        SPEED * SPEED
        /\
        physical_model(  action_vel_x[idx], action_vel_y[idx], 
                sig_var_loc_x[idx], sig_var_loc_y[idx], 
                sig_var_loc_x[idx + 1], sig_var_loc_y[idx + 1], sig_var_drone_status[idx])
    );