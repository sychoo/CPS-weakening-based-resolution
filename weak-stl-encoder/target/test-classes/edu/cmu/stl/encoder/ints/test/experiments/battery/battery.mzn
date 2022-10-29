% This is used to demonstrate what the solver will do
% for two conflicts with weakening

% BEGIN OF SIGNAL ENCODING
int: signal_length = 6;

array[1..signal_length - 1] of var int: actionList;
% 1. move
% 2. land

array[1..signal_length] of var int: sig_var_battery;
% ([10, 9, 8, 7, 6, 5]);

% initial state
constraint sig_var_battery[1] = 8; % initial state

% battery model, relate pre-state to post-state
predicate battery_model(var int: action, var int: pre_state, var int: post_state) =
    if action = 1 then (
        post_state = pre_state - 1
    ) else (
        post_state = pre_state
    )
    endif
    ;

% propagate env model for signal
constraint
    forall (idx in 1..signal_length - 1) (
        battery_model(actionList[idx], sig_var_battery[idx], sig_var_battery[idx + 1])
    );

% addtional constraints for battery value range
constraint
    forall (idx in 1..signal_length) (
        sig_var_battery[idx] >= 0 /\
        sig_var_battery[idx] <= 100
    );

% additional constraints for battery
constraint
    forall (idx in 1..signal_length - 1) (
        actionList[idx] = 1 \/ actionList[idx] = 2
    );
