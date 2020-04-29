package app.wrappers;

import app.models.charts.Chart;
import app.models.charts.ChartRequest;
import app.models.charts.Data;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChartApiWrapper {
    private final String BASE_URL = "https://quickchart.io/chart";
    private HttpClient httpClient = HttpClient.newHttpClient();

    public byte[] getChart(Data data, String chartType) {
        ChartRequest chartRequest = buildRequestObject(data, chartType);
        HttpResponse<byte[]> response = sendHttpRequest(chartRequest);
        return response.body();
    }

    private ChartRequest buildRequestObject(Data data, String chartType) {
        Chart chart = new Chart(chartType, data);

        return new ChartRequest("white", 300, 500, "png", chart);
    }

    private HttpResponse<byte[]> sendHttpRequest(ChartRequest requestBody) {
        String request = objectToJson(requestBody);
        try {
            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(BASE_URL))
                    .POST(HttpRequest
                            .BodyPublishers
                            .ofString(request))
                    .setHeader("Content-Type", "application/json")
                    .build();

            return httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofByteArray());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String objectToJson(ChartRequest requestBody) {
        Gson gson = new Gson();
        return gson.toJson(requestBody);
    }

}
