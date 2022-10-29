% note that the environmental model signal predication must be the same as the longest predicative horizon of the STL

int: time_horizon = 5; % >= max predictive horizon for STL specs
array[1..time_horizon] of var int: sig_var_battery; % signal
constraint sig_var_battery[1] = 10; % initial state

% battery model, relate pre-state to post-state
predicate battery_model(var int: pre_state, var int: post_state) =
    post_state = pre_state - 1;

% propagate env model for signal
constraint
    forall (idx in 1..time_horizon - 1) (
        battery_model(sig_var_battery[idx], sig_var_battery[idx + 1])
    );

% addtional constraints for battery value range
constraint
    forall (idx in 1..time_horizon) (
        sig_var_battery[idx] >= 0 /\
        sig_var_battery[idx] <= 100
    );

% solve maximize signal_var_battery[time_horizon]