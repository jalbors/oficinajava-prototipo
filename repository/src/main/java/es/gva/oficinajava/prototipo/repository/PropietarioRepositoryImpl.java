/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Propietario;

/**
 * Implementación de consultas dinámicas en la entidad {@link Propietario}
 *
 * <p>
 * Añade la implementación de métodos con consultas dinámicas para el
 * repositorio de la entidad {@link Propietario}.
 * </p>
 * <p>
 * Si se quiere crear un método con consultas dinámicas se deber añadir a la
 * interfaz {@link PropietarioRepositoryCustom} y su implementación añadirla aquí.
 * </p>
 *
 * Para más información ver:
 * <ul>
 * <li><a href=
 * "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories"/>Repositories</a>
 * </li>
 * </ul>
 *
 * @author cording at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Transactional(readOnly = true)
public class PropietarioRepositoryImpl extends QueryDslRepositorySupport
    implements PropietarioRepositoryCustom {

  /**
   * Constructor de la clase PropietarioRepositoryImpl
   */
  public PropietarioRepositoryImpl() {
    super(Propietario.class);
  }

  @Override
  public void detach(Propietario propietario) {
    if (propietario != null) {
      getEntityManager().detach(propietario);
    }
  }

}
