package com.ddrissq.sigdosi.iam.user.service;

import com.ddrissq.sigdosi.common.file.storage.FileStorage;
import com.ddrissq.sigdosi.iam.constants.IamErrorMessages;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.role.model.Role;
import com.ddrissq.sigdosi.iam.role.service.RoleService;
import com.ddrissq.sigdosi.iam.security.provider.CurrentUserProvider;
import com.ddrissq.sigdosi.iam.user.dto.*;
import com.ddrissq.sigdosi.iam.user.model.User;
import com.ddrissq.sigdosi.iam.user.model.UserProfile;
import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import com.ddrissq.sigdosi.iam.user.constant.UserErrorMessages;
import com.ddrissq.sigdosi.iam.user.mapper.UserMapper;
import com.ddrissq.sigdosi.iam.user.repository.UserRepository;
import com.ddrissq.sigdosi.iam.user.specification.UserSpecification;
import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.common.exception.InvalidStateTransitionException;
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
    private final FileStorage storage;

    @Override
    public UserResponse get(UUID id) {
        User user = getByIdOrThrow(id);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse create(UserCreateRequest request) {
        validateEmail(request.email());
        validateCui(request.cui());
        validatePhoneNumber(request.phoneNumber());
        Role role = roleService.getByIdOrThrow(request.role());
        String passwordHash = passwordEncoder.encode(UUID.randomUUID().toString());
        User user = mapper.toUser(request);
        UserProfile profile = mapper.toProfile(request);
        user.setProfile(profile);
        user.setRole(role);
        user.setPasswordHash(passwordHash);
        user.setStatus(UserStatus.PENDING);
        User savedUser = repository.save(user);
        return mapper.toResponse(savedUser);
    }

    @Override
    public UserResponse update(UUID id, UserUpdateRequest request) {
        User user = getByIdOrThrow(id);
        String email = request.email() == null
                ? user.getEmail()
                : request.email();
        validateEmail(email, id);
        String cui = request.cui() == null
                ? user.getProfile().getCui()
                : request.cui();
        validateCui(cui, id);
        String phoneNumber = request.phoneNumber() == null
                ? user.getProfile().getPhoneNumber()
                : request.phoneNumber();
        validatePhoneNumber(phoneNumber, id);
        mapper.updateUser(request, user);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse updateRole(UUID id, UserRoleUpdateRequest request) {
        User user = getByIdOrThrow(id);
        Role role = roleService.getByIdOrThrow(request.role());
        user.setRole(role);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse updateStatus(UUID id, UserStatusUpdateRequest request) {
        User user = getByIdOrThrow(id);
        UserStatus currentStatus = user.getStatus();
        UserStatus newStatus = request.status();
        boolean involvesPendingStatus = currentStatus == UserStatus.PENDING
                        || newStatus == UserStatus.PENDING;
        if (involvesPendingStatus) {
            throw new InvalidStateTransitionException(currentStatus, newStatus);
        }
        user.setStatus(newStatus);
        return mapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> getAll(UserSearchRequest request, Pageable pageable) {
        Specification<User> spec = Specification.allOf(
                Specification.anyOf(
                        UserSpecification.hasEmail(request.q()),
                        UserSpecification.hasCui(request.q()),
                        UserSpecification.hasName(request.q()),
                        UserSpecification.hasPhoneNumber(request.q())
                ),
                UserSpecification.hasStatus(request.status()));
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
        UUID id = currentUserProvider.getUserId();
        User user = getByIdOrThrow(id);
        String avatar = storage.save(request.avatar());
        if  (user.getProfile().getAvatar() != null) {
            storage.delete(user.getProfile().getAvatar());
        }
        user.getProfile().setAvatar(avatar);
        return mapper.toResponse(user);
    }

    @Override
    public void updatePassword(UserPasswordUpdateRequest request) {
        UUID id = currentUserProvider.getUserId();
        User user = getByIdOrThrow(id);
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new AuthenticationException(IamErrorMessages.BAD_CREDENTIALS);
        }
        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        user.setPasswordHash(newPasswordHash);
    }

    @Override
    public User getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        UserErrorMessages.NOT_FOUND));
    }

    @Override
    public User getByEmailOrThrow(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        UserErrorMessages.NOT_FOUND));
    }

    private void validateEmail(String email) {
        validateEmail(email, null);
    }

    private void validateEmail(String email, UUID id) {
        boolean exists = id == null
                ? repository.existsByEmail(email)
                : repository.existsByEmailAndIdNot(email, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    UserErrorMessages.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateCui(String cui) {
        validateCui(cui, null);
    }

    private void validateCui(String cui, UUID id) {
        boolean exists = id == null
                ? repository.existsByProfile_Cui(cui)
                : repository.existsByProfile_CuiAndIdNot(cui, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    UserErrorMessages.CUI_ALREADY_EXISTS);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber, null);
    }

    private void validatePhoneNumber(String phoneNumber, UUID id) {
        boolean exists = id == null
                ? repository.existsByProfile_PhoneNumber(phoneNumber)
                : repository.existsByProfile_PhoneNumberAndIdNot(phoneNumber, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    UserErrorMessages.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }

}
