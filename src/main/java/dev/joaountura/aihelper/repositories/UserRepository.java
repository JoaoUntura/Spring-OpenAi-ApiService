package dev.joaountura.aihelper.repositories;

import dev.joaountura.aihelper.models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserApp, UUID> {

    UserApp findByEmail(String email);

}
