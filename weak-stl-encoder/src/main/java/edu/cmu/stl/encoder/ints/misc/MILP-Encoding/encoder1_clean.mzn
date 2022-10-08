% encoding sample STL formula and calculate robustness w.r.t. signal

int: sig_length = 6; % the length of the signal

array[1..sig_length] of int: var_x = 
        ([ 1, 2, 3, 4, 5, -10]);
% G[0, 10](x > 1) /\ (y > 1)

% encoding of STL formula
int: time_a = 0;
int: time_b = 5;

% encoding of signal

% state variable x
% array[1..sig_length] of int: var_x = 
%         ([ 0, 1, 3, 5, 0, 1, 3, 5, 0, 1, 3]);

% % state variable y
% array[1..sig_length] of int: var_y = 
%         ([0, 2, 4, 6, 0, 2, 4, 6, 0, 2, 4]);
        
% robustness encoding of (x > 1) /\ (y > 1) w.r.t. (x > 1) and (y > 1)

% use time as index
% calculate the robustness of individual APs (x > 1) and (y > 1) at the time index, respectively
% use p to keep track of the smallest element's time index

int: M = 1000;

% signal [0-5] [1, 2, 3, 4, 5, 6]
% encoding for conjunction (also find the time index with the minimum robustness for atomic proposition)

array[1..sig_length] of var 0..1: p; % array of boolean flags indicating the minimum elements

var int: rho; % robustness of the entire conjunction

% G[0, 5](x > 1)

constraint
   sum(p) = 1;
   
constraint
  forall (x_idx in 1..sig_length) (
    rho <= var_x[x_idx] - 1 % x - 1
  )
  ;
 constraint
   forall (x_idx in 1..sig_length) (
     (var_x[x_idx] - 1) - (1 - p[x_idx]) * M <= rho 
   )
   ;
  constraint
   forall (x_idx in 1..sig_length) (
     rho <= (var_x[x_idx] - 1) + (1 - p[x_idx]) * M
   )
   ;