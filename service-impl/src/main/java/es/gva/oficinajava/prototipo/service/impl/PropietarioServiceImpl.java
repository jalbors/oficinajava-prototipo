/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Propietario;
import es.gva.oficinajava.prototipo.model.PropietarioInfo;
import es.gva.oficinajava.prototipo.repository.PropietarioRepository;
import es.gva.oficinajava.prototipo.service.api.PropietarioService;

/**
 * Implementación de {@link PropietarioService}
 *
 * <p>
 * Implementación de {@link PropietarioService} basado en la gestión del modelo
 * de datos a través de repositorios y entidades JPA.
 * </p>
 *
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 */
@Service
@Transactional(readOnly = true)
public class PropietarioServiceImpl implements PropietarioService {

	/** Repositorio Spring Data JPA para la entidad {@link Propietario} */
	private final PropietarioRepository repository;

	/**
	 * Constructor de la clase e inyección de dependencias.
	 *
	 * @param repository Repositorio de propietarios.
	 * @param jmsService Servicio de envío de mensajes JMS.
	 */
	@Autowired
	public PropietarioServiceImpl(PropietarioRepository repository) {
		this.repository = repository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Propietario save(Propietario propietario) {
		
		propietario.completar();
		
		Propietario propietarioGuardado = repository.save(propietario);
		return propietarioGuardado;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Propietario update(Propietario propietario) {

		repository.detach(propietario);
		Propietario propietarioActualizado = save(propietario);

		return propietarioActualizado;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		repository.delete(id);
	}

	// === Métodos de CRUD en batch

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<Propietario> save(Iterable<Propietario> propietarios) {
		return repository.save(propietarios);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(Iterable<Long> ids) {
		List<Propietario> aBorrar = repository.findAll(ids);
		repository.deleteInBatch(aBorrar);
	}

	// === Buscadores

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PropietarioInfo> findAllPropietarioInfo() {
		return repository.findAllProjectedBy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<PropietarioInfo> findAllPropietarioInfo(Pageable pageable) {
		return repository.findAllProjectedBy(pageable);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Propietario> findAll() {
		return repository.findAll(new Sort(Direction.ASC, "nombre", "apellidos"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Propietario> findByApellidos(String apellidos) {
		return repository.findByApellidos(apellidos);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Propietario findById(Long id) {
		return repository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Propietario findOneWithMascotasData(Long id) {
		return repository.findOneWithMascotasData(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long countByTipoId(Long tipoId) {
		return repository.countByTipoId(tipoId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Propietario findOne(Long id) {
		return repository.findOne(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return repository.count();
	}

}
