package com.Manevy.Compras.Repository;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="VentaAPI", url = "http://localhost:8083/Ventas")
public interface IVentaAPI {

    @DeleteMapping("/delete/{IdVenta}")
    public String deleteVenta(@PathVariable("IdVenta") Long IdVenta);
}
