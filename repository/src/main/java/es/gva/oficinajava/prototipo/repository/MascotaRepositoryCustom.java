package es.gva.oficinajava.prototipo.repository;

import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Mascota;

@Transactional(readOnly = true)
public interface MascotaRepositoryCustom {
	void detach(Mascota mascota);
}
