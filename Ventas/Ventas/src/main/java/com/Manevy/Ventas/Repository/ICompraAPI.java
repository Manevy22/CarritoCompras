package com.Manevy.Ventas.Repository;

import com.Manevy.Ventas.VentaDTO.CompraDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="CompraAPI", url="http://localhost:8082/Compra")
public interface ICompraAPI {

    @GetMapping("/findCompra/{IdCompra}")
    public CompraDTO getCompra(@PathVariable("IdCompra") Long IdCompra);

    @PutMapping("/setearVenta/{IdCompra}/{IdVenta}")
        public void setearIdVenta(@PathVariable("IdCompra") Long IdCompra, @PathVariable("IdVenta") Long IdVenta);
}
