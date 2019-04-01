/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Configuración de seguridad en memoria para entornos locales del desarrollo.
 * 
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "gvlogin.disabled", havingValue = "true")
public class MemorySecurityConfiguration extends SecurityConfiguration {

  /** Nombre del usuario */
  @Value("${security.user.name}")
  String userName;

  /** Clave del usuario */
  @Value("${security.user.password}")
  String userPassword;

  /** Roles del usuario */
  @Value("${security.user.role}")
  String[] userRoles;

  /** Nombre del administrador */
  @Value("${security.admin.name}")
  String adminName;

  /** Clave del administrador */
  @Value("${security.admin.password}")
  String adminPassword;

  /** Roles del administrador */
  @Value("${security.admin.role}")
  String[] adminRoles;

  /** Nombre de sistemas */
  @Value("${security.system.name}")
  String systemName;

  /** Clave de sistemas */
  @Value("${security.system.password}")
  String systemPassword;

  /** Roles de sistemas */
  @Value("${security.system.role}")
  String[] systemRoles;

  /**
   * {@inheritDoc}
   * 
   * Acceso para todos al formulario de login.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // Se carga la configuración base de seguridad
    super.configure(http);
    
    // Formulario de login accesible para todo el mundo
    http.formLogin();
  }

  /**
   * {@inheritDoc}
   * 
   * Crea los usuarios en memoria para el entorno local de desarrollo.
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
    // Crear usuarios en memoria
    auth.inMemoryAuthentication().withUser(userName).password(userPassword)
        .roles(userRoles);
    auth.inMemoryAuthentication().withUser(adminName).password(adminPassword)
        .roles(adminRoles);
    auth.inMemoryAuthentication().withUser(systemName).password(systemPassword)
        .roles(systemRoles);
  }

}
