/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción a utilizar cuando no se encuentra un recurso.
 *
 * Al estar anotada con @ResponseStatus, al lanzarse se enviará un código de
 * estado HTTP 404 indicando que el registro al que intenta acceder no ha sido
 * encontrado.
 *
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNoExisteException extends RuntimeException {
  
  /** Identifica la clase cuando es serializada */
  private static final long serialVersionUID = 8206218583051711301L;

  /**
   * Construye una nueva excepción con el mensaje especificado.
   *
   * @param mensaje Mensaje de la excepción.
   */
  public RecursoNoExisteException(String mensaje) {
    super(mensaje);
  }

}
