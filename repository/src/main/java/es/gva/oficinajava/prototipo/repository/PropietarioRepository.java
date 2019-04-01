/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Propietario;
import es.gva.oficinajava.prototipo.model.PropietarioInfo;

/**
 * Repositorio Spring Data JPA para la entidad {@link Propietario}
 *
 * <p>
 * Proporciona métodos básicos para la creación, modificación y borrado de una
 * entidad, actualizaciones masivas, soporte de ordenación y paginación, y
 * métodos de consulta.
 * </p>
 * <p>
 * Se deben crear aquí todos los métodos de consulta de la entidad
 * {@link Propietario}.
 * </p>
 *
 * Para más información ver:
 * <ul>
 * <li><a href=
 * "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories"/>Repositories</a>
 * </li>
 * </ul>
 *
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Transactional(readOnly = true)
public interface PropietarioRepository extends PropietarioRepositoryCustom, JpaRepository<Propietario, Long> {

	/**
	 * Ejemplo de consulta con la notación <i>@Query</i> de Spring Data JPA
	 *
	 * <p>
	 * Devuelve {@link Propietario}s cuyo last name empieza por el parámetro
	 * proporcionado.
	 * </p>
	 *
	 * @see <a href=
	 *      "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query">
	 *      Using @Query</a>
	 *
	 * @param apellidos Valor por el que buscar
	 * @return una lista de {@link Propietario}s (o una lista vacía si no se
	 *         encuentra nada)
	 */
	@Query("SELECT DISTINCT propietario FROM Propietario propietario WHERE propietario.apellidos LIKE :apellidos%")
	List<Propietario> findByApellidos(@Param("apellidos") String apellidos);

	/**
	 * Ejemplo de consulta con la notación <i>@Query</i> de Spring Data JPA
	 *
	 * <p>
	 * Recupera un objeto de tipo {@link Propietario} por su id
	 * </p>
	 *
	 * @see <a href=
	 *      "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query">
	 *      Using @Query</a>
	 *
	 * @param id the id to search for
	 * @return the {@link Propietario} if found
	 *
	 */
	@Query("SELECT propietario FROM Propietario propietario left JOIN FETCH propietario.mascotas WHERE propietario.id =:id")
	Propietario findById(@Param("id") Long id);

	/**
	 * Ejemplo de consulta con la notación <i>@Query</i> de Spring Data JPA y uso de
	 * EntityGraph
	 *
	 * <p>
	 * Recupera un objeto de tipo {@link Propietario} por su id junto con las
	 * relaciones especificadas en el EntityGraph Propietario.mascotas.
	 * </p>
	 *
	 * @see <a href=
	 *      "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query">
	 *      Using @Query</a>
	 *
	 * @param id identificador propietario
	 * @return el propietario con el id dado o null si no lo encuentra.
	 *
	 */
	@Query("SELECT propietario FROM Propietario propietario WHERE propietario.id = :id")
	Propietario findOneWithMascotasData(@Param("id") Long id);

	/**
	 * Ejemplo de consulta nativa con la notación <i>@Query</i> de Spring Data JPA
	 *
	 * <p>
	 * Calcula el número de propietarios de un tipo de mascota.
	 * </p>
	 *
	 * @see <a href=
	 *      "http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query">
	 *      Using @Query</a>
	 *
	 * @param typeId identificador del tipo de mascota
	 * @return número de propietarios para el tipo de mascota indicado
	 *
	 */
	@Query(value = "SELECT COUNT(DISTINCT propietarios.ID) FROM PROPIETARIOS propietarios INNER JOIN MASCOTAS mascotas on propietarios.ID=mascotas.PROPIETARIO_ID WHERE mascotas.TIPO_ID = :tipoId", nativeQuery = true)
	long countByTipoId(@Param("tipoId") Long tipoId);

	/**
	 * Ejemplo de proyección a partir de nombre de método
	 *
	 * <p>
	 * Realiza la proyección del listado de todos los propietarios sobre un listado
	 * de DTO's del tipo {@link PropietarioInfo}
	 * </p>
	 *
	 * @return
	 */
	List<PropietarioInfo> findAllProjectedBy();

	/**
	 * Ejemplo de proyección y paginación a partir de nombre de método
	 * 
	 * <p>
	 * Realiza la proyección del listado de todos los propietarios sobre un listado
	 * de DTO's del tipo {@link PropietarioInfo} y pagina los resultados.
	 * </p>
	 * 
	 * @param pageable
	 * @return
	 */
	Page<PropietarioInfo> findAllProjectedBy(Pageable pageable);

	@Query("SELECT apodo FROM Propietario propietario WHERE propietario.apodo LIKE :apodo%")
	List<Propietario> findByApodo(@Param("apodo") String apodo);

}
