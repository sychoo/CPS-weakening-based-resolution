% Tue 01 Mar 2022 04:07:53 PM EST
% Simon Chu

% expected robustness (starting at initial state)
% x > 1 -> 0
% y > 1 -> -1
% z > 1 -> -4
% min of the two -1

int: sig_length = 6; % the length of the signal

array[1..sig_length] of int: var_y = 
        ([ 1, 2, 3, 4, 5, -10]);

array[1..sig_length] of int: var_x = 
        ([0, 2, 4, 6, 0, 2]);

array[1..sig_length] of int: var_z = 
        ([3, 6, 9, 12, 15, 18]);


int: M = 1000;
int: idx = 1; % set initial index as 1
var int: rho;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%% Encode the first pair of conjunction (x > 1, y > 1), result in rho_conj_1 %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

var int: rho_conj_1;
array[1..2] of var 0..1: p_conj_1; % right bound depends on how many atomic propositions are in the logical statement
                                    % in this case, it is a pair


% Condition (1): sum of p is 1 (one minimum value only)
constraint
   sum(p_conj_1) = 1;

% when using multiple variables in the same logical statement, have to list all variables without syntactic sugar like for all
% when having composed expressions like G[0, 5](x > 1 /\ y > 1), you might need multiple p equivalences to calculate the min/max 
% both for the conjunction and the globally expression across the time space

% Condition (2) robustness must be the minimum of all
constraint rho_conj_1 <= var_x[idx] - 1; % x - 1;
constraint rho_conj_1 <= var_y[idx] - 1; % y - 1;

% Condition (3) same how involve big M and P decision array
% find the minimum of the two
constraint rho_conj_1 >= (var_x[idx] - 1) - (1 - p_conj_1[1]) * M;
constraint rho_conj_1 >= (var_y[idx] - 1) - (1 - p_conj_1[2]) * M;

constraint  rho_conj_1 <= (var_x[idx] - 1) + (1 - p_conj_1[1]) * M;
constraint  rho_conj_1 <= (var_y[idx] - 1) + (1 - p_conj_1[2]) * M;



%%%%%%%%%%%%%%%%%%%%%%%%$%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%% Encode the second pair of conjunction (x > 1 /\ y > 1) /\ z > 1, result in rho_conj_2 %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%$%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

var int: rho_conj_2;
array[1..2] of var 0..1: p_conj_2;  % right bound depends on how many atomic propositions are in the logical statement
                                    % in this case, it is a pair


% Condition (1): sum of p is 1 (one minimum value only)
constraint
   sum(p_conj_2) = 1;

% when using multiple variables in the same logical statement, have to list all variables without syntactic sugar like for all
% when having composed expressions like G[0, 5](x > 1 /\ y > 1), you might need multiple p equivalences to calculate the min/max 
% both for the conjunction and the globally expression across the time space

% Condition (2) robustness must be the minimum of all
constraint rho_conj_2 <= rho_conj_1;
constraint rho_conj_2 <= var_z[idx] - 1; % y - 1;

% Condition (3) same how involve big M and P decision array
% find the minimum of the two
constraint rho_conj_1 - (1 - p_conj_2[1]) * M <= rho_conj_2;
constraint (var_z[idx] - 1) - (1 - p_conj_2[2]) * M <= rho_conj_2;

constraint  rho_conj_2 <= rho_conj_1 + (1 - p_conj_2[1]) * M;
constraint  rho_conj_2 <= (var_z[idx] - 1) + (1 - p_conj_2[2]) * M;

constraint rho = rho_conj_2;