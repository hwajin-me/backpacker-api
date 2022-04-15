package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findUserByEmail(Email email);

    boolean existsUserByEmail(Email email);
}
