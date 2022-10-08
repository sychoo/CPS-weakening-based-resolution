%==== simple battery model ====%
% action = 0, battery remain unchanged
% action = 1, battery percentage decrease by 1%

% declare action list
array[1..SIGNAL_LENGTH - 1] of var 0..1: action_list;

% constraint on signal values
constraint
  forall (idx in 1..SIGNAL_LENGTH) (
    sig_var_battery[idx] >= 0.0 /\ sig_var_battery[idx] <= 100.0
  );

% model
predicate model(var int: action,
                var float: curr_battery,
                var float: next_battery) =
  if action = 1 then (
    next_battery = curr_battery - 1.0
  )
  else (
    next_battery = curr_battery
  )
  endif;
  
% propagate
constraint
    forall (idx in 1..SIGNAL_LENGTH - 1) (
        model(action_list[idx], sig_var_battery[idx], sig_var_battery[idx + 1])
    );