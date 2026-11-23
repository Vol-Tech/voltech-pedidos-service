package cl.voltech.pedidos.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
    int id,
    int usuarioId,
    String estado,
    int total,
    LocalDateTime fechaCreacion,
    List<PedidoItemResponse> items) {
}