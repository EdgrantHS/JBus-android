### JBusRD Android App - README

#### Overview
JBusRD Android App is a comprehensive bus management system designed to facilitate bus operators, renters, and passengers in managing bus schedules, bookings, and account operations. Built using Android SDK, the app integrates Retrofit for network operations and Gson for JSON parsing.

#### Features
1. **User Account Management**: Register and log in users.
2. **Bus Management**: View, create, and manage buses.
3. **Schedule Management**: Add and manage bus schedules.
4. **Booking System**: Facilitate passengers to book bus seats.
5. **Renter Registration**: Enable users to register as bus renters.

#### Packages and Key Classes
- `com.edgrantJBusRD.jbus_android.request`: Contains Retrofit interfaces for API calls.
- `com.edgrantJBusRD.jbus_android`: Main package with activity classes for different screens.

#### Key Classes
- `BaseApiService`: Interface defining various API endpoints.
- `RetrofitClient`: Sets up Retrofit client with base URL and OkHttpClient.
- `UtilsApi`: Utility class to get the API service instance.
- `AboutMeActivity`: Handles user account details and top-up functionality.
- `AddBusActivity`: UI and logic for adding a new bus.
- `AddScheduleActivity`: Manages adding schedules for buses.
- `BusArrayAdapter`, `CalendarBusArrayAdapter`: Adapters for displaying bus lists.
- `BusDetailActivity`: Detailed view for a selected bus.
- `MainActivity`: Dashboard for the app.
- `RegisterActivity`, `LoginActivity`, `RegisterRenterActivity`: Handle user registration, login, and renter registration.

#### Setup and Configuration
1. **Base URL**: Modify `BASE_URL_API` in `UtilsApi` to point to your server.
2. **Headers**: Customize headers in `RetrofitClient.okHttpClient()`.
3. **Build and Run**: Build the app using Android Studio and run it on an emulator or device.

#### Dependencies
- Retrofit
- Gson Converter

#### Usage
1. **Login/Register**: Start with user authentication.
2. **Dashboard**: Navigate through buses, bookings, and account details.
3. **Manage Buses**: Add, view, or modify bus details.
4. **Schedule Management**: Add schedules for buses.
5. **Booking**: Book seats on available buses.

#### Notes
- Ensure your backend API is running and accessible, change the IP address to the backend in UtilsApi class.
- Adjust `pageSize` in `MainActivity` and `ManageBusActivity` for pagination.

# Activities
## login
![login](https://github.com/EdgrantHS/JBus-android/assets/60654087/72e78e40-2a6b-4cad-8b43-6808a1e3c2fd)
## register
![register](https://github.com/EdgrantHS/JBus-android/assets/60654087/8d5bec50-da66-4fed-a681-c024255a9947)
## mainactivity
![mainactivity](https://github.com/EdgrantHS/JBus-android/assets/60654087/19929b20-d25e-44cb-987d-83b5df75b305)
## busdetail
![busdetail](https://github.com/EdgrantHS/JBus-android/assets/60654087/423fbe54-468e-482f-b9b3-d41b66928639)
## aboutme
![aboutme](https://github.com/EdgrantHS/JBus-android/assets/60654087/83e2e8dc-952c-44ac-bd36-0286beda552a)
## managebus
![managebus](https://github.com/EdgrantHS/JBus-android/assets/60654087/e73c29fc-e071-4f58-9777-68984c155ff2)
## addschedule
![addschedule](https://github.com/EdgrantHS/JBus-android/assets/60654087/0a552a2a-eea1-4243-9666-a301e49fcc23)
## addbus
![addbus](https://github.com/EdgrantHS/JBus-android/assets/60654087/94433c24-5a85-4b6d-b4f4-52db8f7db6eb)
