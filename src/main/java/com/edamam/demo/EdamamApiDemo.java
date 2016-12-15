package com.edamam.demo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.io.Reader;

/**
 * Sample code for accessing Edamam's Nutrition Analysis API.
 */
public class EdamamApiDemo {
    private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");
    private static final String RECIPE = "{\n" +
            "  \"title\": \"Fresh Ham Roasted With Rye Bread and Dried Fruit Stuffing\",\n" +
            "  \"prep\": \"1. Have your butcher bone and butterfly the ham and score the fat in a diamond pattern. ...\",\n" +
            "  \"yield\": \"About 15 servings\",\n" +
            "  \"ingr\": [\n" +
            "    \"1 fresh ham, about 18 pounds, prepared by your butcher (See Step 1)\",\n" +
            "    \"7 cloves garlic, minced\",\n" +
            "    \"1 tablespoon caraway seeds, crushed\",\n" +
            "    \"4 teaspoons salt\",\n" +
            "    \"Freshly ground pepper to taste\",\n" +
            "    \"1 teaspoon olive oil\",\n" +
            "    \"1 medium onion, peeled and chopped\",\n" +
            "    \"3 cups sourdough rye bread, cut into 1/2-inch cubes\",\n" +
            "    \"1 1/4 cups coarsely chopped pitted prunes\",\n" +
            "    \"1 1/4 cups coarsely chopped dried apricots\",\n" +
            "    \"1 large tart apple, peeled, cored and cut into 1/2-inch cubes\",\n" +
            "    \"2 teaspoons chopped fresh rosemary\",\n" +
            "    \"1 egg, lightly beaten\",\n" +
            "    \"1 cup chicken broth, homemade or low-sodium canned\"\n" +
            "  ]\n" +
            "}";

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.printf("Usage: %s app_id app_key%n", EdamamApiDemo.class.getCanonicalName());
            System.exit(1);
        }
        final String appId = args[0];
        final String appKey = args[1];

        // Prepare the URL
        final HttpUrl url = HttpUrl.parse("https://api.edamam.com/api/nutrition-details")
                                   .newBuilder()
                                   .addQueryParameter("app_id", appId)
                                   .addQueryParameter("app_key", appKey)
                                   .build();

        // And the request.
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(APPLICATION_JSON, RECIPE)) // this will add the Content-Type header as well
                .build();

        final OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();

        // Make the request
        final Response response = httpClient.newCall(request).execute();

        // Handle error responses
        if (!response.isSuccessful()) {
            System.err.printf("Encountered a problem: %d - %s%n", response.code(), response.message());
            System.exit(2);
        }

        try (ResponseBody body = response.body();
             Reader reader = body.charStream()) {

            // Parse the JSON response
            final JsonObject info = new JsonParser().parse(reader).getAsJsonObject();

            // And extract some data from it
            final double yield = info.get("yield").getAsDouble();
            final double calories = info.get("calories").getAsDouble();
            System.out.println("Number of servings: " + yield);
            System.out.println("Total calories: " + calories);
            System.out.println("Full response: " + info);
        }

        System.exit(0);
    }
}
