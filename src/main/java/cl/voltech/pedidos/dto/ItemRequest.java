package cl.voltech.pedidos.dto;

import jakarta.validation.constraints.Positive;

public record ItemRequest(
    @Positive(message = "El producto es obligatorio") int productoId,
    @Positive(message = "La cantidad debe ser mayor a 0") int cantidad) {
}