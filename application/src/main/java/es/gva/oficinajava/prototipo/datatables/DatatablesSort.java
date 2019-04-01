/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import java.util.List;

import org.springframework.data.domain.Sort;

/**
 * Extiende a {@link Sort} para ser usado con los parámetros de ordenación del componente
 * DataTables.
 * 
 * De esta forma, es posible utilizar la información de ordenación para crear consultas
 * de Spring Data.
 * 
 * See {@link https://datatables.net/manual/server-side}
 */
public class DatatablesSort extends Sort {

  private static final long serialVersionUID = 5938901146261470479L;

  /**
   * Crea una ordenación por las columnas del DataTables
   * @param orders el listado de ordenación por columnas del Componente Datatables.
   */
  public DatatablesSort(List<Order> orders) {
    super(orders);
  }

}
//end::datatablesSort[]