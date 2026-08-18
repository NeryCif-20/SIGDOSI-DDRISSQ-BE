package com.ddrissq.sigdosi.iam.user.repository;

import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAccount, UUID>, JpaSpecificationExecutor<UserAccount> {

    Optional<UserAccount> findByEmail(String email);
    
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByProfile_Cui(String cui);
    boolean existsByProfile_CuiAndIdNot(String cui, UUID id);

    boolean existsByProfile_PhoneNumber(String phoneNumber);
    boolean existsByProfile_PhoneNumberAndIdNot(String phoneNumber, UUID id);

}
