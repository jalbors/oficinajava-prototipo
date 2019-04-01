/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resuelve los parámetros de tipo {@link DatatablesSort} durante el proceso de
 * binding.
 * 
 * Extiende del componente {@link SortHandlerMethodArgumentResolver} de Spring
 * Data para utilizar los nombres de parámetros propios del componente
 * DataTables, (*order[i]* and *columns[i]*) y de esta forma crear una instancia
 * de {@link DatatablesSort}.
 *
 * Si estos parámetros no están disponibles, se delega en la implementación de
 * Spring Data por defecto.
 * 
 * @see {@link https://datatables.net/manual/server-side}
 */
public class DatatablesSortHandlerMethodArgumentResolver
    extends SortHandlerMethodArgumentResolver {

  private static final int MAX_ORDERED_COLUMNS = 5;

  private final int maxOrderedColumns;

  /**
   * Crea una nueva instancia indicando el máximo número de columnas por el que
   * se puede ordenar.
   */
  public DatatablesSortHandlerMethodArgumentResolver() {
    this(MAX_ORDERED_COLUMNS);
  }

  /**
   * Crea una nueva instancia
   * 
   * @param maxOrderedColumns máximo número de columnas por el que se puede
   *        ordenar.
   */
  public DatatablesSortHandlerMethodArgumentResolver(int maxOrderedColumns) {
    super();
    this.maxOrderedColumns = maxOrderedColumns;
  }

  /**
   * {@inheritDoc}
   * 
   * Resolver argumento de ordenación.
   */
  @Override
  public DatatablesSort resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer, NativeWebRequest request,
      WebDataBinderFactory binderFactory) {

    return new SortParametersParser(maxOrderedColumns,
        request.getParameterMap()).getSort();
  }

  /**
   * 
   * Parsea los parámetros de ordenación del componente DataTables para crear un
   * {@link DatatablesSort} con la información disponible en esos parámetros.
   * 
   * El formato de esos parámetros debe empezar con *order[i]* y *columns[i]*.
   */
  static class SortParametersParser {

    private static final Pattern PATTERN =
        Pattern.compile("order\\[([0-9]*)?\\]\\[column\\]");

    private final int maxColumnCount;
    private final Map<String, String[]> parameters;

    /**
     * Crea una instancia con un número máximo de columnas soportadas para
     * ordenación, para evitar errores o un uso malicioso de los parámetros que
     * provoque un mal comportamiento de la aplicación o un uso excesivo de
     * recursos.
     *
     * @param maxOrderedColumns número máximo de columnas de ordenación
     *        soportadas
     * @param map
     */
    public SortParametersParser(int maxOrderedColumns,
        Map<String, String[]> parameters) {
      super();
      this.maxColumnCount = maxOrderedColumns;
      this.parameters = parameters;
    }

    /**
     * 
     * Devuelve el número de columnas a ordenar.
     * 
     * @return el número de columnas a ordenar.
     */
    int getColumnCount() {

      if (parameters == null || parameters.isEmpty()) {
        return 0;
      }

      int columnCount = -1;
      for (String paramName : parameters.keySet()) {
        int columnNumber = getColumnPosition(paramName);
        if (columnNumber > columnCount) {
          columnCount = columnNumber;
        }
      }

      columnCount++;

      return columnCount > maxColumnCount ? maxColumnCount : columnCount;
    }

    /**
     * Devuelve la posición dada en una parámetro de ordenación de columna de un
     * Datatables. El parámetro debe empezar con *order[i]*, devolviendo el
     * valor numérico de *i*.
     *
     * @param paramName el nombre de la columna.
     * @return la posición del parámetro o -1 si no es un númer valido o el
     *         formato del parámetro no es el esperado.
     */
    static int getColumnPosition(String paramName) {
      Matcher matcher = PATTERN.matcher(paramName);
      while (matcher.find()) {
        try {
          return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException ex) {
          // Ignoramos el numero, es un error de formato o no es un número.
        }
      }
      return -1;
    }

    String getPropertyNameInOrderPosition(int pos) {
      String columnPosition =
          getParameter(Datatables.orderColumnIndexParameter(pos));

      if (columnPosition == null) {
        return null;
      }

      return getParameter(Datatables.columnNameParameter(columnPosition));
    }

    Direction getOrderDirection(int pos) {
      String direction = getParameter(Datatables.orderDirectionParameter(pos));
      if ("desc".equals(direction)) {
        return Direction.DESC;
      }
      return Direction.ASC;
    }

    Order getOrderInPosition(int pos) {
      String propertyName = getPropertyNameInOrderPosition(pos);
      if (propertyName == null) {
        return null;
      }
      Direction direction = getOrderDirection(pos);
      return new Order(direction, propertyName);
    }

    public DatatablesSort getSort() {
      int columnCount = getColumnCount();

      if (columnCount <= 0) {
        return null;
      }

      List<Order> orderList = new ArrayList<Order>(columnCount);

      for (int i = 0; i < columnCount; i++) {
        Order order = getOrderInPosition(i);
        if (order != null) {
          orderList.add(order);
        }
      }

      if (orderList.isEmpty()) {
        return null;
      }

      return new DatatablesSort(orderList);
    }

    private String getParameter(String name) {
      String[] values = parameters.get(name);
      return (values == null || values.length == 0) ? null : values[0];
    }
  }

}
