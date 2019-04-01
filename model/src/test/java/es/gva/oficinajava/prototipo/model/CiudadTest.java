/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Pruebas unitarias de la clase del modelo Ciudad
 *
 * <p>
 * Cada método de pruebas debe anotarse de forma obligatoria con la anotación
 * `@Test` ya que permite reconocer los métodos de la clase que ejecutan
 * pruebas.
 * <p>
 *
 * <p>
 * Se importan de forma estática los métodos de la clase _Assert_ y de la clase
 * _Assertions_ para que las aserciones de validación sean más concisas.
 * <p>
 * 
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 */
public class CiudadTest {

  /**
   * Prueba que le método equals funciona correctamente
   */
  @Test
  public final void equalsObjetosIguales() {

    // Preparar
    Ciudad ciudad1 = new Ciudad();
    ciudad1.setId(new Long(20));
    ciudad1.setNombre("València");
    ciudad1.setVersion(23);

    Ciudad ciudad2 = new Ciudad();
    ciudad2.setId(ciudad1.getId());
    ciudad2.setNombre(ciudad1.getNombre());
    ciudad2.setVersion(ciudad1.getVersion());

    // Ejecutar
    boolean iguales = ciudad1.equals(ciudad2);

    // Validar
    assertThat(iguales).as("Los objetos son iguales y equals devuelve false")
        .isTrue();
  }

}
