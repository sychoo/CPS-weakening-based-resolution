% x > 1 

% BEGIN OF SIGNAL ENCODING
int: signal_length = 2;
array[1..signal_length] of int: signal_x = ([7, 9]);
array[1..signal_length] of int: signal_y = ([8, 10]);
% END OF SIGNAL ENCODING

% BEGIN OF STL ENCODING
int: M = 1000;
var int: rho;
int: idx = 1;
constraint rho <= signal_x[idx]+1 - (5);
constraint rho >= signal_x[idx]+1 - (5);
% END OF STL ENCODING
