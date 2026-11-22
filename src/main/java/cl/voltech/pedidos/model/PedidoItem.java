package cl.voltech.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido_items")

public class PedidoItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "pedido_id", nullable = false)
  private int pedidoId;

  @Column(name = "producto_id", nullable = false)
  private int productoId;

  @Column(name = "cantidad", nullable = false)
  private int cantidad;

  @Column(name = "precio_unitario", nullable = false)
  private int precioUnitario;
}