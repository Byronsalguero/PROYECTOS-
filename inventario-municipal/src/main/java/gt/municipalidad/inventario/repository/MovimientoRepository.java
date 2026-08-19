package gt.municipalidad.inventario.repository;
import gt.municipalidad.inventario.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> { List<Movimiento> findByEncargadoIdOrderByFechaDesc(Long encargadoId); }
