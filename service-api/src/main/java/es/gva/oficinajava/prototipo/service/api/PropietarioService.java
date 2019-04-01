/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.service.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gva.oficinajava.prototipo.model.Propietario;
import es.gva.oficinajava.prototipo.model.PropietarioInfo;

/**
 *
 * API del servicio para la entidad {@link Propietario}
 *
 * <p>
 * Interfaz que contiene las operaciones referentes a la entidad
 * {@link Propietario}.
 * </p>
 *
 *
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 *
 */
public interface PropietarioService {


  // === Métodos de CRUD

  /**
   * Almacena los datos de la instancia proporcionada de la entidad
   * {@link Propietario}.
   *
   * @param propietario la instancia a almacenar de la entidad.
   * @return los datos de la instancia almacenada.
   */
  Propietario save(Propietario propietario);
  
  /**
   * Desvincula el elemento de la persistencia y lo guarda.
   * 
   * @param propietario Propietario a actualizar.
   * @return Propietario actualizado.
   */
  Propietario update(Propietario propietario);

  /**
   * Elimina un registro de la entidad {@link Propietario}.
   *
   * @param id identificador referente al registro que se va eliminar.
   */
  void delete(Long id);

  // === Métodos de CRUD en batch

  /**
   * Almacena en batch las instancias proporcionadas de la entidad
   * {@link Propietario}.
   *
   * @param propietarios instancias a almacenar.
   * @return lista de instancias almacenadas.
   */
  List<Propietario> save(Iterable<Propietario> propietarios);

  /**
   * Elimina en batch los registros de la entidad {@link Propietario} que
   * coincidan con una serie de identificadores.
   *
   * @param ids identificadores de los registros a eliminar.
   */
  void delete(Iterable<Long> ids);

  // === Buscadores
  
  /**
   * Devuelve listado de todos los propietarios {@link PropietarioInfo}
   *
   * @return listado de propietarios
   */
  List<PropietarioInfo> findAllPropietarioInfo();

  /**
   * Devuelve listado de todos los propietarios {@link PropietarioInfo} paginados
   * según los parámetros recibidos.
   * 
   * @param pageable Página a obtener en el listado.
   * @return Página del listado de información de propietarios.
   */
  Page<PropietarioInfo> findAllPropietarioInfo(Pageable pageable);
  
  /**
   * Devuelve todas las instancias almacenadas de la entidad
   * {@link Propietario}.
   *
   * @return lista de todas las instancias almacenadas.
   */
  List<Propietario> findAll();

  /**
   * Devuelve {@link Propietario}s cuyo last name empieza por el parámetro
   * proporcionado.
   *
   * @param apellidos Valor por el que buscar.
   * @return lista de propietarios o vacía si no se encuentran
   */
  List<Propietario> findByApellidos(String apellidos);

  /**
   *
   * Recupera un objeto de tipo {@link Propietario} por su id
   *
   * @param id the id to search for
   * @return the {@link Propietario} if found
   *
   */
  Propietario findById(Long id);

  /**
   * Devuelve una instancia de la entidad {@link Propietario} a través de su
   * identificador.
   *
   * @param id identificador referente a la instancia que se va a devolver.
   * @return la instancia cuyo identificador está relacionado con el id.
   */
  Propietario findOne(Long id);

  /**
   * Recupera un objeto de tipo {@link Propietario} por su id junto con las
   * relaciones
   *
   * @param id identificador propietario
   * @return el propietario con el id dado o null si no lo encuentra.
   *
   */
  Propietario findOneWithMascotasData(Long id);

  /**
   * Calcula el número de propietarios de un tipo de mascota.
   *
   * @param tipoId identificador del tipo de mascota
   * @return número de propietarios para el tipo de mascota indicado
   *
   */
  long countByTipoId(Long tipoId);
  
  /**
   * Calcula el número de propietarios.
   * 
   * @return número total de propietarios
   */
  long count();

}
