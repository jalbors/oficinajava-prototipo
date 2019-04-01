/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.format;

import es.gva.oficinajava.prototipo.exceptions.RecursoNoExisteException;
import es.gva.oficinajava.prototipo.model.Propietario;
import es.gva.oficinajava.prototipo.service.api.PropietarioService;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Conversión de cadena a objeto y viceversa.
 *
 * <p>
 * Implementación de {@link Formatter} para objetos de tipo {@link Propietario}.
 * Soporte de campo descriptivo por entidad, emplea la propiedad nombre del
 * mismo.
 * </p>
 *
 * <p>
 * Obtiene objetos de tipo {@link Propietario} a partir de su identificador en
 * formato String. Los objetos se cargan a través del
 * {@link PropietarioService}.
 * </p>
 *
 * @author cordin at http://www.disid.com[DISID Corporation S.L.]
 */
public class PropietarioFormatter implements Formatter<Propietario> {

  /** API del servicio para la entidad {@link Propietario} */
  private final PropietarioService propietarioService;

  /**
   * Crea un parser de entidades de tipo {@link Propietario}.
   *
   * @param propietarioService Servicio gestión entidades {@link Propietario}.
   */
  public PropietarioFormatter(PropietarioService propietarioService) {
    this.propietarioService = propietarioService;
  }

  /**
   * Obtiene objetos {@link Propietario} a partir de su identificador String.
   */
  @Override
  public Propietario parse(String text, Locale locale) throws ParseException {
    if (text == null || text.trim().isEmpty()) {
      return null;
    }

    Propietario propietario = propietarioService.findOne(Long.parseLong(text));
    if (propietario == null) {
      throw new RecursoNoExisteException(String
          .format("Propietario con identificador '%s' no encontrado", text));
    }

    return propietario;
  }

  /**
   * Soporte de campo descriptivo, emplea la propiedad nombre del mismo.
   */
  @Override
  public String print(Propietario propietario, Locale locale) {
    return propietario == null ? null : propietario.getNombre();
  }

}
