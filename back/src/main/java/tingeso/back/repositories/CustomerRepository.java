package tingeso.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tingeso.back.entities.CustomerEntity;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findAll();
    List<CustomerEntity> findByVerified(boolean verified);
    CustomerEntity findByRut(String rut);
    CustomerEntity findByEmail(String email);
    CustomerEntity findByPhone(String phone);
    CustomerEntity findByEmailAndPassword(String email, String password);
}
