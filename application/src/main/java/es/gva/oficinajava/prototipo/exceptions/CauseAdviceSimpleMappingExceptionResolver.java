/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.exceptions;

import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Sobreescribe {@link SimpleMappingExceptionResolver.buildLogMessage}.
 */
public class CauseAdviceSimpleMappingExceptionResolver
    extends SimpleMappingExceptionResolver {

  /**
   * {@inheritDoc}
   * 
   * Mensaje personalizado de mensaje de log al producirse una excepción.
   */
  @Override
  public String buildLogMessage(Exception e, HttpServletRequest req) {
    return "La URL '".concat(req.getRequestURL().toString())
        .concat("' ha provocado una excepción: \n")
        .concat(e.toString());
  }

}
