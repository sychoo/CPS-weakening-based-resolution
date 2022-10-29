% encoding sample STL formula and calculate robustness w.r.t. signal

int: sig_length = 6; % the length of the signal

array[1..sig_length] of int: var_x = 
        ([ 1, 2, 3, 4, 5, -10]);

% x > 1
% meaning evaluate the robustness of x > 1 at intial state (t = 0)
% encoding of STL formula (AP)


int: M = 1000;

% signal [0-5] [1, 2, 3, 4, 5, 6]
% encoding for conjunction (also find the time index with the minimum robustness for atomic proposition)

array[1..1] of var 0..1: p; % array of boolean flags indicating the minimum elements

var int: rho; % robustness of the entire conjunction

% G[0, 5](x > 1)

% constraint var_x[1] - 1 = rho;

% constraint sum(p) = 1;
% constraint rho <= var_x[1] - 1; % x - 1
% constraint (var_x[1] - 1) - (1 - p[1]) * M <= rho;
% constraint rho <= (var_x[1] - 1) + (1 - p[1]) * M

constraint
   sum(p) = 1;
   
constraint
  forall (x_idx in 1..1) (
    rho <= var_x[x_idx] - 1 % x - 1
  )
  ;
 constraint
   forall (x_idx in 1..1) (
     (var_x[x_idx] - 1) - (1 - p[x_idx]) * M <= rho 
   )
   ;
  constraint
   forall (x_idx in 1..1) (
     rho <= (var_x[x_idx] - 1) + (1 - p[x_idx]) * M
   )
   ;