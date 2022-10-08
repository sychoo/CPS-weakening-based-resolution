# https://stackoverflow.com/questions/52708019/how-do-i-plot-a-simple-bar-chart-with-python-and-seaborn
import seaborn as sns
import pandas as pd
import matplotlib.pyplot as plt

# df = pd.read_csv("data9-G100.csv")
# df = pd.read_csv("data11-GF200.csv")
df = pd.read_csv("data12_G100.csv")
# df = pd.read_csv("result.csv")

# filter 
# df = df[(df['feature'] == 'runaway enforcer')]
# df = df[(df['feature'] == 'boundary enforcer')]


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
    
    #  y= 'average_robustness_sum',
    # y= 'average_robustness_sum_minus5_normalized',
    #  y= 'average_robustness_sum_normalized',

    # y= 'experiment_item',
    # y = 'min_robustness_sum',
    # y = 'cumulative_robustness_sum',
    # y = 'boundary_cumulative_robustness',
    # y = 'boundary_min_robustness_overall', # good result
    # y = 'runaway_min_robustness_overall', # good result

    # y = 'boundary_average_robustness', # good result
    # y = "mission_waypoints_completed_count",
    # y = 'follower_caught_ego_count',
    # y = 'boundary_activated_count',
    # y = 'runaway_activated_count',
    # y = 'runaway_cumulative_robustness',
    # y = 'runaway_average_robustness_normalized',
    # y = 'runaway_average_robustness',
    # y = 'runaway_average_robustness_minus5_normalized',
    # y = 'boundary_average_robustness_normalized',
    # y = 'boundary_average_robustness_feature_interaction',
    # y = 'runaway_average_robustness_feature_interaction',
    # y = "feature",


    # y = ''

    # y = 'average_robustness_sum',
    # y='method_boolean',
        # y= 'runaway_activation_robustness_average',
    y= 'boundary_activation_robustness_average',
x='experiment_item',
# x = 'method'
# x = 'feature',
# x = 'file_num_mod_25'
ci=None,
)
# valor_plot.set_xlim(-25, 0)
# plt.ylim(-25, 0)
sns.set(style='darkgrid')

valor_plot.bar_label(valor_plot.containers[-1], fmt='%.2f', label_type="edge")
# valor_plot.set_ylim(-20, 20)
plt.show()
