/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.datatables;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Clase de configuración para registrar, {@link DatatablesColumnsHandlerMethodArgumentResolver}
 * {@link DatatablesPageableHandlerMethodArgumentResolver} y {@link DatatablesSortHandlerMethodArgumentResolver}
 * como Argument Resolvers.
 * 
 */
@Configuration
public class DatatablesWebMvcConfiguration extends WebMvcConfigurerAdapter {

  /**
   * @return Resolvedor de paginación.
   */
  @Bean
  public DatatablesPageableHandlerMethodArgumentResolver datatablesPageableResolver() {
    return new DatatablesPageableHandlerMethodArgumentResolver();
  }

  /**
   * @return Resolvedor de ordenación.
   */
  @Bean
  public DatatablesSortHandlerMethodArgumentResolver datatablesSortResolver() {
    return new DatatablesSortHandlerMethodArgumentResolver();
  }

  /**
   * @return Resolvedor de columnas.
   */
  @Bean
  public DatatablesColumnsHandlerMethodArgumentResolver datatablesColumnsResolver() {
    return new DatatablesColumnsHandlerMethodArgumentResolver();
  }

  /**
   * Añadir resolvedores.
   */
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(datatablesPageableResolver());
    argumentResolvers.add(datatablesSortResolver());
    argumentResolvers.add(datatablesColumnsResolver());
  }

}
