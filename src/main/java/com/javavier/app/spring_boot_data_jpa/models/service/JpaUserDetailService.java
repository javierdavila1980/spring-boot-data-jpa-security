package com.javavier.app.spring_boot_data_jpa.models.service;

import com.javavier.app.spring_boot_data_jpa.models.dao.IUsuarioDao;
import com.javavier.app.spring_boot_data_jpa.models.entity.Role;
import com.javavier.app.spring_boot_data_jpa.models.entity.Usuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    //private Log logger = LoggerFactory.getLogger(JpaUserDetailService.class);
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        //System.out.println("usuario objeto: " + usuario.getUsername());
        if (usuario == null) {
            logger.error("Error login, no existe el usuario: '" + username + "'");
            throw new UsernameNotFoundException("Username '" + username + "' no existe en el sistema");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role : usuario.getRoles()){
            logger.info("Rol: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if (authorities.isEmpty()) {
            logger.error("Error el usuario: '" + username + "' no tiene roles asignados");
            throw new UsernameNotFoundException("Error el usuario: '" + username + "' no tiene roles asignados");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }
}
