package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import exercise.entity.CepDetails;

/**
 * Repository "mocakdo"
 * @author Adriano Tanaka
 *
 */
@Repository
public interface CepServiceRepository extends JpaRepository<CepDetails, Long> {

	public CepDetails findByCep(String cep);

}
