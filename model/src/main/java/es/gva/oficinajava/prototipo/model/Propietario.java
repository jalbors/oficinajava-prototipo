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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad <i>Propietario</i>
 *
 * <p>
 * Entidad que representa los propietarios
 * </p>
 *
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Entity
@Table(name = "PROPIETARIOS")
public class Propietario extends Persona implements Serializable {

	private static final long serialVersionUID = 641818381599344335L;

	/** Identificador */
	@Id
	@SequenceGenerator(name = "propietarioGen", sequenceName = "PROPIETARIOS_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "propietarioGen")
	@Column(name = "ID")
	private Long id;

	/** Dirección del propietario */
	// NotEmpty = No nulo y no vacio
	@Column(name = "DIRECCION")
	@Size(max = 255)
	@NotEmpty
	private String direccion;

	/** Ciudad del propietario */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CIUDAD_ID", referencedColumnName = "id")
	@NotNull
	private Ciudad ciudad;

	/** Telefono del propietario */
	@Column(name = "TELEFONO")
	@NotEmpty
	@Size(max = 20)
	@Digits(fraction = 0, integer = 10)
	private String telefono;

	/** Versión para el control de concurrencia optimista */
	@Column(name = "VERSION")
	@Version
	private long version;

	/** Mascotas del propietario */
	// Por defecto el modo de fetch es lazy.
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "propietario")
	private Set<Mascota> mascotas = new HashSet<Mascota>();

	/** apodo del propietario */
	// NotEmpty = No nulo y no vacio
	@Column(name = "APODO")
	@Size(max = 30)
	private String apodo;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Ciudad getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Set<Mascota> getMascotas() {
		return this.mascotas;
	}

	public void setMascotas(Set<Mascota> mascotas) {
		this.mascotas = mascotas;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
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
		return getId() != null && Objects.equals(getId(), ((Propietario) obj).getId());
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
		return "Propietario {" + "id='" + id + '\'' + ", direccion='" + direccion + '\'' + ", ciudad='" + ciudad + '\''
				+ ", telefono='" + telefono + '\'' + ", version='" + version + '\'' + "} " + super.toString();
	}

	
	public String completar() {
		if (getApodo() == null) {
			
			setApodo(getNombre());

		}
		return getApodo();

	}

}
