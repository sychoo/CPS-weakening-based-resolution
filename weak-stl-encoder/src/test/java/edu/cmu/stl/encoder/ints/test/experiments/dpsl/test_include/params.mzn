% DPSL example

%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%% CONSTANTS %%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%

% signal params
int: signal_length = 11;

int: energy_per_second = 1;
int: energy_per_block = 1;

int: dest_x = 0; % 30
int: dest_y = 0; %

%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%% CURRENT STATES %%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%% OBTAINED FROM SYSTEM %%%%%%%%%%%%%%

% current battery
% constraint sig_var_battery[1] = 8;
constraint sig_var_battery[1] = 8;

% current location
constraint sig_var_x[1] = 1;
constraint sig_var_y[1] = 2; 

% current is landed
constraint sig_var_is_landed[1] = 0;
