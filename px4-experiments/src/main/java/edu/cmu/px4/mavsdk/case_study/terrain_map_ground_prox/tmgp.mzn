float: MAX_SPEED = 10.0;    % max speed = 5.00 m/s
float: DURATION = 1.0; % refresh per 1 second

% note that MAX_SPEED constraint is imposed here
array[1..SIGNAL_LENGTH - 1] of var -MAX_SPEED..MAX_SPEED: action_vel_down; % action seq

predicate physical_model(var float: action_vel_down, 
                        var float: curr_d2g, 
                        var float: next_d2g) =
    next_d2g = curr_d2g - DURATION * action_vel_down;
    
% propagate 
constraint
    forall (idx in 1..SIGNAL_LENGTH - 1) (
        physical_model(action_vel_down[idx], sig_var_d2g[idx], sig_var_d2g[idx + 1])
    );