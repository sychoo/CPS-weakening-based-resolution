
int: time_horizon = 15;

%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%% DECISION ARRAY %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%

array[1..time_horizon] of var int: signal_var_x;
array[1..time_horizon] of var int: signal_var_y;


%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%% INITIAL STATE %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%

% position = (0,0)
constraint signal_var_x[1] = 0;
constraint signal_var_y[1] = 0;


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
        signal_var_y[idx] <= 12
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
    ;



% propagate env model transition functions, link pre-state to post-state
constraint
    forall (idx in 1..time_horizon - 1) (
        pos_model(signal_var_x[idx], signal_var_y[idx], signal_var_x[idx + 1], signal_var_y[idx + 1])
    );


% solve maximize signal_var_battery[time_horizon]

% the MiniZinc solver will automatically choose one for 
% non-deterministic choices

% unless otherwise specified maximize or minizinc clause