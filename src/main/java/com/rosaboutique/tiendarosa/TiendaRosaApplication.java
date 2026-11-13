package com.rosaboutique.tiendarosa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación.
 *
 * @SpringBootApplication activa tres cosas a la vez:
 *  1. Configuración automática (arma Tomcat, JPA, H2... según las dependencias del pom).
 *  2. Escaneo de componentes: busca @RestController, @Service, @Repository
 *     en este paquete y en todos los subpaquetes.
 *  3. Marca esta clase como fuente de configuración.
 */
@SpringBootApplication
public class TiendaRosaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaRosaApplication.class, args);
    }
}
