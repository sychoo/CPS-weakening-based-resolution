% 2022-03-21 19:05:55

int: time_horizon = 13;
int: x_dest = 5;
int: y_dest = 5;

%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%% DECISION ARRAY %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%

array[1..time_horizon] of var int: signal_var_x;
array[1..time_horizon] of var int: signal_var_y;
array[1..time_horizon] of var int: signal_var_battery;

%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%% INITIAL STATE %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%

% position = (0,0)
constraint signal_var_x[1] = 0;
constraint signal_var_y[1] = 0;

% battery = 30
constraint signal_var_battery[1] = 30;

%%%%%%%%%%%%%%%%%%%%%%%
%%%%% FINAL STATE %%%%%
%%%%%%%%%%%%%%%%%%%%%%%

% position = (5,5)
constraint signal_var_x[time_horizon] = 5;
constraint signal_var_y[time_horizon] = 5;


%%%%%%%%%%%%%%%%%
%%%%% RANGE %%%%%
%%%%%%%%%%%%%%%%%

constraint
    forall (idx in 1..time_horizon) (
        signal_var_x[idx] >= 0   /\
        signal_var_x[idx] <= 30  /\

        signal_var_y[idx] >= 0   /\
        signal_var_y[idx] <= 12  /\

        signal_var_battery[idx] >= 0 /\
        signal_var_battery[idx] <= 100
    );

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%% TRANSITION FUNCTIONS %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% drone will either go UP, DOWN, LEFT, RIGHT
predicate pos_model(var int: x_pre, var int: y_pre, var int: x_post, var int: y_post) =
    % Note that this is not a cartisian plane
    % DOWN
    x_post = x_pre /\
    y_post = y_pre + 1

    \/
    % UP
    x_post = x_pre /\
    y_post = y_pre - 1

    \/
    % LEFT
    x_post = x_pre - 1 /\
    y_post = y_pre

    \/
    % RIGHT
    x_post = x_pre + 1 /\
    y_post = y_pre

    \/
    % No Movement
    x_post = x_pre /\
    y_post = y_pre    
    ;


% battery goes down with time and distance
predicate battery_model(var int: battery_pre, var int: x_pre, var int: y_pre,
                        var int: battery_post, var int: x_post, var int: y_post) =
    if (x_pre = x_dest /\ y_pre = y_dest) 
        then (battery_post = battery_pre)
    else battery_post = battery_pre - 1
    endif;
    % % when drone did not move
    % ((x_post = x_pre /\ y_post = y_pre) /\
    %     (battery_post = battery_pre - 1))

    % \/
    % % when drone moved
    % (((x_post != x_pre \/ y_post != y_pre) /\
    % (x_pre != x_dest /\ y_pre != y_dest))
    %     /\
    %     (battery_post = battery_pre - 2))
    
    % \/
    % % when drone reached destination
    % % ((x_pre = x_dest /\ y_pre = y_dest) /\
    %     (battery_post = battery_pre)
    % ;

% propagate env model transition functions, link pre-state to post-state
constraint
    forall (idx in 1..time_horizon - 1) (
        pos_model(signal_var_x[idx], signal_var_y[idx], signal_var_x[idx + 1], signal_var_y[idx + 1]) /\
        battery_model(  signal_var_battery[idx], signal_var_x[idx], signal_var_y[idx],
                        signal_var_battery[idx + 1], signal_var_x[idx + 1], signal_var_y[idx + 1])
    );

solve maximize signal_var_battery[time_horizon]

% solve maximize signal_var_battery[time_horizon]

% the MiniZinc solver will automatically choose one for 
% non-deterministic choices

% unless otherwise specified maximize or minizinc clause