# EmitEncoding class

There are four fundamental functions in EmitEncoding class

## TopLevel
```
(compStack.top()) -> (encoding(rho = compStack.top()), "rho")
```
- input: top elements on the `comp stack`
- output: encoding that assign top level robustness reserved variable `rho` to the `input`, define the top-level robustness decision variable `rho`

## Extrema
```
(expr1, expr2, min/max?) -> (encoding(min/max?(rho_expr1, rho_expr2)), "rho_n")
```
- input: two arbitrary expressions on the `compStack`
- output: encoding that calculates the min/max of the expressions, and temporary robustness decision variable `rho_n` that stores it

## ExtremaList
```
(expr1, expr2, min/max?, timebound1, timebound2) -> (encoding(min/max?(expr1, expr2)), "rho_sub_n")
```
- input: two arbitrary expression on the `compStack` that are embedded in temporal operators
- output: encoding that calculates the min/max of expressions at each time step, and the temporary robustness decision variable `rho_sub_n` that stores the resulting value

## ExtremaList2Extrema
```
(rho_sub_n, min/max?) -> (encoding(min/max(rho_sub_n)), rho_n)
``` 
- input: the array of decision variable `rho_sub_n`
- output: encoding that calculate the min/max of the list, the the temporary robustness decision varaible `rho_n` that stores the resulting value.