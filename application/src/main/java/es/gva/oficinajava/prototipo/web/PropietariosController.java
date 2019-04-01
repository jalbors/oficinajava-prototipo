/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import es.gva.oficinajava.prototipo.datatables.ConvertedDatatablesData;
import es.gva.oficinajava.prototipo.datatables.DatatablesColumns;
import es.gva.oficinajava.prototipo.datatables.DatatablesPageable;
import es.gva.oficinajava.prototipo.exceptions.RecursoNoExisteException;
import es.gva.oficinajava.prototipo.model.Ciudad;
import es.gva.oficinajava.prototipo.model.Propietario;
import es.gva.oficinajava.prototipo.model.PropietarioInfo;
import es.gva.oficinajava.prototipo.select2.Select2DataSupport;
import es.gva.oficinajava.prototipo.select2.Select2DataWithConversion;
import es.gva.oficinajava.prototipo.service.api.CiudadService;
import es.gva.oficinajava.prototipo.service.api.PropietarioService;

/**
 * Controlador encargado de gestionar la entidad {@link Propietario} a través de
 * vistas HTML - Thymeleaf que hacen uso de componentes propios de la vista
 * cliente (jQuery DataTables, Bootstrap, etc.)
 * 
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Controller
@RequestMapping(value = "/propietarios", produces = MediaType.TEXT_HTML_VALUE)
public class PropietariosController {

	/** Implementación de _logging_ para mostrar trazas. */
	private static final Logger LOG = LoggerFactory.getLogger(PropietariosController.class);

	/** API del servicio para la entidad {@link Propietario} */
	private final PropietarioService propietarioService;

	/** API del servicio para la entidad {@link Ciudad} */
	private final CiudadService ciudadService;

	/**
	 * Servicio encargado de llevar a cabo las conversiones de datos en la parte
	 * servidora.
	 */
	private final ConversionService conversionService;

	/** Mensajes multi idioma */
	private final MessageSource messageSource;

	/**
	 * Construye la clase e inyecta dependencias.
	 *
	 * @param propietarioService Servicio de propietarios.
	 * @param ciudadService      Servicio de ciudades.
	 * @param conversionService  Servicio de conversión.
	 * @param messageSource      Servicio de mensajes.
	 */
	@Autowired
	public PropietariosController(PropietarioService propietarioService, CiudadService ciudadService,
			ConversionService conversionService, MessageSource messageSource) {
		this.propietarioService = propietarioService;
		this.ciudadService = ciudadService;
		this.conversionService = conversionService;
		this.messageSource = messageSource;

	}

	/**
	 * Obtiene la instancia de propietario cuyo identificador coincide con el
	 * identificador especificado en la URL de la petición.
	 *
	 * <p>
	 * Este método se ejecutará siempre en todas las peticiones.
	 * </p>
	 *
	 * @param id     Identificador del propietario.
	 * @param locale Idioma de la petición.
	 * @return instancia de {@link Propietario}.
	 */

	@ModelAttribute
	public Propietario getPropietario(@PathVariable(value = "propietario", required = false) Long id, Locale locale) {

		Propietario propietario = new Propietario();

		if (id != null) {

			// Show, update, delete: obtener el propietario
			propietario = propietarioService.findOne(id);

			if (propietario == null) {
				// No se ha podido obtener el recurso solicitado
				String mensaje = messageSource.getMessage("error_recurso_no_existe", new Object[] { id }, locale);
				throw new RecursoNoExisteException(mensaje);
			}
		}

		return propietario;
	}

	/**
	 * Devuelve la vista HTML "propietarios/list.html" que muestra el listado de los
	 * propietarios registrados en base de datos haciendo uso del componente jQuery
	 * DataTables.
	 * 
	 * @param model Modelo de la vista.
	 * @return Modelo y vista.
	 */
	@GetMapping
	public ModelAndView list(Model model) {
		return new ModelAndView("propietarios/list");
	}

	/**
	 * Método que sirve los propietarios registrados en la base de datos cuando son
	 * solicitados por el componente DataTables.
	 * 
	 * Incluiye parámetros propios del componente DataTables para ser capaces de
	 * paginar y ordenar en el lado servidor.
	 *
	 * @param datatablesColumns Columnas de la tabla.
	 * @param pageable          Paginación y filtrado.
	 * @param draw              Pintado.
	 * @return Respuesta para datatables.
	 */
	@GetMapping(path = "/dt", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConvertedDatatablesData<PropietarioInfo>> datatables(DatatablesColumns datatablesColumns,
			DatatablesPageable pageable, @RequestParam("draw") Integer draw) {

		// Obtenemos todos los propietarios paginados en función de
		// la petición del componente DataTables.
		Page<PropietarioInfo> propietarios = propietarioService.findAllPropietarioInfo(pageable);
		long total = propietarioService.count();
		ConvertedDatatablesData<PropietarioInfo> datatablesData = new ConvertedDatatablesData<PropietarioInfo>(
				propietarios, total, draw, conversionService, datatablesColumns);
		return ResponseEntity.ok(datatablesData);
	}

	/**
	 * Devuelve la vista HTML "propietarios/create.html" que muestra el formulario
	 * de creación de nuevos propietarios.
	 * 
	 * @param model Modelo de la vista.
	 * @return Modelo y vista.
	 */
	@GetMapping(path = "/create-form")
	public ModelAndView createForm(Model model) {
		model.addAttribute("propietario", new Propietario());
		return new ModelAndView("propietarios/create");
	}

	/**
	 * Crear un nuevo propietario.
	 *
	 * <p>
	 * En el caso de que la creación se ejecute de forma satisfactoria, se devolverá
	 * el código de estado 201 y se incluye el header Location con la URL al nuevo
	 * recurso.
	 * </p>
	 * 
	 * @param propietario Propietario a crear.
	 * @param result      Resultados del mapeo del propietario.
	 * @param model       Modelo de la vista.
	 * @return Modelo y vista.
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView create(@Valid Propietario propietario, BindingResult result, Model model) {

		// Error en la petición
		if (result.hasErrors()) {
			return new ModelAndView("propietarios/create");
		}

		// Crear el propietario
		propietarioService.save(propietario);

		// Redireccionar a mostrar la mascota
		return new ModelAndView("redirect:/propietarios");

	}

	/**
	 * Método que obtiene todas las ciudades existentes en la base de datos
	 * paginadas según la información recibida por el componente Select2.
	 * 
	 * Una vez obtenidas las ciudades paginadas, aplica el formateo haciendo uso del
	 * {@link ConversionService} y devuelve un tipo de respuesta
	 * {@link Select2DataSupport} que podrá ser interpretado por el componente
	 * select2.
	 * 
	 * @param pageable Filtrado del selector.
	 * @param locale   Idioma del selector.
	 * @return Respuesta para el selector.
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, name = "select2", value = "/s2")
	@ResponseBody
	public ResponseEntity<Select2DataSupport<Ciudad>> select2(Pageable pageable, Locale locale) {
		Page<Ciudad> ciudades = ciudadService.findAll(pageable);
		String idExpression = "#{id}";
		Select2DataSupport<Ciudad> select2Data = new Select2DataWithConversion<Ciudad>(ciudades, idExpression,
				conversionService);
		return ResponseEntity.ok(select2Data);
	}

	/**
	 * Devuelve la vista HTML "propietarios/edit.html" que muestra el formulario de
	 * edición de propietarios existentes.
	 * 
	 * @param propietario Propietario a modificar.
	 * @param model       Modelo a mostrar.
	 * @return Modelo y vista.
	 */
	@GetMapping(path = "/{propietario}/edit-form")
	public ModelAndView editForm(@ModelAttribute Propietario propietario, Model model) {
		// Establecer el propietario a actualizar
		model.addAttribute("propietario", propietario);
		// Mostrar el formulario de edición
		return new ModelAndView("propietarios/edit");
	}

	/**
	 * Actualizar un Propietario.
	 *
	 * <p>
	 * Actualiza el estado de la entidad {@link Propietario} con los datos
	 * recibidos.
	 * </p>
	 *
	 * @param propietario Propietario obtenida del formulario.
	 * @param result      Contenedor de errores encontrados en el proceso de
	 *                    transformación de los datos recibidos. Si no hay errores
	 *                    el contenedor estará vacío.
	 * @param model       Modelo de datos para el propietario.
	 * @return Modelo de datos y vista.
	 */
	@PutMapping(value = "/{propietario}")
	public ModelAndView update(@Valid Propietario propietario, BindingResult result, Model model) {

		// Error en la petición
		if (result.hasErrors()) {
			// Establecer el propietario recibido
			model.addAttribute("propietario", propietario);
			return new ModelAndView("propietarios/edit");
		}

		try {

			// Actualizar el propietario
			propietarioService.update(propietario);

		} catch (ObjectOptimisticLockingFailureException excepcionBro) {

			// Error de concurrencia: el registro ha sido actualizado por otro usuario
			LOG.warn("Registro actualizado por otro usuario", excepcionBro);
			result.rejectValue("version", "error_concurrencia", null, "Registro modificado");
			model.addAttribute("propietario", propietario);
			return new ModelAndView("propietarios/edit");
		}

		// Redireccionar a mostrar el propietario
		return new ModelAndView("redirect:/propietarios/" + propietario.getId());
	}

	/**
	 * Devuelve la vista de visualización de un propietario
	 * 
	 * @param propietario Propietario a mostrar.
	 * @param model       Modelo de la vista.
	 * @return Modelo y vista.
	 */
	@GetMapping(value = "/{propietario}")
	public ModelAndView show(@ModelAttribute Propietario propietario, Model model) {
		return new ModelAndView("propietarios/show");
	}

	/**
	 * Obtiene un informe en PDF desde SForms y lo muestra al usuario.
	 * 
	 * @return Array de bytes con el informe en PDF.
	 */
	@GetMapping(path = "/sforms/pdf", produces = "application/pdf")
	public @ResponseBody byte[] sformsPdf() {

		// Esto es un ejemplo:
		// * Toda esta lógica debería estar en un servicio en el módulo integration.
		// * La dirección y el id del informe deberían estar externalizados.
		// * Los parámetros XML del informe deberían generarse dinámicamente.

		String direccion = "http://intranet-dsa.gva.es/ws_sforms_cliente/servlet/Principal/obtenerDocumentoSForms?id_doc_sforms={id_doc_sforms}&formato_doc={formato_doc}&xml_parametros={xml_parametros}";
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("id_doc_sforms", "1215");
		parametros.put("formato_doc", "PDF");
		parametros.put("xml_parametros",
				"<?xml version=\"1.0\" encoding=\"windows-1252\"?><parametros><MAQUINAEXPE>I</MAQUINAEXPE><NUMEXPE>112746</NUMEXPE></parametros>");

		RestTemplate servicioRest = new RestTemplate();

		ResponseEntity<byte[]> respuesta = servicioRest.getForEntity(direccion, byte[].class, parametros);

		byte[] cuerpoRespuesta = respuesta.getBody();
		if (!MediaType.APPLICATION_PDF.equals(respuesta.getHeaders().getContentType())) {
			LOG.warn("No se ha obtenido el informe de SForms: {}", new String(cuerpoRespuesta));

		}

		return cuerpoRespuesta;
	}

//	@GetMapping(path = "/info")
//	public ModelAndView info(@ModelAttribute Propietario propietario, Model model) {
//
//		model.addAttribute("propietar", propietarioService.findAll());
//
//		return new ModelAndView("propietar/info.html");
//
//	}

	@GetMapping(path = "/info")
	public ModelAndView info(Model model) {

		model.addAttribute("propietarios", propietarioService.findAllPropietarioInfo());

		return new ModelAndView("propietarios/info");

	}
	
	@GetMapping(path="hellow")
	public ModelAndView hellow(Model model) {
		
		model.addAttribute("propietarios", propietarioService.findByApellidos(""));
		
		return new ModelAndView("propietarios/hellou");
	}

//  @ResponseBody
//  public ResponseEntity<Select2DataSupport<Ciudad>> select2(Pageable pageable,
//      Locale locale) {
//    Page<Ciudad> ciudades = ciudadService.findAll(pageable);
//    String idExpression = "#{id}";
//    Select2DataSupport<Ciudad> select2Data =
//        new Select2DataWithConversion<Ciudad>(ciudades, idExpression,
//            conversionService);
//    return ResponseEntity.ok(select2Data);
//  

}
