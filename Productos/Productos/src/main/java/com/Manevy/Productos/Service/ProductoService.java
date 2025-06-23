package com.Manevy.Productos.Service;

import com.Manevy.Productos.Model.Producto;
import com.Manevy.Productos.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository repo;

    @Override
    public List<Producto> getProductos() {

        return repo.findAll();

    }

    @Override
    public Boolean saveProducto(Producto producto) {

        if(!repo.existsByNameAndBrandAndPrice(producto.getName(),producto.getBrand(),producto.getPrice())){
            repo.save(producto);
            return true;
        }
       return false;
    }

    @Override
    public void deleteProducto(Long id) {

        repo.deleteById(id);

    }

    @Override
    public Producto findProducto(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void editProducto(Producto producto) {
        repo.save(producto);
    }


}
