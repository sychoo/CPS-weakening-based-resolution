
int: time_horizon = 6;
% 1 -> RIGHT
% 2 -> LEFT
% 3 -> UP
% 4 -> DOWN

% Note that there is a mismatch on the cartesian system
% in the environmental model
% |-------------> x
% |
% |
% \/ y
%
% in the simulator
% |-------------> col
% |
% |
% \/ row
%
% keep in mind

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
int: x_dest = 1;
int: y_dest = 4;


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

function var int: findMax(var float: n1, var float: n2) =
    if n1 >= n2 then 1 else 2
    endif;
    

function var int: findMaxIdx(var float: n1, var float: n2, var float: n3, var float: n4) =
    % n1 is the max
    if n1 >= n2 /\ n1 >= n3 /\ n1 >= n4 then 1
    elseif n2 >= n1 /\ n2 >= n3 /\ n2 >= n4 then 2
    elseif n3 >= n1 /\ n3 >= n2 /\ n3 >= n4 then 3
    else 4
    endif;

% 1 -> RIGHT
% 2 -> LEFT
% 3 -> UP
% 4 -> DOWN
array[1..time_horizon - 1] of var 1..4: actionArray; % keep track of the minimum index 


% return an action for the next step
predicate pos_model2(var int: x_pre, var int: y_pre, var int: x_post, var int: y_post, var int: idx) =
    % decide what action to take
    actionArray[idx] = findMaxIdx(
        distanceDecre(x_pre, y_pre, x_pre + 1, y_pre), % right
        distanceDecre(x_pre, y_pre, x_pre - 1, y_pre), % left
        distanceDecre(x_pre, y_pre, x_pre, y_pre - 1), % up
        distanceDecre(x_pre, y_pre, x_pre, y_pre + 1)  % down
    )
    /\
    if actionArray[idx] = 1
    then (
        % RIGHT
        x_post = x_pre + 1 /\
        y_post = y_pre
    )
    
    elseif actionArray[idx] = 2
    then (
        % LEFT
        x_post = x_pre - 1 /\
        y_post = y_pre
    )

    elseif actionArray[idx] = 3
    then (
        % UP
        x_post = x_pre /\
        y_post = y_pre - 1
    )

    else
    (
        % DOWN
        x_post = x_pre /\
        y_post = y_pre + 1
    )
    endif
    ;

% propagate env model transition functions, link pre-state to post-state
constraint
    forall (idx in 1..time_horizon - 1) (
        pos_model2(signal_var_x[idx], signal_var_y[idx], signal_var_x[idx + 1], signal_var_y[idx + 1], idx)
    );

