package com.dam2.ud7.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dam2.ud7.entity.Usuario;
import com.dam2.ud7.models.UsuarioDAO;

@Controller
@SessionAttributes("usuario")

public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDao;

	@GetMapping(value = "/usuarios/crear")
	public String crearUsuario(Model model) {
		
		model.addAttribute("titulo", "Crear usuario");
		model.addAttribute("usuario", new Usuario());
		return "form";
	}
	
	@PostMapping(value = "/usuarios/guardar")
	public String guardarCurso(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		status.setComplete();
		usuarioDao.save(usuario);
		return "redirect:/usuarios";
	}
	
	@GetMapping(value="/usuarios/editar/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		
		Usuario usuario = null;
		
		if(id > 0) {
			usuario = usuarioDao.findOne(id);
		} else {
			return "redirect:/listar";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Editar usuario");
		return "form";
	}
	
	@GetMapping(value="/usuarios/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		if(id > 0) {
			usuarioDao.delete(id);
		}
		return "redirect:/usuarios";
	}
	
	@GetMapping(value = "/usuarios")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", usuarioDao.findAll());
		return "listar";
	}
}