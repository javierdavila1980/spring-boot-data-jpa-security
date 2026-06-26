package com.javavier.app.spring_boot_data_jpa.controllers;

import com.javavier.app.spring_boot_data_jpa.models.entity.Cliente;
import com.javavier.app.spring_boot_data_jpa.models.service.IClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Map;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IClienteService clienteService;

    @Secured("ROLE_USER")
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash){
        Cliente cliente = clienteService.findOne(id);
        if (cliente == null){
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Detalle del cliente: " + cliente.getNombre());
        return "ver";
    }


    @RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
    public String listar(Model model, Authentication authentication, HttpServletRequest request){

        if (authentication != null){
            logger.info("Hola Usuario: ".concat(authentication.getName()));
        }

        // Otra forma estática de acceder al los datos del usuario autenticado:
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            logger.info("Hola Usuario (SecurityContextHolder): ".concat(authentication.getName()));
        }

        if (hasRole("ROLE_ADMIN")) {
            logger.info("El Usuario: ".concat(authentication.getName()).concat(" SI tiene acceso"));
        } else {
            logger.info("El Usuario: ".concat(authentication.getName()).concat(" NO tiene acceso"));
        }

        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        if (securityContext.isUserInRole("ADMIN")) {
            logger.info("Usando SecurityContextHolderAwareRequestWrapper: ".concat(authentication.getName()).concat(" SI tiene acceso"));
        } else {
            logger.info("Usando SecurityContextHolderAwareRequestWrapper: ".concat(authentication.getName()).concat(" NO tiene acceso"));
        }

        // otra forma de validar el role:
        if (request.isUserInRole("ROLE_ADMIN")) {
            logger.info("Usando HttpServletRequest: ".concat(authentication.getName()).concat(" SI tiene acceso"));
        } else {
            logger.info("Usando HttpServletRequest: ".concat(authentication.getName()).concat(" NO tiene acceso"));
        }


        model.addAttribute("titulo", "Listado de  Clientes");
        model.addAttribute("clientes", clienteService.findAll() );
        return "listar";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form")
    public String crear(Map<String, Object> model){
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", "Formulario de Cliente");
        return "form";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model){
        Cliente cliente = null;
        if (id > 0){
            cliente = clienteService.findOne(id);
        } else {
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "form";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status){

        if (result.hasErrors()){
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }
        //System.out.println("Paso 002: " + cliente.getNombre() + cliente.getApellido() + cliente.getEmail());
        clienteService.save(cliente);
        status.setComplete();
        return "redirect:/listar";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id){
        if (id > 0){
            clienteService.delete(id);
        }
        return "redirect:/listar";
    }

    private boolean hasRole(String role){

        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null){
            return false;
        }

        Authentication auth = context.getAuthentication();

        if (auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (role.equals(authority.getAuthority())) {
                logger.info("Hola usuario: ".concat(auth.getName()).concat(" tu ROL es: ").concat(authority.getAuthority()));
                return true;
            }
        }

        return false;
    }


}
