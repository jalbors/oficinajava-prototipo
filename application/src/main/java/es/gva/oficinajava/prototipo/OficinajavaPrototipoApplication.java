/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import java.io.File;

/**
 * Autoconfiguración de Spring Boot.
 *
 * <p>
 * Clase de aplicación del proyecto para el soporte de Spring Boot. Esta clase
 * tiene dos funciones principales:
 * <ul>
 * <li>Proporcionar el método _main_ para arrancar la aplicación en modo
 * standalone.</li>
 * <li>Servir de clase de configuración central para la configuración Java de
 * Spring.</li>
 * </ul>
 * En esta clase se han aplicado las siguientes configuraciones:
 * <ul>
 * <li><b>Integración de Spring Boot:</b> Se incluye la anotación
 * <i>@SpringBootApplication</i> para marcar la clase como la clase de
 * aplicación principal de Spring Boot.</li>
 * <li><b>Desactivar la auto configuración de Spring Data:</b> Se excluye
 * <i>SpringDataWebAutoConfiguration</i> para evitar el acceso directo a la capa
 * de datos desde la capa de control.</li>
 * <li><b>Soporte para la gestión de caché:</b> Se incluye la
 * notación @EnableCaching para habilitar el uso de la cache de memoria
 * intermedia</li>
 * </ul>
 * <p>
 *
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 */
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
@EnableCaching
@EnableSpringDataWebSupport
public class OficinajavaPrototipoApplication extends SpringBootServletInitializer {

	/** Nombre de la variable de entorno con la ruta base a la configuración */
	private static final String VARIABLE_RUTA_BASE_CONF = "asa.conf";

	/** Nombre del sub directorio de configuración */
	private static final String NOMBRE_DIRECTORIO_CONF = "oficinajava-prototipo-application";

	/** Nombre de la variable de Boot con la ruta a la configuración */
	private static final String VARIABLE_RUTA_CONF = "spring.config.location";

	/** Implementación de logging para mostrar trazas */
	private static final Logger LOG = LoggerFactory.getLogger(OficinajavaPrototipoApplication.class);

	/**
	 * Permite a Spring Boot ejecutar la aplicación en un contenedor embebido.
	 *
	 * <p>
	 * Invoca el método {@code run} de la clase {@link SpringApplication} para
	 * arrancar la aplicación.
	 * </p>
	 *
	 * @param args Paso de argumentos a la aplicación.
	 */
	public static void main(String[] args) {
		SpringApplication.run(OficinajavaPrototipoApplication.class, args);
		LOG.info("Inicio de la aplicacion");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Establece un directorio externo de configuración.
	 * </p>
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder constructor) {

		return setExternalConfig(super.configure(constructor));
	}

	/**
	 * Establece la ruta a la configuración externa.
	 * 
	 * <p>
	 * Establece en la aplicación un directorio externo de configuración, en donde
	 * se buscará el fichero "application.properties".
	 * </p>
	 * 
	 * @param constructor Constructor de la aplicación.
	 */
	private static SpringApplicationBuilder setExternalConfig(SpringApplicationBuilder constructor) {

		StringBuilder ruta = new StringBuilder();
		try {

			// Ruta completa a la configuración, terminando con separador directorio
			ruta.append(System.getProperty(VARIABLE_RUTA_BASE_CONF));
			ruta.append(File.separator);
			ruta.append(NOMBRE_DIRECTORIO_CONF);
			ruta.append(File.separator);

			// Añadir el directorio de configuración a la aplicación
			constructor.properties(VARIABLE_RUTA_CONF.concat("=").concat(ruta.toString()));

		} catch (Exception e) {
			LOG.error("Configuración externa no cargada: ruta={}", ruta, e);
			throw e;
		}

		return constructor;
	}

}
