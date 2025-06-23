package com.Manevy.Ventas.Repository;

import com.Manevy.Ventas.Model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<Venta,Long> {
    //trae un boolean que determina si ya existe una venta con ese id.
    boolean existsByIdCompra(Long IdCompra);

    //Devuelve la venta con ese idCompra
    Venta findByIdCompra(Long idCompra);
}
