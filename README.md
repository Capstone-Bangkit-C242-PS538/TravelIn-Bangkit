# TravelIn: Personalized Travel Destination Recommendation using Collaborative and Content-Based Filtering
## Bangkit Capstone Project

Bangkit Capstone Team ID : C242-PS538	 <br>
Here is our repository for the Bangkit 2024 Capstone project - Cloud Computing.

## DESCRIPTION
Cloud Computing have responsible for creating and managing APIs, databases and servers. We also provide services needed by the Mobile Development and Machine Learning divisions, so that the features we have designed in this mobile application, the data and information entered by users and technicians can be properly used, stored and maintained.

Service that we deployed:
`https://backend-service1-473303426237.asia-southeast2.run.app` and `https://model-lokasi1-733147118446.asia-southeast2.run.app`


## TOOLS
![TravelinCloudArchitecture](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Cloud_Computing/Architecture/Tech%20Logo.png)

- JavaScript
- Node js
- Framework : Hapi js
- Flask
- Google Cloud Platform
- Firebase
- Postman
- Draw.io
- Google Cloud Pricing Calculator

## CLOUD ARCHITECTURE
![TravelinCloudArchitecture](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Cloud_Computing/Architecture/Travelin-Architecture.jpg)

## Google Cloud Pricing Calculator
![TravelinCloudArchitecture](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Cloud_Computing/Architecture/pricing.png)

The Google Cloud Pricing Calculator is used in our project to estimate and plan cloud costs effectively. It provides detailed cost projections based on selected services, usage scenarios, and configurations. This tool helps ensure that we stay within budget, optimize resource allocation, and make informed decisions by comparing different setups and pricing options before implementation.

[EstimatedCostperMonth](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Cloud_Computing/Architecture/cost-est.jpg)



## TravelIn DOCUMENTATION API
![APIDOC](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Cloud_Computing/Architecture/Postman-logo-orange-2021_1155x.png)
We use Postman as a documentation of our API is because of its intuitive and structured platform for describing endpoints, parameters, headers, and API responses. Features like Collections, Environment Variables, and API Documentation make it easier to manage interactive and developer-friendly documentation. Additionally, its direct integration with API testing ensures that the documentation remains accurate and up-to-date with the latest functionality.

[TravelIn API Documentation on Postman](https://documenter.getpostman.com/view/39612721/2sAYBXAAAY)


# How to Use the Endpoint

1. To interact with the `GET` endpoint `https://backend-service1-473303426237.asia-southeast2.run.app/places` in Postman, follow the steps below:

### Prerequisites

- **Postman Installed**: Ensure you have Postman installed on your computer. You can download it from [here](https://www.postman.com/downloads/).
- **API Access**: You'll need the credentials stored in a `credentials.json` file to authenticate and gain access to the API.

### Steps to Call the API

1. **Open Postman**:
   - Launch Postman on your computer.

2. **Create a New Request**:
   - Click the **New** button in Postman.
   - Select **Request** from the available options.
   - Name your request (e.g., "Get Places").
   - Choose a collection or create a new one to save your request.
   - Click **Save**.

3. **Set the Request Type to GET**:
   - In the new request window, select `GET` from the dropdown next to the URL input field.

4. **Enter the API URL**:
   - In the URL input field, enter the API endpoint: 
     ```
     https://backend-service1-473303426237.asia-southeast2.run.app/places
     ```
     
5. **Send the Request**:
   - After setting up the request, click the **Send** button.
   - Postman will process the request and display the response from the server.

6. **Review the Response**:
   - In the **Body** tab, you'll see the response from the API. If the request is successful, you’ll receive a list of places or relevant data returned by the API.
   - If there is an error (e.g., invalid credentials or endpoint not found), the error message will be displayed.

7. **Save the Request (Optional)**:
   - After you’ve verified that the request works, you can save it for later use by clicking the **Save** button.

### Troubleshooting:

- **Authentication Issues**: If you receive authentication errors, double-check the credentials in your `credentials.json` file and ensure they are correctly configured in Postman.
- **Request Failures**: If the request fails (e.g., returns 404 or 500 errors), verify that the API endpoint is correct and that the service is up.

### Here’s an example of how to interact with the endpoint using Postman:
![Places Interface](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Cloud_Computing/Asset%20Endpoint/Places.png)

***

2. To interact with the `POST` endpoint `https://model-lokasi1-733147118446.asia-southeast2.run.app/predict` in Postman, follow the steps below:

### Prerequisites

- **Postman Installed**: Ensure you have Postman installed on your computer. You can download it from [here](https://www.postman.com/downloads/).
- **API Access**: You'll need the credentials stored in a `credentials.json` file to authenticate and gain access to the API.

### Steps to Call the API

1. **Open Postman**:
   - Launch Postman on your computer.

2. **Create a New Request**:
   - Click the **New** button in Postman.
   - Select **Request** from the available options.
   - Name your request (e.g., "Predict").
   - Choose a collection or create a new one to save your request.
   - Click **Save**.

3. **Set the Request Type to POST**:
   - In the new request window, select `POST` from the dropdown next to the URL input field.

4. **Enter the API URL**:
   - In the URL input field, enter the API endpoint: 
     ```
     https://model-lokasi1-733147118446.asia-southeast2.run.app/predict
     ```

5. **Set the Request Body**:
   - In the **Body** tab of the Postman window, choose the `raw` option.
   - Set the format to `JSON` from the dropdown next to `raw`.
   - Add the following sample JSON to send the location data (latitude and longitude):
     ```json
     {
       "lat": -6.915858891676989, 
       "long": 107.61677942993981
     }
     ```
     
6. **Send the Request**:
   - After setting up the request, click the **Send** button.
   - Postman will process the request and display the response from the server.

7. **Review the Response**:
   - In the **Body** tab, you'll see the response from the API. If the request is successful, you’ll receive a list of nearby tourist destinations with details such as:
     - Place name
     - Category
     - Rating
     - Price
     - Distance from the user
     - Description, etc
   - If there is an error (e.g., invalid credentials or endpoint not found), the error message will be displayed.

8. **Save the Request (Optional)**:
   - After you’ve verified that the request works, you can save it for later use by clicking the **Save** button.

## Troubleshooting:

- **Authentication Issues**: If you receive authentication errors, double-check the credentials in your `credentials.json` file and ensure they are correctly configured in Postman.
- **Request Failures**: If the request fails (e.g., returns 404 or 500 errors), verify that the API endpoint is correct and that the service is up.

### Here’s an example of how to interact with the endpoint using Postman:
![Places Interface](https://github.com/Capstone-Bangkit-C242-PS538/TravelIn-Bangkit/blob/Cloud_Computing/Asset%20Endpoint/Predict.png)

## For complete Documentation, please visit [Travelin API Documentation](https://documenter.getpostman.com/view/39612721/2sAYBXAAAY).








