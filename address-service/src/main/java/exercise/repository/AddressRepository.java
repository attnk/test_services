package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import exercise.entity.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {


}
