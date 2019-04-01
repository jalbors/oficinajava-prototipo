/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Page;

import es.gva.oficinajava.prototipo.datatables.DatatablesColumns.Column;

/**
 * Estructura de la respuesta para las peticiones del componente Datatables.
 * 
 * La información se convertirá haciendo uso del {@link ConversionService}
 * registrado. Convertirá cada uno de los datos enviados para cada una de las
 * columnas siempre que sea posible.
 * 
 * Tendrá en cuenta el número de columnas a mostrar según la petición realizada
 * por el componente para convertir únicamente aquella información que vaya a
 * ser visualizada.
 * 
 * El {@link Locale} en el que se encuentra el proyecto se tendrá en cuenta, por
 * lo que la información de la respuesta podrá variar según el idioma
 * establecido en la página HTML en el momento de la petición.
 * 
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
public class ConvertedDatatablesData<T>
    extends DatatablesData<Map<String, Object>> {

  private static final TypeDescriptor TYPE_STRING =
      TypeDescriptor.valueOf(String.class);

  /**
   * Crea una respuesta de datatables
   *
   * @param data the data to show
   * @param recordsTotal número total de elementos
   * @param draw cuenta el número de peticiones realizada por el componente
   *        Datatables.
   * @param conversionService ConversionService a utilizar
   */
  public ConvertedDatatablesData(Page<T> data, Long recordsTotal, Integer draw,
      ConversionService conversionService) {
    this(data.getContent(), recordsTotal, data.getTotalElements(), draw,
        conversionService, null);
  }

  /**
   * Crea una respuesta de datatables
   *
   * @param data the data to show
   * @param recordsTotal número total de elementos
   * @param draw cuenta el número de peticiones realizada por el componente
   *        Datatables.
   * @param conversionService ConversionService a utilizar
   * @param columns columnas recibidas en la petición del componente Datatables.
   */
  public ConvertedDatatablesData(Page<T> data, Long recordsTotal, Integer draw,
      ConversionService conversionService, DatatablesColumns columns) {
    this(data.getContent(), recordsTotal, data.getTotalElements(), draw,
        conversionService, columns);
  }

  /**
   * Crea una respuesta de datatables
   *
   * @param data the data to show
   * @param recordsTotal número total de elementos
   * @param recordsFiltered número total de elementos después de aplicar el
   *        filtrado
   * @param draw cuenta el número de peticiones realizada por el componente
   *        Datatables.
   * @param conversionService ConversionService a utilizar
   */
  public ConvertedDatatablesData(List<T> data, Long recordsTotal,
      Long recordsFiltered, Integer draw, ConversionService conversionService) {
    this(data, recordsTotal, recordsFiltered, draw, conversionService, null);
  }

  /**
   * Crea una respuesta de datatables
   *
   * @param data the data to show
   * @param recordsTotal número total de elementos
   * @param recordsFiltered número total de elementos después de aplicar el
   *        filtrado
   * @param draw cuenta el número de peticiones realizada por el componente
   *        Datatables.
   * @param conversionService ConversionService a utilizar
   * @param columns columnas recibidas en la petición del componente Datatables.
   */
  public ConvertedDatatablesData(List<T> data, Long recordsTotal,
      Long recordsFiltered, Integer draw, ConversionService conversionService,
      DatatablesColumns columns) {
    super(convertAll(data, conversionService, columns), recordsTotal,
        recordsFiltered, draw, null);
  }

  private static List<Map<String, Object>> convertAll(List<?> data,
      ConversionService conversionService, DatatablesColumns columns) {

    if (data == null) {
      return new ArrayList<Map<String, Object>>();
    }

    List<Map<String, Object>> converted = new ArrayList<>(data.size());

    for (Object value : data) {
      Map<String, Object> convertedValue =
          columns == null ? convert(value, conversionService)
              : convert(value, conversionService, columns);
      converted.add(convertedValue);
    }

    return converted;
  }

  private static Map<String, Object> convert(Object value,
      ConversionService conversionService) {

    BeanWrapper bean = new BeanWrapperImpl(value);
    PropertyDescriptor[] properties = bean.getPropertyDescriptors();
    Map<String, Object> convertedValue = new HashMap<>(properties.length);

    for (int i = 0; i < properties.length; i++) {
      String name = properties[i].getName();
      Object propertyValue = bean.getPropertyValue(name);
      if (propertyValue != null && conversionService
          .canConvert(propertyValue.getClass(), String.class)) {
        TypeDescriptor source = bean.getPropertyTypeDescriptor(name);
        String convertedPropertyValue = conversionService
            .convert(propertyValue, source, TYPE_STRING).toString();
        convertedValue.put(name, convertedPropertyValue);
      }
    }

    return convertedValue;
  }

  private static Map<String, Object> convert(Object value,
      ConversionService conversionService, DatatablesColumns columns) {

    BeanWrapper bean = new BeanWrapperImpl(value);
    Map<String, Object> convertedValue = new HashMap<>();

    for (Column column : columns.getColumns()) {
      String property = column.getData();
      convertedValue.put(property,
          convertProperty(bean, property, conversionService, convertedValue));
    }

    return convertedValue;
  }

  private static Object convertProperty(BeanWrapper parentBean, String property,
      ConversionService conversionService, Map<String, Object> parentValue) {

    TypeDescriptor source = parentBean.getPropertyTypeDescriptor(property);
    Object propertyValue = parentBean.getPropertyValue(property);

    int dotIndex = property.indexOf('.');
    if (dotIndex > 0) {
      String baseProperty = property.substring(0, dotIndex);
      String childProperty = property.substring(dotIndex + 1);
      Map<String, Object> childValue =
          getChildValue(parentValue, baseProperty, childProperty);
      BeanWrapper childBean =
          new BeanWrapperImpl(parentBean.getPropertyValue(baseProperty));
      Object convertedProperty = convertProperty(childBean, childProperty,
          conversionService, childValue);
      childValue.put(childProperty, convertedProperty);
      return childValue;
    } else {
      return (String) conversionService.convert(propertyValue, source,
          TYPE_STRING);
    }
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> getChildValue(
      Map<String, Object> parentValue, String baseProperty,
      String childProperty) {
    Map<String, Object> childValue = null;

    if (parentValue.containsKey(childProperty)) {
      Object value = parentValue.get(baseProperty);
      if (value instanceof Map<?, ?>) {
        childValue = (Map<String, Object>) value;
      }
    }

    if (childValue == null) {
      childValue = new HashMap<>();
      parentValue.put(baseProperty, childValue);
    }

    return childValue;
  }

}
