/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Ciudad;

/**
 * Repositorio Spring Data JPA para la entidad {@link Ciudad}
 *
 * <p>
 * Proporciona métodos básicos para la creación, modificación y borrado de una
 * entidad, actualizaciones masivas, soporte de ordenación y paginación, y
 * métodos de consulta.
 * </p>
 * <p>
 * Se deben crear aquí todos los métodos de consulta de la entidad
 * {@link Ciudad}.
 * </p>
 *
 * Para más información ver:
 * <ul>
 * <li><a href=
 * "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories"/>Repositories</a>
 * </li>
 * </ul>
 *
 * @see JpaRepository
 *
 * @author Juan Carlos García at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Transactional(readOnly = true)
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

  /**
   * Método que obtiene todas las ciudades paginadas en función de los parámetros
   * obtenidos.
   */
  public Page<Ciudad> findAll(Pageable pageable);

}

