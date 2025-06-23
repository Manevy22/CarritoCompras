package com.Manevy.Compras.Service;

import com.Manevy.Compras.Model.Compra;
import com.Manevy.Compras.Model.Producto;
import com.Manevy.Compras.Repository.ICompraRepository;
import com.Manevy.Compras.Repository.IProductoAPI;
import com.Manevy.Compras.Repository.IVentaAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCompra implements IServiceCompra {

    @Autowired
    private ICompraRepository repo;
    @Autowired
    IProductoAPI repoApi;
    @Autowired
    IVentaAPI repoApi1;

    @Override
    public List<Compra> getCompras() {
        return repo.findAll();
    }

    @Override
    public void saveCompra(Compra compra) {
        repo.save(compra);
    }

    @Override
    @CircuitBreaker(name="VentaAPI", fallbackMethod = "fallbackDeleteVenta")
    @Retry(name="VentaAPI")
    public void deleteCompra(Long id) {
        repoApi1.deleteVenta(this.findCompra(id).getIdVenta());
        repo.deleteById(id);
    }
    public String fallbackDeleteVenta(Throwable throwable){
        return "No fue posible borrar la Venta";
    }

    @Override
    public Compra findCompra(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void editCompra(Compra compra) {
        repo.save(compra);
    }

    //Aplicar CircuitBreaker

    @Override
    @CircuitBreaker(name="ProductoAPI", fallbackMethod = "fallbackGetProducto")
    @Retry(name="ProductoAPI")
    public void addProduct(Long idCompra,Long idProducto){
        Compra compra=this.findCompra(idCompra);
    //llamar a la API
    Producto producto=repoApi.getProducto(idProducto);
        //se suma su precio al precio total
    compra.setTotal(compra.getTotal()+producto.getPrice());
        //se agrega su nombre y marca a la lista
    compra.getCarrito().add(producto.getBrand()+" "+producto.getName());
    this.editCompra(compra);
    }

    @Override
    @CircuitBreaker(name="ProductoAPI", fallbackMethod = "fallbackGetProducto")
    @Retry(name="ProductoAPI")
    public void removeProduct(Long idCompra,Long idProducto){
        Compra compra=this.findCompra(idCompra);
        //llamar a la API
        Producto producto=repoApi.getProducto(idProducto);
        //se resta su precio al precio total
        compra.setTotal(compra.getTotal()-producto.getPrice());
        //se quita su nombre y marca a la lista
        compra.getCarrito().remove ((producto.getBrand()+" "+producto.getName()));
        this.editCompra(compra);
    }
    public Producto fallbackGetProducto(Throwable throwable){
        return new Producto(9999999L,"Servicio Producto no disponible",null,0);
    }
}
