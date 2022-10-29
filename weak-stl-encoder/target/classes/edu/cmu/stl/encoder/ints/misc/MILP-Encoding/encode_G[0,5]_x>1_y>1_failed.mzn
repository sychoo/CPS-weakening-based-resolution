int: sig_length = 6; % the length of the signal

array[1..sig_length] of int: var_x = 
        ([ 1, 2, 3, 4, 5, -10]);

array[1..sig_length] of int: var_y = 
        ([0, 2, 4, 6, 0, 2]);

% expected robustness
% x > 1 -> -11
% y > 1 -> -1
% min of the two -11

int: M = 1000;

var int: rho;

% int: idx = 1; % set initial index as 1

int: num_of_sig_vars = 2; % number of signal variabled used in the conjunction

% conjunction will be evaluated first before evaluation of min across the time horizon
% numerical suffix p_sig_var_(x), x stand for the time 
% in the future may be helpful to use two dimensional decision array to represent p for each pair of conjunctions
% 2d array 2: bool(int) x
%          6: array bool(int). length array = 6, length array[0] = 2;
array[1..sig_length, 1..num_of_sig_vars] of var 0..1: p_sig_var;

% equivalent to the following
% array[1..num_of_sig_vars] of var 0..1: p_sig_var_1; % for calculating min/max across different signal variables (sig_var)
% array[1..num_of_sig_vars] of var 0..1: p_sig_var_2;
% array[1..num_of_sig_vars] of var 0..1: p_sig_var_3;
% array[1..num_of_sig_vars] of var 0..1: p_sig_var_4;
% array[1..num_of_sig_vars] of var 0..1: p_sig_var_5;
% array[1..num_of_sig_vars] of var 0..1: p_sig_var_6;

array[1..sig_length] of var 0..1: p; % for calculating min/max across time

% var_x : p[1]
% var_y : p[2]

% Condition (1): sum of p is 1 (one minimum value only)
% note that this is a constraint across the time horizon
constraint
   sum(p) = 1;

int: sig_var_x_idx = 1;
int: sig_var_y_idx = 2;

% sig_var_idx
% x |-> 1
% y |-> 2
constraint
        forall (idx in 1..sig_length) (
                sum (sig_var_idx in 1..num_of_sig_vars) (p_sig_var[idx, sig_var_idx]) = 1
        )
        ;
% equivalent to the following
%    sum(p_sig_var_1) = 1;
%    sum(p_sig_var_2) = 1;
%    sum(p_sig_var_3) = 1;
%    sum(p_sig_var_4) = 1;
%    sum(p_sig_var_5) = 1;
%    sum(p_sig_var_6) = 1;

% when using multiple variables in the same logical statement, have to list all variables without syntactic sugar like for all
% when having composed expressions like G[0, 5](x > 1 /\ y > 1), you might need multiple p equivalences to calculate the min/max 
% both for the conjunction and the globally expression across the time space

% Condition (2) robustness must be the minimum of all
constraint 
  forall (idx in 1..sig_length) (
        rho <= var_x[idx] - 1 /\ % x - 1;
        rho <= var_y[idx] - 1 % y - 1;
  )
  ;

% Condition (3) same how involve big M and P decision array
% find the minimum of the time horizon
 constraint
   forall (idx in 1..sig_length) (
        (var_x[idx] - 1) - (1 - p_sig_var[idx, sig_var_x_idx]) * M <= rho /\
        (var_y[idx] - 1) - (1 - p_sig_var[idx, sig_var_y_idx]) * M <= rho /\
        (var_x[idx] - 1) - (1 - p[idx]) * M <= rho /\
        (var_y[idx] - 1) - (1 - p[idx]) * M <= rho
   )
   ;

constraint
        forall (idx in 1..sig_length) (
          rho <= (var_x[idx] - 1) + (1 - p_sig_var[idx, sig_var_x_idx]) * M /\
          rho <= (var_y[idx] - 1) + (1 - p_sig_var[idx, sig_var_y_idx]) * M /\
          rho <= (var_x[idx] - 1) + (1 - p[idx]) * M /\
          rho <= (var_y[idx] - 1) + (1 - p[idx]) * M
        )
        ;
