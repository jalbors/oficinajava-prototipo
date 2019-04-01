/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;

/**
 * Pruebas unitarias de la clase del modelo Mascota
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
public class MascotaTest {

  /**
   * Prueba que le método equals funciona correctamente
   */
  @Test
  public void equalsObjetosNoIguales() {

    // Preparar
    TipoMascota tipo = new TipoMascota();
    tipo.setId(new Long(100));
    tipo.setNombre("Tipo Mascota");
    
    Mascota mascota = new Mascota();
    mascota.setEstado(EstadoMascota.SANA);
    mascota.setFechaFallecimiento(new Date());
    mascota.setFechaNacimiento(new Date());
    mascota.setNombre("Mascota");
    mascota.setPropietario(null);
    mascota.setTipo(new TipoMascota());
    mascota.setVersion(1);
    mascota.setVisitas(new HashSet<Visita>());
    mascota.setId(new Long(1));

    Mascota mascota2 = new Mascota();
    mascota2.setEstado(mascota.getEstado());
    mascota2.setFechaFallecimiento(mascota.getFechaFallecimiento());
    mascota2.setFechaNacimiento(mascota.getFechaNacimiento());
    mascota2.setNombre(mascota.getNombre());
    mascota2.setPropietario(mascota.getPropietario());
    mascota2.setTipo(new TipoMascota());
    mascota2.getTipo().setId(mascota.getTipo().getId());
    mascota2.getTipo().setNombre(mascota.getTipo().getNombre());
    mascota2.setVersion(mascota.getVersion());
    mascota2.setVisitas(mascota.getVisitas());

    // Ejecutar
    boolean iguales = mascota.equals(mascota2);

    // Validar
    assertFalse("Los objetos no son iguales y equals devuelve true", iguales);



  }

}
