/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.config;

import es.gva.oficinajava.prototipo.exceptions.CauseAdviceSimpleMappingExceptionResolver;
import es.gva.oficinajava.prototipo.format.CiudadFormatter;
import es.gva.oficinajava.prototipo.format.PropietarioFormatter;
import es.gva.oficinajava.prototipo.model.Ciudad;
import es.gva.oficinajava.prototipo.model.Propietario;
import es.gva.oficinajava.prototipo.service.api.CiudadService;
import es.gva.oficinajava.prototipo.service.api.PropietarioService;

import io.tracee.binding.springmvc.TraceeInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * Configuración adicional de Spring MVC.
 *
 * <p>
 * Añade configuración adicional a Spring MVC:
 * <ul>
 * <li>Formatter de TipoMascota, Mascota y Propietario.</li>
 * <li>Interceptor de TracEE</li>
 * </ul>
 * </p>
 *
 * @author mmartinez at http://www.disid.com[DISID Corporation S.L.]
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

  /** API del servicio para la entidad {@link Propietario} */
  private final PropietarioService propietarioService;

  /** API del servicio para la entidad {@link Ciudad} */
  private final CiudadService ciudadService;

  /**
   * Construir bean de configuración e inyectar dependencias.
   *
   * @param mascotaService Servicio de gestión de entidades {@link Mascota}
   * @param propietarioService Servicio gestión entidades {@link Propietario}
   * @param ciudadService Servicio gestión entidades {@link Ciudad}
   */
  public WebMvcConfiguration(
      PropietarioService propietarioService, CiudadService ciudadService) {
    super();
    this.propietarioService = propietarioService;
    this.ciudadService = ciudadService;
  }

  /**
   * Formateadores de conversión de cadena a objeto y viceversa.
   */
  @Override
  public void addFormatters(FormatterRegistry registry) {
    super.addFormatters(registry);
    registry.addFormatter(new CiudadFormatter(ciudadService));
    registry.addFormatter(new PropietarioFormatter(propietarioService));
  }


  /**
   * Crea el bean LocalValidatorFactoryBean.
   *
   * @return LocalValidatorFactoryBean Bean.
   */
  @Primary
  @Bean
  public LocalValidatorFactoryBean validator() {
    return new LocalValidatorFactoryBean();
  }

  /**
   * Gestionar las preferencias de idioma del usuario de la aplicación.
   *
   * <p>
   * Utiliza la sesión para almacenar la prefencias de idioma y permite definir
   * un idioma por defecto mediante un código del estándar ISO 639.
   * </p>
   *
   * @return LocaleResolver Bean
   */
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    localeResolver.setDefaultLocale(new Locale("ca"));
    return localeResolver;
  }

  /**
   * Habilita el cambio de idioma.
   *
   * <p>
   * Se encarga de obtener un parámetro de la petición que define el idioma
   * deseado en la sesión correspondiente a la petición.
   * </p>
   *
   * @return LocaleChangeInterceptor Bean
   */
  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor =
        new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
  }

  /**
   * {@inheritDoc}
   * 
   * Interceptor de TracEE para el identificador de traza.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    // Interceptor necesario para el uso de TracEE
    registry.addInterceptor(new TraceeInterceptor());

    registry.addInterceptor(localeChangeInterceptor());
  }

  /**
   * Mapeo de excepciones con vistas.
   * 
   * @return Representación del mapeo.
   */
  public HandlerExceptionResolver simpleMappingExceptionResolver() {
    SimpleMappingExceptionResolver exceptionResolver =
        new CauseAdviceSimpleMappingExceptionResolver();
    Properties properties = new Properties();
    properties.put("RecursoNoExisteException", "error-recurso-no-existe");
    exceptionResolver.setExceptionMappings(properties);
    exceptionResolver.setDefaultStatusCode(HttpStatus.NOT_FOUND.value());
    exceptionResolver.setOrder(-1);
    // Habilita el logging proporcionando el nombre del logger a utilizar
    exceptionResolver.setWarnLogCategory(
        CauseAdviceSimpleMappingExceptionResolver.class.getName());
    return exceptionResolver;
  }

  /**
   * {@inheritDoc}
   * 
   * Activa el nuevo mapeo de excepciones con vistas.
   */
  @Override
  public void extendHandlerExceptionResolvers(
      List<HandlerExceptionResolver> exceptionResolvers) {
    super.extendHandlerExceptionResolvers(exceptionResolvers);
    exceptionResolvers.add(0, simpleMappingExceptionResolver());
  }

  /**
   * {@inheritDoc}
   * 
   * Establece el mapeo directo de peticiones a páginas.
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/errores/403").setViewName("errores/403");
  }

}
