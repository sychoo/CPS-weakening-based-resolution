# https://stackoverflow.com/questions/52708019/how-do-i-plot-a-simple-bar-chart-with-python-and-seaborn
import seaborn as sns
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("data.csv")
print(df.head())
# valor_ano = pd.DataFrame({'level_1':[1900, 2014, 2015, 2016, 2017, 2018], 
#                          'total':[0.0, 154.4, 490.9, 628.4,715.2,601.5],
# 			'random':[5, 4, 4, 2, 0, 4]})

# valor_ano = pd.DataFrame({'level_1':[2015, 2016, 2017, 2018], 
#                          'robustness':[490.9, 628.4,715.2,601.5],
# 			'method':["priority", "priority", "weakening", "weakening"],
# 'feature':["runaway", "boundary", "runaway", "boundary"]})

# valor_ano.drop(0, axis=0, inplace=True)

valor_plot = sns.barplot(
    data= df,
    hue= 'method',
     y= 'average_robustness_sum',
    # y = 'min_robustness_sum',
    # y = 'cumulative_robustness_sum',
    #y = 'boundary_cumulative_robustness',
    # y='method_boolean',
x='feature')
plt.show()
