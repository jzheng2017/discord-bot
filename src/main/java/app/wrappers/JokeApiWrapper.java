package app.wrappers;

import app.models.jokeapi.Joke;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JokeApiWrapper {
    private final String BASE_URL = "https://sv443.net/jokeapi/v2/joke/";
    private Joke joke;
    private HttpClient httpClient = HttpClient.newHttpClient();


    public Joke getJoke(String[] categories, String[] blacklist) {
        try {
            String url = buildURL(categories, blacklist);
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();

            HttpResponse<String> response;

            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            this.joke = gson.fromJson(response.body(), Joke.class);
            return this.joke;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }

        return new Joke();
    }

    public String getJokeBody() {
        if (joke == null) {
            throw new IllegalStateException("getJoke() must be called first");
        }
        return String.format("**%s** \n%s", joke.getSetup(), joke.getDelivery());
    }

    private String buildURL(String[] categories, String[] blacklist) {
        String url = BASE_URL;
        if (categories != null && categories.length > 0) {
            url += String.join(",", categories);
        } else {
            url += "Any";
        }

        if (blacklist != null && blacklist.length > 0) {
            url += "?blacklistFlags=";
            url += String.join(",", blacklist);
        }

        return url;
    }
}
