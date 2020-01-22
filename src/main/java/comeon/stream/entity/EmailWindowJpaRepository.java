package comeon.stream.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailWindowJpaRepository extends JpaRepository<EmailWindowEntity, Long> {

    List<EmailWindowEntity> findByEmail(String email);
}
