/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 
 * Extiende el valor de {@link PageRequest} para crear una nueva instancia del objeto
 * {@link Pageable} basada en los parámetros de paginación recibidos por el componente
 * DataTables.
 *
 * Gracias a este componente, es posible introducir paginación de DataTables en las 
 * consultas de Spring Data.
 * 
 * @see {@link https://datatables.net/manual/server-side}
 * 
 */
public class DatatablesPageable extends PageRequest {

  private static final long serialVersionUID = -5222098249548875453L;

  public DatatablesPageable(Pageable pageable) {
    super(pageable.getPageNumber() / pageable.getPageSize(), pageable.getPageSize(),
        pageable.getSort());
  }

}
//end::datatablesPageable[]