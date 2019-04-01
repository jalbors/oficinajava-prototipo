/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

/**
 * Entidad <i>Ciudad</i>
 *
 * <p>
 * Entidad que representa las ciudades.
 * </p>
 *
 * @author Juan Carlos García at http://www.disid.com[DISID Corporation S.L.]
 *
 */

@Entity
@Table(name = "CIUDADES")
public class Ciudad implements Serializable {

  private static final long serialVersionUID = -622398920617085054L;

  /** Identificador */
  @Id
  @SequenceGenerator(name = "ciudadesGen", sequenceName = "CIUDADES_ID_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ciudadesGen")
  @Column(name = "ID")
  private Long id;

  /** Nombre de la ciudad */
  @Column(name = "NOMBRE")
  @NotEmpty
  @Size(max = 30)
  private String nombre;

  /** Versión para el control de concurrencia optimista */
  @Column(name = "VERSION")
  @Version
  private long version;

  /** Propietarios que habitan esta ciudad */
  // Por defecto el modo de fetch es lazy.
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "ciudad")
  private Set<Propietario> propietarios = new HashSet<>();

  /**
   * @return the id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the nombre
   */
  public String getNombre() {
    return this.nombre;
  }

  /**
   * @param nombre the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * @return the version
   */
  public long getVersion() {
    return this.version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(long version) {
    this.version = version;
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
    return getId() != null && Objects.equals(getId(), ((Ciudad) obj).getId());
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
    return "Ciudad {" + "id='" + id + '\'' + ", nombre='" + nombre + "} "
        + super.toString();
  }

}
