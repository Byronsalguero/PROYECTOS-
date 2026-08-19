package gt.municipalidad.inventario.controller;

import gt.municipalidad.inventario.model.*;
import gt.municipalidad.inventario.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class InventarioController {
    private final EncargadoRepository encargados;
    private final BienRepository bienes;
    private final MovimientoRepository movimientos;
    public InventarioController(EncargadoRepository e, BienRepository b, MovimientoRepository m){this.encargados=e;this.bienes=b;this.movimientos=m;}

    @GetMapping("/encargados") public List<Encargado> encargados(){return encargados.findAll();}
    @PostMapping("/encargados") public Encargado crearEncargado(@RequestBody Encargado x){return encargados.save(x);}
    @PutMapping("/encargados/{id}") public ResponseEntity<Encargado> editarEncargado(@PathVariable Long id,@RequestBody Encargado x){return encargados.findById(id).map(e->{x.setId(id);return ResponseEntity.ok(encargados.save(x));}).orElse(ResponseEntity.notFound().build());}
    @DeleteMapping("/encargados/{id}") public ResponseEntity<Void> borrarEncargado(@PathVariable Long id){if(!encargados.existsById(id))return ResponseEntity.notFound().build();encargados.deleteById(id);return ResponseEntity.noContent().build();}

    @GetMapping("/bienes") public List<Bien> bienes(){return bienes.findAll();}
    @PostMapping("/bienes") public Bien crearBien(@RequestBody Bien x){return bienes.save(x);}
    @PutMapping("/bienes/{id}") public ResponseEntity<Bien> editarBien(@PathVariable Long id,@RequestBody Bien x){return bienes.findById(id).map(b->{x.setId(id);return ResponseEntity.ok(bienes.save(x));}).orElse(ResponseEntity.notFound().build());}
    @DeleteMapping("/bienes/{id}") public ResponseEntity<Void> borrarBien(@PathVariable Long id){if(!bienes.existsById(id))return ResponseEntity.notFound().build();bienes.deleteById(id);return ResponseEntity.noContent().build();}

    @GetMapping("/movimientos") public List<Movimiento> movimientos(){return movimientos.findAll();}
    @GetMapping("/hoja-responsabilidad/{encargadoId}") public Map<String,Object> hoja(@PathVariable Long encargadoId){
        Encargado e=encargados.findById(encargadoId).orElseThrow(); List<Movimiento> lista=movimientos.findByEncargadoIdOrderByFechaDesc(encargadoId);
        BigDecimal debe=lista.stream().filter(x->x.getTipo()==Movimiento.TipoMovimiento.DEBE).map(Movimiento::getMonto).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal haber=lista.stream().filter(x->x.getTipo()==Movimiento.TipoMovimiento.HABER).map(Movimiento::getMonto).reduce(BigDecimal.ZERO,BigDecimal::add);
        return Map.of("encargado",e,"movimientos",lista,"debe",debe,"haber",haber,"saldo",debe.subtract(haber));
    }
    @PostMapping("/movimientos") public Movimiento movimiento(@RequestBody Movimiento x){
        Bien b=bienes.findById(x.getBien().getId()).orElseThrow();
        int actual=b.getCantidad()==null?0:b.getCantidad();
        int nuevo=x.getTipo()==Movimiento.TipoMovimiento.DEBE?actual+x.getCantidad():actual-x.getCantidad();
        if(nuevo<0) throw new IllegalArgumentException("El HABER no puede superar la existencia disponible");
        b.setCantidad(nuevo); bienes.save(b); return movimientos.save(x);
    }
}
