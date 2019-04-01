/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.select2;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

/**
 * Clase que contiene la información de la respuesta para peticiones realazadas
 * por el componente Select2 desde la capa de la vista a nivel de cliente.
 * 
 * Esta clase será convertida a JSON, por lo que cada uno de los nombres de las propiedades
 * debe coincidir con el esperado por el componente Select2.
 * 
 * Esta clase hace uso de {@link ConversionService} para convertir la información recibida a String.
 * 
 * @see https://select2.github.io/examples.html#data-ajax
 *
 * @param <T> El tipo de respuesta
 */
public class Select2DataWithConversion<T> extends Select2DataSupport<T> {

  private final ConversionService conversionService;
  private final Expression parseIdExpression;
  private boolean includeEntireElement;

  /**
   * Crea un nuevo elemento select2 a partir de la información de la petición
   *
   * @param page la información a mostrar
   * @param idExpression la expresión SpEl para el campo id
   * @param conversionService el servicio de conversión
   */
  public Select2DataWithConversion(Page<T> page, String idExpression,
      ConversionService conversionService) {
    super(page);
    Assert.notNull(page, "A ConversionService is required");

    this.conversionService = conversionService;
    TemplateParserContext templateParserContext = new TemplateParserContext();
    ExpressionParser parser = new SpelExpressionParser();
    parseIdExpression = parser.parseExpression(idExpression, templateParserContext);
    // By default, the entire element will not be included in the response
    this.includeEntireElement = false;
  }

  /**
   * Crea una nueva respuesta para select2 con el elemento completo obtenido.
   *
   * @param page la información a mostrar
   * @param idExpression la expresión SpEl para el campo id
   * @param conversionService el servicio de conversión
   * @param includeEntireElement true si se quiere incluir el elemento completo en formato JSON en la respuesta
   * select2. De esta forma, los desarrolladores tendrán más información sobre el elemento obtenido. DEFAULT: false
   */
  public Select2DataWithConversion(Page<T> page, String idExpression,
      ConversionService conversionService, boolean includeEntireElement) {
    this(page, idExpression, conversionService);
    this.includeEntireElement = includeEntireElement;
  }


  /**
   * {@inheritDoc}
   * 
   * Crea el dato del elemento con el id y el texto.
   */
  @Override
  protected Data<T> createData(T element) {
    String id = getIdAsString(element);
    String text = getAsString(element);

    if (includeEntireElement) {
      return new Data<T>(id, text, element);
    }

    return new Data<T>(id, text);
  }

  /**
   * {@inheritDoc}
   * 
   * Obtiene la cadena del elemento a través del servicio de conversión.
   */
  @Override
  protected String getAsString(T element) {
    return conversionService.convert(element, String.class);
  }

  /**
   * {@inheritDoc}
   * 
   * Obtiene el id del elemento a través del parser de ids.
   */
  @Override
  protected String getIdAsString(T element) {
    return parseIdExpression.getValue(element, String.class);
  }
  
}
// end::select2DataWithConversion[]