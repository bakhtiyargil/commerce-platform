package az.baxtiyargil.commerce.lib.security;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class RolePermissionMapper {

    public Set<String> getPermissionsForRoles(Set<String> roleNames) {
        return Role.getPermissionsForRoles(roleNames);
    }

    public boolean roleHasPermission(String roleName, String permissionValue) {
        try {
            Role role = Role.fromName(roleName);
            return role.hasPermission(permissionValue);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Set<String> getPermissionsForRole(String roleName) {
        try {
            Role role = Role.fromName(roleName);
            return role.getPermissionValues();
        } catch (IllegalArgumentException e) {
            return Collections.emptySet();
        }
    }

    public Set<String> getAllRoles() {
        return Set.of(
                Role.CUSTOMER.name(),
                Role.MERCHANT.name(),
                Role.ADMIN.name()
        );
    }

    public String getRoleDescription(String roleName) {
        try {
            Role role = Role.fromName(roleName);
            return role.getDescription();
        } catch (IllegalArgumentException e) {
            return "Unknown role";
        }
    }
}
