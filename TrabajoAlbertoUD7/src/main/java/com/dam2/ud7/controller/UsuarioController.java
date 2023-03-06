package com.dam2.ud7.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dam2.ud7.models.Usuario;
import com.dam2.ud7.service.ServiceImplementationUsuario;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {

	@Autowired
	private ServiceImplementationUsuario siu;

	@GetMapping({ "/login", "/" })
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("usuario" ,new Usuario());
		return "register";
	}
	
	@PostMapping("/register/guardar")
	
	public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult rsult, Model model, SessionStatus status) {
		usuario.setEnabled(true);
		usuario.setRole("ROLE_USER");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		siu.save(usuario);
		return "login";
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		return "redirect:login/?logout";
	}

	@GetMapping(value = "/usuarios/crear")
	public String crearUsuario(Model model) {

		model.addAttribute("titulo", "Crear usuario");
		model.addAttribute("usuario", new Usuario());
		return "crearUsuarioForm";
	}

	@PostMapping(value = "/usuarios/guardar")
	public String guardarCurso(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		status.setComplete();
		usuario.setRole("ROLE_USER");
		usuario.setEnabled(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		siu.save(usuario);
		return "redirect:/usuarios";
	}

	@GetMapping(value = "/usuarios/editar/{id}")
	public String editarPorId(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Optional<Usuario> usuario = null;

		if (id > 0) {
			usuario = siu.findById(id);
		} else {
			return "redirect:/cursos";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Editar usuario");
		return "crearUsuarioForm";
	}
	
	@GetMapping(value = "/usuarios/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		if (id > 0) {
			siu.deleteById(id);
		}
		return "redirect:/usuarios";
	}

	@GetMapping(value = "/usuarios")
	public String listar(Model model) {
		System.out.println(currentUser());
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", siu.findAll());
		return "listarUsuarios";
	}
	
	@ResponseBody
	public String currentUser() {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (principal instanceof UserDetails) {
	        return ((UserDetails) principal).getUsername();
	    } else {
	        return principal.toString();
	    }
	}
	
}