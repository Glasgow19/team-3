import googlemaps
from datetime import datetime
from geopy.geocoders import Nominatim
import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("./activify-cf1b9-firebase-adminsdk-zk6pr-5fc5a2b292.json");
firebase_admin.initialize_app(cred);
db = firestore.client();
doc_ref = db.collection(u'users').document(u'BU0O6A5WutEgXNyYmcCT')
doc = doc_ref.get()
dictDoc = doc.to_dict();
print(dictDoc["Postcode"]);

#Queries
queryLoc = dictDoc["Postcode"];
#Maps initalization
gmaps = googlemaps.Client(key='');
geolocator = Nominatim(user_agent="myGeocoder");
location = geolocator.geocode(queryLoc);
locationString=str(location.latitude)+","+str(location.longitude);
print(location.longitude);
print(location.latitude);
# Look up an address with reverse geocoding

gymQuery = gmaps.places_nearby(location=locationString, radius=500, type="gym",name="gym");
parkQuery = gmaps.places_nearby(location=locationString, radius=500, type="park");
gymResultNum = len(gymQuery["results"]);
parkResultNum = len(parkQuery["results"]);

for result in parkQuery["results"]:
    print(result['name']);
for result in gymQuery["results"]:
    print(result["name"]);

print("There are "+str(gymResultNum)+" gyms in the vicinity.");
print("There are "+str(parkResultNum)+" parks in the vicinity.");
