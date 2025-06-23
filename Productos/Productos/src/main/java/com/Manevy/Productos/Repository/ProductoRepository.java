package com.Manevy.Productos.Repository;

import com.Manevy.Productos.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    boolean existsByNameAndBrandAndPrice(String name, String brand,Double price);
}
