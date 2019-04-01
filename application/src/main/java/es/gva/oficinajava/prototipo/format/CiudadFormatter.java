/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.format;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import es.gva.oficinajava.prototipo.exceptions.RecursoNoExisteException;
import es.gva.oficinajava.prototipo.model.Ciudad;
import es.gva.oficinajava.prototipo.service.api.CiudadService;

/**
 * Conversión de cadena a objeto y viceversa.
 *
 * <p>
 * Implementación de {@link Formatter} para objetos de tipo {@link Ciudad}.
 * Soporte de campo descriptivo por entidad, emplea la propiedad nombre del
 * mismo.
 * </p>
 *
 * <p>
 * Obtiene objetos de tipo {@link Ciudad} a partir de su identificador en
 * formato String. Los objetos se cargan a través del
 * {@link CiudadService}.
 * </p>
 *
 * @author cordin at http://www.disid.com[DISID Corporation S.L.]
 */
public class CiudadFormatter implements Formatter<Ciudad> {

  /** API del servicio para la entidad {@link Ciudad} */
  private final CiudadService ciudadService;

  /**
   * Crea un parser de entidades de tipo {@link Ciudad}.
   *
   * @param ciudadService Servicio gestión entidades {@link Ciudad}.
   */
  public CiudadFormatter(CiudadService ciudadService) {
    this.ciudadService = ciudadService;
  }

  /**
   * Obtiene objetos {@link Ciudad} a partir de su identificador String.
   */
  @Override
  public Ciudad parse(String text, Locale locale) throws ParseException {
    if (text == null || text.trim().isEmpty()) {
      return null;
    }

    Ciudad ciudad = ciudadService.findOne(Long.parseLong(text));
    if (ciudad == null) {
      throw new RecursoNoExisteException(
          String.format("Ciudad con identificador '%s' no encontrado", text));
    }

    return ciudad;
  }

  /**
   * Soporte de campo descriptivo, emplea la propiedad nombre del mismo.
   */
  @Override
  public String print(Ciudad ciudad, Locale locale) {
    return ciudad == null ? null : ciudad.getNombre();
  }

}
// end::ciudadFormatter[]