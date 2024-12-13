# TravelIn: Personalized Travel Destination Recommendation using Collaborative and Content-Based Filtering
## Bangkit Capstone Project

Bangkit Capstone Team ID : C242-PS538	 <br>
Here is our repository for the Bangkit 2024 Capstone project - Cloud Computing.

## DESCRIPTION
Cloud Computing have responsible for creating and managing APIs, databases and servers. We also provide services needed by the Mobile Development and Machine Learning divisions, so that the features we have designed in this mobile application, the data and information entered by users and technicians can be properly used, stored and maintained.


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

To interact with the `GET` endpoint `https://backend-service1-473303426237.asia-southeast2.run.app/places` in Postman, follow the steps below:

## Prerequisites

- **Postman Installed**: Ensure you have Postman installed on your computer. You can download it from [here](https://www.postman.com/downloads/).
- **API Access**: You'll need the credentials stored in a `credentials.json` file to authenticate and gain access to the API.

## Steps to Call the API

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

5. **Add Authentication (If Needed)**:
   - If the API requires authentication (which is likely), you need to include your credentials.
   - In Postman, go to the **Authorization** tab, and depending on the authentication type used by the API (e.g., Bearer Token, OAuth 2.0), fill in the necessary details.
   - If using **OAuth 2.0** or another authentication method, ensure that your `credentials.json` is correctly used to generate the required tokens or credentials.

6. **Load `credentials.json` (if required)**:
   - Some APIs may require credentials in the form of a `credentials.json` file, which contains API keys or other authentication details.
   - To use it in Postman:
     1. Go to the **Authorization** tab in Postman.
     2. Choose the appropriate authorization type (e.g., OAuth 2.0).
     3. If the API requires credentials from a `credentials.json` file, you can manually input the API key from the file or use a script to load the credentials into Postman.

7. **Send the Request**:
   - After setting up the request, click the **Send** button.
   - Postman will process the request and display the response from the server.

8. **Review the Response**:
   - In the **Body** tab, you'll see the response from the API. If the request is successful, you’ll receive a list of places or relevant data returned by the API.
   - If there is an error (e.g., invalid credentials or endpoint not found), the error message will be displayed.

9. **Save the Request (Optional)**:
   - After you’ve verified that the request works, you can save it for later use by clicking the **Save** button.

## Additional Notes:

- **Credentials**: The `credentials.json` file typically contains sensitive data such as API keys, client secrets, and other authentication-related information. Always ensure it’s securely stored.
- **Handling Credentials in Postman**: If you need to programmatically authenticate using your `credentials.json` (e.g., for OAuth 2.0 or Google API access), you might need to use Postman's scripting features or configure Postman to load the credentials dynamically.

## Troubleshooting:

- **Authentication Issues**: If you receive authentication errors, double-check the credentials in your `credentials.json` file and ensure they are correctly configured in Postman.
- **Request Failures**: If the request fails (e.g., returns 404 or 500 errors), verify that the API endpoint is correct and that the service is up.







