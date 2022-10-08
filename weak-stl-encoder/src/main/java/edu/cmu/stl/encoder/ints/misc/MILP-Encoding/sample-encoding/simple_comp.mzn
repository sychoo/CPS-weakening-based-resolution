% BEGIN OF SIGNAL ENCODING
int: signal_length = 2;
array[1..signal_length] of int: signal_var_x = ([7, 9]);
array[1..signal_length] of int: signal_var_y = ([8, 10]);
% END OF SIGNAL ENCODING

% BEGIN OF STL ENCODING
int: M = 1000;
var int: rho;
int: idx = 1;
constraint rho <= signal_var_x[idx] - (0);
constraint rho >= signal_var_x[idx] - (0);
% END OF STL ENCODING
