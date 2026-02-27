package az.baxtiyargil.commerce.lib.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static az.baxtiyargil.commerce.lib.security.Permission.ORDER_CANCEL;
import static az.baxtiyargil.commerce.lib.security.Permission.ORDER_CANCEL_ANY;
import static az.baxtiyargil.commerce.lib.security.Permission.ORDER_CREATE;
import static az.baxtiyargil.commerce.lib.security.Permission.ORDER_DELETE;
import static az.baxtiyargil.commerce.lib.security.Permission.ORDER_READ;
import static az.baxtiyargil.commerce.lib.security.Permission.ORDER_UPDATE;
import static az.baxtiyargil.commerce.lib.security.Permission.PAYMENT_INITIATE;
import static az.baxtiyargil.commerce.lib.security.Permission.PAYMENT_REFUND;
import static az.baxtiyargil.commerce.lib.security.Permission.PAYMENT_VOID;
import static az.baxtiyargil.commerce.lib.security.Permission.PRODUCT_APPROVE;
import static az.baxtiyargil.commerce.lib.security.Permission.PRODUCT_CREATE;
import static az.baxtiyargil.commerce.lib.security.Permission.PRODUCT_DELETE;
import static az.baxtiyargil.commerce.lib.security.Permission.PRODUCT_READ;
import static az.baxtiyargil.commerce.lib.security.Permission.PRODUCT_UPDATE;
import static az.baxtiyargil.commerce.lib.security.Permission.STORE_APPROVE;
import static az.baxtiyargil.commerce.lib.security.Permission.STORE_CREATE;
import static az.baxtiyargil.commerce.lib.security.Permission.STORE_DELETE;
import static az.baxtiyargil.commerce.lib.security.Permission.STORE_READ;
import static az.baxtiyargil.commerce.lib.security.Permission.STORE_UPDATE;
import static az.baxtiyargil.commerce.lib.security.Permission.SYSTEM_AUDIT_LOG;
import static az.baxtiyargil.commerce.lib.security.Permission.SYSTEM_CONFIG;
import static az.baxtiyargil.commerce.lib.security.Permission.USER_BAN;
import static az.baxtiyargil.commerce.lib.security.Permission.USER_CREATE;
import static az.baxtiyargil.commerce.lib.security.Permission.USER_DELETE;
import static az.baxtiyargil.commerce.lib.security.Permission.USER_READ;
import static az.baxtiyargil.commerce.lib.security.Permission.USER_UPDATE;

@Getter
@RequiredArgsConstructor
public enum Role {

    // ═══════════════════════════════════════════════════════════════════════
    // CUSTOMER — Regular end user
    // ═══════════════════════════════════════════════════════════════════════
    CUSTOMER(
            "Regular customer",
            ORDER_CREATE,
            ORDER_READ,
            ORDER_CANCEL,
            PRODUCT_READ,
            PAYMENT_INITIATE
    ),

    // ═══════════════════════════════════════════════════════════════════════
    // MERCHANT — Business user
    // ═══════════════════════════════════════════════════════════════════════
    MERCHANT(
            "Business merchant",
            // Orders
            ORDER_CREATE,
            ORDER_READ,
            ORDER_UPDATE,
            ORDER_DELETE,
            ORDER_CANCEL,

            // Products
            PRODUCT_CREATE,
            PRODUCT_READ,
            PRODUCT_UPDATE,
            PRODUCT_DELETE,

            // Payments
            PAYMENT_INITIATE,
            PAYMENT_REFUND,

            // Store
            STORE_CREATE,
            STORE_READ,
            STORE_UPDATE
    ),

    // ═══════════════════════════════════════════════════════════════════════
    // ADMIN — System administrator
    // ═══════════════════════════════════════════════════════════════════════
    ADMIN(
            "System administrator",
            // Orders
            ORDER_CREATE,
            ORDER_READ,
            ORDER_UPDATE,
            ORDER_DELETE,
            ORDER_CANCEL,
            ORDER_CANCEL_ANY,

            // Products
            PRODUCT_CREATE,
            PRODUCT_READ,
            PRODUCT_UPDATE,
            PRODUCT_DELETE,
            PRODUCT_APPROVE,

            // Payments
            PAYMENT_INITIATE,
            PAYMENT_REFUND,
            PAYMENT_VOID,

            // Store
            STORE_CREATE,
            STORE_READ,
            STORE_UPDATE,
            STORE_DELETE,
            STORE_APPROVE,

            // Users
            USER_CREATE,
            USER_READ,
            USER_UPDATE,
            USER_DELETE,
            USER_BAN,

            // System
            SYSTEM_CONFIG,
            SYSTEM_AUDIT_LOG
    );

    private final String description;
    private final Set<Permission> permissions;

    Role(String description, Permission... permissions) {
        this.description = description;
        this.permissions = Arrays.stream(permissions).collect(Collectors.toUnmodifiableSet());
    }

    public Set<String> getPermissionValues() {
        return permissions.stream()
                .map(Permission::getValue)
                .collect(Collectors.toUnmodifiableSet());
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public boolean hasPermission(String permissionValue) {
        return permissions.stream()
                .anyMatch(p -> p.getValue().equals(permissionValue));
    }

    public static Role fromName(String name) {
        try {
            return Role.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown role: " + name);
        }
    }

    public static Set<String> getPermissionsForRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return Collections.emptySet();
        }

        return roleNames.stream()
                .map(String::toUpperCase)
                .filter(Role::isValidRole)
                .map(Role::valueOf)
                .flatMap(role -> role.getPermissionValues().stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    private static boolean isValidRole(String name) {
        try {
            Role.valueOf(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
