
float: FOLLOWER_DRONE_MAX_SPEED = 5.0;
float: EGO_AVOID_SPEED = 10.0;
float: REFRESH_RATE_SECOND = 1;

float: BOUND_UPPER_X = 8.0;
float: BOUND_LOWER_X = -35.0;
float: BOUND_UPPER_Y = 8.0;
float: BOUND_LOWER_Y = -35.0;

% flying to the right
float: PREV_EGO_VEL_X = -6.0;
float: PREV_EGO_VEL_Y = 0.0;
float: PREV_EGO_VEL_Z = 0.0;

% model the physical dynamics of the drone (3D)
% note that x, y, and z indicates north, east, and down
array[1..SIGNAL_LENGTH - 1] of var -EGO_AVOID_SPEED..EGO_AVOID_SPEED: ego_vel_x; % action seq
array[1..SIGNAL_LENGTH - 1] of var -EGO_AVOID_SPEED..EGO_AVOID_SPEED: ego_vel_y; % action seq
array[1..SIGNAL_LENGTH - 1] of var -EGO_AVOID_SPEED..EGO_AVOID_SPEED: ego_vel_z; % action seq

array[1..SIGNAL_LENGTH - 1] of var -FOLLOWER_DRONE_MAX_SPEED..FOLLOWER_DRONE_MAX_SPEED: follower_vel_x; % action seq
array[1..SIGNAL_LENGTH - 1] of var -FOLLOWER_DRONE_MAX_SPEED..FOLLOWER_DRONE_MAX_SPEED: follower_vel_y; % action seq
array[1..SIGNAL_LENGTH - 1] of var -FOLLOWER_DRONE_MAX_SPEED..FOLLOWER_DRONE_MAX_SPEED: follower_vel_z; % action seq

array[1..SIGNAL_LENGTH - 1] of var -EGO_AVOID_SPEED..EGO_AVOID_SPEED: follower_perceive_ego_vel_x; % action seq
array[1..SIGNAL_LENGTH - 1] of var -EGO_AVOID_SPEED..EGO_AVOID_SPEED: follower_perceive_ego_vel_y; % action seq
array[1..SIGNAL_LENGTH - 1] of var -EGO_AVOID_SPEED..EGO_AVOID_SPEED: follower_perceive_ego_vel_z; % action seq

% note that z cannot be positive is encoded in the range limit
array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: ego_loc_x; % state variable 
array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: ego_loc_y; % state variable 
array[1..SIGNAL_LENGTH] of var -1000.0..0.0: ego_loc_z; % state variable 

array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: follower_loc_x; % state variable 
array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: follower_loc_y; % state variable 
array[1..SIGNAL_LENGTH] of var -1000.0..0.0: follower_loc_z; % state variable 

array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: follower_perceive_ego_loc_x; % state variable 
array[1..SIGNAL_LENGTH] of var -1000.0..1000.0: follower_perceive_ego_loc_y; % state variable 
array[1..SIGNAL_LENGTH] of var -1000.0..0.0: follower_perceive_ego_loc_z; % state variable 


% given the current x, y, and z position of the drone (regardless of which drone)
% and the action space that it is allowed to take, return the next x, y, and z position
% action + current state --> next state
predicate physical_model(var float: vel_x, var float: vel_y, var float: vel_z, % actions
                        var float: curr_x, var float: curr_y, var float: curr_z,
                        var float: next_x, var float: next_y, var float: next_z) =
        next_x = curr_x + REFRESH_RATE_SECOND * vel_x
        /\
        next_y = curr_y + REFRESH_RATE_SECOND * vel_y
        /\
        next_z = curr_z + REFRESH_RATE_SECOND * vel_z
        ;

% ego (0, 0, 10)
constraint ego_loc_x[1] = 0.0;
constraint ego_loc_y[1] = 0.0;
constraint ego_loc_z[1] = -10.0;

% follower (0, -8, 10)
constraint follower_loc_x[1] = 0.0;
constraint follower_loc_y[1] = -8.0;
constraint follower_loc_z[1] = -10.0;

constraint follower_perceive_ego_loc_x[1] = 0.0;
constraint follower_perceive_ego_loc_y[1] = 0.0;
constraint follower_perceive_ego_loc_z[1] = -10.0;

%******************************************************
% constraints for the ego drone
%******************************************************

% velocity should not exceed the maximum speed
predicate ego_drone_speed_constraints(var float: ego_vel_x, var float: ego_vel_y, var float: ego_vel_z) =
    ego_vel_x * ego_vel_x + ego_vel_y * ego_vel_y + ego_vel_z * ego_vel_z <= EGO_AVOID_SPEED * EGO_AVOID_SPEED
    ;


