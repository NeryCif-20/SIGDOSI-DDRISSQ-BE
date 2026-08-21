package com.ddrissq.sigdosi.iam.permission.support;

import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionTestData {

    public static Permission.PermissionBuilder aPermission() {
        return Permission.builder()
                .module(MODULE)
                .action(ACTION);
    }

    public static List<Permission> generateForModule(String module) {
        List<PermissionAction> actions = List.of(ACTION, DIFFERENT_ACTION);
        List<Permission> permissions = new ArrayList<>(actions.size());
        for (PermissionAction action : actions) {
            Permission permission = PermissionTestData.aPermission()
                    .module(module)
                    .action(action)
                    .build();
            permissions.add(permission);
        }
        return permissions;
    }

    public static List<Permission> generateForAction(PermissionAction action) {
        List<String> modules = List.of(MODULE, DIFFERENT_MODULE);
        List<Permission> permissions = new ArrayList<>(modules.size());
        for (String module : modules) {
            Permission permission = PermissionTestData.aPermission()
                    .module(module)
                    .action(action)
                    .build();
            permissions.add(permission);
        }
        return permissions;
    }

}
