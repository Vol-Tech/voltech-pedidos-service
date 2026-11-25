package cl.voltech.pedidos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.voltech.pedidos.dto.CambiarEstadoRequest;
import cl.voltech.pedidos.dto.CrearPedidoRequest;
import cl.voltech.pedidos.dto.ItemRequest;
import cl.voltech.pedidos.dto.PedidoItemResponse;
import cl.voltech.pedidos.dto.PedidoResponse;
import cl.voltech.pedidos.exception.EstadoPedidoInvalidoException;
import cl.voltech.pedidos.exception.ResourceNotFoundException;
import cl.voltech.pedidos.exception.StockInsuficienteException;
import cl.voltech.pedidos.mapper.PedidoMapper;
import cl.voltech.pedidos.model.Pedido;
import cl.voltech.pedidos.model.PedidoItem;
import cl.voltech.pedidos.model.Producto;
import cl.voltech.pedidos.repository.PedidoItemRepo;
import cl.voltech.pedidos.repository.PedidoRepo;
import cl.voltech.pedidos.repository.ProductoRepo;

@Service
public class PedidoService {
  private final PedidoRepo pedidoRepo;
  private final PedidoItemRepo pedidoItemRepo;
  private final ProductoRepo productoRepo;

  public PedidoService(PedidoRepo pedidoRepo, PedidoItemRepo pedidoItemRepo, ProductoRepo productoRepo) {
    this.pedidoRepo = pedidoRepo;
    this.pedidoItemRepo = pedidoItemRepo;
    this.productoRepo = productoRepo;
  }

  @Transactional
  public PedidoResponse crearPedido(CrearPedidoRequest request) {
    int total = 0;

    for (ItemRequest item : request.items()) {
      Producto producto = buscarProducto(item.productoId());
      validarStock(producto, item.cantidad());
      total += producto.getPrecio() * item.cantidad();
    }

    Pedido pedido = pedidoRepo.save(PedidoMapper.toModel(request, total));

    for (ItemRequest item : request.items()) {
      Producto producto = buscarProducto(item.productoId());
      pedidoItemRepo.save(PedidoMapper.toItemModel(pedido.getId(), item, producto));
    }

    return armarRespuesta(pedido);
  }

  public PedidoResponse obtenerPedido(int id) {
    return armarRespuesta(buscarPedido(id));
  }

  public List<PedidoResponse> obtenerPedidosUsuario(int usuarioId) {
    List<Pedido> pedidos = pedidoRepo.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    List<PedidoResponse> respuestas = new ArrayList<>();

    for (Pedido pedido : pedidos) {
      respuestas.add(armarRespuesta(pedido));
    }

    return respuestas;
  }

  public PedidoResponse cambiarEstado(int id, CambiarEstadoRequest request) {
    Pedido pedido = buscarPedido(id);

    if ("PAGADO".equals(pedido.getEstado()) && !"PAGADO".equals(request.estado())) {
      throw new EstadoPedidoInvalidoException("Un pedido pagado no puede cambiar de estado");
    }

    pedido.setEstado(request.estado());
    pedidoRepo.save(pedido);

    return armarRespuesta(pedido);
  }

  private PedidoResponse armarRespuesta(Pedido pedido) {
    List<PedidoItem> items = pedidoItemRepo.findByPedidoId(pedido.getId());
    List<PedidoItemResponse> itemsRespuesta = new ArrayList<>();

    for (PedidoItem item : items) {
      Optional<Producto> producto = productoRepo.findById(item.getProductoId());
      String nombre = "Producto no disponible";
      String imagenUrl = null;

      if (producto.isPresent()) {
        nombre = producto.get().getNombre();
        imagenUrl = producto.get().getImagenUrl();
      }

      itemsRespuesta.add(PedidoMapper.toItemResponse(item, nombre, imagenUrl));
    }

    return PedidoMapper.toResponse(pedido, itemsRespuesta);
  }

  private Pedido buscarPedido(int id) {
    return pedidoRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id " + id));
  }

  private Producto buscarProducto(int productoId) {
    return productoRepo.findById(productoId)
        .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + productoId));
  }

  private void validarStock(Producto producto, int cantidad) {
    if (cantidad > producto.getStock()) {
      throw new StockInsuficienteException(
          "Stock insuficiente: quedan " + producto.getStock() + " unidades de " + producto.getNombre());
    }
  }
}