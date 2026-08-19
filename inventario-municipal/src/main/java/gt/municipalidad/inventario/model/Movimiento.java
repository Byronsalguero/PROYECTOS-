package gt.municipalidad.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Getter @Setter @NoArgsConstructor
public class Movimiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) private Bien bien;
    @ManyToOne(optional=false) private Encargado encargado;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private TipoMovimiento tipo;
    @Column(nullable=false) private Integer cantidad;
    @Column(precision=12, scale=2, nullable=false) private BigDecimal monto;
    @Column(nullable=false) private LocalDate fecha = LocalDate.now();
    private String observaciones;
    public enum TipoMovimiento { DEBE, HABER }
}
