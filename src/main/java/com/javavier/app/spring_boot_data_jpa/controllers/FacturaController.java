package com.javavier.app.spring_boot_data_jpa.controllers;

import com.javavier.app.spring_boot_data_jpa.models.entity.Cliente;
import com.javavier.app.spring_boot_data_jpa.models.entity.Factura;
import com.javavier.app.spring_boot_data_jpa.models.entity.ItemFactura;
import com.javavier.app.spring_boot_data_jpa.models.entity.Producto;
import com.javavier.app.spring_boot_data_jpa.models.service.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private IClienteService clienteService;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id,
                      Model model,
                      RedirectAttributes flash){

        Factura factura = clienteService.findFacturaById(id);

        if (factura == null){
            flash.addFlashAttribute("error", "La factura no existe en la base de datos");
            return "redirect:/listar";
        }
        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Factura: " + factura.getDescripcion());
        return "factura/ver";

    }


    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId,
                        Map<String, Object> model,
                        RedirectAttributes flash){

        Cliente cliente = clienteService.findOne(clienteId);
        if (cliente == null){
            flash.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);
        model.put("factura", factura);
        model.put("titulo", "Crear Factura");

        return "factura/form";
    }

    @GetMapping(value="/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        //System.out.println("001 buscar: " + term);
        List<Producto> listadoProductos = clienteService.findByNombre(term);
        //System.out.println(listadoProductos.size());
        return listadoProductos;
    }

    @PostMapping("/form")
    public String guardar(Factura factura,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name="cantidad_[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status){

        //System.out.println("Paso 001 ");

        for (int i = 0; i < itemId.length; i++){
            Producto producto = clienteService.findProductoById(itemId[i]);

            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.addItemFactura(linea);

            log.info("Id: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
        }

        clienteService.saveFactura(factura);

        status.setComplete();

        flash.addFlashAttribute("success", "Factura creada con éxito");

        return "redirect:/ver/" + factura.getCliente().getId();
    }

}
