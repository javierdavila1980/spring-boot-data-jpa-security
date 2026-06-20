package com.javavier.app.spring_boot_data_jpa.models.service;

import com.javavier.app.spring_boot_data_jpa.models.entity.Cliente;
import com.javavier.app.spring_boot_data_jpa.models.entity.Factura;
import com.javavier.app.spring_boot_data_jpa.models.entity.Producto;

import java.util.List;

public interface IClienteService {
    public List<Cliente> findAll();
    public void save(Cliente cliente);
    public Cliente findOne(Long id);
    public void delete(Long id);
    public List<Producto> findByNombre(String term);
    public void saveFactura(Factura factura);
    public Producto findProductoById(Long id);
    public Factura findFacturaById(Long id);

}
