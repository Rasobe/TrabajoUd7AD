package com.dam2.ud7.controller;

import java.awt.print.Pageable;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dam2.ud7.models.Curso;
import com.dam2.ud7.service.ServiceImplementationCurso;
import com.dam2.ud7.service.ServiceImplementationUsuario;

@Controller
@SessionAttributes("curso")

public class CursoController {

	@Autowired
	private ServiceImplementationCurso sic;
	
	@Autowired
	private ServiceImplementationUsuario siu;

	@GetMapping(value = "/cursos/crear")
	public String crearCurso(Model model) {

		model.addAttribute("titulo", "Crear curso");
		model.addAttribute("curso", new Curso());
		return "crearCursoForm";
	}

	@PostMapping(value = "/cursos/guardar")
	public String guardarCurso(@Valid Curso curso, BindingResult result, Model model, SessionStatus status) {
		status.setComplete();
		curso.setUsuario(siu.findByUsername(currentUser()));
		sic.save(curso);
		return "redirect:/cursos/miscursos";
	}

	@GetMapping(value = "/cursos/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Optional<Curso> curso = null;

		if (id > 0) {
			curso = sic.findById(id);
		} else {
			return "redirect:/listarCursos";
		}
		model.put("curso", curso.get());
		model.put("titulo", "Editar curso");
		return "crearCursoForm";
	}

	@GetMapping(value = "/cursos/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		if (id > 0) {
			sic.deleteById(id);
		}
		return "redirect:/cursos/miscursos";
	}

	@GetMapping(value = "/cursos")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de cursos");
		model.addAttribute("cursos", sic.findAll());
		return "listarCursos";
	}
	
	@GetMapping("/cursos/miscursos")
	public String modificarMiperfil(Model model) {
		model.addAttribute("cursos", sic.findAllByUsuario(siu.findByUsername(currentUser())));
		model.addAttribute("titulo", "Mis cursos");
		return "listarCursos";
	}
	
	@GetMapping(value = "/cursos/{username}")
	public String mostrarCursosDeUsuario(@PathVariable(value = "username") String username, Model model) {
		model.addAttribute("cursos", sic.findAllByUsuario(siu.findByUsername(username)));
		model.addAttribute("titulo", "Cursos de " + username);
		return "listarCursos";
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