isWeak (EmitEncoding)
- for indicating whether a weakened encoding needs to be generated
- true: need to be weakened
- false: generate encoding without weakening, original robustness

EmitEncoding.statWeakenFlag
- true: if there's at least one statement in the STL expression that
  needs to be weakened
- false: no sub-expression in the stl statement needs to be weakened
