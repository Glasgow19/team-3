import googlemaps
from datetime import datetime
from geopy.geocoders import Nominatim
import firebase_admin
from firebase_admin import credentials, firestore
import numpy as np
from sklearn.linear_model import LinearRegression

def process_address(address):
    queryLoc = address[-7:]
    print(queryLoc)
    #Maps initalization
    gmaps = googlemaps.Client(key='AIzaSyBQxIgbIqn9PnlP_cfwrT1Ol6YB4UrstQw');
    geolocator = Nominatim(user_agent="myGeocoder");
    location = geolocator.geocode(queryLoc);
    locationString=str(location.latitude)+","+str(location.longitude);
    # Look up an address with reverse geocoding
    print(location.longitude);
    print(location.latitude);

    gymQuery = gmaps.places_nearby(location=locationString, radius=500, type="gym",name="gym");
    parkQuery = gmaps.places_nearby(location=locationString, radius=500, type="park");
    gymResultNum = len(gymQuery["results"]);
    parkResultNum = len(parkQuery["results"])   
    return gymResultNum + parkResultNum

cred = credentials.Certificate("./activify-cf1b9-firebase-adminsdk-zk6pr-5fc5a2b292.json");
firebase_admin.initialize_app(cred);
db = firestore.client();
doc_ref = db.collection(u'surveyanswers').stream()
input_var_ages = []
input_var_addresses = []
processed_addresses = []
input_activity = []

for docs in doc_ref:
    dictDoc = docs.to_dict()

    input_var_ages.append(dictDoc["Age"])
    input_var_addresses.append(dictDoc["Address"])
    input_activity.append(dictDoc["Physical"])

for address in input_var_addresses:
    try:
        processed_addresses.append(process_address(address))
    except:
        pass

x = np.array(list(zip(input_var_ages, processed_addresses)))
print(x)
y = input_activity
x,y = np.array(x), np.array(y)
model = LinearRegression().fit(x,y)
r_sq = model.score(x,y);
print('Coefficient of determination', r_sq)