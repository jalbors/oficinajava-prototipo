/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

/**
 * Contiene los roles de los usuarios de la aplicación. En función al rol del
 * usuario registrado a la aplicación, podrá acceder a unas determinadas
 * características de ésta.
 */
public enum Roles {

  ADMIN, USER, SYSTEM;

  public String getRoleName() {
    return "ROLE_".concat(this.name());
  }

}
