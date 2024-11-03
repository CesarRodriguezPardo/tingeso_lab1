package tingeso.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tingeso.back.entities.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    List<UserEntity> findByVerified(boolean verified);
    UserEntity findByRut(String rut);
    UserEntity findByEmail(String email);
    UserEntity findByPhone(String phone);
    UserEntity findByEmailAndPassword(String email, String password);
}
