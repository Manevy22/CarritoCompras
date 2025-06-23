package com.Manevy.Ventas.Service;


import com.Manevy.Ventas.Model.Venta;
import com.Manevy.Ventas.Repository.ICompraAPI;
import com.Manevy.Ventas.Repository.IVentaRepository;
import com.Manevy.Ventas.VentaDTO.CompraDTO;
import com.Manevy.Ventas.VentaDTO.VentaDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository repo;
    @Autowired
    private ICompraAPI repoapi;

    @Override
    public List<Venta> getVenta() {
        return repo.findAll();
    }


    @Override
    @CircuitBreaker(name="CompraAPI",fallbackMethod = "fallbackgetCompra")
    @Retry(name="CompraAPI")
    public boolean saveVenta(Venta venta) {
        CompraDTO compra = repoapi.getCompra(venta.getIdCompra());
        if (compra == null || compra.getCarrito().isEmpty()) {
            return false;
        }
        if (!repo.existsByIdCompra(venta.getIdCompra())) {
            repo.save(venta);
            this.setearCompraEnVenta(venta.getIdCompra());
            return true;
        }
        return false;
    }

    public CompraDTO fallbackgetCompra(Throwable throwable){
        List<String> listfall=new ArrayList<>();
        return new CompraDTO(0,listfall);
    }

    @CircuitBreaker(name="CompraAPI",fallbackMethod = "fallbacksetearCompraEnVenta")
    @Retry(name="CompraAPI")
    private void setearCompraEnVenta(Long idCompra) {
        Venta venta=repo.findByIdCompra(idCompra);
        repoapi.setearIdVenta(idCompra,venta.getId());
    }

    public void fallbacksetearCompraEnVenta(Throwable throwable){
        System.out.println("Microservices Compras isn´t enable");
    }

    @Override
    public void deleteVenta(Long id) {
    repo.deleteById(id);
    }

    @Override
    public Venta findVenta(Long id) {
       return repo.findById(id).orElse(null);
    }
    @Override
    @CircuitBreaker(name="CompraAPI",fallbackMethod = "fallbackgetCompra")
    @Retry(name="CompraAPI")
    public VentaDTO findVentaDTO(Long id) {
        Venta venta=repo.findById(id).orElse(null);
        VentaDTO vdto=new VentaDTO();
        if(venta!=null){
            CompraDTO compra=repoapi.getCompra(venta.getIdCompra());
            vdto.setPrice(compra.getTotal());
            vdto.setProductos(compra.getCarrito());
        vdto.setId(venta.getId());
        vdto.setFecha(venta.getFecha());
        }
        return vdto;
    }

    @Override
    public boolean editVenta(Long id,Venta venta) {
    Venta vent=this.findVenta(id);
    if(vent!=null){
        if(venta.getFecha()!=null)vent.setFecha(venta.getFecha());
        if(venta.getIdCompra()!=null)vent.setIdCompra(venta.getIdCompra());
        repo.save(vent);
        return true;
    }
    return false;
    }
}
