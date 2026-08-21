package com.ddrissq.sigdosi.iam.user.service;

import com.ddrissq.sigdosi.iam.user.dto.*;
import com.ddrissq.sigdosi.iam.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserResponse get(UUID id);
    UserResponse create(UserCreateRequest request);
    UserResponse update(UUID id, UserUpdateRequest request);
    UserResponse updateRole(UUID id, UserRoleUpdateRequest request);
    UserResponse updateStatus(UUID id, UserStatusUpdateRequest request);
    Page<UserResponse> getAll(UserSearchRequest request, Pageable pageable);
    UserResponse update(UserUpdateRequest request);
    void updatePassword(UserPasswordUpdateRequest request);
    UserResponse updateAvatar(UserAvatarUpdateRequest request);
    User getByIdOrThrow(UUID id);
    User getByEmailOrThrow(String email);

}
