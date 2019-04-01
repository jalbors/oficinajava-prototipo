/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;

/**
 * Pruebas unitarias de la clase del modelo {@link Propietario}
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
public class PropietarioTest {

  /**
   * Prueba que el método equals funciona correctamente
   */
  @Test
  public final void equalsObjetosIguales() {

    // Preparar
    Propietario propietario1 = new Propietario();
    propietario1.setId(new Long(100));
    propietario1.setApellidos("Apellidos Prueba");
    propietario1.setCiudad(new Ciudad());
    propietario1.setDireccion("Dirección Prueba");
    propietario1.setFechaNacimiento(new Date());
    propietario1.setMascotas(new HashSet<Mascota>());
    propietario1.setNombre("Nombre Prueba");
    propietario1.setTelefono("12345678");
    propietario1.setVersion(23);

    Propietario propietario2 = new Propietario();
    propietario2.setId(propietario1.getId());
    propietario2.setApellidos(propietario1.getApellidos());
    propietario2.setCiudad(propietario1.getCiudad());
    propietario2.setDireccion(propietario1.getDireccion());
    propietario2.setFechaNacimiento(propietario1.getFechaNacimiento());
    propietario2.setMascotas(propietario1.getMascotas());
    propietario2.setNombre(propietario1.getNombre());
    propietario2.setTelefono(propietario1.getTelefono());
    propietario2.setVersion(propietario1.getVersion());

    // Ejecutar
    boolean iguales = propietario1.equals(propietario2);

    // Validar
    assertThat(iguales).as("Los objetos son iguales y equals devuelve false")
        .isTrue();

  }

}
