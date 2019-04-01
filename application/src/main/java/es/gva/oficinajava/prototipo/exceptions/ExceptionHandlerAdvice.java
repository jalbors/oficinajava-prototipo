/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.exceptions;

import es.gva.oficinajava.prototipo.model.EstadoVisitaInvalidoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * Manejador de excepciones de la aplicación.
 *
 * <p>
 * La anotación {@link ControllerAdvice} es detectada automáticamente por Spring
 * MVC y aplicará el aspecto implementado a todos los métodos anotados con
 * {@link RequestMapping}, en el caso de esta clase el aspecto implementado es
 * {@link ExceptionHandler}, gestión de excepciones.
 * </p>
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

  /** Interfaz para resolver mensajes en la aplicación */
  private final MessageSource messageSource;

  /** Implementación de logging para mostrar trazas */
  private static final Logger LOG =
      LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

  /**
   * Constructor de la clase e inyección de dependencias.
   *
   * @param messageSource Mensajes multi idioma.
   */
  @Autowired
  public ExceptionHandlerAdvice(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Gestionar las excepciones de tipo {@link EstadoVisitaInvalidoException}.
   *
   * @param req petición HTTP.
   * @param e excepcion a gestionar
   * @param locale objeto que permite la obtención idioma seleccionado.
   */
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(EstadoVisitaInvalidoException.class)
  public ModelAndView sendEstadoVisitaInvalidoExceptionStatusCode(HttpServletRequest req, EstadoVisitaInvalidoException e, Locale locale) {
	  //mensaje de error
    String error = this.messageSource.getMessage(
        "error_estadoVisitaInvalidoException",
        new Object[] {e.getVisita().getId(), e.getVisita().getDescripcion()},
        locale);

    //poner en el archivo que salga el error de error para encontrarlo sin tener que buscarlo en application.properties
    LOG.error(error, e);

    ModelAndView modeloVista = new ModelAndView();
    modeloVista.addObject("mensaje", error);
    modeloVista.setViewName("errores/409");
    return modeloVista;
  }

}
