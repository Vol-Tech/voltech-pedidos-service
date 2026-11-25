package cl.voltech.pedidos.mapper;

import java.util.List;

import cl.voltech.pedidos.dto.CrearPedidoRequest;
import cl.voltech.pedidos.dto.ItemRequest;
import cl.voltech.pedidos.dto.PedidoItemResponse;
import cl.voltech.pedidos.dto.PedidoResponse;
import cl.voltech.pedidos.model.Pedido;
import cl.voltech.pedidos.model.PedidoItem;
import cl.voltech.pedidos.model.Producto;

public interface PedidoMapper {

  public static Pedido toModel(CrearPedidoRequest request, int total) {
    return new Pedido(0, request.usuarioId(), "PENDIENTE", total, null);
  }

  public static PedidoItem toItemModel(int pedidoId, ItemRequest item, Producto producto) {
    return new PedidoItem(0, pedidoId, producto.getId(), item.cantidad(), producto.getPrecio());
  }

  public static PedidoItemResponse toItemResponse(PedidoItem item, String nombre, String imagenUrl) {
    return new PedidoItemResponse(
        item.getProductoId(),
        nombre,
        imagenUrl,
        item.getCantidad(),
        item.getPrecioUnitario(),
        item.getPrecioUnitario() * item.getCantidad());
  }

  public static PedidoResponse toResponse(Pedido pedido, List<PedidoItemResponse> items) {
    return new PedidoResponse(
        pedido.getId(),
        pedido.getUsuarioId(),
        pedido.getEstado(),
        pedido.getTotal(),
        pedido.getFechaCreacion(),
        items);
  }
}