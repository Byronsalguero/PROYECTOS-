package gt.municipalidad.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor
public class Encargado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String nombre;
    private String puesto;
    private String dependencia;
    private String telefono;
    private Boolean activo = true;
}
