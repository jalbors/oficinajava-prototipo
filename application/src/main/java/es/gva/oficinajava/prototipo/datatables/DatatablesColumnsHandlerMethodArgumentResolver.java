/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import es.gva.oficinajava.prototipo.datatables.Datatables.ColumnParamType;

/**
 * Resuelve los parámetros de tipo {@link DatatablesColumns} durante el proceso
 * de binding en los métodos definidos en los controladores.
 * 
 * 
 * Existe un limite en el número de columnas que es posible gestionar para
 * evitar grandes cantidades de parámetros. Cualquier columna cuyo índice sea
 * mayor que el limite establecido será ignorada.
 *
 * See {@link https://datatables.net/manual/server-side}
 * 
 */
public class DatatablesColumnsHandlerMethodArgumentResolver
    implements HandlerMethodArgumentResolver {

  private static final int MAX_COLUMNS = 50;

  private final int maxColumns;

  /**
   * Crea una nueva instancia con un máximo de columnas a resolver.
   */
  public DatatablesColumnsHandlerMethodArgumentResolver() {
    this(MAX_COLUMNS);
  }

  /**
   * Crea una nueva instancia con un máximo de columnas a resolver.
   * 
   * @param maxColumns el máximo número de columnas que es posible resolver
   */
  public DatatablesColumnsHandlerMethodArgumentResolver(int maxColumns) {
    this.maxColumns = maxColumns;
  }

  /**
   * {@inheritDoc}
   * 
   * Parámetros soportados: DatatablesColumns.
   */
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return DatatablesColumns.class.equals(parameter.getParameterType());
  }

  /**
   * {@inheritDoc}
   * 
   * Resolver argumento del mapa de parámetros de la petición.
   */
  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer, NativeWebRequest request,
      WebDataBinderFactory binderFactory) throws Exception {

    return new ColumnsParametersParser(maxColumns, request.getParameterMap())
        .getColumns();
  }

  /**
   * Parsea la configuración de las columnas enviadas por el componente
   * Datatables.
   * 
   * Los parámetros esperados son aquellos que empiezan por "columns[index]".
   * 
   */
  static class ColumnsParametersParser {

    private final int maxColumns;
    private final Map<String, String[]> parameters;

    /**
     * Crea una nueva instancia del parser.
     * 
     * @param maxColumns máximo número de columnas a procesar
     * @param parameterMap los parámetros para procesar
     */
    public ColumnsParametersParser(int maxColumns,
        Map<String, String[]> parameterMap) {
      this.maxColumns = maxColumns;
      this.parameters = parameterMap;
    }

    /**
     * Parsea los parámetros y crea nuevas instancias de
     * {@link DatatablesColumns} que incluyen toda la información proporcionada.
     * 
     * @return a {@link DatatablesColumns} con los parámetros necesarios.
     */
    public DatatablesColumns getColumns() {
      DatatablesColumns columns = new DatatablesColumns();

      Set<Entry<String, String[]>> entrySet = parameters.entrySet();

      for (Entry<String, String[]> entry : entrySet) {
        String parameter = entry.getKey();
        String value = getParameter(parameter);
        if (!StringUtils.isEmpty(value) && Datatables.isColumn(parameter)) {
          addColumnValue(columns, parameter, value);
        }
      }

      return columns;
    }

    private void addColumnValue(DatatablesColumns columns, String parameter,
        String value) {
      ColumnParamType type = Datatables.columnParameterType(parameter);
      int index = Datatables.columnIndex(parameter);
      if (type != null && isValidIndex(index)) {
        switch (type) {
          case DATA:
            columns.setData(index, value);
            break;
          case NAME:
            columns.setName(index, value);
            break;
          case ORDERABLE:
            columns.setOrderable(index, Boolean.valueOf(value));
            break;
          default:
            break;
        }
      }
    }

    private boolean isValidIndex(int index) {
      return index > -1 && index < maxColumns;
    }

    private String getParameter(String name) {
      String[] values = parameters.get(name);
      return (values == null || values.length == 0) ? null : values[0];
    }
  }
}
