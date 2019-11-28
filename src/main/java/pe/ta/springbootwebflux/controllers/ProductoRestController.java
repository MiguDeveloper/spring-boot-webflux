package pe.ta.springbootwebflux.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.ta.springbootwebflux.models.documents.Producto;
import pe.ta.springbootwebflux.models.repository.IProductoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private IProductoRepository productoRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class);

    @GetMapping
    public Flux<Producto> index() {
        Flux<Producto> productos = productoRepository.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).doOnNext(prod -> log.info(prod.getNombre()));

        return productos;
    }

    @GetMapping("/{id}")
    public Mono<Producto> getProducto(@PathVariable String id) {
        // Primera forma de hacerlo, mas rapida e inmediata
        // Mono<Producto> producto = productoRepository.findById(id);

        // Segunda forma: solo con fines educativos
        Flux<Producto> productos = productoRepository.findAll();

        Mono<Producto> producto = productos.filter(p -> p.getId().equals(id))
                .next()
                .doOnNext(prod -> log.info(prod.getNombre()));

        return producto;
    }
}
