package com.javavier.app.spring_boot_data_jpa.models.dao;

import com.javavier.app.spring_boot_data_jpa.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String username);

}
