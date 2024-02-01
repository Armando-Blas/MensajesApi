package org.seekop.service;

import com.seekop.v10.googlecloud.GoogleTranslation;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Servicio para obtener mensajes en diferentes idiomas.
 */
public class MensajesIdiomasService {
    private final String idIdioma;
    private final String idpais;

    /**
     * Constructor que inicializa el servicio con un idioma y país específicos.
     *
     * @param idIdioma Idioma para el servicio.
     * @param idpais   País para el servicio.
     */
    public MensajesIdiomasService(String idIdioma, String idpais) {
        this.idIdioma = idIdioma;
        this.idpais = idpais;
    }

    /**
     * Método para obtener un mensaje en el idioma y país configurados.
     *
     * @param idgrupo    Identificador de grupo.
     * @param idproceso  Identificador de proceso.
     * @param idvariable Identificador de variable.
     * @param mensaje    Mensaje a traducir.
     * @return Mensaje traducido.
     */
    public String obtenerMensaje(String idgrupo, String idproceso, String idvariable, String mensaje) {
        String responseData = mensaje;
        try {
            // Construir la URL con los argumentos
            String url = construirUrl(idIdioma, idpais, idgrupo, idproceso, idvariable, mensaje);
            System.out.println(url);

            // Crear una conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            // Configurar la solicitud como GET
            connection.setRequestMethod("GET");

            // Obtener la respuesta
            int responseCode = connection.getResponseCode();

            // Leer la respuesta
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                System.out.println("Peticion Exitosa obtenerMensaje: " + " - " + response.toString());

                // Analizar la respuesta JSON
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Obtener el valor de la propiedad "response"
                responseData = jsonResponse.optString("response", "No se encontró la propiedad 'response'");

            } else {
                System.out.println("Error en la solicitud. Código de respuesta: " + responseCode);
            }
            connection.disconnect();
            return responseData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para construir la URL con los argumentos.
     *
     * @param idIdioma   Idioma para la URL.
     * @param idpais     País para la URL.
     * @param idgrupo    Identificador de grupo para la URL.
     * @param idproceso  Identificador de proceso para la URL.
     * @param idvariable Identificador de variable para la URL.
     * @param mensaje    Mensaje para la URL.
     * @return URL construida.
     */
    private String construirUrl(String idIdioma, String idpais, String idgrupo, String idproceso, String idvariable, String mensaje) {
        String dominio = "seekop10.sicopweb.net";
        String local = "localhost:8080";
        return "http://" + dominio + "/api/10.0.0/api-idiomas/search-message/" +
                "ididioma=" + idIdioma +
                "&idpais=" + idpais +
                "&idgrupo=" + idgrupo +
                "&idproceso=" + idproceso +
                "&idvariable=" + idvariable +
                "&mensaje=" + mensaje;
    }
}
