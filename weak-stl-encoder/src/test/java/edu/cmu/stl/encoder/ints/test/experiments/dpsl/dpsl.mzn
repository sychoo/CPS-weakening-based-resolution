% DPSL example

%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%% CONSTANTS %%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%

% signal params
int: signal_length = 11;

int: energy_per_second = 1;
int: energy_per_block = 1;

int: dest_x = 0; % 30
int: dest_y = 0; %

%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%% CURRENT STATES %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%% OBTAINED FROM SYSTEM %%%%%%%%%%%%%%

% current battery
% constraint sig_var_battery[1] = 8;
constraint sig_var_battery[1] = 8;

% current location
constraint sig_var_x[1] = 1;
constraint sig_var_y[1] = 2; 

% current is landed
constraint sig_var_is_landed[1] = 0;

%%%%%%%%%%%%% OBTAINED FROM CALCULATION %%%%%%%%%%%%%%

% current distance to hospital
constraint sig_var_distance_to_hospital[1] = abs(sig_var_x[1] - dest_x) + abs(sig_var_y[1] - dest_y);

% current estimated range
constraint sig_var_estimated_range[1] = sig_var_battery[1] div (energy_per_block + energy_per_second);

% current remaining range upon delivery
constraint sig_var_remaining_range_upon_delivery[1] = sig_var_estimated_range[1] - sig_var_distance_to_hospital[1];


%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%% DECISION ARRAYS %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%

% actions
array[1..signal_length - 1] of var 0..5: actionList;
% 0. nop (hovering, - energy per second)
% 1. right
% 2. left
% 3. up
% 4. down
% 5. land

% signals
array[1..signal_length] of var 0..100: sig_var_battery;
array[1..signal_length] of var 0..29 : sig_var_x;
array[1..signal_length] of var 0..11 : sig_var_y;
array[1..signal_length] of var 0..42 : sig_var_distance_to_hospital;
array[1..signal_length] of var 0..42 : sig_var_estimated_range;
array[1..signal_length] of var 0..42 : sig_var_remaining_range_upon_delivery;
array[1..signal_length] of var 0..1  : sig_var_is_landed;



% battery model, relate pre-state to post-state with the action
predicate battery_model(var int: action, var int: pre_state, var int: post_state) =
    % move
    if action = 1 \/ action = 2 \/ action = 3 \/ action = 4 then (
        post_state = pre_state - energy_per_block - energy_per_second
    )
    % nop
    % land
    else (
        post_state = pre_state
    )
    endif
    ;

predicate x_model(var int: action, var int: pre_state, var int: post_state) =
    % 1. right
    if action = 1 then (
        post_state = pre_state + 1
    )

    % 2. left
    elseif action = 2 then (
        post_state = pre_state - 1
    )

    % 3. up
    % 4. down

    % 0. nop
    % 5. land
    else (
        post_state = pre_state
    )
    endif
    ;

predicate y_model(var int: action, var int: pre_state, var int: post_state) =
    % 3. up
    if action = 3 then (
        post_state = pre_state - 1
    )

    % 4. down
    elseif action = 4 then (
        post_state = pre_state + 1
    )

    % 1. right
    % 2. left

    % 0. nop
    % 5. land
    else (
        post_state = pre_state
    )
    endif
    ;

predicate distance_to_hospital_model(var int: x, var int: y, var int: post_state) =
    post_state = abs(x - dest_x) + abs(y - dest_y)
    ;

predicate estimated_range_model(var int: battery, var int: post_state) =
    post_state = battery div (energy_per_block + energy_per_second)
    ;

predicate remaining_range_upon_delivery_model(var int: estimated_range, var int: distance_to_hospital, var int: post_state) =
    post_state = estimated_range - distance_to_hospital
    ;

predicate is_landed_model(var int: idx, var int: action, var int: pre_state, var int: post_state) =
    % after landing, landing action can't occur again
    if action = 5 then (
        forall(i in idx + 1..signal_length - 1) (
            actionList[i] != 5 
        )
        /\
        % after landing, the is_landed signal var must remain true
        forall(i in idx + 1..signal_length) (
            % post_state = 1
            sig_var_is_landed[i] = 1
        )

        % landed -> has reached the destination
        /\
        sig_var_x[idx + 1] = dest_x
        /\
        sig_var_y[idx + 1] = dest_y

    )

    % state remains the same
    else (
        post_state = pre_state
    )
    endif
    ;


