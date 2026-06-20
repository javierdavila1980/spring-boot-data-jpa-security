package com.javavier.app.spring_boot_data_jpa.models.service;

import com.javavier.app.spring_boot_data_jpa.models.dao.IClienteDao;
import com.javavier.app.spring_boot_data_jpa.models.dao.IFacturaDao;
import com.javavier.app.spring_boot_data_jpa.models.dao.IProductoDao;
import com.javavier.app.spring_boot_data_jpa.models.dao.NoUsarIClienteDao;
import com.javavier.app.spring_boot_data_jpa.models.entity.Cliente;
import com.javavier.app.spring_boot_data_jpa.models.entity.Factura;
import com.javavier.app.spring_boot_data_jpa.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao;

    @Autowired
    private IFacturaDao facturaDao;


    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        //System.out.println("term: " + term);
        return productoDao.findByNombre(term);
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProductoById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }
}
