package com.ddrissq.sigdosi.iam.permission.support;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;

import java.util.UUID;

public class PermissionTestConstants {

    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID DIFFERENT_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final String MODULE = "USERS";
    public static final String DIFFERENT_MODULE = "ROLES";
    public static final PermissionAction ACTION = PermissionAction.CREATE;
    public static final PermissionAction DIFFERENT_ACTION = PermissionAction.DELETE;

}
