/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad <i>Mascota</i>
 *
 * <p>
 * Entidad que representa las mascotas.
 * </p>
 *
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Entity
@Table(name = "MASCOTAS")
public class Mascota implements Serializable {

	private static final long serialVersionUID = -622398920617085054L;

	/** Identificador */
	@Id
	@SequenceGenerator(name = "mascotaGen", sequenceName = "MASCOTAS_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mascotaGen")
	@Column(name = "ID")
	private Long id;

	/** Nombre de la mascota */
	@Column(name = "NOMBRE")
	@NotEmpty
	@Size(max = 30)
	private String nombre;

	/** Fecha de naciemiento de la mascota */
	@Column(name = "FECHA_NACIMIENTO")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	private Date fechaNacimiento;

	/** Fecha de fallecimiento de la mascota */
	@Column(name = "FECHA_FALLECIMIENTO")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date fechaFallecimiento;

	/** EstadoMascota de la mascota */
	@Column(name = "ESTADO")
	@Enumerated(EnumType.STRING)
	@NotNull
	private EstadoMascota estado;

	/** Versión para el control de concurrencia optimista */
	@Column(name = "VERSION")
	@Version
	private long version;

	/** Tipo de mascota */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPO_ID", referencedColumnName = "id")
	@NotNull
	private TipoMascota tipo;

	/** Propietario de la mascota */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPIETARIO_ID", referencedColumnName = "id")
	@NotNull
	private Propietario propietario;

	/** Visitas de la mascota */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mascota")
	private Set<Visita> visitas = new HashSet<Visita>();

	/** apodo del propietario */
	// NotEmpty = No nulo y no vacio
	@Column(name = "APODO")
	@Size(max = 20)
	private String apodo;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaFallecimiento() {
		return this.fechaFallecimiento;
	}

	public void setFechaFallecimiento(Date fechaFallecimiento) {
		this.fechaFallecimiento = fechaFallecimiento;
	}

	public EstadoMascota getEstado() {
		return this.estado;
	}

	public void setEstado(EstadoMascota estado) {
		this.estado = estado;
	}

	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public TipoMascota getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoMascota tipo) {
		this.tipo = tipo;
	}

	public Propietario getPropietario() {
		return this.propietario;
	}

	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}

	public Set<Visita> getVisitas() {
		return this.visitas;
	}

	public void setVisitas(Set<Visita> visitas) {
		this.visitas = visitas;
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
		return getId() != null && Objects.equals(getId(), ((Mascota) obj).getId());
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
		return "Mascota {" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' + ", Fecha Nacimiento='"
				+ fechaNacimiento + '\'' + ", Fecha Fallecimiento='" + fechaFallecimiento + '\'' + ", version='"
				+ version + '\'' + "} " + super.toString();
	}

}
