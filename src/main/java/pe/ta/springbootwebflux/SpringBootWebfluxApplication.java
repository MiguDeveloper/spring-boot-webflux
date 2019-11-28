package pe.ta.springbootwebflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import pe.ta.springbootwebflux.models.documents.Producto;
import pe.ta.springbootwebflux.models.repository.IProductoRepository;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

    @Autowired
    private IProductoRepository productoRepository;

    // Utilizamos solo en el ambiente de pruebas
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Eliminamos los productos que ya estan registrados a la hora de ejecutar el proyecto
        mongoTemplate.dropCollection("productos").subscribe();

        Flux.just(
                new Producto("Tv Panasonic", 634.5),
                new Producto("Sony Camara", 2323.2),
                new Producto("Apple ipod", 253.4),
                new Producto("Hewlet packart", 984.3),
                new Producto("Bianchi bicicleta", 648.7),
                new Producto("HP Notebook", 9474.9),
                new Producto("TV Sony bravia", 654.0)
        )
        .flatMap(producto -> {
            producto.setCreateAt(new Date());
            return productoRepository.save(producto);
        })
        .subscribe(producto -> log.info("insert: " + producto.getId() + " " + producto.getNombre()));
    }
}
