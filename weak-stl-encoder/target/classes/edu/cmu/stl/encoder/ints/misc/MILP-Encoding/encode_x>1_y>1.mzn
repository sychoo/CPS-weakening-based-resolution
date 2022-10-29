% Sat Feb 19 11:16:46 EST 2022
% Simon Chu

% expected robustness (starting at initial state)
% x > 1 -> 0
% y > 1 -> -1
% min of the two -1

int: sig_length = 6; % the length of the signal

array[1..sig_length] of int: var_x = 
        ([ 1, 2, 3, 4, 5, -10]);

array[1..sig_length] of int: var_y = 
        ([0, 2, 4, 6, 0, 2]);



int: M = 1000;

var int: rho;

int: idx = 1; % set initial index as 1

int: num_of_sig_vars = 2; % number of signal variabled used in the conjunction

array[1..num_of_sig_vars] of var 0..1: p_sig_var; 

% var_x : p[1]
% var_y : p[2]

% Condition (1): sum of p is 1 (one minimum value only)
constraint
   sum(p_sig_var) = 1;

% when using multiple variables in the same logical statement, have to list all variables without syntactic sugar like for all
% when having composed expressions like G[0, 5](x > 1 /\ y > 1), you might need multiple p equivalences to calculate the min/max 
% both for the conjunction and the globally expression across the time space

% Condition (2) robustness must be the minimum of all
constraint rho <= var_x[idx] - 1; % x - 1;
constraint rho <= var_y[idx] - 1; % y - 1;

% Condition (3) same how involve big M and P decision array
% find the minimum of the two
constraint (var_x[idx] - 1) - (1 - p_sig_var[1]) * M <= rho;
constraint (var_y[idx] - 1) - (1 - p_sig_var[2]) * M <= rho;

constraint  rho <= (var_x[idx] - 1) + (1 - p_sig_var[1]) * M;
constraint  rho <= (var_y[idx] - 1) + (1 - p_sig_var[2]) * M;
