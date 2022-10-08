int: rows = 2; % len(m)
int: cols = 11; % len(m[1])

array[1..rows, 1..cols] of int: m = 
    array2d(1..rows, 1..cols,
        [
            0, 1, 3, 5, 0, 1, 3, 5, 0, 1, 3,
            0, 2, 4, 6, 0, 2, 4, 6, 0, 2, 4,
        ]);

% above is equivalent to 
% [[0, 1, 3, 5, 0, 1, 3, 5, 0, 1, 3],
%  [0, 2, 4, 6, 0, 2, 4, 6, 0, 2, 4]]

output ["accessing 2nd row, 3rd elements of array m (note that index start with 1): \(m[2, 3])", "\n"]