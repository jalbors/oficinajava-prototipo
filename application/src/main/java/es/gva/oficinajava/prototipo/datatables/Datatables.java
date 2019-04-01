/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase de utilidades y valores constantes para integrar el componente
 * DataTables con Spring MVC.
 */
public class Datatables {

  public static final String PARAMETER_DRAW = "draw";

  public static final String PARAMETER_LENGTH = "length";

  public static final String PARAMETER_START = "start";

  private static final String PARAM_COLUMN_PREFIX = "columns[";

  private static final String PARAM_COLUMN_NAME_SUFIX = "][data]";

  private static final String PARAM_ORDER_DIR_SUFIX = "][dir]";

  private static final String PARAM_ORDER_PREFIX = "order[";

  private static final String PARAM_ORDER_COLUMN_SUFIX = "][column]";

  private static final String PARAM_SEARCH_TYPE = "search";

  private static final Pattern COLUMN_INDEX_PATTERN =
      Pattern.compile("columns\\[([0-9]*)?\\]*");

  private static final Pattern COLUMN_TYPE_PATTERN =
      Pattern.compile("columns\\[([0-9]*)?\\]\\[([a-z]*)?\\]*");

  private static final int MATCHER_GROUP = 2;

  /** Implementación de _logging_ para mostrar trazas. */
  private static final Logger LOG = LoggerFactory.getLogger(Datatables.class);


  /**
   * Devuelve el nombre del parámetro que provee el índice de la columna en la
   * lista de columnas de un DataTables.
   * 
   * @param index posición en la lista
   * @return el nombre del parámetro
   */
  public static String orderColumnIndexParameter(int index) {
    return PARAM_ORDER_PREFIX + index + PARAM_ORDER_COLUMN_SUFIX;
  }

  /**
   * Devuelve el nombre del parámetro que indica la ordenación de una columna
   * según el índice proporcionado.
   * 
   * @param index índice de la columna.
   * @return el nombre del parámetro
   */
  public static String orderDirectionParameter(int index) {
    return PARAM_ORDER_PREFIX + index + PARAM_ORDER_DIR_SUFIX;
  }

  /**
   * Devuelve el nombre del parámetro que indica el nombre de la columna.
   * 
   * @param columnPosition índice de la columna.
   * @return el nombre del parámetro
   */
  public static String columnNameParameter(String columnPosition) {
    return PARAM_COLUMN_PREFIX + columnPosition + PARAM_COLUMN_NAME_SUFIX;
  }

  /**
   * Devuelve true si el parámetro proporcionado es un atributo de columna del
   * componente DataTables.
   * 
   * @param parameter nombre de la propiedad
   * @return true si es un atributo de columna
   */
  public static boolean isColumn(String parameter) {
    return parameter != null && parameter.startsWith(PARAM_COLUMN_PREFIX);
  }

  /**
   * Devuelve el índice de la columna.
   * 
   * @param parameter la columna
   * @return el índice de la columna.
   */
  public static int columnIndex(String parameter) {
    if (isColumn(parameter)) {
      Matcher matcher = COLUMN_INDEX_PATTERN.matcher(parameter);
      while (matcher.find()) {
        try {
          return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException ex) {
          // Ignore number, it has a format error or its is not a number
        }
      }
    }
    return -1;
  }

  /**
   * Devuelve el tipo de parámetro de columna.
   * 
   * @param parameter el parámetro de columna
   * @return el parámetro de columna.
   */
  public static ColumnParamType columnParameterType(String parameter) {
    if (isColumn(parameter)) {
      Matcher matcher = COLUMN_TYPE_PATTERN.matcher(parameter);
      while (matcher.find()) {
        String type = matcher.group(MATCHER_GROUP);
        if (PARAM_SEARCH_TYPE.equals(type)
            && parameter.endsWith("[search][regex]")) {
          return ColumnParamType.REGEX;
        }
        try {
          return ColumnParamType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
          LOG.debug("Format error or its is not a valid column type", ex);
          // Ignore type, it has a format error or its is not a valid column
          // type
        }
      }
    }
    return null;
  }


  /**
   * Enumerado que define los tipos de columna de un componente DataTables.
   */
  public enum ColumnParamType {
    DATA, NAME, ORDERABLE, REGEX, SEARCHABLE
  }
}
