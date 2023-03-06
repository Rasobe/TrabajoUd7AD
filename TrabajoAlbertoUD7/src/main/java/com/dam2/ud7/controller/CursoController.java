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

import com.dam2.ud7.models.Curso;
import com.dam2.ud7.service.CursoDAO;

@Controller
@SessionAttributes("curso")

	
public class CursoController {

	@Autowired
	private CursoDAO cursoDao;

	@GetMapping(value = "/cursos/crear")
	public String crearCurso(Model model) {
		
		model.addAttribute("titulo", "Crear curso");
		model.addAttribute("curso", new Curso());
		return "crearCursoForm";
	}
	
	@PostMapping(value = "/cursos/guardar")
	public String guardarCurso(@Valid Curso curso, BindingResult result, Model model, SessionStatus status) {
		status.setComplete();
		cursoDao.save(curso);
		return "redirect:/cursos";
	}
	
	@GetMapping(value="/cursos/editar/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		
		Curso curso = null;
		
		if(id > 0) {
			curso = cursoDao.findOne(id);
		} else {
			return "redirect:/listarCursos";
		}
		model.put("curso", curso);
		model.put("titulo", "Editar curso");
		return "crearCursoForm";
	}
	
	@GetMapping(value="/cursos/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		if(id > 0) {
			cursoDao.delete(id);
		}
		return "redirect:/cursos";
	}
	
	@GetMapping(value = "/cursos")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de cursos");
		model.addAttribute("cursos", cursoDao.findAll());
		return "listarCursos";
	}
	
}