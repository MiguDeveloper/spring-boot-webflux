package pe.ta.springbootwebflux.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.ta.springbootwebflux.models.documents.Producto;

public interface IProductoRepository extends ReactiveMongoRepository<Producto, String> {
}
