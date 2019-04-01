/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.repository;

import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Propietario;

/**
 * Interfaz para consultas dinámicas del repositorio de la entidad
 * {@link Propietario}
 *
 * <p>
 * Añade los métodos con consultas dinámicas para el repositorio de la entidad
 * {@link Propietario}.
 *
 * Si se quiere crear un método con consultas dinámicas se deber añadir en esta
 * interfaz y su implementación añadirla en {@link PropietarioRepositoryImpl}.
 * </p>
 *
 * Para más información ver:
 * <ul>
 * <li><a href=
 * "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories"/>Repositories</a>
 * </li>
 * </ul>
 *
 * @author cordin at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Transactional(readOnly = true)
public interface PropietarioRepositoryCustom {

  /**
   * Desconecta un Propietario del sistema de persistencia.
   * 
   * @param propietario Propietario.
   */
  void detach(Propietario propietario);

}
