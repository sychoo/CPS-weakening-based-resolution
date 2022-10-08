% org.json.JSONException: A JSONObject text must begin with '{' at 1 [character 2 line 1]
%
% note that the Result Module does not recognize this yet.
% to do: use the minizinc command to figure out the result, parse it propertly with out json exception
% Use this editor as a MiniZinc scratch book
%===== BEGIN OF SIGNAL DECLARATION (AUTO) =====
int: SIGNAL_LENGTH = 3;
array[1..SIGNAL_LENGTH] of var 0.0..1000.0: sig_var_remaining_range_upon_delivery;
array[1..SIGNAL_LENGTH] of var 0.0..1000.0: sig_var_is_landing;
array[1..SIGNAL_LENGTH] of var 0.0..1000.0: sig_var_battery;

%===== END OF SIGNAL DECLARATION =====

%===== BEGIN OF RUNTIME PARAMETER (MANUAL) =====
constraint sig_var_battery[1] = 34.000000;
constraint sig_var_drone_status[1] = 1;
float: DEST_X = 146.585091;
float: DEST_Y = -37.482372;

%===== END OF RUNTIME PARAMETER =====

%===== BEGIN OF ENVIRONMENTAL MODEL (MANUAL) =====
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
float: SPEED = 10.0;
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
    );% purpose: simulate the battery consumption of a drone
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
float: DURATION = 1.0;                              % seconds
float: DRAIN_RATE = FULL_BATTERY / SIM_BAT_DRAIN; % percent/second

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
    next_battery = curr_battery - DURATION * DRAIN_RATE
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
%===== END OF ENVIRONMENTAL MODEL =====

