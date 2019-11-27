package pe.ta.springbootwebflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pe.ta.springbootwebflux.models.documents.Producto;
import pe.ta.springbootwebflux.models.repository.IProductoRepository;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

    @Autowired
    private IProductoRepository productoRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flux.just(
                new Producto("Tv Panasonic", 634.5),
                new Producto("Sony Camara", 2323.2),
                new Producto("Apple ipod", 253.4),
                new Producto("Hewlet packart", 984.3),
                new Producto("Bianchi bicicleta", 648.7),
                new Producto("HP Notebook", 9474.9),
                new Producto("TV Sony bravia", 654.0)
        )
        .flatMap(producto -> productoRepository.save(producto))
        .subscribe(producto -> log.info("insert: " + producto.getId() + " " + producto.getNombre()));
    }
}
