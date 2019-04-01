/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.gva.oficinajava.prototipo.model.Ciudad;
import es.gva.oficinajava.prototipo.model.Propietario;
import es.gva.oficinajava.prototipo.repository.PropietarioRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Pruebas unitarias de la clase {@link PropietarioServiceImpl}
 *
 * Cada método de pruebas debe anotarse de forma obligatoria con la anotación
 * `@Test` ya que permite reconocer los métodos de la clase que ejecutan
 * pruebas.
 *
 * Se importan de forma estática los métodos de la clase {@link Assert} y de la
 * clase {@link Assertions} para que las aserciones de validación sean más
 * concisas.
 * 
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 */
public class PropietarioServiceImplTest {

  /** Mock para crear Stubs del repositorio de propietarios */
  @Mock
  PropietarioRepository repository;

  /** Servicio a probar */
  PropietarioServiceImpl propietarioService;

  /** Objeto ficticio Ciudad */
  @Mock
  Ciudad ciudad;

  /** Regla JUnit para inicializar Mocks y validar el uso de Mockito */
  @Rule
  public MockitoRule mockito = MockitoJUnit.rule();

  /** Propietario a salvar */
  Propietario propietario1;

  Propietario propietario2;

  static final Long PROPIETARIO_ID = new Long(1);

  /**
   * Fase de preparación
   *
   * <p>
   * Antes de la ejecución de cada prueba se ejecuta este método que inicializa
   * el servicio y las entidades del dominio.
   * <p>
   *
   * <p>
   * Crear:
   * <ul>
   * <li>2 propietarios uno con id y otro sin id</li>
   * <li>Mock de {@link PropietarioRepository}
   * </ul>
   * </p>
   */
  @Before
  public void setUp() {

    // Preparar
    propietario1 = new Propietario();
    propietario1.setNombre("Nombre");
    propietario1.setApellidos("Apellidos");
    propietario1.setDireccion("Direccion");
    propietario1.setCiudad(ciudad);
    propietario1.setId(PROPIETARIO_ID);

    propietario2 = new Propietario();
    propietario2.setNombre("Nombre");
    propietario2.setApellidos("Apellidos");
    propietario2.setDireccion("Direccion");
    propietario2.setCiudad(ciudad);
    propietario2.setId(null);

    // Crear instancia servicio a validar con mocks que simulan sus dependencias
    propietarioService =
        new PropietarioServiceImpl(repository);

    // Stub del método save para que devuelva el propietario con id
    when(repository.save(propietario2)).thenReturn(propietario1);

    // Stub del método save para que devuelva el mismo propietario de prueba
    when(repository.save(propietario1)).thenReturn(propietario1);
  }

  /**
   * Validar el comportamiento del método {@link PropietarioServiceImpl#save}.
   * 
   * <p>
   * Se valida que se han invocado de los Mocks los métodos con los parámetros y
   * valores correctos y que el {@link Propietario} devuelto es el correcto.
   * </p>
   */
  @Test
  public final void savePropietario() {

    // Ejecutar
    Propietario propietario = propietarioService.save(propietario2);

    // Verificar: se ha devuelto el objeto correcto
    assertThat(propietario).as("El propietario devuelto no es el correcto")
        .isEqualTo(propietario1);

  }

  }
