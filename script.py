import pandas as pd  
import numpy as np    
from sklearn.linear_model import LinearRegression

dataset = pd.read_csv('data.csv')

y_train = dataset['prediction'].values.reshape(-1,1)

del dataset['prediction']

regressor = LinearRegression()  
regressor.fit(dataset, y_train) #training the algorithm

data = [[13, 500, 125248]]
X_test = pd.DataFrame(data, columns = ['best_known', 'nodes', 'edges'])
y_pred = regressor.predict(X_test)


print(y_pred)