%******************************************************
% model the follower drone 
% (generate the most effective velocity vector to catch the ego drone)
%******************************************************
predicate distance_to_follower_drone_model(var float: ego_x, var float: ego_y, var float: ego_z, 
                                           var float: follower_x, var float: follower_y, var float: follower_z,
                                           var float: distance_to_follower) =

    distance_to_follower * distance_to_follower = (ego_x - follower_x) * (ego_x - follower_x) + (ego_y - follower_y) * (ego_y - follower_y) + (ego_z - follower_z) * (ego_z - follower_z)
    ;

% associate the location action with the state (location) of the ego drone and follower drone
% fly from follower drone --> ego drone
predicate follower_drone_model(var float: ego_x, var float: ego_y, var float: ego_z, 
                              var float: next_follower_x, var float: next_follower_y, var float: next_follower_z,
                              var float: follower_x, var float: follower_y, var float: follower_z,
                              var float: follower_vel_x, var float: follower_vel_y, var float: follower_vel_z) =
    % follower use max speed to fly towards ego drone                          
    % speed limit - do not exceed maximum speed
    (follower_vel_x * follower_vel_x + follower_vel_y * follower_vel_y + follower_vel_z * follower_vel_z = FOLLOWER_DRONE_MAX_SPEED * FOLLOWER_DRONE_MAX_SPEED)
    /\

    % case when the follower is closing in and catch the ego drone (foobar)
    if ((ego_x - follower_x) * (ego_x - follower_x) + (ego_y - follower_y) * (ego_y - follower_y) + (ego_z - follower_z) * (ego_z - follower_z) <= (FOLLOWER_DRONE_MAX_SPEED * REFRESH_RATE_SECOND) * (FOLLOWER_DRONE_MAX_SPEED * REFRESH_RATE_SECOND)) then (
        follower_vel_x = ego_x - follower_x 
        /\
        follower_vel_y = ego_y - follower_y
        /\
        follower_vel_z = ego_z - follower_z
    )

    % case when the the follower is still far from the ego, use max speed
    else (
        % old distance - new distance = max speed * refresh rate
        % sqrt((follower_x - ego_x) * (follower_x - ego_x) + (follower_y - ego_y) * (follower_y - ego_y) + (follower_z - ego_z) * (follower_z - ego_z)) - 
        % sqrt((next_follower_x - ego_x) * (next_follower_x - ego_x) + (next_follower_y - ego_y) * (next_follower_y - ego_y) + (next_follower_z - ego_z) * (next_follower_z - ego_z)) = REFRESH_RATE_SECOND * FOLLOWER_DRONE_MAX_SPEED
        % below is the flattening of above equation
        4 * ((follower_x - ego_x) * (follower_x - ego_x) + (follower_y - ego_y) * (follower_y - ego_y) + (follower_z - ego_z) * (follower_z - ego_z)) * ((next_follower_x - ego_x) * (next_follower_x - ego_x) + (next_follower_y - ego_y) * (next_follower_y - ego_y) + (next_follower_z - ego_z) * (next_follower_z - ego_z)) =
        (((follower_x - ego_x) * (follower_x - ego_x) + (follower_y - ego_y) * (follower_y - ego_y) + (follower_z - ego_z) * (follower_z - ego_z)) + 
        ((next_follower_x - ego_x) * (next_follower_x - ego_x) + (next_follower_y - ego_y) * (next_follower_y - ego_y) + (next_follower_z - ego_z) * (next_follower_z - ego_z)) -
        (REFRESH_RATE_SECOND * FOLLOWER_DRONE_MAX_SPEED) * (REFRESH_RATE_SECOND * FOLLOWER_DRONE_MAX_SPEED)) *
        (((follower_x - ego_x) * (follower_x - ego_x) + (follower_y - ego_y) * (follower_y - ego_y) + (follower_z - ego_z) * (follower_z - ego_z)) + 
        ((next_follower_x - ego_x) * (next_follower_x - ego_x) + (next_follower_y - ego_y) * (next_follower_y - ego_y) + (next_follower_z - ego_z) * (next_follower_z - ego_z)) -
        (REFRESH_RATE_SECOND * FOLLOWER_DRONE_MAX_SPEED) * (REFRESH_RATE_SECOND * FOLLOWER_DRONE_MAX_SPEED))
        
        /\
        % % make sure the velocity has the right sign
        follower_vel_x * (ego_x - follower_x) >= 0
        /\
        follower_vel_y * (ego_y - follower_y) >= 0
        /\
        follower_vel_z  * (ego_z - follower_z) >= 0
    )
    endif;

%******************************************************
% model the boundary
% assumption is that the drone is within the boundary
% will result in a negative value if the drone is outside the boundary
%******************************************************

