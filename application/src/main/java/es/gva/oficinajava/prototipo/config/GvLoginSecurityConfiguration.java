/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.config;

import es.gva.gvlogin.aplicacion.MyAutenticationManager;
import es.gva.gvlogin.sso.addon.filters.SSOAuthenticationFailureHandler;
import es.gva.gvlogin.sso.addon.filters.SpringSecuritySSOFilter;
import es.gva.gvlogin.sso.addon.filters.SsoEnvironmentResolver;
import es.gva.gvlogin.sso.addon.session.TimeIntervalTriggeringStrategy;
import es.gva.gvlogin.sso.addon.utils.SSOAddonConfiguration;
import es.gva.gvlogin.sso.client.factory.ServiceClientFactoryImpl;
import es.gva.oficinajava.prototipo.filter.AuthenticationDetailsFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.inject.Provider;

/**
 * Configuración del adon de gvLogin para entornos productivos.
 * 
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "gvlogin.disabled", havingValue = "false",
    matchIfMissing = true)
@PropertySource("${gvlogin.config.file}")
public class GvLoginSecurityConfiguration extends SecurityConfiguration {

 
  /** Ruta del archivo de configuración del addon de gvLogin */
  @Value("${gvlogin.config.file}")
  String ssoConfigFile;

  /** Tipo autenticación cliente servicio web SOAP gvLogin */
  @Value("${cliente.gvlogin.auth}")
  String clienteGvloginAuth;

  /** Incluir timestamp en cliente servicio web SOAP gvLogin */
  @Value("${clientes.generateTimestamp}")
  Boolean clientesGenerateTimestamp;

  /** Logear llamadas cliente servicio web SOAP gvLogin */
  @Value("${clientes.logCalls}")
  Boolean clientesLogCalls;

  /** No validar certificado en cliente servicio web SOAP gvLogin */
  @Value("${clientes.disableCnCheck}")
  Boolean clientesDisableCnCheck;

  /** Dirección base de la web de gvLogin en la intranet */
  @Value("${sso.addon.url.intranet}")
  String ssoAddonUrlIntranet;
  
  /** Página de logout de gvLogin */
  @Value("${sso.addon.ssoLogOutURL}")
  String ssoAddonssoLogoutURL;

  /** Dirección base de la aplicación */
  @Value("${sso.addon.appPublicBaseURL}")
  String ssoAddonAppPublicBaseURL;  

  /** Proveedor del manejador de fallo al autenticar */
  Provider<SSOAuthenticationFailureHandler> ssoAddonAuthenticationFailureHandlerProvider;

  /**
   * Constructor de la clase e inyección de dependencias.
   *
   * @param ssoAddonAuthenticationFailureHandlerProvider Provider de
   *        {@link SSOAuthenticationFailureHandler}
   */
  @Autowired
  public GvLoginSecurityConfiguration(
      Provider<SSOAuthenticationFailureHandler> ssoAddonAuthenticationFailureHandlerProvider) {
    super();
    this.ssoAddonAuthenticationFailureHandlerProvider =
        ssoAddonAuthenticationFailureHandlerProvider;
  }

  /**
   * {@inheritDoc}
   * 
   * Incluye los filtros para gvLogin.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // Se carga la configuración base de seguridad
    super.configure(http);
    
    // Añadir filtro de gvLogin y propio de la aplicación a los existentes
    http.addFilterBefore(ssoAddonSpringSecurityFilter(),
        BasicAuthenticationFilter.class);
    http.addFilterAfter(miAppFilter(),
        SpringSecuritySSOFilter.class);

    http.sessionManagement().maximumSessions(sesionesConcurrentes)
        .expiredUrl(logoutUrl());
  }
  
  /**
   * Gestor de autenticación.
   * 
   * @return Gestor de autenticación.
   */
  @Bean
  public MyAutenticationManager authenticationManager() {
    return new MyAutenticationManager();
  }

  /**
   * Filtro propio para establecer los detalles del usuario.
   * 
   * <p>
   * Es requerido por el control de concurrencia.
   * </p>
   * 
   * @return Filtro de detalles del usuario.
   */
  public AuthenticationDetailsFilter miAppFilter() {
    return new AuthenticationDetailsFilter();
  }

  /**
   * Filtro del addon de gvLogin con sus beans necesarios.
   * 
   * @return Filtro el addon de gvLogin.
   */
  public SpringSecuritySSOFilter ssoAddonSpringSecurityFilter() {
    return new SpringSecuritySSOFilter(gvloginWSFactory(),
        ssoAddonAuthenticationFailureHandlerProvider,
        ssoAddonSessionValidationStrategy(), ssoAddonConfiguration(),
        ssoEnvironmentResolver());
  }

  /**
   * Carga la configuración del addon de gvLogin de las propiedades.
   * 
   * @return Configuración del addon de gvLogin.
   */
  @Bean
  public SSOAddonConfiguration ssoAddonConfiguration() {
    // La ruta no acepta el prefijo "file:" 
    return new SSOAddonConfiguration(ssoConfigFile.replaceFirst("file:", "")) {};
  }

  /**
   * Cliente del servicio web SOAP de gvLogin.
   * 
   * @return Cliente del servicio web SOAP de gvLogin.
   */
  @Bean
  public ServiceClientFactoryImpl gvloginWSFactory() {
    return new ServiceClientFactoryImpl(clienteGvloginAuth,
        clientesGenerateTimestamp, clientesLogCalls, clientesDisableCnCheck,
        null);
  }

  /**
   * Recargar la configuración del addon a intervalos de tiempo.
   * 
   * @return Estrategia de recarga de la configración del addon de gvLogin.
   */
  @Bean
  public TimeIntervalTriggeringStrategy ssoAddonSessionValidationStrategy() {
    return new TimeIntervalTriggeringStrategy(ssoAddonConfiguration());
  }

  /**
   * Resuelve si gvLogin está funcionando en intranet o internet.
   * 
   * @return Resolvedor del entorno de gLogin.
   */
  @Bean
  public SsoEnvironmentResolver ssoEnvironmentResolver() {
    SsoEnvironmentResolver ssoEnvironmentResolver =
        new SsoEnvironmentResolver();
    ssoEnvironmentResolver.setSsoAddonConfiguration(ssoAddonConfiguration());
    return ssoEnvironmentResolver;
  }

  /**
   * Proveedor del manejador de fallo al autenticar.
   * 
   * @return Proveedor del manejador de fallo al autenticar.
   */
  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public SSOAuthenticationFailureHandler ssoAddonAuthenticationFailureHandler() {
    return new SSOAuthenticationFailureHandler(ssoAddonConfiguration());

  }

  /**
   * Resuelve la dirección de logout
   * 
   * @return
   */
  @Bean
  public String logoutUrl() {
    return ssoAddonUrlIntranet.concat(ssoAddonssoLogoutURL)
        .concat("?callbackUrl=").concat(ssoAddonAppPublicBaseURL);
  }

}

