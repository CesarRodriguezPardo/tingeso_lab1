package tingeso.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tingeso.back.entities.SavingAccountEntity;

import java.util.List;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccountEntity, Long> {
    SavingAccountEntity findByRut(String rut);
    List<SavingAccountEntity> findAll();
}
