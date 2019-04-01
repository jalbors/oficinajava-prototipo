/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Pruebas unitarias de la clase del modelo Visita
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

public class VisitaTest {

  /**
   * Cancelar una visita cuando la visita no está cancelada o cerrada, para
   * verificar que no se produce ninguna excepción.
   * 
   * @throws EstadoVisitaInvalidoException excepción que se produce cuando el
   *         cambio de estado es incorrecto respecto al flujo establecido
   */
  @Test
  public void cancelarVisita() throws EstadoVisitaInvalidoException {

    // Preparar
    final Visita visita = new Visita();
    visita.setEstado(EstadoVisita.PENDIENTE);

    // Ejecutar
    visita.cancelar();

    // Validar
    assertEquals("El estado de la visita no es CANCELADA", visita.getEstado(),
        EstadoVisita.CANCELADA);
    assertThat(visita.getFechaCierre())
        .as("La fecha de cierre de la visita es null").isToday();
  }

  /**
   * Intenta cancelar un visita ya terminada, para verficar que se produce la
   * excepción {@link EstadoVisitaInvalidoException}
   */
  @Test
  public void cancelarVisitaTerminada()
  {
    // Preparar
    final Visita visita = new Visita();
    visita.setEstado(EstadoVisita.TERMINADA);

    // Ejecutar
    Throwable excepcion = catchThrowable(() -> visita.cancelar());

    // Validar
    assertThat(excepcion).isInstanceOf(EstadoVisitaInvalidoException.class);
  }

  /**
   * Intenta cancelar un visita ya cancelada, para verficar que se produce la
   * excepción {@link EstadoVisitaInvalidoException}
   * 
   * @throws EstadoVisitaInvalidoException excepción que se produce cuando el
   *         cambio de estado es incorrecto respecto al flujo establecido
   */
  @Test(expected = EstadoVisitaInvalidoException.class)
  public void cancelarVisitaCancelada() throws EstadoVisitaInvalidoException
  {
    // Preparar
    final Visita visita = new Visita();
    visita.setEstado(EstadoVisita.CANCELADA);

    // Ejecutar
    visita.cancelar();
  }

}
