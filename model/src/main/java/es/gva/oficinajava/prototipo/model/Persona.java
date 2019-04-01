/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Ejemplo de MappedSuperClass
 *
 * <p>
 * Representa una persona
 * </p>
 *
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@MappedSuperclass
public class Persona {

  /** Nombre */
  @Column(name = "NOMBRE")
  @NotEmpty
  @Size(max = 30)
  protected String nombre;

  /** Apellidos */
  @Column(name = "APELLIDOS")
  @NotEmpty
  @Size(max = 30)
  protected String apellidos;
  
  /** Fecha de naciemiento de la persona */
  @Column(name = "FECHA_NACIMIENTO")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @NotNull
  private Date fechaNacimiento;

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return this.apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }
  
  public Date getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(Date fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
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
    return "Persona {" + "Nombre='" + nombre + '\'' + ", Apellidos='"
        + apellidos + '\'' + "} " + super.toString();
  }

}
