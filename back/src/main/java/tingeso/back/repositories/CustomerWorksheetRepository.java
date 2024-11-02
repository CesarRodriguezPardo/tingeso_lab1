package tingeso.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tingeso.back.entities.CustomerWorksheetEntity;

import java.util.List;

@Repository
public interface CustomerWorksheetRepository extends JpaRepository<CustomerWorksheetEntity, Long> {
    CustomerWorksheetEntity findByRut(String rut);
    List<CustomerWorksheetEntity> findAll();
}
