# Tier Coding Exercise

[![Build Status](https://app.bitrise.io/app/618c3a4bfa351ffb/status.svg?token=DgWUBKL_AqEvJ31OQ506wQ&branch=main)](https://app.bitrise.io/app/618c3a4bfa351ffb#/builds)

## Build
1. Clone this repository
2. Import into Android Studio
3. Add Google API key to local.properties
4. Build the app

## Tools
- Network: Retrofit, Moshi
- Dependency Injection: Dagger 2
- Async communication: Rxjava, Rxandroid
- Testing: Espresso
- Continuous Integration: Bitrise

## Running tests locally
Run on a device connected via USB or set up an emulator:

    gradlew connectedAndroidTest

## TODO
- Write more end-to-end tests
- Improve UX with better loading and error states

## Screenshots
![Alt text](/screenshots/map.png?raw=true "Map")
