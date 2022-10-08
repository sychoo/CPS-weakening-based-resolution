% https://stackoverflow.com/questions/48278879/minizinc-array-of-multidimensional-array

int: d1 = 2; % len(m)
int: d2 = 3; % len(m[1])
int: d3 = 4; % len(m[1, 1])

array[1..d1, 1..d2, 1..d3] of int: m = 
    array3d(1..d1, 1..d2, 1..d3,
        [
            1, 2, 3, 4, 5, 6,
            7, 8, 9, 10, 11, 12,
            13, 14, 15, 16, 17, 18,
            19, 20, 21, 22, 23, 24,
        ]);
% above array is equivalent to
% 
% len(array) where array is each divided 2d array
% block # = d1
% len(array) = d2
% len(array[0]) = d3
% 
% block(1)
%     [[1, 2, 3, 4],
%      [5, 6, 7, 8],
%      [9, 10, 11, 12]],

% block(2)
%     [[13, 14, 15, 16],
%      [17, 18, 19, 20],
%      [21, 22, 23, 24]]

output ["accessing 2nd row, 3rd elements of array m (note that index start with 1): \(m[2, 3, 1]) \(sum(m[2,3]))", "\n"]