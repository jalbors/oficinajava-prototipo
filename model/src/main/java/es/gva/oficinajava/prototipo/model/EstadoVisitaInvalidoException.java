/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

/**
 * Clase que define la excepción <i>EstadoVisitaInvalidoException</i>
 *
 * Esta excepción será lanzada para evitar el cambio de estados de las visitas
 * de forma que no se siga el flujo establecido
 *
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 */
public class EstadoVisitaInvalidoException extends Exception {

  /** Visita afectada */
  private Visita visita;

  /** Estado al que cambiar */
  private EstadoVisita estadoDestino;

  /** Identificador de la clase al serializar */
  private static final long serialVersionUID = -141166865347876831L;

  /**
   * Construye una nueva excepción sin información adicional.
   */
  public EstadoVisitaInvalidoException() {
    super();
  }

  /**
   * Construye una nueva excepción con el mensaje especificado.
   *
   * @param mensaje mensaje de la excepción.
   */
  public EstadoVisitaInvalidoException(String mensaje) {
    super(mensaje);
  }

  /**
   * Construye una nueva excepción indicando la visita y nuevo estado.
   *
   * @param visita Visita afectada.
   * @param estadoDestino Estado al que cambiar.
   */
  public EstadoVisitaInvalidoException(Visita visita,
      EstadoVisita estadoDestino) {
    super();
    this.visita = visita;
    this.estadoDestino = estadoDestino;
  }

  /**
   * Obtener información adicional del error: visita.
   * 
   * @return Visita afectada.
   */
  public Visita getVisita() {
    return this.visita;
  }

  /**
   * Obtener información adicional del error: estado destino.
   * 
   * @return Estado al que cambiar.
   */
  public EstadoVisita getEstadoDestino() {
    return this.estadoDestino;
  }

}