% propagate env model for signal
constraint
    forall (idx in 1..signal_length - 1) (
        battery_model(actionList[idx], sig_var_battery[idx], sig_var_battery[idx + 1])
        /\
        x_model(actionList[idx], sig_var_x[idx], sig_var_x[idx + 1])
        /\
        y_model(actionList[idx], sig_var_y[idx], sig_var_y[idx + 1])
        /\
        distance_to_hospital_model(sig_var_x[idx + 1], sig_var_y[idx + 1], sig_var_distance_to_hospital[idx + 1])
        /\
        estimated_range_model(sig_var_battery[idx + 1], sig_var_estimated_range[idx + 1])
        /\
        remaining_range_upon_delivery_model(sig_var_estimated_range[idx + 1], sig_var_distance_to_hospital[idx + 1], sig_var_remaining_range_upon_delivery[idx + 1])
        /\
        is_landed_model(idx, actionList[idx], sig_var_is_landed[idx], sig_var_is_landed[idx + 1])
        /\
        % nop can't happen before landing
        if sig_var_is_landed[idx] != 1 then (
            actionList[idx] != 0
        )
        endif
    );

% time horizon 必须 land
% constraint exists (idx in 1..signal_length - 1) (
%     actionList[idx] = 5
% );

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% G[0,1](remaining_range_upon_delivery >= 0)
% G[0,1]((<<battery <= 10>>(delta_battery, 8)) -> (F[0,1](is_landed = 1)))
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% BEGIN OF STL ENCODING
int: M = 1000;
% BEGIN OF STL ENCODING FOR rho_stl_1
var int: rho_stl_1;
var int: rho_1;
constraint rho_1 <= (sig_var_remaining_range_upon_delivery[1])-(0);
constraint rho_1 >= (sig_var_remaining_range_upon_delivery[1])-(0);
var int: rho_2;
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
var int: rho_stl_2;
var int: rho_5;
constraint rho_5 <= (sig_var_battery[1])-(10);
constraint rho_5 >= (sig_var_battery[1])-(10);
var int: rho_6;
var int: rho_8;
constraint rho_8 <= (sig_var_is_landed[1])-(1);
constraint rho_8 >= (sig_var_is_landed[1])-(1);
var int: rho_9;
constraint rho_9 <= (1)-(sig_var_is_landed[1]);
constraint rho_9 >= (1)-(sig_var_is_landed[1]);
var int: rho_7;
array[1..2] of var 0..1: p_10;
constraint sum(p_10) = 1;
constraint rho_7 <= rho_8;
constraint rho_7 <= rho_9;
constraint rho_7 >= rho_8 - (1 - p_10[1]) * M;
constraint rho_7 <= rho_8 + (1 - p_10[1]) * M;
constraint rho_7 >= rho_9 - (1 - p_10[2]) * M;
constraint rho_7 <= rho_9 + (1 - p_10[2]) * M;
var int: rho_12;
constraint rho_12 <= (sig_var_is_landed[2])-(1);
constraint rho_12 >= (sig_var_is_landed[2])-(1);
var int: rho_13;
constraint rho_13 <= (1)-(sig_var_is_landed[2]);
constraint rho_13 >= (1)-(sig_var_is_landed[2]);
var int: rho_11;
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
var int: rho_4;
array[1..2] of var 0..1: p_16;
constraint sum(p_16) = 1;
constraint rho_4 >= rho_5;
constraint rho_4 >= rho_6;
constraint rho_4 >= rho_5 - (1 - p_16[1]) * M;
constraint rho_4 <= rho_5 + (1 - p_16[1]) * M;
constraint rho_4 >= rho_6 - (1 - p_16[2]) * M;
constraint rho_4 <= rho_6 + (1 - p_16[2]) * M;
var int: rho_18;
constraint rho_18 <= (sig_var_battery[2])-(10);
constraint rho_18 >= (sig_var_battery[2])-(10);
var int: rho_19;
var int: rho_21;
constraint rho_21 <= (sig_var_is_landed[2])-(1);
constraint rho_21 >= (sig_var_is_landed[2])-(1);
var int: rho_22;
constraint rho_22 <= (1)-(sig_var_is_landed[2]);
constraint rho_22 >= (1)-(sig_var_is_landed[2]);
var int: rho_20;
array[1..2] of var 0..1: p_23;
constraint sum(p_23) = 1;
constraint rho_20 <= rho_21;
constraint rho_20 <= rho_22;
constraint rho_20 >= rho_21 - (1 - p_23[1]) * M;
constraint rho_20 <= rho_21 + (1 - p_23[1]) * M;
constraint rho_20 >= rho_22 - (1 - p_23[2]) * M;
constraint rho_20 <= rho_22 + (1 - p_23[2]) * M;
var int: rho_25;
constraint rho_25 <= (sig_var_is_landed[3])-(1);
constraint rho_25 >= (sig_var_is_landed[3])-(1);
var int: rho_26;
constraint rho_26 <= (1)-(sig_var_is_landed[3]);
constraint rho_26 >= (1)-(sig_var_is_landed[3]);
var int: rho_24;
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
var int: rho_17;
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
var int: delta_battery;
var int: rho_stl_2_w;
var int: rho_32;
constraint delta_battery >= 0;
constraint delta_battery <= 8;
constraint rho_32 <= ((sig_var_battery[1])-(10))+(delta_battery);
constraint rho_32 >= ((sig_var_battery[1])-(10))+(delta_battery);
var int: rho_33;
var int: rho_35;
constraint rho_35 <= (sig_var_is_landed[1])-(1);
constraint rho_35 >= (sig_var_is_landed[1])-(1);
var int: rho_36;
constraint rho_36 <= (1)-(sig_var_is_landed[1]);
constraint rho_36 >= (1)-(sig_var_is_landed[1]);
var int: rho_34;
array[1..2] of var 0..1: p_37;
constraint sum(p_37) = 1;
constraint rho_34 <= rho_35;
constraint rho_34 <= rho_36;
constraint rho_34 >= rho_35 - (1 - p_37[1]) * M;
constraint rho_34 <= rho_35 + (1 - p_37[1]) * M;
constraint rho_34 >= rho_36 - (1 - p_37[2]) * M;
constraint rho_34 <= rho_36 + (1 - p_37[2]) * M;
var int: rho_39;
constraint rho_39 <= (sig_var_is_landed[2])-(1);
constraint rho_39 >= (sig_var_is_landed[2])-(1);
var int: rho_40;
constraint rho_40 <= (1)-(sig_var_is_landed[2]);
constraint rho_40 >= (1)-(sig_var_is_landed[2]);
var int: rho_38;
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
var int: rho_31;
array[1..2] of var 0..1: p_43;
constraint sum(p_43) = 1;
constraint rho_31 >= rho_32;
constraint rho_31 >= rho_33;
constraint rho_31 >= rho_32 - (1 - p_43[1]) * M;
constraint rho_31 <= rho_32 + (1 - p_43[1]) * M;
constraint rho_31 >= rho_33 - (1 - p_43[2]) * M;
constraint rho_31 <= rho_33 + (1 - p_43[2]) * M;
var int: rho_45;
constraint delta_battery >= 0;
constraint delta_battery <= 8;
constraint rho_45 <= ((sig_var_battery[2])-(10))+(delta_battery);
constraint rho_45 >= ((sig_var_battery[2])-(10))+(delta_battery);
var int: rho_46;
var int: rho_48;
constraint rho_48 <= (sig_var_is_landed[2])-(1);
constraint rho_48 >= (sig_var_is_landed[2])-(1);
var int: rho_49;
constraint rho_49 <= (1)-(sig_var_is_landed[2]);
constraint rho_49 >= (1)-(sig_var_is_landed[2]);
var int: rho_47;
array[1..2] of var 0..1: p_50;
constraint sum(p_50) = 1;
constraint rho_47 <= rho_48;
constraint rho_47 <= rho_49;
constraint rho_47 >= rho_48 - (1 - p_50[1]) * M;
constraint rho_47 <= rho_48 + (1 - p_50[1]) * M;
constraint rho_47 >= rho_49 - (1 - p_50[2]) * M;
constraint rho_47 <= rho_49 + (1 - p_50[2]) * M;
var int: rho_52;
constraint rho_52 <= (sig_var_is_landed[3])-(1);
constraint rho_52 >= (sig_var_is_landed[3])-(1);
var int: rho_53;
constraint rho_53 <= (1)-(sig_var_is_landed[3]);
constraint rho_53 >= (1)-(sig_var_is_landed[3]);
var int: rho_51;
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
var int: rho_44;
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
constraint rho_stl_2_w >= 0;
solve minimize (rho_stl_2_w - rho_stl_2);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% additional constraints
%%%%%%%%%%%%%%%%%%%%%%%%%555
constraint rho_stl_1 >= 0;
