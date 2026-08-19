package com.ddrissq.sigdosi.iam.user.mapper;

import com.ddrissq.sigdosi.iam.user.dto.UserCreateRequest;
import com.ddrissq.sigdosi.iam.user.dto.UserResponse;
import com.ddrissq.sigdosi.iam.user.dto.UserUpdateRequest;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import org.mapstruct.*;

@Mapper
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "profile.cui", source = "cui")
    @Mapping(target = "profile.firstName", source = "firstName")
    @Mapping(target = "profile.lastName", source = "lastName")
    @Mapping(target = "profile.phoneNumber", source = "phoneNumber")
    UserAccount toUser(UserCreateRequest request);

    @Mapping(target = "cui", source = "profile.cui")
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target = "lastName", source = "profile.lastName")
    @Mapping(target = "avatar", source = "profile.avatar")
    @Mapping(target = "phoneNumber", source = "profile.phoneNumber")
    UserResponse toResponse(UserAccount user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "profile.cui", source = "cui")
    @Mapping(target = "profile.firstName", source = "firstName")
    @Mapping(target = "profile.lastName", source = "lastName")
    @Mapping(target = "profile.phoneNumber", source = "phoneNumber")
    void updateUser(UserUpdateRequest request, @MappingTarget UserAccount user);

}
