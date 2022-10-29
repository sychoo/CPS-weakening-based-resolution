% develop a more comprehensive env model to model taking off and landing
% in addition of x and y coordinates, also model altitude

int: SIGNAL_LENGTH = 10;
float: GOAL_ALTITUDE = 20; % m

float: FULL_BATTERY = 100;                        % percent
float: SIM_BAT_DRAIN = 100;                       % seconds (PX4 parameter)
float: DURATION = 1.0;                              % seconds
float: DRAIN_RATE = FULL_BATTERY / SIM_BAT_DRAIN; % percent/second

% take into account time passed during solving process, causing current battery to be inaccurate
float: SOLVER_OVERHEAD_DURATION_OFFSET = 1.0;           % seconds


% list representing actions
array[1..SIGNAL_LENGTH - 1] of var 0..2: action_list; % Take off = 0, Fly horizontally = 1, Land = 2
array[1..SIGNAL_LENGTH] of var 0..3: sig_var_drone_status; % IN_AIR = 0, LANDING = 1, ON_GROUND = 2, TAKING_OFF = 3
array[1..SIGNAL_LENGTH] of var float: sig_var_altitude;

array[1..SIGNAL_LENGTH] of var 0.0..100.0: sig_var_battery; % signal/state seq
array[1..SIGNAL_LENGTH] of var 0..1: sig_var_is_landing; % signal/state seq

% Test case 1:
% ===========
% constraint action_list[1] = 2;
% constraint sig_var_drone_status[1] = 0;
% ===========
% expected next drone_status changed to LANDING = 1
% sig_var_drone_status[2] = 1;

% Test case 2:
% ===========
% constraint action_list[1] = 0;
% constraint sig_var_drone_status[1] = 2;
% ===========
% expected next drone_status changed to TAKING_OFF = 3
% sig_var_drone_status[2] = 3;

% link between action_list and drone_status
predicate action_drone_status(var int: curr_action,
                        var float: curr_drone_status,
                        var float: next_drone_status) =
% Take off: 0, ON_GROUND: 2 --> TAKING_OFF: 3
% Land: 2, IN_AIR: 0 --> LANDING: 1
  if (curr_action = 2) /\ (curr_drone_status = 0) then (
    next_drone_status = 1
  )endif 

  /\

  if (curr_action = 0) /\ (curr_drone_status = 2) then (
    next_drone_status = 3
  )
  endif;

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
    
% enforce sequence 0 -> 1 -> 2 for drone_status
% a number of models depends on the current drone status
constraint 
  forall (idx in 1..SIGNAL_LENGTH - 1) (
    sig_var_drone_status[idx] <= sig_var_drone_status[idx + 1] /\
    sig_var_drone_status[idx + 1] - sig_var_drone_status[idx] <= 1
  );

% enforce sequence 0 -> 1 -> 2 for action_list
% a number of models depends on the current drone status
constraint 
  forall (idx in 1..SIGNAL_LENGTH - 2) (
    action_list[idx] <= action_list[idx + 1] /\
    action_list[idx + 1] - action_list[idx] <= 1
  );



% model (can depends on other states)
predicate battery_model(var int: curr_drone_status,
                        var float: curr_battery,
                        var float: next_battery) =
    % not ON_GROUND = 2
  if (curr_drone_status = 0 \/ curr_drone_status = 1 \/ curr_drone_status = 3) then (
    next_battery = curr_battery - (DURATION + SOLVER_OVERHEAD_DURATION_OFFSET) * DRAIN_RATE
  )
  else (
    next_battery = curr_battery
  )
  endif;

% propagate
constraint
    forall (idx in 1..SIGNAL_LENGTH - 1) (
        action_drone_status(action_list[idx], sig_var_drone_status[idx], sig_var_drone_status[idx + 1]) /\
        battery_model(sig_var_drone_status[idx], sig_var_battery[idx], sig_var_battery[idx + 1])
    );
