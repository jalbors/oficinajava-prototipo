/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

/**
 * Data Transfer Object <i>PropietarioInfo</i>.
 *
 * <p>
 * Datos básicos de un propietario
 * </p>
 *
 * Para más información ver:
 * <ul>
 * <li><a href=
 * "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections">Projections</a>
 * </li>
 * </ul>
 *
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 *
 */
public interface PropietarioInfo {

  /** Identificador del propietario */
  Long getId();

  /** Nombre del propietario */
  String getNombre();

  /** Apellidos del propietario */
  String getApellidos();
  
  /** Ciudad del propietario */
  String getCiudadNombre();

  /** Dirección del propietario */
  String getDireccion();

  /** Telefono del propietario */
  String getTelefono();

}
