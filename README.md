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