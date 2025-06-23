package com.Manevy.Productos.Controller;


import com.Manevy.Productos.Model.Producto;
import com.Manevy.Productos.Service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Producto")
public class ProductoController {

    @Autowired
    private IProductoService servis;


    @GetMapping("/AllProducts")
    public ResponseEntity<List<Producto>> getProductos() {
       try{
        List<Producto> productos=servis.getProductos();
        return ResponseEntity.status(HttpStatus.OK).body(productos);
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
     }

   @PostMapping("/create")
    public ResponseEntity<?> saveProducto(@RequestBody Producto producto) {
        try{
            if(servis.saveProducto(producto))
            {
            return ResponseEntity.status(HttpStatus.OK).body(producto);
            }
            else return ResponseEntity.status(HttpStatus.CONFLICT).body("The product with these features already exist!");
        }
        catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
  }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
       try{
        servis.deleteProducto(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product successfully removed");
       }
       catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }

    }

    @GetMapping("/findProducto/{id}")
    public ResponseEntity<Producto> findProducto(@PathVariable Long id) {
        try{

            return ResponseEntity.status(HttpStatus.OK).body(servis.findProducto(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Producto> editProducto(@PathVariable Long id , @RequestBody Producto producto) {
        try {
            Producto pro = servis.findProducto(id);
            if (pro != null) {
                pro.setName(producto.getName());
                pro.setBrand(producto.getBrand());
                pro.setPrice(producto.getPrice());
                servis.editProducto(pro);
                return ResponseEntity.status(HttpStatus.OK).body(pro);
            }
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
