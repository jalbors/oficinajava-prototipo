package es.gva.oficinajava.prototipo.service.api;

import es.gva.oficinajava.prototipo.model.Mascota;

public interface MascotaService {
	// guarda la mascota creada
	Mascota save(Mascota masc);

	// actualiza la mascota elegida
	Mascota update(Mascota masc);
}
