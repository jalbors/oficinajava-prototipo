package es.gva.oficinajava.prototipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import es.gva.oficinajava.prototipo.model.Mascota;
import es.gva.oficinajava.prototipo.model.Propietario;

@Transactional(readOnly = true)
public interface MascotaRepository extends PropietarioRepositoryCustom, JpaRepository<Mascota, Long> {
	
	@Query("SELECT apodo FROM Mascota masc WHERE masc.id = :id")
	Mascota findOneWithMascotasData(@Param("id") Long id);

	//void detach(Mascota mascota);
}
