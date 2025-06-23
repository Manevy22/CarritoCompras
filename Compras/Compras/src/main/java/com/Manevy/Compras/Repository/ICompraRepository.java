package com.Manevy.Compras.Repository;

import com.Manevy.Compras.Model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompraRepository extends JpaRepository<Compra, Long> {
}
