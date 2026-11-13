package com.rosaboutique.tiendarosa.config;

import com.rosaboutique.tiendarosa.model.Producto;
import com.rosaboutique.tiendarosa.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

/**
 * Carga datos de ejemplo al arrancar, para que el GET ya muestre algo.
 * Como H2 vive en memoria, la base se vacía cada vez que se reinicia la app.
 */
@Configuration
public class DatosIniciales {

    // Un CommandLineRunner se ejecuta una vez, justo después de iniciar Spring.
    @Bean
    CommandLineRunner cargarProductos(ProductoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Producto("Labial Rosa Pétalo", "Bloom", "Labiales", new BigDecimal("24.90"), 40),
                        new Producto("Rubor Algodón de Azúcar", "Candy Glow", "Rubores", new BigDecimal("32.50"), 25),
                        new Producto("Sombra Rosa Cuarzo", "Bloom", "Sombras", new BigDecimal("45.00"), 15),
                        new Producto("Crema Hidratante Peonía", "Pure Rose", "Cuidado facial", new BigDecimal("58.90"), 30)
                ));
            }
        };
    }
}
