package com.javavier.app.spring_boot_data_jpa.models.dao;

import com.javavier.app.spring_boot_data_jpa.models.entity.Cliente;

import java.util.List;

public interface NoUsarIClienteDao {

    public List<Cliente> findAll();
    public void save(Cliente cliente);
    public Cliente findOne(Long id);
    public void delete(Long id);

}
