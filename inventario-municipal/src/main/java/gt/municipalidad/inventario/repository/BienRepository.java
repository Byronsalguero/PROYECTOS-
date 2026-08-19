package gt.municipalidad.inventario.repository;
import gt.municipalidad.inventario.model.Bien;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface BienRepository extends JpaRepository<Bien, Long> { Optional<Bien> findByCodigo(String codigo); }
