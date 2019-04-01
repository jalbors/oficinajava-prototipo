/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Pruebas unitarias del enumerado Roles
 *
 * <p>
 * Cada método de pruebas debe anotarse de forma obligatoria con la anotación
 * `@Test` ya que permite reconocer los métodos de la clase que ejecutan
 * pruebas.
 * <p>
 *
 * <p>
 * Se importan de forma estática los métodos de la clase_Assertions_ para que
 * las aserciones de validación sean más concisas.
 * <p>
 * 
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 */
public class RolesTest {

  /**
   * Comprueba que se genera correctamente el nombre del rol añadiendo el
   * prefijo <i>ROLE_</i>
   */
  @Test
  public final void getRoleNameCorrecto() {
    // Ejcutar
    String nombreRole = Roles.ADMIN.getRoleName();

    //Validar
    assertThat(nombreRole).startsWith("ROLE_");
  }

}