% solve maximize signal_var_battery[time_horizon]

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% G[0, 5](<<battery >= 10>>(delta_1, 5))
% G[0, 5](<<battery < 7>>(delta_2, 5))
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% BEGIN OF STL ENCODING
int: M = 1000;
% BEGIN OF STL ENCODING FOR rho_stl_1
var int: rho_stl_1;
var int: rho_1;
constraint rho_1 <= (sig_var_battery[1])-(10);
constraint rho_1 >= (sig_var_battery[1])-(10);
var int: rho_2;
constraint rho_2 <= (sig_var_battery[2])-(10);
constraint rho_2 >= (sig_var_battery[2])-(10);
var int: rho_3;
constraint rho_3 <= (sig_var_battery[3])-(10);
constraint rho_3 >= (sig_var_battery[3])-(10);
var int: rho_4;
constraint rho_4 <= (sig_var_battery[4])-(10);
constraint rho_4 >= (sig_var_battery[4])-(10);
var int: rho_5;
constraint rho_5 <= (sig_var_battery[5])-(10);
constraint rho_5 >= (sig_var_battery[5])-(10);
var int: rho_6;
constraint rho_6 <= (sig_var_battery[6])-(10);
constraint rho_6 >= (sig_var_battery[6])-(10);
% Globally encoding
array[1..6] of var 0..1: p_7;
constraint sum(p_7) = 1;
constraint rho_stl_1 <= rho_1;
constraint rho_stl_1 <= rho_2;
constraint rho_stl_1 <= rho_3;
constraint rho_stl_1 <= rho_4;
constraint rho_stl_1 <= rho_5;
constraint rho_stl_1 <= rho_6;
constraint rho_stl_1 >= rho_1 - (1 - p_7[1]) * M;
constraint rho_stl_1 <= rho_1 + (1 - p_7[1]) * M;
constraint rho_stl_1 >= rho_2 - (1 - p_7[2]) * M;
constraint rho_stl_1 <= rho_2 + (1 - p_7[2]) * M;
constraint rho_stl_1 >= rho_3 - (1 - p_7[3]) * M;
constraint rho_stl_1 <= rho_3 + (1 - p_7[3]) * M;
constraint rho_stl_1 >= rho_4 - (1 - p_7[4]) * M;
constraint rho_stl_1 <= rho_4 + (1 - p_7[4]) * M;
constraint rho_stl_1 >= rho_5 - (1 - p_7[5]) * M;
constraint rho_stl_1 <= rho_5 + (1 - p_7[5]) * M;
constraint rho_stl_1 >= rho_6 - (1 - p_7[6]) * M;
constraint rho_stl_1 <= rho_6 + (1 - p_7[6]) * M;
% end of Globally encoding
% END OF STL ENCODING FOR rho_stl_1
% BEGIN OF STL ENCODING FOR rho_stl_1_w
var int: delta_1;
var int: rho_stl_1_w;
var int: rho_8;
constraint delta_1 >= 0;
constraint delta_1 <= 5;
constraint rho_8 <= ((sig_var_battery[1])-(10))+(delta_1);
constraint rho_8 >= ((sig_var_battery[1])-(10))+(delta_1);
var int: rho_9;
constraint delta_1 >= 0;
constraint delta_1 <= 5;
constraint rho_9 <= ((sig_var_battery[2])-(10))+(delta_1);
constraint rho_9 >= ((sig_var_battery[2])-(10))+(delta_1);
var int: rho_10;
constraint delta_1 >= 0;
constraint delta_1 <= 5;
constraint rho_10 <= ((sig_var_battery[3])-(10))+(delta_1);
constraint rho_10 >= ((sig_var_battery[3])-(10))+(delta_1);
var int: rho_11;
constraint delta_1 >= 0;
constraint delta_1 <= 5;
constraint rho_11 <= ((sig_var_battery[4])-(10))+(delta_1);
constraint rho_11 >= ((sig_var_battery[4])-(10))+(delta_1);
var int: rho_12;
constraint delta_1 >= 0;
constraint delta_1 <= 5;
constraint rho_12 <= ((sig_var_battery[5])-(10))+(delta_1);
constraint rho_12 >= ((sig_var_battery[5])-(10))+(delta_1);
var int: rho_13;
constraint delta_1 >= 0;
constraint delta_1 <= 5;
constraint rho_13 <= ((sig_var_battery[6])-(10))+(delta_1);
constraint rho_13 >= ((sig_var_battery[6])-(10))+(delta_1);
% Globally encoding
array[1..6] of var 0..1: p_14;
constraint sum(p_14) = 1;
constraint rho_stl_1_w <= rho_8;
constraint rho_stl_1_w <= rho_9;
constraint rho_stl_1_w <= rho_10;
constraint rho_stl_1_w <= rho_11;
constraint rho_stl_1_w <= rho_12;
constraint rho_stl_1_w <= rho_13;
constraint rho_stl_1_w >= rho_8 - (1 - p_14[1]) * M;
constraint rho_stl_1_w <= rho_8 + (1 - p_14[1]) * M;
constraint rho_stl_1_w >= rho_9 - (1 - p_14[2]) * M;
constraint rho_stl_1_w <= rho_9 + (1 - p_14[2]) * M;
constraint rho_stl_1_w >= rho_10 - (1 - p_14[3]) * M;
constraint rho_stl_1_w <= rho_10 + (1 - p_14[3]) * M;
constraint rho_stl_1_w >= rho_11 - (1 - p_14[4]) * M;
constraint rho_stl_1_w <= rho_11 + (1 - p_14[4]) * M;
constraint rho_stl_1_w >= rho_12 - (1 - p_14[5]) * M;
constraint rho_stl_1_w <= rho_12 + (1 - p_14[5]) * M;
constraint rho_stl_1_w >= rho_13 - (1 - p_14[6]) * M;
constraint rho_stl_1_w <= rho_13 + (1 - p_14[6]) * M;
% end of Globally encoding
% END OF STL ENCODING FOR rho_stl_1_w
% BEGIN OF STL ENCODING FOR rho_stl_2
var int: rho_stl_2;
var int: rho_15;
constraint rho_15 <= (7)-(sig_var_battery[1]);
constraint rho_15 >= (7)-(sig_var_battery[1]);
var int: rho_16;
constraint rho_16 <= (7)-(sig_var_battery[2]);
constraint rho_16 >= (7)-(sig_var_battery[2]);
var int: rho_17;
constraint rho_17 <= (7)-(sig_var_battery[3]);
constraint rho_17 >= (7)-(sig_var_battery[3]);
var int: rho_18;
constraint rho_18 <= (7)-(sig_var_battery[4]);
constraint rho_18 >= (7)-(sig_var_battery[4]);
var int: rho_19;
constraint rho_19 <= (7)-(sig_var_battery[5]);
constraint rho_19 >= (7)-(sig_var_battery[5]);
var int: rho_20;
constraint rho_20 <= (7)-(sig_var_battery[6]);
constraint rho_20 >= (7)-(sig_var_battery[6]);
% Globally encoding
array[1..6] of var 0..1: p_21;
constraint sum(p_21) = 1;
constraint rho_stl_2 <= rho_15;
constraint rho_stl_2 <= rho_16;
constraint rho_stl_2 <= rho_17;
constraint rho_stl_2 <= rho_18;
constraint rho_stl_2 <= rho_19;
constraint rho_stl_2 <= rho_20;
constraint rho_stl_2 >= rho_15 - (1 - p_21[1]) * M;
constraint rho_stl_2 <= rho_15 + (1 - p_21[1]) * M;
constraint rho_stl_2 >= rho_16 - (1 - p_21[2]) * M;
constraint rho_stl_2 <= rho_16 + (1 - p_21[2]) * M;
constraint rho_stl_2 >= rho_17 - (1 - p_21[3]) * M;
constraint rho_stl_2 <= rho_17 + (1 - p_21[3]) * M;
constraint rho_stl_2 >= rho_18 - (1 - p_21[4]) * M;
constraint rho_stl_2 <= rho_18 + (1 - p_21[4]) * M;
constraint rho_stl_2 >= rho_19 - (1 - p_21[5]) * M;
constraint rho_stl_2 <= rho_19 + (1 - p_21[5]) * M;
constraint rho_stl_2 >= rho_20 - (1 - p_21[6]) * M;
constraint rho_stl_2 <= rho_20 + (1 - p_21[6]) * M;
% end of Globally encoding
% END OF STL ENCODING FOR rho_stl_2
% BEGIN OF STL ENCODING FOR rho_stl_2_w
var int: delta_2;
var int: rho_stl_2_w;
var int: rho_22;
constraint delta_2 >= 0;
constraint delta_2 <= 5;
constraint rho_22 <= ((7)-(sig_var_battery[1]))+(delta_2);
constraint rho_22 >= ((7)-(sig_var_battery[1]))+(delta_2);
var int: rho_23;
constraint delta_2 >= 0;
constraint delta_2 <= 5;
constraint rho_23 <= ((7)-(sig_var_battery[2]))+(delta_2);
constraint rho_23 >= ((7)-(sig_var_battery[2]))+(delta_2);
var int: rho_24;
constraint delta_2 >= 0;
constraint delta_2 <= 5;
constraint rho_24 <= ((7)-(sig_var_battery[3]))+(delta_2);
constraint rho_24 >= ((7)-(sig_var_battery[3]))+(delta_2);
var int: rho_25;
constraint delta_2 >= 0;
constraint delta_2 <= 5;
constraint rho_25 <= ((7)-(sig_var_battery[4]))+(delta_2);
constraint rho_25 >= ((7)-(sig_var_battery[4]))+(delta_2);
var int: rho_26;
constraint delta_2 >= 0;
constraint delta_2 <= 5;
constraint rho_26 <= ((7)-(sig_var_battery[5]))+(delta_2);
constraint rho_26 >= ((7)-(sig_var_battery[5]))+(delta_2);
var int: rho_27;
constraint delta_2 >= 0;
constraint delta_2 <= 5;
constraint rho_27 <= ((7)-(sig_var_battery[6]))+(delta_2);
constraint rho_27 >= ((7)-(sig_var_battery[6]))+(delta_2);
% Globally encoding
array[1..6] of var 0..1: p_28;
constraint sum(p_28) = 1;
constraint rho_stl_2_w <= rho_22;
constraint rho_stl_2_w <= rho_23;
constraint rho_stl_2_w <= rho_24;
constraint rho_stl_2_w <= rho_25;
constraint rho_stl_2_w <= rho_26;
constraint rho_stl_2_w <= rho_27;
constraint rho_stl_2_w >= rho_22 - (1 - p_28[1]) * M;
constraint rho_stl_2_w <= rho_22 + (1 - p_28[1]) * M;
constraint rho_stl_2_w >= rho_23 - (1 - p_28[2]) * M;
constraint rho_stl_2_w <= rho_23 + (1 - p_28[2]) * M;
constraint rho_stl_2_w >= rho_24 - (1 - p_28[3]) * M;
constraint rho_stl_2_w <= rho_24 + (1 - p_28[3]) * M;
constraint rho_stl_2_w >= rho_25 - (1 - p_28[4]) * M;
constraint rho_stl_2_w <= rho_25 + (1 - p_28[4]) * M;
constraint rho_stl_2_w >= rho_26 - (1 - p_28[5]) * M;
constraint rho_stl_2_w <= rho_26 + (1 - p_28[5]) * M;
constraint rho_stl_2_w >= rho_27 - (1 - p_28[6]) * M;
constraint rho_stl_2_w <= rho_27 + (1 - p_28[6]) * M;
% end of Globally encoding
% END OF STL ENCODING FOR rho_stl_2_w
constraint rho_stl_1_w >= 0;
constraint rho_stl_2_w >= 0;
solve minimize (rho_stl_1_w - rho_stl_1)+(rho_stl_2_w - rho_stl_2);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% additional constraints
%%%%%%%%%%%%%%%%%%%%%%%%%555
% constraint rho_stl_2 >= 0;