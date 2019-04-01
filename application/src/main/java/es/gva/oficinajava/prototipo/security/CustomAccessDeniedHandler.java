/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gestión personalizada de la excepción {@link AccessDeniedException}.
 *
 * <p>
 * Si es un servicio REST, devolver código de error HTTP. Sino redirigir a la
 * página de error de ZK o a la de Thymeleaf.
 * </p>
 */
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {

  /** Implementación de logging para mostrar trazas */
  private static final Logger LOG =
      LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

  /**
   * {@inheritDoc}
   * 
   * <p>
   * Si es un servicio REST, devolver código de error HTTP. Sino redirigir a la
   * página de error de ZK o a la de Thymeleaf.
   * </p>
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {

    LOG.warn("Se ha producido una excepción de seguridad: "
        + accessDeniedException.getLocalizedMessage());

    ContentNegotiationStrategy negotiationStrategy =
        new HeaderContentNegotiationStrategy();
    MediaTypeRequestMatcher matcher =
        new MediaTypeRequestMatcher(negotiationStrategy, MediaType.TEXT_HTML);
    matcher.setUseEquals(false);

    if (matcher.matches(request)) {

      // Si es un servicio REST, devolver código de error HTTP
      if (request.getRequestURI().startsWith("/api/")) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN,
            accessDeniedException.getLocalizedMessage());
      } else {
        DefaultRedirectStrategy redirectStrategy =
            new DefaultRedirectStrategy();
        redirectStrategy.setContextRelative(false);

        // Redirigir a la página de error de ZK o a la de Thymeleaf
        if (request.getRequestURI().contains(".zul")) {
          redirectStrategy.sendRedirect(request, response, "/errores/403.zul");
        } else {
          redirectStrategy.sendRedirect(request, response, "/errores/403");
        }
      }
    } else {
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
  }

}
