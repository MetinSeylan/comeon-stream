package comeon.stream.services;

import comeon.stream.entity.EmailWindowEntity;
import comeon.stream.entity.EmailWindowJpaRepository;
import comeon.stream.services.models.EmailCount;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StorageService {
    private final EmailWindowJpaRepository emailWindowJpaRepository;

    public void create(EmailCount emailCount) {
        EmailWindowEntity emailWindowEntity = new EmailWindowEntity();
        emailWindowEntity.setCount(emailCount.getCount());
        emailWindowEntity.setEmail(emailCount.getEmail());
        emailWindowEntity.setEndDate(emailCount.getEndDate());
        emailWindowEntity.setStartDate(emailCount.getStartDate());

        emailWindowJpaRepository.save(emailWindowEntity);
    }

    public List<EmailWindowEntity> retrieve(String email) {
        return emailWindowJpaRepository.findByEmail(email);
    }
}
