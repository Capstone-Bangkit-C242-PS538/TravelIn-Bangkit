# TravelIn: Personalized Travel Destination Recommendation using Collaborative and Content-Based Filtering
## Bangkit Capstone Project

Bangkit Capstone Team ID : C242-PS538	 <br>
Here is our repository for the Bangkit 2024 Capstone project - Machine Learning.

## DESCRIPTION
The Machine Learning model can be used to provide information about a content-based filtering approach for its recommendation system, providing personalized and accurate recommendations for users.

## TOOLS

- Pandas
- Numpy
- Tensorflow
- Scikit
- Matplotlib

## DATASETS 
The variables are user id, technicians id, repair needed, repair covered, rating with explanation as following : 

- place_Id: sequence id number from 1 to n
- place_name:name of the destination that would we recommend to the user
- description: short description of the destination, like the history or any information about the place
- category: place category or what type of destination is the place provide
- city: city location of the destination
- price: price that we need to pay to go to the destination
- lat: indicates the position north or south of the equator
- long: indicates the position east or west of the Prime Meridian, which passes through Greenwich, England


[Latest Dataset](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Machine_Learning/Dataset/tourid.csv)

## HOW TO RECOMMEND
The Machine Learning model can recommend the user with the nearest destination of their location based on the catgory they chose. 

## Model Accurancy and Loss
1. Train and Val MAE <br>
![TravelinApp](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Machine_Learning/Assets/mae.jpg)<br>

2. Train and Val Loss <br>
![TravelinApp](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Machine_Learning/Assets/train.jpg)<br>

1. Test Accurancy <br>
![TravelinApp](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Machine_Learning/Assets/accurancy.jpg)<br>

