import pandas as pd
from flask import Flask, jsonify, request
from tensorflow.keras.models import load_model
import pickle
import numpy as np



UP_Wheat = load_model('UP_Wheat')
october = pickle.load(open('UP_Wheat/october.pkl','rb'))
november = pickle.load(open('UP_Wheat/november.pkl','rb'))
december = pickle.load(open('UP_Wheat/december.pkl','rb'))
january = pickle.load(open('UP_Wheat/january.pkl','rb'))
february = pickle.load(open('UP_Wheat/february.pkl','rb'))


UP_Rice = load_model('UP_Rice')
june = pickle.load(open('UP_Rice/june.pkl','rb'))
july = pickle.load(open('UP_Rice/july.pkl','rb'))
august = pickle.load(open('UP_Rice/august.pkl','rb'))
september = pickle.load(open('UP_Rice/september.pkl','rb'))
october = pickle.load(open('UP_Rice/october.pkl','rb'))
november = pickle.load(open('UP_Rice/november.pkl','rb'))


UP_Sugarcane = load_model('UP_Sugarcane')
june = pickle.load(open('UP_Sugarcane/june.pkl','rb'))
july = pickle.load(open('UP_Sugarcane/july.pkl','rb'))
august = pickle.load(open('UP_Sugarcane/august.pkl','rb'))
september = pickle.load(open('UP_Sugarcane/september.pkl','rb'))
october = pickle.load(open('UP_Sugarcane/october.pkl','rb'))



MH_Arhar = load_model('MH_Arhar')
june = pickle.load(open('MH_Arhar/june.pkl','rb'))
july = pickle.load(open('MH_Arhar/july.pkl','rb'))
august = pickle.load(open('MH_Arhar/august.pkl','rb'))
september = pickle.load(open('MH_Arhar/september.pkl','rb'))
october = pickle.load(open('MH_Arhar/october.pkl','rb'))
november = pickle.load(open('MH_Arhar/november.pkl','rb'))



MH_Cotton = load_model('MH_Cotton')
june = pickle.load(open('MH_Cotton/june.pkl','rb'))
july = pickle.load(open('MH_Cotton/july.pkl','rb'))
august = pickle.load(open('MH_Cotton/august.pkl','rb'))
september = pickle.load(open('MH_Cotton/september.pkl','rb'))
october = pickle.load(open('MH_Cotton/october.pkl','rb'))
november = pickle.load(open('MH_Cotton/november.pkl','rb'))




MH_Rice = load_model('MH_Rice')
june = pickle.load(open('MH_Rice/june.pkl','rb'))
july = pickle.load(open('MH_Rice/july.pkl','rb'))
august = pickle.load(open('MH_Rice/august.pkl','rb'))
september = pickle.load(open('MH_Rice/september.pkl','rb'))
october = pickle.load(open('MH_Rice/october.pkl','rb'))
november = pickle.load(open('MH_Rice/november.pkl','rb'))



MH_Soyabean = load_model('MH_Soyabean')
june = pickle.load(open('MH_Soyabean/june.pkl','rb'))
july = pickle.load(open('MH_Soyabean/july.pkl','rb'))
august = pickle.load(open('MH_Soyabean/august.pkl','rb'))
september = pickle.load(open('MH_Soyabean/september.pkl','rb'))
october = pickle.load(open('MH_Soyabean/october.pkl','rb'))
november = pickle.load(open('MH_Soyabean/november.pkl','rb'))



HR_Wheat = load_model('HR_Wheat')
october = pickle.load(open('HR_Wheat/october.pkl','rb'))
november = pickle.load(open('HR_Wheat/november.pkl','rb'))
december = pickle.load(open('HR_Wheat/december.pkl','rb'))
january = pickle.load(open('HR_Wheat/january.pkl','rb'))
february = pickle.load(open('HR_Wheat/february.pkl','rb'))



HR_Rice = load_model('HR_Rice')
june = pickle.load(open('HR_Rice/june.pkl','rb'))
july = pickle.load(open('HR_Rice/july.pkl','rb'))
august = pickle.load(open('HR_Rice/august.pkl','rb'))
september = pickle.load(open('HR_Rice/september.pkl','rb'))
october = pickle.load(open('HR_Rice/october.pkl','rb'))
november = pickle.load(open('HR_Rice/november.pkl','rb'))



Bihar_Maize = load_model('Bihar_Maize')
june = pickle.load(open('Bihar_Maize/june.pkl','rb'))
july = pickle.load(open('Bihar_Maize/july.pkl','rb'))
august = pickle.load(open('Bihar_Maize/august.pkl','rb'))
september = pickle.load(open('Bihar_Maize/september.pkl','rb'))
october = pickle.load(open('Bihar_Maize/october.pkl','rb'))



Bihar_Rice = load_model('Bihar_Rice')
june = pickle.load(open('Bihar_Rice/june.pkl','rb'))
july = pickle.load(open('Bihar_Rice/july.pkl','rb'))
august = pickle.load(open('Bihar_Rice/august.pkl','rb'))
september = pickle.load(open('Bihar_Rice/september.pkl','rb'))
october = pickle.load(open('Bihar_Rice/october.pkl','rb'))



Bihar_Wheat = load_model('Bihar_Wheat')
november = pickle.load(open('Bihar_Wheat/november.pkl','rb'))
december = pickle.load(open('Bihar_Wheat/december.pkl','rb'))
january = pickle.load(open('Bihar_Wheat/january.pkl','rb'))
february = pickle.load(open('Bihar_Wheat/february.pkl','rb'))




