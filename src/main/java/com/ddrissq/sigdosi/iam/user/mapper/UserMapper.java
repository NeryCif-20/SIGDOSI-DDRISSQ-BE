package com.ddrissq.sigdosi.iam.user.mapper;

import com.ddrissq.sigdosi.common.mapper.util.StringMapper;
import com.ddrissq.sigdosi.iam.role.controller.RoleController;
import com.ddrissq.sigdosi.iam.user.dto.UserCreateRequest;
import com.ddrissq.sigdosi.iam.user.dto.UserResponse;
import com.ddrissq.sigdosi.iam.user.dto.UserUpdateRequest;
import com.ddrissq.sigdosi.iam.user.model.User;
import com.ddrissq.sigdosi.iam.user.model.UserProfile;
import org.mapstruct.*;

@Mapper(uses = {RoleController.class, StringMapper.class})
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "status", ignore = true)
    User toUser(UserCreateRequest request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    UserProfile toProfile(UserCreateRequest request);

    @Mapping(target = "cui", source = "profile.cui")
    @Mapping(target = "firstName", source = "profile.firstName", qualifiedByName = "capitalize")
    @Mapping(target = "lastName", source = "profile.lastName", qualifiedByName = "capitalize")
    @Mapping(target = "avatar", source = "profile.avatar")
    @Mapping(target = "phoneNumber", source = "profile.phoneNumber")
    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "profile.cui", source = "cui")
    @Mapping(target = "profile.firstName", source = "firstName", qualifiedByName = "capitalize")
    @Mapping(target = "profile.lastName", source = "lastName", qualifiedByName = "capitalize")
    @Mapping(target = "profile.phoneNumber", source = "phoneNumber")
    void updateUser(UserUpdateRequest request, @MappingTarget User user);

}
