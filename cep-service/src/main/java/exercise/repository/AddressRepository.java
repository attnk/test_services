package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import exercise.entity.AddressEntity;
import exercise.entity.CepDetails;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	@Query("SELECT a.id FROM AddressEntity a WHERE a.cep = :cepDetails")
	public Long findByCepId(@Param("cepDetails") CepDetails cepDetails);

}