predicate distance_to_boundary_model(var float: ego_x, var float: ego_y, var float: ego_z, var float: distance_to_boundary) =
    distance_to_boundary = min(min(ego_x - BOUND_LOWER_X, BOUND_UPPER_X - ego_x), min(ego_y - BOUND_LOWER_Y, BOUND_UPPER_Y - ego_y))
    % distance_to_boundary = min(ego_x - BOUND_LOWER_X, BOUND_UPPER_X - ego_x)
    ;

% predict the trajectory of follower perceived ego drone
constraint
    forall (idx in 1..SIGNAL_LENGTH - 1) (
        follower_perceive_ego_vel_x[idx] = PREV_EGO_VEL_X /\
        follower_perceive_ego_vel_y[idx] = PREV_EGO_VEL_Y /\
        follower_perceive_ego_vel_z[idx] = PREV_EGO_VEL_Z
    );

% propagate the state of the drone
constraint
    forall (idx in 1..SIGNAL_LENGTH) (
  
        % calculate state variables
        distance_to_follower_drone_model(ego_loc_x[idx], ego_loc_y[idx], ego_loc_z[idx],
                                         follower_loc_x[idx], follower_loc_y[idx], follower_loc_z[idx],
                                         sig_var_distance2follower[idx])
        /\
        distance_to_boundary_model(ego_loc_x[idx], ego_loc_y[idx], ego_loc_z[idx],
                                   sig_var_distance2bound[idx])

    );

