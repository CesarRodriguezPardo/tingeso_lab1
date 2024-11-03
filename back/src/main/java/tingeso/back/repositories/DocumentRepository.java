package tingeso.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tingeso.back.entities.DocumentEntity;

import javax.swing.text.Document;
import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    DocumentEntity findByUserId(String userId);
    List<DocumentEntity> findAll();
}