%===== BEGIN OF STL ROBUSTNESS CONSTRAINT (AUTO) =====
% BEGIN OF STL ENCODING
int: M = 1000;
% BEGIN OF STL ENCODING FOR rho_stl_1
var float: rho_stl_1;
var float: rho_1;
constraint rho_1 <= (sig_var_remaining_range_upon_delivery[1])-(0);
constraint rho_1 >= (sig_var_remaining_range_upon_delivery[1])-(0);
var float: rho_2;
constraint rho_2 <= (sig_var_remaining_range_upon_delivery[2])-(0);
constraint rho_2 >= (sig_var_remaining_range_upon_delivery[2])-(0);
% Globally encoding
array[1..2] of var 0..1: p_3;
constraint sum(p_3) = 1;
constraint rho_stl_1 <= rho_1;
constraint rho_stl_1 <= rho_2;
constraint rho_stl_1 >= rho_1 - (1 - p_3[1]) * M;
constraint rho_stl_1 <= rho_1 + (1 - p_3[1]) * M;
constraint rho_stl_1 >= rho_2 - (1 - p_3[2]) * M;
constraint rho_stl_1 <= rho_2 + (1 - p_3[2]) * M;
% end of Globally encoding
% END OF STL ENCODING FOR rho_stl_1
% BEGIN OF STL ENCODING FOR rho_stl_2
var float: rho_stl_2;
var float: rho_5;
constraint rho_5 <= (sig_var_battery[1])-(70);
constraint rho_5 >= (sig_var_battery[1])-(70);
var float: rho_6;
var float: rho_8;
constraint rho_8 <= (sig_var_is_landing[1])-(1);
constraint rho_8 >= (sig_var_is_landing[1])-(1);
var float: rho_9;
constraint rho_9 <= (1)-(sig_var_is_landing[1]);
constraint rho_9 >= (1)-(sig_var_is_landing[1]);
var float: rho_7;
array[1..2] of var 0..1: p_10;
constraint sum(p_10) = 1;
constraint rho_7 <= rho_8;
constraint rho_7 <= rho_9;
constraint rho_7 >= rho_8 - (1 - p_10[1]) * M;
constraint rho_7 <= rho_8 + (1 - p_10[1]) * M;
constraint rho_7 >= rho_9 - (1 - p_10[2]) * M;
constraint rho_7 <= rho_9 + (1 - p_10[2]) * M;
var float: rho_12;
constraint rho_12 <= (sig_var_is_landing[2])-(1);
constraint rho_12 >= (sig_var_is_landing[2])-(1);
var float: rho_13;
constraint rho_13 <= (1)-(sig_var_is_landing[2]);
constraint rho_13 >= (1)-(sig_var_is_landing[2]);
var float: rho_11;
array[1..2] of var 0..1: p_14;
constraint sum(p_14) = 1;
constraint rho_11 <= rho_12;
constraint rho_11 <= rho_13;
constraint rho_11 >= rho_12 - (1 - p_14[1]) * M;
constraint rho_11 <= rho_12 + (1 - p_14[1]) * M;
constraint rho_11 >= rho_13 - (1 - p_14[2]) * M;
constraint rho_11 <= rho_13 + (1 - p_14[2]) * M;
% Eventually encoding
array[1..2] of var 0..1: p_15;
constraint sum(p_15) = 1;
constraint rho_6 >= rho_7;
constraint rho_6 >= rho_11;
constraint rho_6 >= rho_7 - (1 - p_15[1]) * M;
constraint rho_6 <= rho_7 + (1 - p_15[1]) * M;
constraint rho_6 >= rho_11 - (1 - p_15[2]) * M;
constraint rho_6 <= rho_11 + (1 - p_15[2]) * M;
% end of Eventually encoding
var float: rho_4;
array[1..2] of var 0..1: p_16;
constraint sum(p_16) = 1;
constraint rho_4 >= rho_5;
constraint rho_4 >= rho_6;
constraint rho_4 >= rho_5 - (1 - p_16[1]) * M;
constraint rho_4 <= rho_5 + (1 - p_16[1]) * M;
constraint rho_4 >= rho_6 - (1 - p_16[2]) * M;
constraint rho_4 <= rho_6 + (1 - p_16[2]) * M;
var float: rho_18;
constraint rho_18 <= (sig_var_battery[2])-(70);
constraint rho_18 >= (sig_var_battery[2])-(70);
var float: rho_19;
var float: rho_21;
constraint rho_21 <= (sig_var_is_landing[2])-(1);
constraint rho_21 >= (sig_var_is_landing[2])-(1);
var float: rho_22;
constraint rho_22 <= (1)-(sig_var_is_landing[2]);
constraint rho_22 >= (1)-(sig_var_is_landing[2]);
var float: rho_20;
array[1..2] of var 0..1: p_23;
constraint sum(p_23) = 1;
constraint rho_20 <= rho_21;
constraint rho_20 <= rho_22;
constraint rho_20 >= rho_21 - (1 - p_23[1]) * M;
constraint rho_20 <= rho_21 + (1 - p_23[1]) * M;
constraint rho_20 >= rho_22 - (1 - p_23[2]) * M;
constraint rho_20 <= rho_22 + (1 - p_23[2]) * M;
var float: rho_25;
constraint rho_25 <= (sig_var_is_landing[3])-(1);
constraint rho_25 >= (sig_var_is_landing[3])-(1);
var float: rho_26;
constraint rho_26 <= (1)-(sig_var_is_landing[3]);
constraint rho_26 >= (1)-(sig_var_is_landing[3]);
var float: rho_24;
array[1..2] of var 0..1: p_27;
constraint sum(p_27) = 1;
constraint rho_24 <= rho_25;
constraint rho_24 <= rho_26;
constraint rho_24 >= rho_25 - (1 - p_27[1]) * M;
constraint rho_24 <= rho_25 + (1 - p_27[1]) * M;
constraint rho_24 >= rho_26 - (1 - p_27[2]) * M;
constraint rho_24 <= rho_26 + (1 - p_27[2]) * M;
% Eventually encoding
array[1..2] of var 0..1: p_28;
constraint sum(p_28) = 1;
constraint rho_19 >= rho_20;
constraint rho_19 >= rho_24;
constraint rho_19 >= rho_20 - (1 - p_28[1]) * M;
constraint rho_19 <= rho_20 + (1 - p_28[1]) * M;
constraint rho_19 >= rho_24 - (1 - p_28[2]) * M;
constraint rho_19 <= rho_24 + (1 - p_28[2]) * M;
% end of Eventually encoding
var float: rho_17;
array[1..2] of var 0..1: p_29;
constraint sum(p_29) = 1;
constraint rho_17 >= rho_18;
constraint rho_17 >= rho_19;
constraint rho_17 >= rho_18 - (1 - p_29[1]) * M;
constraint rho_17 <= rho_18 + (1 - p_29[1]) * M;
constraint rho_17 >= rho_19 - (1 - p_29[2]) * M;
constraint rho_17 <= rho_19 + (1 - p_29[2]) * M;
% Globally encoding
array[1..2] of var 0..1: p_30;
constraint sum(p_30) = 1;
constraint rho_stl_2 <= rho_4;
constraint rho_stl_2 <= rho_17;
constraint rho_stl_2 >= rho_4 - (1 - p_30[1]) * M;
constraint rho_stl_2 <= rho_4 + (1 - p_30[1]) * M;
constraint rho_stl_2 >= rho_17 - (1 - p_30[2]) * M;
constraint rho_stl_2 <= rho_17 + (1 - p_30[2]) * M;
% end of Globally encoding
% END OF STL ENCODING FOR rho_stl_2
% BEGIN OF STL ENCODING FOR rho_stl_2_w
var float: delta_battery;
var float: rho_stl_2_w;
var float: rho_32;
constraint delta_battery >= 0;
constraint delta_battery <= 30.0;
constraint rho_32 <= ((sig_var_battery[1])-(70))+(delta_battery);
constraint rho_32 >= ((sig_var_battery[1])-(70))+(delta_battery);
var float: rho_33;
var float: rho_35;
constraint rho_35 <= (sig_var_is_landing[1])-(1);
constraint rho_35 >= (sig_var_is_landing[1])-(1);
var float: rho_36;
constraint rho_36 <= (1)-(sig_var_is_landing[1]);
constraint rho_36 >= (1)-(sig_var_is_landing[1]);
var float: rho_34;
array[1..2] of var 0..1: p_37;
constraint sum(p_37) = 1;
constraint rho_34 <= rho_35;
constraint rho_34 <= rho_36;
constraint rho_34 >= rho_35 - (1 - p_37[1]) * M;
constraint rho_34 <= rho_35 + (1 - p_37[1]) * M;
constraint rho_34 >= rho_36 - (1 - p_37[2]) * M;
constraint rho_34 <= rho_36 + (1 - p_37[2]) * M;
var float: rho_39;
constraint rho_39 <= (sig_var_is_landing[2])-(1);
constraint rho_39 >= (sig_var_is_landing[2])-(1);
var float: rho_40;
constraint rho_40 <= (1)-(sig_var_is_landing[2]);
constraint rho_40 >= (1)-(sig_var_is_landing[2]);
var float: rho_38;
array[1..2] of var 0..1: p_41;
constraint sum(p_41) = 1;
constraint rho_38 <= rho_39;
constraint rho_38 <= rho_40;
constraint rho_38 >= rho_39 - (1 - p_41[1]) * M;
constraint rho_38 <= rho_39 + (1 - p_41[1]) * M;
constraint rho_38 >= rho_40 - (1 - p_41[2]) * M;
constraint rho_38 <= rho_40 + (1 - p_41[2]) * M;
% Eventually encoding
array[1..2] of var 0..1: p_42;
constraint sum(p_42) = 1;
constraint rho_33 >= rho_34;
constraint rho_33 >= rho_38;
constraint rho_33 >= rho_34 - (1 - p_42[1]) * M;
constraint rho_33 <= rho_34 + (1 - p_42[1]) * M;
constraint rho_33 >= rho_38 - (1 - p_42[2]) * M;
constraint rho_33 <= rho_38 + (1 - p_42[2]) * M;
% end of Eventually encoding
var float: rho_31;
array[1..2] of var 0..1: p_43;
constraint sum(p_43) = 1;
constraint rho_31 >= rho_32;
constraint rho_31 >= rho_33;
constraint rho_31 >= rho_32 - (1 - p_43[1]) * M;
constraint rho_31 <= rho_32 + (1 - p_43[1]) * M;
constraint rho_31 >= rho_33 - (1 - p_43[2]) * M;
constraint rho_31 <= rho_33 + (1 - p_43[2]) * M;
var float: rho_45;
constraint delta_battery >= 0;
constraint delta_battery <= 30.0;
constraint rho_45 <= ((sig_var_battery[2])-(70))+(delta_battery);
constraint rho_45 >= ((sig_var_battery[2])-(70))+(delta_battery);
var float: rho_46;
var float: rho_48;
constraint rho_48 <= (sig_var_is_landing[2])-(1);
constraint rho_48 >= (sig_var_is_landing[2])-(1);
var float: rho_49;
constraint rho_49 <= (1)-(sig_var_is_landing[2]);
constraint rho_49 >= (1)-(sig_var_is_landing[2]);
var float: rho_47;
array[1..2] of var 0..1: p_50;
constraint sum(p_50) = 1;
constraint rho_47 <= rho_48;
constraint rho_47 <= rho_49;
constraint rho_47 >= rho_48 - (1 - p_50[1]) * M;
constraint rho_47 <= rho_48 + (1 - p_50[1]) * M;
constraint rho_47 >= rho_49 - (1 - p_50[2]) * M;
constraint rho_47 <= rho_49 + (1 - p_50[2]) * M;
var float: rho_52;
constraint rho_52 <= (sig_var_is_landing[3])-(1);
constraint rho_52 >= (sig_var_is_landing[3])-(1);
var float: rho_53;
constraint rho_53 <= (1)-(sig_var_is_landing[3]);
constraint rho_53 >= (1)-(sig_var_is_landing[3]);
var float: rho_51;
array[1..2] of var 0..1: p_54;
constraint sum(p_54) = 1;
constraint rho_51 <= rho_52;
constraint rho_51 <= rho_53;
constraint rho_51 >= rho_52 - (1 - p_54[1]) * M;
constraint rho_51 <= rho_52 + (1 - p_54[1]) * M;
constraint rho_51 >= rho_53 - (1 - p_54[2]) * M;
constraint rho_51 <= rho_53 + (1 - p_54[2]) * M;
% Eventually encoding
array[1..2] of var 0..1: p_55;
constraint sum(p_55) = 1;
constraint rho_46 >= rho_47;
constraint rho_46 >= rho_51;
constraint rho_46 >= rho_47 - (1 - p_55[1]) * M;
constraint rho_46 <= rho_47 + (1 - p_55[1]) * M;
constraint rho_46 >= rho_51 - (1 - p_55[2]) * M;
constraint rho_46 <= rho_51 + (1 - p_55[2]) * M;
% end of Eventually encoding
var float: rho_44;
array[1..2] of var 0..1: p_56;
constraint sum(p_56) = 1;
constraint rho_44 >= rho_45;
constraint rho_44 >= rho_46;
constraint rho_44 >= rho_45 - (1 - p_56[1]) * M;
constraint rho_44 <= rho_45 + (1 - p_56[1]) * M;
constraint rho_44 >= rho_46 - (1 - p_56[2]) * M;
constraint rho_44 <= rho_46 + (1 - p_56[2]) * M;
% Globally encoding
array[1..2] of var 0..1: p_57;
constraint sum(p_57) = 1;
constraint rho_stl_2_w <= rho_31;
constraint rho_stl_2_w <= rho_44;
constraint rho_stl_2_w >= rho_31 - (1 - p_57[1]) * M;
constraint rho_stl_2_w <= rho_31 + (1 - p_57[1]) * M;
constraint rho_stl_2_w >= rho_44 - (1 - p_57[2]) * M;
constraint rho_stl_2_w <= rho_44 + (1 - p_57[2]) * M;
% end of Globally encoding
% END OF STL ENCODING FOR rho_stl_2_w
constraint rho_stl_2_w > 0;
solve minimize (rho_stl_2_w - rho_stl_2);

%===== END OF STL ROBUSTNESS CONSTRAINT =====


