
# Imagine a stack is represented like this

```
| 0 | -> top (end of stack)
| 1 | 
| 2 |
|_3_| -> bottom (beginning of stack)
```

the top of the stack is 0
```java
stack.pop() = 0
```

the bottom of the stack is 3
```java
stack.get(0) = 3
```

# `x > 1`

simple comparison expression
```
 op = '>'
    /\
   x  1
```

this will make a stack of normalized expressions to be evaluated by MiniZinc
the parse tree for `x > 1` is as follows

## `exit_Id`

arith stack

```
|_x_|
```

## `exit_Int`

arith stack

```
| 1 |
|_x_|
```

## `exit_Comp_Expr`

obtain the left and right operand of the comparison expression

pop operation 1

```java
right = arithStack.pop() = 1
```

arith stack
```
|_x_|
```

pop operation 2
```java
left = arithStack.pop() = x
```

arithstack
```
|__| (empty)
```

comp stack

```
|_x-1_| (normalized expression)
```


# `x > 1 /\ y > 1`

simple conjunction
```
    op = '/\'
       /\
(x > 1)  (y > 1)
```

## `exit_Comp_Expr`

comp stack

```
|_x-1_|
```

## `exit_Comp_Expr`

comp stack

```
| y-1 |
|_x-1_|
```

## `exit_Logic_Expr`

obtain the left and right operand of the logical expr via `pop()`

comp stack
```
|__| (empty)
```

execute encoding scheme

```java
rho_temp = encode("/\", "x-1", "y-1")
```

push the resulting value onto the stack

comp stack

```
|_rho_temp_|
```

<!-- comp stack

```
| y-1 |
|_x-1_|
```

logicOp stack

|_/\_| -->

# x > 1 \/ y > 1
# all disjunctions


# x > 1 /\ (y > 1 \/ w > 1) \/ z > 1 /\ a > 1

# x > 1 /\ y > 1 \/ w > 1 \/ z > 1 /\ a > 1
# (x > 1 /\ y > 1) \/ (w > 1) \/ (z > 1 /\ a > 1)
# mix of conjunctions and disjunctions

## exit_Comp_Expr

comp stack

|
