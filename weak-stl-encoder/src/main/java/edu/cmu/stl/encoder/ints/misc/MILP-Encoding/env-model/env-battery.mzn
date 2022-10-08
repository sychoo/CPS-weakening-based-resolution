% minizinc env-battery.mzn --timelimit 5000
% limit the time to 5 seconds

int: time_horizon = 5;

% decision array
array[1..time_horizon] of var int: signal_var_battery;

% battery environmental model (decrement by 1)

% function var int: battery_model(var int : x1) =
%     x1 - 1;

predicate battery_model(var int: pre_state, var int: post_state) =
    post_state = pre_state - 1 \/
    post_state = pre_state - 2;

% initial state
% battery = 20
constraint signal_var_battery[1] = 20;

% propagate env model, link pre-state to post-state
constraint
    forall (idx in 1..time_horizon - 1) (
        battery_model(signal_var_battery[idx], signal_var_battery[idx + 1])
    );

% constraint the value range for the battery value
constraint
    forall (idx in 1..time_horizon) (
        signal_var_battery[idx] >= 0 /\
        signal_var_battery[idx] <= 100
    );

solve maximize signal_var_battery[time_horizon]

% the MiniZinc solver will automatically choose one for 
% non-deterministic choices

% unless otherwise specified maximize or minizinc clause