% link the action of the drone to the states
constraint 
    forall (idx in 1..SIGNAL_LENGTH - 1) (
        % primitive physical model
        % ego drone
        physical_model(ego_vel_x[idx], ego_vel_y[idx], ego_vel_z[idx], 
                       ego_loc_x[idx], ego_loc_y[idx], ego_loc_z[idx], 
                       ego_loc_x[idx + 1], ego_loc_y[idx + 1], ego_loc_z[idx + 1])
        /\
        % follower drone
        physical_model(follower_vel_x[idx], follower_vel_y[idx], follower_vel_z[idx],
                       follower_loc_x[idx], follower_loc_y[idx], follower_loc_z[idx],
                       follower_loc_x[idx + 1], follower_loc_y[idx + 1], follower_loc_z[idx + 1])
        /\
        % follower perceived ego drone
        physical_model(follower_perceive_ego_vel_x[idx], follower_perceive_ego_vel_y[idx], follower_perceive_ego_vel_z[idx],
                       follower_perceive_ego_loc_x[idx], follower_perceive_ego_loc_y[idx], follower_perceive_ego_loc_z[idx],
                       follower_perceive_ego_loc_x[idx + 1], follower_perceive_ego_loc_y[idx + 1], follower_perceive_ego_loc_z[idx + 1])
        /\
        % rigid follower drone model (keep chasin')
        % determine whether use the current tick information, or use the predictive ego drone location
        follower_drone_model(
            % ego_loc_x[idx + 1], ego_loc_y[idx + 1], ego_loc_z[idx + 1],
            follower_perceive_ego_loc_x[idx + 1], follower_perceive_ego_loc_y[idx + 1], follower_perceive_ego_loc_z[idx + 1],
                             follower_loc_x[idx], follower_loc_y[idx], follower_loc_z[idx],
                             follower_loc_x[idx + 1], follower_loc_y[idx + 1], follower_loc_z[idx + 1],
                             follower_vel_x[idx], follower_vel_y[idx], follower_vel_z[idx])
        /\
        % ego drone speed constraints
        ego_drone_speed_constraints(ego_vel_x[idx], ego_vel_y[idx], ego_vel_z[idx])
    );

%===== BEGIN OF STL ROBUSTNESS CONSTRAINT (AUTO) =====

% int: SIGNAL_LENGTH = 2;
% array[1..SIGNAL_LENGTH] of var 0.0..1000.0: sig_var_distance2follower;
% array[1..SIGNAL_LENGTH] of var 0.0..1000.0: sig_var_distance2bound;

% % BEGIN OF STL ENCODING
% int: M = 1000;
% % BEGIN OF STL ENCODING FOR rho_stl_1
% var float: rho_stl_1;
% var float: rho_1;
% constraint rho_1 <= (sig_var_distance2bound[1])-(10);
% constraint rho_1 >= (sig_var_distance2bound[1])-(10);
% var float: rho_2;
% constraint rho_2 <= (sig_var_distance2bound[2])-(10);
% constraint rho_2 >= (sig_var_distance2bound[2])-(10);
% % Globally encoding
% array[1..2] of var 0..1: p_3;
% constraint sum(p_3) = 1;
% constraint rho_stl_1 <= rho_1;
% constraint rho_stl_1 <= rho_2;
% constraint rho_stl_1 >= rho_1 - (1 - p_3[1]) * M;
% constraint rho_stl_1 <= rho_1 + (1 - p_3[1]) * M;
% constraint rho_stl_1 >= rho_2 - (1 - p_3[2]) * M;
% constraint rho_stl_1 <= rho_2 + (1 - p_3[2]) * M;
% % end of Globally encoding
% % END OF STL ENCODING FOR rho_stl_1
% % BEGIN OF STL ENCODING FOR rho_stl_1_w
% var float: delta_bound;
% var float: rho_stl_1_w;
% var float: rho_4;
% constraint delta_bound >= 0;
% constraint delta_bound <= 5.0;
% constraint rho_4 <= ((sig_var_distance2bound[1])-(10))+(delta_bound);
% constraint rho_4 >= ((sig_var_distance2bound[1])-(10))+(delta_bound);
% var float: rho_5;
% constraint delta_bound >= 0;
% constraint delta_bound <= 5.0;
% constraint rho_5 <= ((sig_var_distance2bound[2])-(10))+(delta_bound);
% constraint rho_5 >= ((sig_var_distance2bound[2])-(10))+(delta_bound);
% % Globally encoding
% array[1..2] of var 0..1: p_6;
% constraint sum(p_6) = 1;
% constraint rho_stl_1_w <= rho_4;
% constraint rho_stl_1_w <= rho_5;
% constraint rho_stl_1_w >= rho_4 - (1 - p_6[1]) * M;
% constraint rho_stl_1_w <= rho_4 + (1 - p_6[1]) * M;
% constraint rho_stl_1_w >= rho_5 - (1 - p_6[2]) * M;
% constraint rho_stl_1_w <= rho_5 + (1 - p_6[2]) * M;
% % end of Globally encoding
% % END OF STL ENCODING FOR rho_stl_1_w
% % BEGIN OF STL ENCODING FOR rho_stl_2
% var float: rho_stl_2;
% var float: rho_7;
% constraint rho_7 <= (sig_var_distance2follower[1])-(10);
% constraint rho_7 >= (sig_var_distance2follower[1])-(10);
% var float: rho_8;
% constraint rho_8 <= (sig_var_distance2follower[2])-(10);
% constraint rho_8 >= (sig_var_distance2follower[2])-(10);
% % Globally encoding
% array[1..2] of var 0..1: p_9;
% constraint sum(p_9) = 1;
% constraint rho_stl_2 <= rho_7;
% constraint rho_stl_2 <= rho_8;
% constraint rho_stl_2 >= rho_7 - (1 - p_9[1]) * M;
% constraint rho_stl_2 <= rho_7 + (1 - p_9[1]) * M;
% constraint rho_stl_2 >= rho_8 - (1 - p_9[2]) * M;
% constraint rho_stl_2 <= rho_8 + (1 - p_9[2]) * M;
% % end of Globally encoding
% % END OF STL ENCODING FOR rho_stl_2
% % BEGIN OF STL ENCODING FOR rho_stl_2_w
% var float: delta_runaway;
% var float: rho_stl_2_w;
% var float: rho_10;
% constraint delta_runaway >= 0;
% constraint delta_runaway <= 5.0;
% constraint rho_10 <= ((sig_var_distance2follower[1])-(10))+(delta_runaway);
% constraint rho_10 >= ((sig_var_distance2follower[1])-(10))+(delta_runaway);
% var float: rho_11;
% constraint delta_runaway >= 0;
% constraint delta_runaway <= 5.0;
% constraint rho_11 <= ((sig_var_distance2follower[2])-(10))+(delta_runaway);
% constraint rho_11 >= ((sig_var_distance2follower[2])-(10))+(delta_runaway);
% % Globally encoding
% array[1..2] of var 0..1: p_12;
% constraint sum(p_12) = 1;
% constraint rho_stl_2_w <= rho_10;
% constraint rho_stl_2_w <= rho_11;
% constraint rho_stl_2_w >= rho_10 - (1 - p_12[1]) * M;
% constraint rho_stl_2_w <= rho_10 + (1 - p_12[1]) * M;
% constraint rho_stl_2_w >= rho_11 - (1 - p_12[2]) * M;
% constraint rho_stl_2_w <= rho_11 + (1 - p_12[2]) * M;
% % end of Globally encoding
% % END OF STL ENCODING FOR rho_stl_2_w
% constraint rho_stl_1_w > 0;
% constraint rho_stl_2_w > 0;
% solve minimize (rho_stl_1_w - rho_stl_1)+(rho_stl_2_w - rho_stl_2) + delta_runaway + delta_bound;

% %===== END OF STL ROBUSTNESS CONSTRAINT =====

