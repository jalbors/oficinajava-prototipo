/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Objeto que almacena la información recibida en las peticiones llevadas a cabo por
 * el componente DataTables de la capa de la vista a nivel de cliente.
 * 
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 * @see https://datatables.net/manual/server-side
 *
 */
public class DatatablesColumns {

  final Map<Integer, InternalColumn> columns;

  /**
   * Crea una nueva instancia
   */
  public DatatablesColumns() {
    columns = new TreeMap<>();
  }

  /**
   * Devuelve la lista de columnas y su configuración
   * 
   * @return la lista de columnas y su configuración
   */
  public Iterable<Column> getColumns() {
    List<Column> values = new ArrayList<>(columns.size());
    values.addAll(columns.values());
    return values;
  }

  /**
   * Establece el valor de una columna
   * 
   * @param index el indice de la columna
   * @param data el valor de la columna
   */
  public void setData(int index, String data) {
    getColumn(index).setData(data);
  }

  /**
   * Establece el nombre de una columna
   * 
   * @param index el indice de la columna
   * @param name el nombre de la columna
   */
  public void setName(int index, String name) {
    getColumn(index).setName(name);
  }

  /**
   * Establece el atributo 'orderable' de una columna
   * 
   * @param index indice de la columna
   * @param orderable valor del atributo orderable
   */
  public void setOrderable(int index, boolean orderable) {
    getColumn(index).setOrderable(orderable);
  }

  /**
   * {@inheritDoc}
   * 
   * Muestra las columnas del datatables.
   */
  @Override
  public String toString() {
    return "DatatablesColumns [columns=" + columns + "]";
  }

  private InternalColumn getColumn(int index) {
    InternalColumn column = columns.get(index);
    if (column == null) {
      column = new InternalColumn(index);
      columns.put(index, column);
    }
    return column;
  }

  /**
   * Interfaz que define las operaciones disponibles para una columna. 
   * 
   * @author jcagarcia at http://www.disid.com[DISID Corporation S.L.]
   */
  public static interface Column {
    /**
     * Operación que devuelve el indice de la columna.
     * @return el indice de la columna
     */
    int getIndex();

    /**
     * Operación que devuelve el data de la columna 
     * @return valor data
     */
    String getData();

    /**
     * Operación que devuelve el nombre de la columna
     * @return el nombre de la columna
     */
    String getName();

    /**
     * Operación que indica si la columna es ordenable.
     * @return si la columna es ordenable
     */
    boolean isOrderable();

  }

  /**
   * Implementación de una {@link Column}.
   * 
   */
  private static class InternalColumn implements Column {

    private final int index;
    private String data;
    private String name;
    private boolean orderable;

    public InternalColumn(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }

    public String getData() {
      return data;
    }

    public void setData(String data) {
      this.data = data;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public boolean isOrderable() {
      return orderable;
    }

    public void setOrderable(boolean orderable) {
      this.orderable = orderable;
    }

    @Override
    public String toString() {
      return "InternalColumn [index=" + index + ", data=" + data + ", name=" + name + ", orderable="
          + orderable + "]";
    }

  }

}
