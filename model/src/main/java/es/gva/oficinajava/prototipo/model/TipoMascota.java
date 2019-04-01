/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad <i>TipoMascota</i>
 *
 * <p>
 * Entidad que representa los tipos de mascota
 * </p>
 *
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Entity
@Table(name = "TIPOS")
public class TipoMascota implements Serializable {

	private static final long serialVersionUID = 8883704701818122117L;

	/** Identificador */
	@Id
	@SequenceGenerator(name = "tipoMascotaGen", sequenceName = "TIPOS_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipoMascotaGen")
	@Column(name = "ID")
	private Long id;

	/** Nombre del tipo de mascota */
	@Column(name = "NOMBRE")
	@Size(max = 80)
	@NotNull
	private String nombre;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	 * How to implement equals and hashCode using the JPA entity identifier (primary
	 * key)</a></li>
	 * </ul>
	 * </p>
	 *
	 * @param obj referencia a entidad con la que comparar
	 * @return true si esta entidad es igual que la entidad recibida como parámetro;
	 *         false en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		return getId() != null && Objects.equals(getId(), ((TipoMascota) obj).getId());
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
	 * How to implement equals and hashCode using the JPA entity identifier (primary
	 * key)</a></li>
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
		return "TipoMascota {" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' + "} ";
	}

}
