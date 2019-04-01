/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resuelve el parámetro de tipo {@link DatatablesPageable} en los métodos del
 * controlador.
 * 
 * Extiende el {@link PageableHandlerMethodArgumentResolver} de Spring Data para
 * establecer los parámetros utilizados por el componente DataTables y así crear
 * un {@link DatatablesPageable}
 * 
 * See {@link https://datatables.net/manual/server-side}
 */
public class DatatablesPageableHandlerMethodArgumentResolver
    extends PageableHandlerMethodArgumentResolver {

  /**
   * Crea una nueva instancia, proveyendo el nombre de los parámetros de
   * paginación.
   */
  public DatatablesPageableHandlerMethodArgumentResolver() {
    super(new DatatablesSortHandlerMethodArgumentResolver());
    setPageParameterName(Datatables.PARAMETER_START);
    setSizeParameterName(Datatables.PARAMETER_LENGTH);
  }

  /**
   * {@inheritDoc}
   * 
   * Parámetros soportados: DatatablesPageable.
   */
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return DatatablesPageable.class.equals(parameter.getParameterType());
  }

  /**
   * {@inheritDoc}
   * 
   * Resolver argumento de paginación.
   */
  @Override
  public DatatablesPageable resolveArgument(MethodParameter methodParameter,
      ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {
    Pageable pageable = super.resolveArgument(methodParameter, mavContainer,
        webRequest, binderFactory);

    return new DatatablesPageable(pageable);
  }

}
