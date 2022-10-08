# plot a simple bar chart
# https://stackabuse.com/seaborn-bar-plot-tutorial-and-examples/
# https://stackoverflow.com/questions/52708019/how-do-i-plot-a-simple-bar-chart-with-python-and-seaborn
import matplotlib.pyplot as plt
import seaborn as sns

# sns.set_style('darkgrid')

x = ['A', 'B', 'C']
y = [1, 5, 3]

data = []
data.append(x)
data.append(y)
#sns.barplot(x=x, data=y)

valor_graph = sns.barplot(
    # hue=["a", "b"],
    #x= [2014, 2015, 2016, 2017, 2018],
	x =["a", "b", "c", "d", "e"],
    y= [154.4, 490.9, 628.4,715.2,601.5])
plt.show()
