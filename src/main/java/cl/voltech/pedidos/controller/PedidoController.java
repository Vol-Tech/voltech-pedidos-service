package cl.voltech.pedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.voltech.pedidos.dto.CambiarEstadoRequest;
import cl.voltech.pedidos.dto.CrearPedidoRequest;
import cl.voltech.pedidos.dto.PedidoResponse;
import cl.voltech.pedidos.service.PedidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

  private final PedidoService pedidoService;

  public PedidoController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  @PostMapping
  public ResponseEntity<PedidoResponse> crearPedido(@Valid @RequestBody CrearPedidoRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crearPedido(request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoResponse> obtenerPedido(@PathVariable("id") int id) {
    return ResponseEntity.ok(pedidoService.obtenerPedido(id));
  }

  @GetMapping("/usuario/{usuarioId}")
  public ResponseEntity<List<PedidoResponse>> obtenerPedidosUsuario(@PathVariable("usuarioId") int usuarioId) {
    return ResponseEntity.ok(pedidoService.obtenerPedidosUsuario(usuarioId));
  }

  @PutMapping("/{id}/estado")
  public ResponseEntity<PedidoResponse> cambiarEstado(
      @PathVariable("id") int id,
      @Valid @RequestBody CambiarEstadoRequest request) {
    return ResponseEntity.ok(pedidoService.cambiarEstado(id, request));
  }
}