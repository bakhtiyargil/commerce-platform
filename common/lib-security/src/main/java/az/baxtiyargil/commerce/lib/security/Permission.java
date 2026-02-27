package az.baxtiyargil.commerce.lib.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    // ═══════════════════════════════════════════════════════════════════════
    // ORDER PERMISSIONS
    // ═══════════════════════════════════════════════════════════════════════
    ORDER_CREATE("ORDER:CREATE", "Create new orders"),
    ORDER_READ("ORDER:READ", "View orders"),
    ORDER_UPDATE("ORDER:UPDATE", "Update order details"),
    ORDER_DELETE("ORDER:DELETE", "Delete orders"),
    ORDER_CANCEL("ORDER:CANCEL", "Cancel own orders"),
    ORDER_CANCEL_ANY("ORDER:CANCEL_ANY", "Cancel any order (admin)"),

    // ═══════════════════════════════════════════════════════════════════════
    // PRODUCT PERMISSIONS
    // ═══════════════════════════════════════════════════════════════════════
    PRODUCT_CREATE("PRODUCT:CREATE", "Create new products"),
    PRODUCT_READ("PRODUCT:READ", "View products"),
    PRODUCT_UPDATE("PRODUCT:UPDATE", "Update product details"),
    PRODUCT_DELETE("PRODUCT:DELETE", "Delete products"),
    PRODUCT_APPROVE("PRODUCT:APPROVE", "Approve products for listing"),

    // ═══════════════════════════════════════════════════════════════════════
    // PAYMENT PERMISSIONS
    // ═══════════════════════════════════════════════════════════════════════
    PAYMENT_INITIATE("PAYMENT:INITIATE", "Initiate payments"),
    PAYMENT_REFUND("PAYMENT:REFUND", "Process refunds"),
    PAYMENT_VOID("PAYMENT:VOID", "Void payments"),

    // ═══════════════════════════════════════════════════════════════════════
    // STORE PERMISSIONS
    // ═══════════════════════════════════════════════════════════════════════
    STORE_CREATE("STORE:CREATE", "Create new stores"),
    STORE_READ("STORE:READ", "View store details"),
    STORE_UPDATE("STORE:UPDATE", "Update store information"),
    STORE_DELETE("STORE:DELETE", "Delete stores"),
    STORE_APPROVE("STORE:APPROVE", "Approve store registrations"),

    // ═══════════════════════════════════════════════════════════════════════
    // USER PERMISSIONS
    // ═══════════════════════════════════════════════════════════════════════
    USER_CREATE("USER:CREATE", "Create new users"),
    USER_READ("USER:READ", "View user profiles"),
    USER_UPDATE("USER:UPDATE", "Update user information"),
    USER_DELETE("USER:DELETE", "Delete users"),
    USER_BAN("USER:BAN", "Ban users"),

    // ═══════════════════════════════════════════════════════════════════════
    // SYSTEM PERMISSIONS
    // ═══════════════════════════════════════════════════════════════════════
    SYSTEM_CONFIG("SYSTEM:CONFIG", "Modify system configuration"),
    SYSTEM_AUDIT_LOG("SYSTEM:AUDIT_LOG", "View audit logs");

    private final String value;
    private final String description;

    public static Permission fromValue(String value) {
        for (Permission permission : values()) {
            if (permission.value.equals(value)) {
                return permission;
            }
        }
        throw new IllegalArgumentException("Unknown permission: " + value);
    }

    @Override
    public String toString() {
        return value;
    }

}
