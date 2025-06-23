package com.Manevy.Compras.Service;

import com.Manevy.Compras.Model.Compra;
import com.Manevy.Compras.Model.Producto;

import java.util.List;

public interface IServiceCompra {
    public List<Compra> getCompras();
    public void saveCompra(Compra compra);
    public void deleteCompra(Long id);
    public Compra findCompra(Long id);
    public void editCompra(Compra compra);
    public void addProduct(Long idCompra,Long idProducto);
    public void removeProduct(Long idCompra,Long idProducto);
}
