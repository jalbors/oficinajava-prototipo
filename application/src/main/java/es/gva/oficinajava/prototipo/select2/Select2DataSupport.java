/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.select2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

/**
 * Clase abstracta que contiene la información de la respuesta para peticiones realazadas
 * por el componente Select2 desde la capa de la vista a nivel de cliente.
 *
 * Esta clase será convertida a JSON, por lo que cada uno de los nombres de las propiedades
 * debe coincidir con el esperado por el componente Select2.
 *
 * El componente select2 espera un objeto JSON con las siguientes propiedades:
 * * _results_: El array de datos. Cada elemento tendrá un _id_ y un _text_ a mostrar.
 * * _pagination_: Un objeto con una propiedad _more_ que será true si existen más elementos a mostrar. 
 * available. 
 * 
 * @see https://select2.github.io/examples.html#data-ajax
 *
 * @param <T> Objeto de respuesta
 */
public abstract class Select2DataSupport<T> {

  private final Page<T> page;

  /**
   * Crea una respuesta para el componente select2 según la información obtenida en la petición
   * Hace uso de expresiones SpEL 
   * (http://docs.spring.io/spring/docs/current/spring-framework-reference/html/expressions.html#expressions-templating)
   * para crear la propiedad identificador.
   * Delega la creación de la propiedad _text_ en las implementaciones hijas.  
   *
   * @param page the data to show
   */
  public Select2DataSupport(Page<T> page) {
    this.page = page;
    Assert.notNull(page, "The results list is required");
  }

  /**
   * Retorna los datos a devolver en el componente Select2.
   * @return the data
   */
  public List<Data<T>> getResults() {
    List<T> content = page.getContent();
    List<Data<T>> results = new ArrayList<Data<T>>(content.size());
    for (int i = 0; i < content.size(); i++) {
      Data<T> data = createData(content.get(i));
      results.add(data);
    }
    return results;
  }

  /**
   * Devuelve la información de la paginación para la respuesta.
   * @return información sobre paginación
   */
  public Pagination getPagination() {
    return new Pagination(!page.isLast());
  }

  /**
   * Información que será devuelva. 
   */
  protected static class Data<T> {
    private final String id;
    private final String text;

    /**
     * Contiene toda la información del elemento
     */
    private final T info;

    public Data(String id, String text) {
      this.id = id;
      this.text = text;
      this.info = null;
    }

    /**
     * Constructor que permite proporcionarle el elemento completo. 
     * 
     * @param id
     * @param text
     * @param element
     * 
     */
    public Data(String id, String text, T element) {
      this.id = id;
      this.text = text;
      this.info = element;
    }

    /**
     * @return Identificador.
     */
    public String getId() {
      return id;
    }

    /**
     * @return Texto.
     */
    public String getText() {
      return text;
    }

    /**
     * @return Información.
     */
    public T getInfo() {
      return info;
    }
  }

  /**
   * Información sobre la paginación del elemento select2.
   */
  protected static class Pagination {
    private final boolean more;

    /**
     * Crea una nueva{@link Pagination}.
     * @param more true si existe más elementos en el resto de páginas.
     */
    public Pagination(boolean more) {
      this.more = more;
    }

    /**
     * Si existe más información en el resto de páginas-
     * @return true si existe más elementos en el resto de páginas.
     */
    public boolean isMore() {
      return more;
    }
  }

  /**
   * Crea el dato dato un elemento.
   * 
   * @param element Elemento.
   * @return Dato.
   */
  protected Data<T> createData(T element) {
    String id = getIdAsString(element);
    String text = getAsString(element);
    return new Data<T>(id, text);
  }

  protected abstract String getAsString(T element);

  /**
   * @param element
   * @param parseIdExpression
   * @return
   */
  protected abstract String getIdAsString(T element);
}
// end::select2DataSupport[]