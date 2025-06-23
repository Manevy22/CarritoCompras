package com.Manevy.Compras.Repository;


import com.Manevy.Compras.Model.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ProductoAPI", url = "http://localhost:8081/Producto")
public interface IProductoAPI {

    @GetMapping("/findProducto/{id}")
    public Producto getProducto(@PathVariable("id") Long idProducto);
}
