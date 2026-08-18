package com.ddrissq.sigdosi.iam.user.service;

import com.ddrissq.sigdosi.iam.role.entity.Role;
import com.ddrissq.sigdosi.iam.role.service.RoleService;
import com.ddrissq.sigdosi.iam.security.provider.CurrentUserProvider;
import com.ddrissq.sigdosi.iam.user.dto.*;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import com.ddrissq.sigdosi.iam.user.model.UserAccountStatus;
import com.ddrissq.sigdosi.iam.user.exception.UserExceptionMessages;
import com.ddrissq.sigdosi.iam.user.mapper.UserMapper;
import com.ddrissq.sigdosi.iam.user.repository.UserRepository;
import com.ddrissq.sigdosi.iam.user.specification.UserSpecification;
import com.ddrissq.sigdosi.shared.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.shared.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.shared.exception.InvalidStateTransitionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public UserResponse get(UUID id) {
        UserAccount user = getByIdOrThrow(id);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse create(UserCreateRequest request) {
        validateEmail(request.email());
        validateCui(request.cui());
        validatePhoneNumber(request.phoneNumber());
        Role role = roleService.getByIdOrThrow(request.role());
        String passwordHash = passwordEncoder.encode(UUID.randomUUID().toString());
        UserAccount user = mapper.toUser(request);
        user.setRole(role);
        user.setPasswordHash(passwordHash);
        user.setStatus(UserAccountStatus.PENDING);
        UserAccount savedUser = repository.save(user);
        return mapper.toResponse(savedUser);
    }

    @Override
    public UserResponse update(UUID id, UserUpdateRequest request) {
        UserAccount user = getByIdOrThrow(id);
        validateEmail(request.email(), id);
        validateCui(request.cui(), id);
        validatePhoneNumber(request.phoneNumber(), id);
        mapper.updateUser(request, user);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse updateRole(UUID id, UserRoleUpdateRequest request) {
        UserAccount user = getByIdOrThrow(id);
        Role role = roleService.getByIdOrThrow(request.role());
        user.setRole(role);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse updateStatus(UUID id, UserStatusUpdateRequest request) {
        UserAccount user = getByIdOrThrow(id);
        UserAccountStatus currentStatus = user.getStatus();
        UserAccountStatus newStatus = request.status();
        boolean involvesPendingStatus = currentStatus == UserAccountStatus.PENDING
                        || newStatus == UserAccountStatus.PENDING;
        if (involvesPendingStatus) {
            throw new InvalidStateTransitionException(currentStatus, newStatus);
        }
        user.setStatus(newStatus);
        return mapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> getAll(UserSearchRequest request, Pageable pageable) {
        Specification<UserAccount> spec = Specification.allOf(
                UserSpecification.hasEmail(request.email()),
                UserSpecification.hasStatus(request.status()),
                UserSpecification.hasCui(request.cui()),
                UserSpecification.hasName(request.name()),
                UserSpecification.hasPhoneNumber(request.phoneNumber()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public UserResponse update(UserUpdateRequest request) {
        UUID id = currentUserProvider.getUserId();
        return update(id, request);
    }

    @Override
    public UserResponse updateAvatar(UserAvatarUpdateRequest request) {
        return null;
    }

    @Override
    public void updatePassword(UserPasswordUpdateRequest request) {
        UUID id = currentUserProvider.getUserId();
        UserAccount user = getByIdOrThrow(id);
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {

        }
        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        user.setPasswordHash(newPasswordHash);
    }

    @Override
    public UserAccount getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        UserExceptionMessages.NOT_FOUND));
    }

    @Override
    public UserAccount getByEmailOrThrow(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        UserExceptionMessages.NOT_FOUND));
    }

    private void validateEmail(String email) {
        validateEmail(email, null);
    }

    private void validateEmail(String email, UUID id) {
        if (email == null) return;
        boolean exists = id == null
                ? repository.existsByEmail(email)
                : repository.existsByEmailAndIdNot(email, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    UserExceptionMessages.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateCui(String cui) {
        validateCui(cui, null);
    }

    private void validateCui(String cui, UUID id) {
        if (cui == null) return;
        boolean exists = id == null
                ? repository.existsByProfile_Cui(cui)
                : repository.existsByProfile_CuiAndIdNot(cui, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    UserExceptionMessages.CUI_ALREADY_EXISTS);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber, null);
    }

    private void validatePhoneNumber(String phoneNumber, UUID id) {
        if (phoneNumber == null) return;
        boolean exists = id == null
                ? repository.existsByProfile_PhoneNumber(phoneNumber)
                : repository.existsByProfile_PhoneNumberAndIdNot(phoneNumber, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    UserExceptionMessages.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }

}
