/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
/**
 * Datos con la respuesta necesaria para que sea interpretada por el componente DataTables. 
 * Esta clase será convertida a JSON, por lo que el nombre de las propiedades debe seguir la misma
 * nomenclatura de propiedades que la esperada por el componente DataTables.
 * 
 * @param <T> El tipo de respuesta
 */
/**
 * @author mmartinez at http://www.organizacion.web[ORGANIZACION]
 *
 * @param <T>
 */
public class DatatablesData<T> {

  private List<T> data;
  private Long recordsTotal;
  private Long recordsFiltered;
  private final Integer draw;
  private String error;

  /**
   * Crea una respuesta para el componente DataTables.
   *
   * @param data la información a mostrar.
   * @param recordsTotal el número total de elementos encontrados.
   * @param recordsFiltered el número total de elementos después de aplicar paginación
   * @param draw cuenta del número de peticiones del componente Datatables. Debe recibirse en la petición al
   * método JSON.
   */
  public DatatablesData(List<T> data, Long recordsTotal, Long recordsFiltered, Integer draw) {
    this(data, recordsTotal, recordsFiltered, draw, null);
  }

  /**
   * Crea una respuesta de error para el componente DataTables.
   * 
   * @param draw cuenta del número de peticiones del componente Datatables. Debe recibirse en la petición al
   * método JSON
   * @param error el error producido para informar al usuario.
   */
  public DatatablesData(Integer draw, String error) {
    this(null, null, null, draw, error);
  }

  /**
   * Crea una respuesta para el componente Datatables con la información obtenida en la petición
   * anterior.
   *
   * @param data la información a mostrar
   * @param recordsTotal el número de registros obtenidos
   * @param recordsFiltered el número de registros obtenidos después de aplicar paginación
   * @param draw cuenta del número de peticiones del componente Datatables. Debe recibirse en la petición al método JSON
   * @param error (optional) el error producido para informar al usuario.
   */
  public DatatablesData(List<T> data, Long recordsTotal, Long recordsFiltered, Integer draw,
      String error) {
    this(recordsTotal, recordsFiltered, draw, error);
    this.data = data;
  }

  /**
   * Crea una respuesta para el componente Datatables con la información obtenida en la petición
   * anterior.
   *
   * @param dataPage la página
   * @param recordsTotal el total de registros encontrados
   * @param draw cuenta del número de peticiones del componente Datatables. Debe recibirse en la petición al método JSON
   */
  public DatatablesData(Page<T> dataPage, Long recordsTotal, Integer draw) {
    this(dataPage.getContent(), recordsTotal, dataPage.getTotalElements(), draw);
  }

  protected DatatablesData(Long recordsTotal, Long recordsFiltered, Integer draw, String error) {
    this.recordsTotal = recordsTotal;
    this.recordsFiltered = recordsFiltered;
    this.draw = draw;
    this.error = error;
  }

  /**
   * Obtiene los datos.
   * 
   * @return Datos.
   */
  public List<T> getData() {
    return Collections.unmodifiableList(data);
  }

  /**
   * Obtiene los registros totales.
   * 
   * @return Registros totales.
   */
  public Long getRecordsTotal() {
    return recordsTotal;
  }

  /**
   * Obtiene los registros filtrados.
   * 
   * @return Registros filtrados.
   */
  public Long getRecordsFiltered() {
    return recordsFiltered;
  }

  /**
   * Obtiene el dibujado.
   * 
   * @return Dibujado.
   */
  public Integer getDraw() {
    return draw;
  }

  /**
   * Obtiene el error.
   * 
   * @return Error.
   */
  public String getError() {
    return error;
  }
  
}
