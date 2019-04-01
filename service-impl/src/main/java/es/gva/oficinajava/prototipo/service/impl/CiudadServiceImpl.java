/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Ciudad;
import es.gva.oficinajava.prototipo.repository.CiudadRepository;
import es.gva.oficinajava.prototipo.service.api.CiudadService;

/**
 * Implementación de {@link CiudadService}
 *
 * <p>
 * Implementación de {@link CiudadService} basado en la gestión del modelo de
 * datos a través de repositorios y entidades JPA.
 * </p>
 *
 * @author Juan Carlos García at http://www.disid.com[DISID Corporation S.L.]
 *
 */
@Service
@Transactional(readOnly = true)
public class CiudadServiceImpl implements CiudadService {

  /** Repositorio Spring Data JPA para la entidad {@link Ciudad} */
  private final CiudadRepository repository;

  /**
   * Constructor de la clase
   *
   * @param repository
   */
  @Autowired
  public CiudadServiceImpl(CiudadRepository repository) {
    this.repository = repository;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<Ciudad> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Ciudad findOne(Long id){
    return repository.findOne(id);
  }

}
