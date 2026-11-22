package cl.voltech.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.voltech.pedidos.model.PedidoItem;

@Repository
public interface PedidoItemRepo extends JpaRepository<PedidoItem, Integer> {
  List<PedidoItem> findByPedidoId(int pedidoId);
}