package com.dam2.ud7.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.dam2.ud7.models.Usuario;
import com.dam2.ud7.service.ServiceImplementationUsuario;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {

	@Autowired
	private ServiceImplementationUsuario siu;

	@GetMapping({ "/login", "/" })
	public String login(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return "redirect:/usuarios";
		} else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
			return "redirect:/cursos";
		}
		return "login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "register";
	}

	@PostMapping("/register/guardar")

	public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult rsult, Model model,
			SessionStatus status) {
		for (Usuario u : siu.findAll()) {
			if (u.getUsername().equalsIgnoreCase(usuario.getUsername())) {
				return "register";
			}
		}
		usuario.setEnabled(true);
		usuario.setRole("ROLE_USER");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		siu.save(usuario);
		return "login";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@GetMapping(value = "/usuarios/crear")
	public String crearUsuario(Model model) {
		model.addAttribute("titulo", "Crear usuario");
		model.addAttribute("usuario", new Usuario());
		return "crearUsuarioForm";
	}

	@PostMapping(value = "/usuarios/guardar")
	public String guardarCurso(@Valid Usuario usuario, BindingResult result, Model model,
			@RequestPart("file") MultipartFile imagen, @RequestParam("password") String password, SessionStatus status) {

//		for (Usuario u : siu.findAll()) {
//			if (u.getUsername().equalsIgnoreCase(usuario.getUsername())) {
//				return "redirect:/usuarios/editar/miperfil";
//			}
//		}

		if (usuario.getRole() == null) {
			usuario.setRole("ROLE_USER");
		}

		
		
		System.out.println(imagen.getOriginalFilename());

		if (!imagen.isEmpty()) {
			Path directorioImagenes = Paths.get("src//main//resources//static//img");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

			try {
				byte[] bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);

				usuario.setImagen(imagen.getOriginalFilename());

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		if (!password.isEmpty()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			usuario.setPassword(encoder.encode(password));
		} else {
			usuario.setPassword(siu.findByUsername(usuario.getUsername()).getPassword());
		}
			
		status.setComplete();
		siu.save(usuario);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return "redirect:/usuarios";
		}
		return "redirect:/cursos";
	}

	@GetMapping(value = "/usuarios/editar/{id}")
	public String editarPorId(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Optional<Usuario> usuario = null;

		if (id > 0) {
			usuario = siu.findById(id);
			if (usuario.isEmpty()) {
				return "redirect:/cursos";
			}
		} else {
			return "redirect:/cursos";
		}
		model.put("usuario", usuario.get());
		model.put("titulo", "Editar usuario");
		return "crearUsuarioForm";
	}

	@GetMapping("/usuarios/editar/miperfil")
	public String modificarMiperfil(Model model) {
		String username = currentUser();
		Usuario u = siu.findByUsername(username);
		model.addAttribute("usuario", u);
		model.addAttribute("titulo", "Editar mi usuario");
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