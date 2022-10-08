% use left and right index to find the range of array with robustness sum following certain condition

% Use this editor as a MiniZinc scratch book
var int: rho_1;
var int: rho_2;
var int: rho_3;
var int: rho_4;
var int: rho_5;

% suppose rho_3 + rho_4 achieves the max rho

var int: l_idx;
var int: r_idx;

array[1..5] of var int: a;

constraint l_idx >= 1 /\ l_idx <= 5;
constraint r_idx >= 1 /\ r_idx <= 5;
constraint l_idx <= r_idx;

constraint   a[1] = 0 /\
             a[2] = -3 /\
             a[3] = 8 /\
             a[4] = 7 /\
             a[5] = -8
             ;

% find out which consecutive index achieves sum >= 15   
% constraint exists (i in 1..3) (a[i] > 7);      
% constraint
% exists (l_idx in 1..5, r_idx in 1..5) (sum(j in l_idx..r_idx)(a[j]) >= 10);

% constraint a[l_idx] >= 7;
constraint sum(j in l_idx..r_idx) (a[j]) >= 15;
% constraint 
% forall (i in l_idx..r_idx) (
%     sum(j in l_idx..r_idx)(a[j]) >= 10;
%   sum(a[i]) >= 10
% );

solve maximize r_idx - l_idx;