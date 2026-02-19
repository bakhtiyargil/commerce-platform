package az.baxtiyargil.commerce.auth.domain;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record ServiceAuthContext(String userId, String email, String username, Set<String> roles,
                                 Set<String> permissions, String requestId, String correlationId, long issuedAt,
                                 long expiresAt) {

    public ServiceAuthContext(String userId,
                              String email,
                              String username,
                              Set<String> roles,
                              Set<String> permissions,
                              String requestId,
                              String correlationId,
                              long issuedAt,
                              long expiresAt
    ) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.roles = roles != null ? Set.copyOf(roles) : Set.of();
        this.permissions = permissions != null ? Set.copyOf(permissions) : Set.of();
        this.requestId = requestId;
        this.correlationId = correlationId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public static ServiceAuthContext create(
            String userId,
            String email,
            String username,
            Set<String> roles,
            Set<String> permissions,
            String correlationId,
            long ttlSeconds
    ) {
        long now = Instant.now().toEpochMilli();
        return new ServiceAuthContext(
                userId, email, username, roles, permissions,
                UUID.randomUUID().toString(),
                correlationId != null ? correlationId : UUID.randomUUID().toString(),
                now,
                now + ttlSeconds * 1_000L
        );
    }

    public boolean isExpired() {
        return Instant.now().toEpochMilli() > expiresAt;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
}
