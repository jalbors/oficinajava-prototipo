package es.gva.oficinajava.prototipo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Mascota;
import es.gva.oficinajava.prototipo.repository.MascotaRepository;
import es.gva.oficinajava.prototipo.service.api.MascotaService;

public class MascotaServiceImpl implements MascotaService{
	private final MascotaRepository repositoryMascota;
	
	@Autowired
	public MascotaServiceImpl(MascotaRepository repository) {
		this.repositoryMascota = repository;
	}
	
	@Override
	@Transactional
	public Mascota save(Mascota masc) {
		
		Mascota mascotaGuardada = repositoryMascota.save(masc);
		return mascotaGuardada;
		
	}

	@Override
	@Transactional
	public Mascota update(Mascota mascota) {

		//repositoryMascota.detach(mascota);
		Mascota mascAct = save(mascota);

		return mascAct;
	}

}
