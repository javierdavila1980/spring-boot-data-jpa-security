package com.javavier.app.spring_boot_data_jpa.models.dao;

import com.javavier.app.spring_boot_data_jpa.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;


public interface IClienteDao extends CrudRepository<Cliente, Long> {

}
