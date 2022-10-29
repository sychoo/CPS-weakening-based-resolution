% purpose: simulate the battery consumption of a drone
%
% assumption: drone does not consume energy while being stationary,
%             it does consume energy in a constant rate while flying
%
% expected result:  sig_var_battery = [100.0, 99.0, 99.0];
%                        actionList = [1, 0];

% constants
% how long it take for the battery to drain (from 100% to 0%)
% int: SIGNAL_LENGTH = 3;
float: FULL_BATTERY = 100;                        % percent
float: SIM_BAT_DRAIN = 100;                       % seconds (PX4 parameter)
float: DURATION = 2.0;                              % seconds
float: DRAIN_RATE = FULL_BATTERY / SIM_BAT_DRAIN; % percent/second

% take into account time passed during solving process, causing current battery to be inaccurate
float: SOLVER_OVERHEAD_DURATION_OFFSET = 1.0;           % seconds

% variables
% array[1..SIGNAL_LENGTH] of var 0.0..100.0: sig_var_battery; % signal/state seq
% array[1..SIGNAL_LENGTH - 1] of var 0..1: actionList;        % action seq
                                                            % 0 stationary (landed)
                                                            % 1 flying

% indicate the status of the drone                                                       
array[1..SIGNAL_LENGTH] of var 0..2: sig_var_drone_status; % IN_AIR: 0, LANDING: 1, ON_GROUND: 2

% enforce sequence 0 -> 1 -> 2 for drone_status
% a number of models depends on the current drone status
constraint 
  forall (idx in 1..SIGNAL_LENGTH - 1) (
    sig_var_drone_status[idx] <= sig_var_drone_status[idx + 1] /\
    sig_var_drone_status[idx + 1] - sig_var_drone_status[idx] <= 1
  );

% link signal drone_status to signal is_landing
constraint
    forall (idx in 1..SIGNAL_LENGTH) (
      if (sig_var_drone_status[idx] = 1) then (
        sig_var_is_landing[idx] = 1
      ) else (
        sig_var_is_landing[idx] = 0
      )
      endif
    );

% constraint on signal values
constraint
  forall (idx in 1..SIGNAL_LENGTH) (
    sig_var_battery[idx] >= 0.0 /\ sig_var_battery[idx] <= 100.0
  );

% model (can depends on other states)
predicate battery_model(var int: curr_drone_status,
                        var float: curr_battery,
                        var float: next_battery) =
  if (curr_drone_status = 0 \/ curr_drone_status = 1) then (
    next_battery = curr_battery - (DURATION + SOLVER_OVERHEAD_DURATION_OFFSET) * DRAIN_RATE
  )
  else (
    next_battery = curr_battery
  )
  endif;
 
% propagate
constraint
    forall (idx in 1..SIGNAL_LENGTH - 1) (
        battery_model(sig_var_drone_status[idx], sig_var_battery[idx], sig_var_battery[idx + 1])
    );

% constraint sig_var_drone_status[1] = 2;