# time bound weakening
supplying following parameters
- param_l: weakening parameter for left
- param_l_ub: max weakening
- param_r: weakening parameter for right
- param_r_ub: max weakening

auto-generated variable to find sub-robustness
- l_idx
- r_idx

note that all user supplied timebound has been taken into consideration
Globally weakening by shrinking the time bound
- [.(......)...]
- []: the originally calculated body values
- () : l_idx and r_idx (the actual decision)
- size(): the size (# of elements) of the original calculated body values

l_idx = 1 + param_l
r_idx = size() - param_r


Eventually weakening by shrinking the time bound
- [.....(......{.....................}......)....]
          delta                        delta
     delta_max                          delta_max
- {}: time bound that the user specified originally     
- []: the originally calculated body values
- () : l_idx and r_idx (the actual decision)
- size(): the size (# of elements) of the original calculated body values

l_idx = 1 + delta_max - delta
l_idx = 1 + param_l_ub - param_l

r_idx = size() - delta_max + delta
r_idx = size() - param_r_ub + param_r

