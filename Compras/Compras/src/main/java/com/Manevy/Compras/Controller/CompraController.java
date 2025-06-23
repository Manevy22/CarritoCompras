package com.Manevy.Compras.Controller;


import com.Manevy.Compras.Model.Compra;
import com.Manevy.Compras.Service.IServiceCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Compra")
public class CompraController {

    @Autowired
    private IServiceCompra servis;

    //Create
    @PostMapping("/create")
    public ResponseEntity<String> createCompra(@RequestBody Compra compra){
        try{
            servis.saveCompra(compra);
            return ResponseEntity.status(HttpStatus.OK).body("Purchase created successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }
    //Delete
    @DeleteMapping("/delete/{IdCompra}")
    public ResponseEntity<String> deleteCompra(@PathVariable Long IdCompra){
        try{
            servis.deleteCompra(IdCompra);
            return ResponseEntity.status(HttpStatus.OK).body("Purchase deleted successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }

    //find
    @GetMapping("/findCompra/{IdCompra}")
    public ResponseEntity<?> findCompra(@PathVariable Long IdCompra){
        try{
        return ResponseEntity.status(HttpStatus.OK).body(servis.findCompra(IdCompra));
        }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }

    //findAll
    @GetMapping("/AllCompras")
    public ResponseEntity<?> findAllCompras(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servis.getCompras());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }

    //update
    @PutMapping("/updateCompra/{IdCompra}")
    public ResponseEntity<?> updateCompra(@PathVariable Long IdCompra, @RequestBody Compra compra){
        try{
             Compra comp=servis.findCompra(IdCompra);
            if(comp!=null){
            comp.setTotal(compra.getTotal());
                comp.setCarrito(compra.getCarrito());
                comp.setIdVenta(compra.getIdVenta());
                servis.saveCompra(comp);
            return ResponseEntity.status(HttpStatus.OK).body(comp);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doesn't exits purchase with Id: "+IdCompra);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }
    @PutMapping("/addProducto/{idCompra}/{idProducto}")
    public ResponseEntity<?> addProduct(@PathVariable Long idCompra,@PathVariable Long idProducto){
        try{
            servis.addProduct(idCompra,idProducto);
            return ResponseEntity.status(HttpStatus.OK).body("product successfully added");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }
    @PutMapping("/removeProduct/{idCompra}/{idProducto}")
    public ResponseEntity<?> removeProduct(@PathVariable Long idCompra,@PathVariable Long idProducto){
        try{
            servis.removeProduct(idCompra,idProducto);
            return ResponseEntity.status(HttpStatus.OK).body("product successfully removed");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }
    @PutMapping("/setearVenta/{IdCompra}/{IdVenta}")
    public ResponseEntity<?> setearIdVenta(@PathVariable Long IdCompra, @PathVariable Long IdVenta){
        try{
            Compra comp=servis.findCompra(IdCompra);
            if(comp!=null){
                comp.setIdVenta(IdVenta);
                servis.saveCompra(comp);
                return ResponseEntity.status(HttpStatus.OK).body(comp);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doesn't exits purchase with Id: "+IdCompra);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }


}
