package cl.voltech.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CambiarEstadoRequest(
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "PENDIENTE|PAGADO|RECHAZADO", message = "El estado debe ser PENDIENTE, PAGADO o RECHAZADO")
    String estado
) {
}