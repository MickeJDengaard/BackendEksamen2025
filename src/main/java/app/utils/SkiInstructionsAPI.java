package app.utils;

import app.dtos.SkiInstructionsDTO;
import app.enums.Level;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class SkiInstructionsAPI {

    private static final String BASE_URL = "https://apiprovider.cphbusinessapps.dk/skilesson/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<SkiInstructionsDTO> getInstructionsByLevel(Level level) {
        String levelParam = level.name().toLowerCase();
        String url = BASE_URL + levelParam;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<>() {});
            } else {
                System.err.println("Fejl ved hentning af ski-instruktioner. Statuskode: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return List.of();
    }
}
