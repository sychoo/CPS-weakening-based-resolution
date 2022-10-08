% Tue 01 Mar 2022 04:07:53 PM EST
% Simon Chu

% Idea: at each LogicExpr, also calculate along the time horizon
% Big Idea:
% G[0, 5](x > 1 /\ y > 1 /\ z > 1)
%
% first calculate the robustness of G[0, 5](x > 1 /\ y > 1)
%
% get robustness result rho_conj_1
%
% then calculate the robustness of 
%
% rho(G[0, 5](x > 1 /\ y > 1)) /\ G[0, 5](z > 1)
% AKA
% rho_conj_1 /\ G[0, 5](z > 1)
% which is rho_conj_2


% expected robustness (starting at initial state)
% x > 1 -> -11
% y > 1 -> -1
% z > 1 -> -19
% min of the two -1

int: sig_length = 6; % the length of the signal

array[1..sig_length] of int: var_x = 
        ([ 1, 2, 3, 4, 5, -10]);

array[1..sig_length] of int: var_y = 
        ([0, 2, 4, 6, 0, 2]);

array[1..sig_length] of int: var_z = 
        ([3, 6, 9, 12, 15, -18]);


int: M = 1000;
int: upperTimeBound = 5;
int: lowerTimeBound = 0;

var int: rho; % global robustness

% idx is no longer 1
% int: idx = 1; % set initial index as 1

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%% Encode the first pair of conjunction (x > 1, y > 1), result in rho_conj_1 %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% expected robustness in stage 1: -11
var int: rho_conj_1;

array[1..upperTimeBound - lowerTimeBound + 1] of var int: rho_sub_1;

% array[1..2] of var 0..1: p_conj_1; % right bound depends on how many atomic propositions are in the logical statement
                                %     in this case, it is a pair
% length array = 6, length array[0] = 2

array[1..upperTimeBound - lowerTimeBound + 1, 1..2] of var 0..1: p_conj_1;



% Condition (1): sum of p is 1 (one minimum value only)
constraint
%    sum(p_conj_1) = 1;
        forall (idx in lowerTimeBound + 1..upperTimeBound + 1) (
                sum (logic_expr_idx in 1..2) (p_conj_1[idx, logic_expr_idx]) = 1
        )
        ;

% when using multiple variables in the same logical statement, have to list all variables without syntactic sugar like for all
% when having composed expressions like G[0, 5](x > 1 /\ y > 1), you might need multiple p equivalences to calculate the min/max 
% both for the conjunction and the globally expression across the time space

% Condition (2) robustness must be the minimum of all
constraint 
        forall (idx in lowerTimeBound + 1..upperTimeBound + 1) (
                rho_sub_1[idx] <= var_x[idx] - 1 /\
                rho_sub_1[idx] <= var_y[idx] - 1
        )
        ;

% Condition (3) same how involve big M and P decision array
% find the minimum of the two
constraint
        forall (idx in lowerTimeBound + 1..upperTimeBound + 1) ( 
                rho_sub_1[idx] >= (var_x[idx] - 1) - (1 - p_conj_1[idx, 1]) * M /\
                rho_sub_1[idx] >= (var_y[idx] - 1) - (1 - p_conj_1[idx, 2]) * M /\
                rho_sub_1[idx] <= (var_x[idx] - 1) + (1 - p_conj_1[idx, 1]) * M   /\
                rho_sub_1[idx] <= (var_y[idx] - 1) + (1 - p_conj_1[idx, 2]) * M
        )
        ;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% calculate the overall robustness
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

array[lowerTimeBound + 1..upperTimeBound + 1] of var 0..1: p_sub_1; 

% condition (1)
constraint sum(p_sub_1) = 1;

% condition (2)
constraint 
        forall (idx in 1..upperTimeBound - lowerTimeBound + 1) (
                rho_conj_1 <= rho_sub_1[idx]
        )
        ;

% condition (3)
constraint
        forall (idx in 1..upperTimeBound - lowerTimeBound + 1) (
                rho_conj_1 >= rho_sub_1[idx] - (1 - p_sub_1[idx]) * M /\
                rho_conj_1 <= rho_sub_1[idx] + (1 - p_sub_1[idx]) * M
        )
;

%%%%%%%%%%%%%%%%%%%%%%%%$%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%% Encode the second pair of conjunction (x > 1 /\ y > 1) /\ z > 1, result in rho_conj_2 %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%$%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% expected 2nd stage result: -19

var int: rho_conj_2;
% array[1..2] of var 0..1: p_conj_1; % right bound depends on how many atomic propositions are in the logical statement
                                %     in this case, it is a pair
% length array = 6, length array[0] = 2
array[1..upperTimeBound - lowerTimeBound + 1, 1..2] of var 0..1: p_conj_2;

array[1..upperTimeBound - lowerTimeBound + 1] of var int: rho_sub_2;


% Condition (1): sum of p is 1 (one minimum value only)
constraint
%    sum(p_conj_1) = 1;rho_conj_1 /\ z
        forall (idx in lowerTimeBound + 1..upperTimeBound + 1) (
                sum (logic_expr_idx in 1..2) (p_conj_2[idx, logic_expr_idx]) = 1
        )
        ;

% when using multiple variables in the same logical statement, have to list all variables without syntactic sugar like for all
% when having composed expressions like G[0, 5](x > 1 /\ y > 1), you might need multiple p equivalences to calculate the min/max 
% both for the conjunction and the globally expression across the time space

% Condition (2) robustness must be the minimum of all
constraint 
        forall (idx in lowerTimeBound + 1..upperTimeBound + 1) (
                rho_sub_2[idx] <= rho_conj_1 /\ %var_x[idx] - 1 /\
                rho_sub_2[idx] <= var_z[idx] - 1
        )
        ;

% Condition (3) same how involve big M and P decision array
% find the minimum of the two
constraint
        forall (idx in lowerTimeBound + 1..upperTimeBound + 1) ( 
                rho_sub_2[idx] >= rho_conj_1 - (1 - p_conj_2[idx, 1]) * M /\
                rho_sub_2[idx] >= (var_z[idx] - 1) - (1 - p_conj_2[idx, 2]) * M /\
                rho_sub_2[idx] <= rho_conj_1 + (1 - p_conj_2[idx, 1]) * M   /\
                rho_sub_2[idx] <= (var_z[idx] - 1) + (1 - p_conj_2[idx, 2]) * M
        )
        ;

% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% % calculate the overall robustness
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

array[lowerTimeBound + 1..upperTimeBound + 1] of var 0..1: p_sub_2; 

% condition (1)
constraint sum(p_sub_2) = 1;

% condition (2)
constraint 
        forall (idx in 1..upperTimeBound - lowerTimeBound + 1) (
                rho_conj_2 <= rho_sub_2[idx]
        )
        ;

% condition (3)
constraint
        forall (idx in 1..upperTimeBound - lowerTimeBound + 1) (
                rho_conj_2 >= rho_sub_2[idx] - (1 - p_sub_1[idx]) * M /\
                rho_conj_2 <= rho_sub_2[idx] + (1 - p_sub_1[idx]) * M
        )
        ;



%%%% automatic %%%%
constraint rho = rho_conj_2;
