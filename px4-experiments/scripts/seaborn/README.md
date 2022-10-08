# Excel Formulas
check if an excel cell contains certain text --> RETURN TRUE/FALSE
- =ISNUMBER(SEARCH("WEAK", C2:C39))
- https://exceljet.net/formula/cell-contains-specific-text

put text if TRUE
- =IF(D2, "weakening", "priority")

# condense the bar plot to 3 bars only
=IF(ISNUMBER(SEARCH("WEAK", C2)), "weakening", IF(ISNUMBER(SEARCH("PRIORITY - RUNAWAY", C2)), "priority w/ runaway", "priority w/ boundary"))

# min max scaling
safe landing
=IF(BF2 < 0, (BF2-$BF$100)/(-$BF$100) - 1, BF2/$BF$101)

delivery planning
=IF(BB2 < 0, (BB2-$BB$100)/(-$BB$100) - 1, BB2/$BB$101)
TODO
- added cumulative_robustness_sum
- added average_robustness_sum
- added min_robustness_sum

Process
- (runaway - 5) --> then normalized 