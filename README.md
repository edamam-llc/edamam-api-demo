# Demo for the *Nutrition Analysis API and Database* 

This repository contains a very simple program, which uses Edamam's
[Nutrition Analysis API](https://developer.edamam.com/) to analyze a recipe and
displays the response.

## Dependencies
The code uses the [OkHttp](http://square.github.io/okhttp/) library to perform
the HTTP request and Google's [Gson](https://github.com/google/gson) to handle
JSON.

## How to build
Clone the repository:

    git clone https://bitbucket.org/edamam/edamam-api-demo.git

Enter the directory:

    cd edamam-api-demo

And build it:

    ./gradlew assemble

## How to try
The build will create an executable jar file in `build/libs`. To test it, you'll
need to provide your application id and application key:

    java -jar build/libs/edamam-api-1.0-SNAPSHOT.jar APP_ID APP_KEY

*Please note, that this will run a real request against the API, which will count
as API usage.*