package cl.voltech.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.voltech.pedidos.model.Producto;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {
}