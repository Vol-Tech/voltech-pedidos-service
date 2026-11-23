package cl.voltech.pedidos.dto;

public record PedidoItemResponse(
    int productoId,
    String nombre,
    String imagenUrl,
    int cantidad,
    int precioUnitario,
    int subtotal) {
}