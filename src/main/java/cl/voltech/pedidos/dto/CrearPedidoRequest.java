package cl.voltech.pedidos.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record CrearPedidoRequest(
    @Positive(message = "El usuario es obligatorio") int usuarioId,
    @NotEmpty(message = "El pedido debe tener al menos un producto") @Valid List<ItemRequest> items) {
}