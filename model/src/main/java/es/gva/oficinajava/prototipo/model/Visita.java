/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad <i>Visita</i>
 *
 * <p>
 * Entidad que representa las visitas.
 * </p>
 *
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Entity
@Table(name = "VISITAS")
public class Visita implements Serializable {

  private static final long serialVersionUID = 2391685842788977441L;

  /** Identificador */
  @Id
  @SequenceGenerator(name = "visitaGen", sequenceName = "VISITAS_ID_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visitaGen")
  @Column(name = "ID")
  private Long id;

  /**
   * Fecha de la visita
   *
   * <p>
   * Anotaciones:
   * <ul>
   * <li>@Column: Nombre del campo en base de datos</li>
   * <li>@Temporal: Define tipo de datos temporal en base de datos</li>
   * <li>@DateTimeFormat: Define el formato de la fecha
   * <li>@NotNull: Indica que el campo no puede ser null</li>
   * </ul>
   * </p>
   */
  @Column(name = "FECHA_VISITA")
  @NotNull
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fecha;

  /** Descripción de la visita */
  @NotEmpty
  @Column(name = "DESCRIPCION")
  @Size(max = 255)
  private String descripcion;

  /** Estado de la visita */
  @Column(name = "ESTADO")
  @Enumerated(EnumType.STRING)
  @NotNull
  private EstadoVisita estado;

  /** Fecha de la visita */
  @Column(name = "FECHA_CIERRE")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaCierre;

  /** Versión para el control de concurrencia optimista */
  @Column(name = "VERSION")
  @Version
  private long version;

  /** Mascota de la visita */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MASCOTA_ID", referencedColumnName = "id")
  @NotNull
  private Mascota mascota;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFecha() {
    return this.fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public EstadoVisita getEstado() {
    return this.estado;
  }

  public void setEstado(EstadoVisita status) {
    this.estado = status;
  }

  public Date getFechaCierre() {
    return this.fechaCierre;
  }

  public void setFechaCierre(Date fechaCierre) {
    this.fechaCierre = fechaCierre;
  }

  public long getVersion() {
    return this.version;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public Mascota getMascota() {
    return mascota;
  }

  public void setMascota(Mascota mascota) {
    this.mascota = mascota;
  }

  /**
   * Dos entidades son iguales si son la misma instancia o tienen el mismo
   * identificador.
   *
   * <p>
   * Esta implementación de `equals` es específica para entidades JPA y usa el
   * identificador de la entidad para ello. Para más información ver:
   * <ul>
   * <li><a href=
   * "https://vladmihalcea.com/2016/06/06/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/"/>
   * How to implement equals and hashCode using the JPA entity identifier
   * (primary key)</a></li>
   * </ul>
   * </p>
   *
   * @param obj referencia a entidad con la que comparar
   * @return true si esta entidad es igual que la entidad recibida como
   *         parámetro; false en caso contrario.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    return getId() != null && Objects.equals(getId(), ((Visita) obj).getId());
  }

  /**
   * Esta implementación de `hashCode` es específica para entidades JPA y usa un
   * valor fijo de` int` para poder Identificar la entidad en las colecciones
   * después de asignar un nuevo id a la entidad.
   *
   * <p>
   * Para más información ver:
   * <ul>
   * <li><a href=
   * "https://vladmihalcea.com/2016/06/06/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/"/>
   * How to implement equals and hashCode using the JPA entity identifier
   * (primary key)</a></li>
   * </ul>
   * </p>
   *
   * @result código hash
   */
  @Override
  public int hashCode() {
    return 31;
  }

  /**
   * Obtener una representación concisa en modo texto de la entidad.
   * <p>
   * Esta representación es interna y su objetivo es que sea utilizada por los
   * desarrolladores. No utilizar esta representación para usuarios.
   * </p>
   * <p>
   * <b>NUNCA</b> mostrar relaciones, podría penalizar considerablemente el
   * rendimiento de la aplicación. <b>ÚNICAMENTE</b> mostrar atributos de la
   * entidad.
   * </p>
   */
  @Override
  public String toString() {
    return "Visita {" + "id='" + id + '\'' + ", descripcion='" + descripcion
        + '\'' + ", fecha='" + fecha + '\'' + ", version='" + version + '\''
        + "} " + super.toString();
  }

  /**
   * Visita cancelada. Establece el estado de la visita a <b>CANCELADA</b> y la
   * fecha de cierre de la visita a la actual, si la visita no está ya cancelado
   * o terminada.
   *
   * @throws EstadoVisitaInvalidoException cuando la visita ya ha sido cancelada
   *         o terminada.
   */
  public void cancelar() throws EstadoVisitaInvalidoException {
    if (getEstado() == EstadoVisita.CANCELADA
        || getEstado() == EstadoVisita.TERMINADA) {
      throw new EstadoVisitaInvalidoException(this, EstadoVisita.CANCELADA);
    }

    setEstado(EstadoVisita.CANCELADA);
    setFechaCierre(Calendar.getInstance().getTime());
  }

}
