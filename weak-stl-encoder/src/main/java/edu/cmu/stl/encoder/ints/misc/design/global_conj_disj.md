# Detailed Example

## Input

STL formula

```
G[0, 1](x > 1 /\ y > 1 \/ z > 1)
```

Signal

```
{
    "0" : {
        "x" : 3,
        "y" : 2,
        "z" : 1
    },

    "1" : {
        "x" : 2,
        "y" : 1,
        "z" : 0
    }
}

```

## Parse Tree

```
  G[0,1](stl)
           |
         '\/'
       /      \
    '/\'      '>' 
   /    \     /  \
  '>'   '>'  z    1
  / \   / \
 x   1 y   1
```

### compilation sequence (note that arith stack representation has been eliminated for simplicity)

### step 1: `G[0,1](...)`
### `EnterStl`
the temporal operator and time bound here are pushed onto the stacks

```
temp op stack

|   |
|_G_|

time bound stack

|        |
|_[0, 1]_|
```

### step 2: `x > 1`
### `ExitCompExpr`
```
 '>'
 / \
x   1
```
result in

```
comp stack (stores normalized expr before encoding)

|     |
|_x-1_|
```

### step 3: `y > 1`
### `ExitCompExpr`

```
 '>'
 / \
y   1
```
result in

```
comp stack

|     |
| y-1 |
|_x-1_|
```

### step 4: `x > 1 /\ y > 1`
### `ExitLogicAnd`
```
    '/\'
   /    \
  '>'   '>'
  / \   / \
 x   1 y   1
```
result in

```
check enclosed temporal operator is indeed globally operator

rho_sub is a list at each point in the time horizon

rho_sub[i] = min(x[i] - 1, y[i] - 1), where i is each time step

comp stack

|         |
|_rho_sub_|
```

### step 5: `z > 1`
### `ExitCompExpr`

```
 '>'
 / \
z   1
```
result in

```
comp stack

|   z-1   |
|_rho_sub_|
```

### step 6: `(x > 1 /\ y > 1) \/ (z > 1)`
### `ExitLogicOrExpr`

```
         '\/'
       /      \
    '/\'      '>' 
   /    \     /  \
  '>'   '>'  z    1
  / \   / \
 x   1 y   1
```
result in

```
check enclosed temporal operator is indeed globally operator

rho_sub_2 is a list at each point in the time horizon

rho_sub_2[i] = max(rho_sub_2[i], z[i] - 1), where i is each time step

comp stack

|           |
|_rho_sub_2_|
```

### step 7: `...(x > 1 /\ y > 1 \/ z > 1)`
### `ExitStl`
```
calculate the robustness of rho_sub_2 list

based on outer temporal operator `G`

rho_3 = min(rho_sub_2)

push rho_3 back to the comp stack

|       |
|_rho_3_|
```

### step 8: `G[0, 1](x > 1 /\ y > 1 \/ z > 1)`
### `ExitStl`
```
calculate the overall robustness

rho = rho_3
```