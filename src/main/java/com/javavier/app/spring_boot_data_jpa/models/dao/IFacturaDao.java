package com.javavier.app.spring_boot_data_jpa.models.dao;

import com.javavier.app.spring_boot_data_jpa.models.entity.Factura;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
}
