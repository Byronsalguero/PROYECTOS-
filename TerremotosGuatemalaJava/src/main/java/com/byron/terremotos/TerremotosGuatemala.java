package com.byron.terremotos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

public class TerremotosGuatemala {
    private static final String URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&latitude=15.7835&longitude=-90.2308&maxradiuskm=500&orderby=time&limit=20";
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("     MONITOR DE TERREMOTOS - GUATEMALA");
        System.out.println("============================================");
        System.out.println("Actualización automática cada 60 segundos.\n");

        ScheduledExecutorService servicio = Executors.newSingleThreadScheduledExecutor();
        servicio.scheduleAtFixedRate(() -> consultarTerremotos(), 0, 60, TimeUnit.SECONDS);
    }

    private static void consultarTerremotos() {
        try {
            HttpClient cliente = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();
            HttpRequest solicitud = HttpRequest.newBuilder().uri(URI.create(URL)).timeout(Duration.ofSeconds(20)).GET().build();
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() != 200) {
                System.out.println("Error HTTP: " + respuesta.statusCode());
                return;
            }

            JSONObject datos = new JSONObject(respuesta.body());
            JSONArray eventos = datos.getJSONArray("features");

            System.out.println("\n========== " + FECHA.format(Instant.now()) + " ==========");
            System.out.println("Eventos encontrados: " + eventos.length());

            for (int i = 0; i < eventos.length(); i++) {
                JSONObject evento = eventos.getJSONObject(i);
                JSONObject propiedades = evento.getJSONObject("properties");
                JSONArray coordenadas = evento.getJSONObject("geometry").getJSONArray("coordinates");

                double magnitud = propiedades.optDouble("mag", 0);
                String lugar = propiedades.optString("place", "Ubicación desconocida");
                long tiempo = propiedades.optLong("time", 0);
                double longitud = coordenadas.getDouble(0);
                double latitud = coordenadas.getDouble(1);
                double profundidad = coordenadas.length() > 2 ? coordenadas.getDouble(2) : 0;

                System.out.printf(Locale.US, "M %.1f | %s | %s | Lat %.4f Lon %.4f | Prof. %.1f km%n",
                        magnitud, FECHA.format(Instant.ofEpochMilli(tiempo)), lugar, latitud, longitud, profundidad);
            }
        } catch (Exception e) {
            System.out.println("No fue posible actualizar los terremotos: " + e.getMessage());
        }
    }
}
