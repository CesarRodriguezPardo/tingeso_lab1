package tingeso.back.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tingeso.back.entities.CreditEntity;

@Repository
public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
    CreditEntity findByRut(String rut);
}
