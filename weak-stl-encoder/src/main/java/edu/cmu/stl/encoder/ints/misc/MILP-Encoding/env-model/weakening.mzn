% BEGIN OF SIGNAL ENCODING
int: signal_length = 6;
array[1..signal_length] of int: sig_var_x = ([-5, 3, 1, -1, -3, -5]);
array[1..signal_length] of int: sig_var_y = ([6, 4, 2, -2, -4, -6]);
array[1..signal_length] of int: sig_var_z = ([30, 20, 10, -10, -20, -30]);
% END OF SIGNAL ENCODING

% BEGIN OF STL ENCODING
int: M = 1000;
var int: rho;
var int: delta;
% constraint rho <= (sig_var_x[1])-(0);
constraint rho <= (sig_var_x[1])-(0) + delta;
% constraint rho >= (sig_var_x[1])-(0);
constraint rho >= (sig_var_x[1])-(0) + delta;

constraint delta >= 0; % = 0, no weakening, degree of weakening must be greater than 5
constraint rho > 0;
solve minimize delta;

% END OF STL ENCODING
