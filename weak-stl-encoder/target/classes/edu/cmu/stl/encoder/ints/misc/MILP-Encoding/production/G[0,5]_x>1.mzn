% G[0,5](x > 1)

% BEGIN OF SIGNAL ENCODING
int: signal_length = 6;
array[1..signal_length] of int: sig_var_x = ([5, 3, 1, -1, -3, -5]);
array[1..signal_length] of int: sig_var_y = ([6, 4, 2, -2, -4, -6]);
array[1..signal_length] of int: sig_var_z = ([30, 20, 10, -10, -20, -30]);
% END OF SIGNAL ENCODING

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% calculate robustness of AP at each time step
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% BEGIN OF STL ENCODING
int: M = 1000;
var int: rho;
var int: rho_1;
array[1..5-0 + 1] of var int: rho_sub_2;
array[1..5-0 + 1] of var 0..1: p_3;
constraint forall (idx in 1..6) (
	rho_sub_2[idx] >= sig_var_x[idx] - (10) /\
	rho_sub_2[idx] <= sig_var_x[idx] - (10)
)
;
% constraint sum (p_3) = 1;
% constraint forall (idx in 1..6) (
% 	rho_sub_2[idx] <= sig_var_x[idx] - (10)
% )
% ;
% constraint forall (idx in 0 + 1..5 + 1) (
% 	rho_sub_2[idx] >= sig_var_x[idx] - (10) - (1 - p_3[idx]) * M/\
% 	rho_sub_2[idx] <= sig_var_x[idx] - (10) + (1 - p_3[idx]) * M
% )
% ;

%%%%%%%%%%%%%%%%%%%%%%%%
%% Overall Robustness
%%%%%%%%%%%%%%%%%%%%%%%%

array[0 + 1..5 + 1] of var 0..1: p_sub_4;
constraint sum (p_sub_4) = 1;
constraint forall (idx in 1..5 - 0 + 1) (
	rho_1 <= rho_sub_2[idx]
)
;
constraint forall (idx in 1..5 - 0 + 1) (
	rho_1 >= rho_sub_2[idx] - (1 - p_sub_4[idx]) * M/\
	rho_1 <= rho_sub_2[idx] + (1 - p_sub_4[idx]) * M
)
;
constraint rho <= rho_1;
constraint rho >= rho_1;
% END OF STL ENCODING

