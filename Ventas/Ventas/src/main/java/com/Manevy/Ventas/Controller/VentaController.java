package com.Manevy.Ventas.Controller;
import com.Manevy.Ventas.Model.Venta;
import com.Manevy.Ventas.Service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Ventas")
public class VentaController {

    @Autowired
    private IVentaService servis;

    //getAll
    @GetMapping("/AllVentas")
    public ResponseEntity<?> findAllVentas(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servis.getVenta());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }
    //get
    @GetMapping("/findVenta/{IdVenta}")
    public ResponseEntity<?> findVenta(@PathVariable Long IdVenta){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servis.findVenta(IdVenta));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }
    //delete
    @DeleteMapping("/delete/{IdVenta}")
    public ResponseEntity<String> deleteVenta(@PathVariable Long IdVenta){
        try{
            servis.deleteVenta(IdVenta);
            return ResponseEntity.status(HttpStatus.OK).body("Venta successfully removed");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }


    //update
@PutMapping("/updateVenta/{IdVenta}")
    public ResponseEntity<?> updateVenta(@PathVariable Long IdVenta, Venta venta){
        try{
            if(servis.editVenta(IdVenta, venta)){
                return ResponseEntity.status(HttpStatus.OK).body("Venta successfully updated");
            }
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta with Id "+IdVenta+" wasn´t found");
        }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
}
    //create
    @PostMapping("/create")
    public ResponseEntity<String> createVenta(@RequestBody Venta venta){
        try{
            if(servis.saveVenta(venta)) {
                return ResponseEntity.status(HttpStatus.OK).body("Venta successfully created");
            }
            else return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Venta with that Carrito already exits or that Carrito are empty");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }
    //getVentaInfo
    @GetMapping("/findVentaInfo/{IdVenta}")
    public ResponseEntity<?> findVentaInfo(@PathVariable Long IdVenta){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servis.findVentaDTO(IdVenta));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }
}