PB_Rice = load_model('PB_Rice')
june = pickle.load(open('PB_Rice/june.pkl','rb'))
july = pickle.load(open('PB_Rice/july.pkl','rb'))
august = pickle.load(open('PB_Rice/august.pkl','rb'))
september = pickle.load(open('PB_Rice/september.pkl','rb'))
october = pickle.load(open('PB_Rice/october.pkl','rb'))
november = pickle.load(open('PB_Rice/november.pkl','rb'))


PB_Maize = load_model('PB_Maize')
june = pickle.load(open('PB_Maize/june.pkl','rb'))
july = pickle.load(open('PB_Maize/july.pkl','rb'))
august = pickle.load(open('PB_Maize/august.pkl','rb'))
september = pickle.load(open('PB_Maize/september.pkl','rb'))
october = pickle.load(open('PB_Maize/october.pkl','rb'))
november = pickle.load(open('PB_Maize/november.pkl','rb'))



PB_Wheat = load_model('PB_Wheat')
november = pickle.load(open('PB_Wheat/november.pkl','rb'))
december = pickle.load(open('PB_Wheat/december.pkl','rb'))
january = pickle.load(open('PB_Wheat/january.pkl','rb'))
february = pickle.load(open('PB_Wheat/february.pkl','rb'))




# app
app = Flask(__name__)

# routes
@app.route('/up/wheat/', methods=['POST'])

def predict_1():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = october.predict(data_df) 
   result2 = november.predict(data_df) 
   result3 = december.predict(data_df) 
   result4 = january.predict(data_df) 
   result5 = february.predict(data_df) 
   
   
   dataset = pd.DataFrame({'october': result1, 'november': result2, 'december': result3, 'january': result4, 'february': result5})
   Yield = UP_Wheat.predict(dataset).flatten()
   
   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)


@app.route('/up/rice/', methods=['POST'])
def predict_2():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)
   result6 = november.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5, 'november': result6})
   Yield = UP_Rice.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)



@app.route('/up/sugarcane/', methods=['POST'])
def predict_3():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5})
   Yield = UP_Sugarcane.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)





@app.route('/mh/arhar/', methods=['POST'])
def predict_4():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)
   result6 = november.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5, 'november': result6})
   Yield = MH_Arhar.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)



@app.route('/mh/cotton/', methods=['POST'])
def predict_5():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)
   result6 = november.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5, 'november': result6})
   Yield = MH_Cotton.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)




@app.route('/mh/rice/', methods=['POST'])
def predict_6():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)
   result6 = november.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5, 'november': result6})
   Yield = MH_Rice.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)







@app.route('/mh/soyabean/', methods=['POST'])
def predict_7():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)
   result6 = november.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5, 'november': result6})
   Yield = MH_Soyabean.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)


@app.route('/hr/wheat/', methods=['POST'])
def predict_8():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = october.predict(data_df)
   result2 = november.predict(data_df)
   result3 = december.predict(data_df)
   result4 = january.predict(data_df)
   result5 = february.predict(data_df)

   dataset = pd.DataFrame(
      {'october': result1, 'november': result2, 'december': result3, 'january': result4, 'february': result5})
   Yield = HR_Wheat.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)




@app.route('/hr/rice/', methods=['POST'])
def predict_9():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)
   result6 = november.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5, 'november': result6})
   Yield = HR_Rice.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}



   # return data
   return jsonify(prediction)




@app.route('/br/maize/', methods=['POST'])
def predict_10():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5})
   Yield = Bihar_Maize.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)



@app.route('/br/rice/', methods=['POST'])
def predict_11():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5})
   Yield = Bihar_Rice.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)



@app.route('/br/wheat/', methods=['POST'])
def predict_12():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = november.predict(data_df)
   result2 = december.predict(data_df)
   result3 = january.predict(data_df)
   result4 = february.predict(data_df)

   dataset = pd.DataFrame(
      {'november': result1, 'december': result2, 'january': result3, 'february': result4})
   Yield = Bihar_Wheat.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)




@app.route('/pb/rice/', methods=['POST'])
def predict_13():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)
   result6 = november.predict(data_df)


   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5, 'november': result6})
   Yield = PB_Rice.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)



@app.route('/pb/wheat/', methods=['POST'])
def predict_14():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = november.predict(data_df)
   result2 = december.predict(data_df)
   result3 = january.predict(data_df)
   result4 = february.predict(data_df)

   dataset = pd.DataFrame(
      {'november': result1, 'december': result2, 'january': result3, 'february': result4})
   Yield = PB_Wheat.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)



@app.route('/pb/maize/', methods=['POST'])
def predict_15():
   # get data
   data = request.get_json(force=True)

   # convert data into dataframe
   data.update((x, [y]) for x, y in data.items())
   data_df = pd.DataFrame.from_dict(data)

   # predictions
   result1 = june.predict(data_df)
   result2 = july.predict(data_df)
   result3 = august.predict(data_df)
   result4 = september.predict(data_df)
   result5 = october.predict(data_df)
   result6 = november.predict(data_df)




   dataset = pd.DataFrame(
      {'june': result1, 'july': result2, 'august': result3, 'september': result4, 'october': result5, 'november': result6})
   Yield = PB_Maize.predict(dataset).flatten()

   prediction = np.array2string(Yield)

   # send back to browser
   output = {'results': Yield[0]}

   # return data
   return jsonify(prediction)






if __name__ == '__main__':
   app.run(port = 5000, debug=True)
