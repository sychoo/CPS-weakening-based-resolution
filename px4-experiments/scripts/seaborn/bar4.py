# https://stackoverflow.com/questions/52708019/how-do-i-plot-a-simple-bar-chart-with-python-and-seaborn
import seaborn as sns
import pandas as pd
import matplotlib.pyplot as plt

# df = pd.read_csv("data9-G100.csv")
df = pd.read_csv("data9-G100-modified.csv")
# filter 
# df = df[(df['feature'] == 'runaway enforcer')]
# df = df[(df['feature'] == 'boundary enforcer')]
sns.set_theme(style='darkgrid')

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
    # hue= 'experiment_item',
    hue='method',
    #  y= 'average_robustness_sum',
    # y= 'average_robustness_sum_minus5_normalized',
    #  y= 'average_robustness_sum_normalized',

    # y= 'experiment_item',
    # y = 'min_robustness_sum',
    # y = 'cumulative_robustness_sum',
    # y = 'boundary_cumulative_robustness',
    # y = 'boundary_min_robustness', # good result
    # y = 'runaway_min_robustness', # good result

    # y = 'boundary_average_robustness', # good result
    # y = "mission_waypoints_completed_count",
    # y = 'follower_caught_ego_count',
    # y = 'boundary_activated_count',
    # y = 'runaway_activated_count',
    # y = 'runaway_cumulative_robustness',
    # y = 'runaway_average_robustness_normalized',
    # y = 'runaway_average_robustness',
    # y = 'runaway_average_robustness_minus5_normalized',
    y = 'boundary_average_robustness_normalized',
    # y = 'boundary_average_robustness',
    # y = "feature",


    # y = ''

    # y = 'average_robustness_sum',
    # y='method_boolean',
# x='experiment_item',
# x = 'method'
x = 'feature',
# x = 'file_num_mod_25'
)
# valor_plot.axhline(-18.0, ls='--', color='red')
# https://stackoverflow.com/questions/31632637/label-axes-on-seaborn-barplot
# valor_plot.set(xlabel='Method', ylabel='Combined Robustness Normalized')
# valor_plot.set(xlabel='Feature Prioritized', ylabel='Runaway Enforcer Robustness')
valor_plot.set(xlabel='Feature Prioritized', ylabel='Boundary Enforcer Robustness')

# valor_plot.set(xlabel='Feature Prioritized', ylabel='Runaway Enforcer Minimum Robustness')
# valor_plot.set(xlabel='Feature Prioritized', ylabel='Boundary Enforcer Minimum Robustness')


plt.show()

