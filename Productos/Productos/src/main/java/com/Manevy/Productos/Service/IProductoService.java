package com.Manevy.Productos.Service;

import com.Manevy.Productos.Model.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> getProductos();
    public Boolean saveProducto(Producto producto);
    public void deleteProducto(Long id);
    public Producto findProducto(Long id);
    public void editProducto(Producto producto);
}
