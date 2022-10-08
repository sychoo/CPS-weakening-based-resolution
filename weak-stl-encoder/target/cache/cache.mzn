% BEGIN OF SIGNAL ENCODING
int: signal_length = 2;
array[1..signal_length] of int: sig_var_battery = ([7, 9]);
% END OF SIGNAL ENCODING

% BEGIN OF STL ENCODING
int: M = 1000;
% BEGIN OF STL ENCODING FOR rho_stl_1
var int: rho_stl_1;
var int: rho_1;
constraint rho_1 <= (sig_var_battery[1])-(10);
constraint rho_1 >= (sig_var_battery[1])-(10);
var int: rho_2;
constraint rho_2 <= (sig_var_battery[2])-(10);
constraint rho_2 >= (sig_var_battery[2])-(10);
% Globally encoding
array[1..2] of var 0..1: p_3;
constraint sum(p_3) = 1;
constraint rho_stl_1 <= rho_1;
constraint rho_stl_1 <= rho_2;
constraint rho_stl_1 >= rho_1 - (1 - p_3[1]) * M;
constraint rho_stl_1 <= rho_1 + (1 - p_3[1]) * M;
constraint rho_stl_1 >= rho_2 - (1 - p_3[2]) * M;
constraint rho_stl_1 <= rho_2 + (1 - p_3[2]) * M;
% end of Globally encoding
% END OF STL ENCODING FOR rho_stl_1
