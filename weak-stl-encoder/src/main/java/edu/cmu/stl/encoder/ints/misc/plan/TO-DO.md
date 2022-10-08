# PRIORITY
- change topLevelGloballyLogicAndExpr so that
    - put more load in `ExitLogicExpr` clause
        - within `ExitLogicExpr`, check `tempOpStack` and get information regarding surrounding temporal operator.
    - add overall robustness calculation (based on rho_sub) to `ExitStl`
- eliminate topLevelTempOpCompExpr, including
    - topLevelGloballyCompExpr
    - topLevelEventuallyCompExpr
- finish theoretical framework for STL

# Robustness Evaluation Implementation Plan
## support logical expr
    - conjunction
        - (x > 1) /\ (y > 1)
    - disjunction
        - (x > 1) \/ (y > 1)
    - negation
        - not (x > 1)
 
## support STL globally (G) operator
    - G with single AP comp expr
        - G[0, 5](x > 1)
    - G with single AP logic expr
        - G[0, 5](x > 1) /\ (y > 1)
    - G with nested temporal operator
        - G[0, 5](G[0, 3](x > 1))

## repeat the same for eventually operator




# Robustness Evaluation Implementation Issues

- to prevent the collison of variable names, each variable (except reserved ones like rho (overall robustness) and idx (index in the time horizon))
    - static keyword may be helpful in this case

- special constants need to be explicitly listed in CompilationListener
    - miniZincArrayStartingIndex = 1
    - finalRobustnessDecisionVar = "rho"
    - miniZincSignalVarTimeIdxVar = "idx"



# Environmental Model
- define environmental model grammar and translation (preferably in the same grammar file STL-Int.g4, but through separate evaluation channel parallel to prog() (i.e. env()), which will be proceeded first before the evaluation of STL formula w.r.t to Signal)

    - env() --> prog()

- signal parsing and encoding will not be necessary if there is a environmental model present

    - however the current state still need to be passed in, in order to predict the future

- Use decision variable array (array of var int) along with environment model (described in function to predict future signals)

```
 forall (idx in 1...sig_length) (
    array[idx + 1] = func/pred(array[idx])
)
```

- the method works for simple trivial behavior. How to handle non-deterministic behavior?

- If you need non-deterministic behaviour, use a predicate:

- see MiniZinc handbook end of section 2.3.5 Local Variables

```
    predicate x_or_x_plus_1(var int: x, var int: z)  let {
    var 0..1: y;
    } in z=x+y;
```