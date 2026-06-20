package com.javavier.app.spring_boot_data_jpa.models.dao;

import com.javavier.app.spring_boot_data_jpa.models.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoUsarClienteDaoImpl implements NoUsarIClienteDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Cliente> findAll() {
        //System.out.println("paso 001");
        return em.createQuery("from Cliente").getResultList();
    }

    @Override
    public void save(Cliente cliente) {
        //System.out.println("Paso 003: " + cliente.getId() + cliente.getNombre() + cliente.getApellido() + cliente.getEmail());
        if (cliente.getId() != null && cliente.getId() > 0){
            em.merge(cliente);
        } else {
            em.persist(cliente);
        }
    }

    @Override
    public Cliente findOne(Long id) {
        return em.find(Cliente.class, id);
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = findOne(id);
        em.remove(cliente);
    }

}
