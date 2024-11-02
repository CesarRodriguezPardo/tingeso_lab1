package tingeso.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tingeso.back.entities.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
