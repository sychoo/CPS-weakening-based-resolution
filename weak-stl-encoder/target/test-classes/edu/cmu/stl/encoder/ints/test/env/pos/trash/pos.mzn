
int: time_horizon = 2;

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
%%%%% DESTINATION %%%%%
%%%%%%%%%%%%%%%%%%%%%%%

% position = (5,5)
% constraint signal_var_x[time_horizon] = 5;
% constraint signal_var_y[time_horizon] = 5;
int: x_dest = 5;
int: y_dest = 5;


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

function var float: distance(var int : x1, var int : y1, var int : x2, var int: y2) =
    sqrt((x2 - x1) ^ 2 + (y2 - y1) ^ 2);


% calculate difference of distance to destiny 
function var float: distanceDecre(var int : x_curr, var int : y_curr, var int : x_next, var int: y_next) =
    distance(x_curr, y_curr, x_dest, y_dest) - distance(x_next, y_next, x_dest, y_dest);

% right, left, up, down
% [[....]
%  [....]
%  ...
%  ]
% array[1..time_horizon - 1, 1..4] of var float: distDecre; % decrement of distance to destiny on each action
array[1..time_horizon - 1, 1..4] of var float: distDecre; % decrement of distance to destiny on each action

% array[1..time_horizon - 1] of var 1..4: maxIdx; % keep track of the minimum index 

% array[1..time_horizon - 1] of var 1..4: actionArray; % keep track of the minimum index 
% var 1..4: action;

% constraint action = 1;

% return an action for the next step
predicate pos_model2(var int: x_pre, var int: y_pre, var int: x_post, var int: y_post, var int: idx) =
    % decide what action to take
    distDecre[idx, 1] = distanceDecre(x_pre, y_pre, x_pre + 1, y_pre) /\
    distDecre[idx, 2] = distanceDecre(x_pre, y_pre, x_pre - 1, y_pre) /\
    distDecre[idx, 3] = distanceDecre(x_pre, y_pre, x_pre, y_pre - 1) /\
    distDecre[idx, 4] = distanceDecre(x_pre, y_pre, x_pre, y_pre + 1)
    ;
    % action = 1 -> (
    % x_post = x_pre + 1 /\
    % y_post = y_pre + 1);

% drone will either go UP, DOWN, LEFT, RIGHT
% given the previous state, DETERMINISTICALLY decide next step
predicate pos_model(var int: x_pre, var int: y_pre, var int: x_post, var int: y_post, var int: idx) =
    % Note that this is not a cartisian plane
    % instead the origin is at the top-left of the window

    % calculate the new distance to hospital
    % float: dist = distance(x_pre, y_pre, x_dest, y_dest) /\
    % distDecre[idx, 1] = distance(x_pre, y_pre, x_dest, y_dest) - distance(x_pre + 1, y_pre, x_dest, y_dest) /\
    % distDecre[idx, 2] = distance(x_pre, y_pre, x_dest, y_dest) - distance(x_pre - 1, y_pre, x_dest, y_dest) /\
    % distDecre[idx, 3] = distance(x_pre, y_pre, x_dest, y_dest) - distance(x_pre, y_pre - 1, x_dest, y_dest) /\
    % distDecre[idx, 4] = distance(x_pre, y_pre, x_dest, y_dest) - distance(x_pre, y_pre + 1, x_dest, y_dest) /\

% maxIdx[idx] = max([
%     distance(x_pre, y_pre, x_dest, y_dest) - distance(x_pre + 1, y_pre, x_dest, y_dest),
%     distance(x_pre, y_pre, x_dest, y_dest) - distance(x_pre - 1, y_pre, x_dest, y_dest),
%     distance(x_pre, y_pre, x_dest, y_dest) - distance(x_pre, y_pre - 1, x_dest, y_dest),
%     distance(x_pre, y_pre, x_dest, y_dest) - distance(x_pre, y_pre + 1, x_dest, y_dest)
% ]) /\

    % array comprehension
    % maxIdx[idx] = 1 /\
    % max([ distDecre[idx, i] | i in 1..4 ]) /\ % arg_min choose the least idx to break ties
    % minIdx[idx] = let { constraint length([ distDecre[idx, i] | i in 1..4 ]) > 0 } in arg_max_total([ distDecre[idx, i] | i in 1..4 ])
(
    % decide what final action to choose based on the distance decrement
    % maxIdx[idx] = 1 ->
    (
        % RIGHT
        
        x_post = x_pre + 1 /\
        y_post = y_pre
    )
    
    /\
% 
    % maxIdx[idx] = 2 ->
    (
        % LEFT
        x_post = x_pre - 1 /\
        y_post = y_pre
    )
    
    /\

    % maxIdx[idx] = 3 ->
    (
        % UP
        x_post = x_pre /\
        y_post = y_pre - 1
    )

    /\

    % maxIdx[idx] = 4 ->
    (
        % DOWN
        x_post = x_pre /\
        y_post = y_pre + 1
    )
);
    % % decide what final action to choose based on the distance decrement
    % if minIdx[idx] = 1 then
    % (
    %     % RIGHT
    %     x_post = x_pre + 1 /\
    %     y_post = y_pre
    % )
    
    % else if minIdx[idx] = 2 then
    % (
    %     % LEFT
    %     x_post = x_pre - 1 /\
    %     y_post = y_pre
    % )

    % else if minIdx[idx] = 3 then
    % (
    %     % UP
    %     x_post = x_pre /\
    %     y_post = y_pre - 1
    % )

    % else 
    % (
    %     % DOWN
    %     x_post = x_pre /\
    %     y_post = y_pre + 1
    % );
    % ;    



% propagate env model transition functions, link pre-state to post-state
constraint
    forall (idx in 1..time_horizon - 1) (
        pos_model2(signal_var_x[idx], signal_var_y[idx], signal_var_x[idx + 1], signal_var_y[idx + 1], idx)
    );


% constraint
%     forall 
% solve maximize signal_var_battery[time_horizon]

% the MiniZinc solver will automatically choose one for 
% non-deterministic choices

% unless otherwise specified maximize or minizinc clause
% 