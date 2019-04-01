/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Controlador encargado de gestionar las peticiones sobre gestión y política de
 * Cookies.
 * 
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 */
@Controller
@RequestMapping("/cookies")
public class CookiesController {

  /**
   * Devuelve la vista HTML "cookies/show.html" que muestra las políticas de
   * cookies de esta aplicación.
   * 
   * @param model
   * @return
   */
  @GetMapping
  public ModelAndView list(Model model) {
    return new ModelAndView("cookies/show");
  }


  /**
   * Método que acepta la política de cookies y redirige a la página origen
   * de la petición
   * 
   * @param model
   * @return
   */
  @GetMapping("/accept")
  public ModelAndView accept(HttpServletRequest request, HttpSession session,
      Model model) {
    // Añadiendo atributo de sesión
    session.setAttribute("cookies_aceptadas", "true");
    
    // Redirección a la página de origen de la petición
    return new ModelAndView(Optional.ofNullable(request.getHeader("Referer"))
        .map(requestUrl -> "redirect:" + requestUrl).orElse("/"));
  }

}
