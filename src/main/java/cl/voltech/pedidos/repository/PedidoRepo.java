package cl.voltech.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.voltech.pedidos.model.Pedido;

@Repository
public interface PedidoRepo extends JpaRepository<Pedido, Integer> {
  List<Pedido> findByUsuarioIdOrderByFechaCreacionDesc(int usuarioId);
}