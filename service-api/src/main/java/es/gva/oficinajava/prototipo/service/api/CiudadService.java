/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gva.oficinajava.prototipo.model.Ciudad;

/**
 *
 * API del servicio para la entidad {@link Ciudad}
 *
 * <p>
 * Interfaz que contiene las operaciones referentes a la entidad
 * {@link Ciudad}.
 * </p>
 *
 *
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 *
 */
public interface CiudadService {


  /**
   * Devuelve listado de todas las {@link Ciudad} paginadas
   * según el parámetro proporcionado.
   *
   * @param pageable Página del listado a obtener.
   * @return listado de Ciudades.
   */
  Page<Ciudad> findAll(Pageable pageable);

  /**
   * Devuelve un único registro de {@link Ciudad}
   * 
   * @param id Identificador de la ciudad.
   * @return Ciudad obtenida.
   */
  Ciudad findOne(Long id);

}
