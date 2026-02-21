package az.baxtiyargil.commerce.lib.security;

import java.util.Optional;

public final class ServiceAuthContextHolder {

    private static final InheritableThreadLocal<ServiceAuthContext> CONTEXT =
            new InheritableThreadLocal<>();

    private ServiceAuthContextHolder() {
    }

    public static void set(ServiceAuthContext context) {
        CONTEXT.set(context);
    }

    public static ServiceAuthContext get() {
        ServiceAuthContext ctx = CONTEXT.get();
        if (ctx == null) {
            throw new IllegalStateException("No ServiceAuthContext in current thread");
        }
        return ctx;
    }

    public static Optional<ServiceAuthContext> getOptional() {
        return Optional.ofNullable(CONTEXT.get());
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static String getUserId() {
        return get().userId();
    }

    public static String getEmail() {
        return get().email();
    }

    public static String getCorrelationId() {
        return get().correlationId();
    }

    public static boolean hasPermission(String permission) {
        return get().hasPermission(permission);
    }

    public static boolean hasRole(String role) {
        return get().hasRole(role);
    }
}
