package org.seekop;


import org.seekop.service.MensajesIdiomasService;
/**
 * Descripción de la clase.
 */
public class Main {
    /**
     * Descripción del método.
     *
     * @param args Argumentos del método.
     */
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MensajesIdiomasService notificacionService = new MensajesIdiomasService("ES", "MX");
        System.out.println(""+notificacionService.obtenerMensaje("Alta prospecto", "Alta", "ErroreMail", "Mensaje de prueba"));
    }
}