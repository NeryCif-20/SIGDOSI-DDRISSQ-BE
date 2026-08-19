package com.ddrissq.sigdosi.iam.role.entity;

import com.ddrissq.sigdosi.iam.permission.entity.Permission;
import com.ddrissq.sigdosi.common.entity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Role extends AbstractEntity {

    private String name;
    private String description;
    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    public Set<Permission> getPermissions() {
        return Set.copyOf(this.permissions);
    }

    public void setPermissions(Set<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            this.permissions.clear();
            return;
        }
        this.permissions.retainAll(permissions);
        this.permissions.addAll(permissions);
    }

}
