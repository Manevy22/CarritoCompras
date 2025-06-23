package com.Manevy.Ventas.Service;

import com.Manevy.Ventas.Model.Venta;
import com.Manevy.Ventas.VentaDTO.VentaDTO;

import java.util.List;

public interface IVentaService {
    public List<Venta> getVenta();
    public boolean saveVenta(Venta venta);
    public void deleteVenta(Long id);
    public Venta findVenta(Long id);
    public boolean editVenta(Long id,Venta venta);
    public VentaDTO findVentaDTO(Long id);
}
