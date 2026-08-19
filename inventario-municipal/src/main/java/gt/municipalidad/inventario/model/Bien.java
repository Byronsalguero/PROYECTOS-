package gt.municipalidad.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Getter @Setter @NoArgsConstructor
public class Bien {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false) private String codigo;
    @Column(nullable=false) private String descripcion;
    private String categoria;
    private Integer cantidad = 0;
    @Column(precision=12, scale=2) private BigDecimal valorUnitario = BigDecimal.ZERO;
    private String estado = "ACTIVO";
}
