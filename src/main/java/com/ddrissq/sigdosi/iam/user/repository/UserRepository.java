package com.ddrissq.sigdosi.iam.user.repository;

import com.ddrissq.sigdosi.iam.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByProfileCui(String cui);
    boolean existsByProfileCuiAndIdNot(String cui, UUID id);

    boolean existsByProfilePhoneNumber(String phoneNumber);
    boolean existsByProfilePhoneNumberAndIdNot(String phoneNumber, UUID id);

}
