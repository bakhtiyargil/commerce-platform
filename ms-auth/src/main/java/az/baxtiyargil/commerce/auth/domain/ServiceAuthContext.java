package az.baxtiyargil.commerce.auth.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public final class ServiceAuthContext {

    @JsonProperty("userId")
    private final String userId;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("username")
    private final String username;

    @JsonProperty("roles")
    private final Set<String> roles;

    @JsonProperty("permissions")
    private final Set<String> permissions;

    @JsonProperty("requestId")
    private final String requestId;       // unique per auth-context creation

    @JsonProperty("correlationId")
    private final String correlationId;   // propagated from gateway → links entire request chain

    @JsonProperty("issuedAt")
    private final long issuedAt;

    @JsonProperty("expiresAt")
    private final long expiresAt;

    @JsonCreator
    public ServiceAuthContext(
            @JsonProperty("userId") String userId,
            @JsonProperty("email") String email,
            @JsonProperty("username") String username,
            @JsonProperty("roles") Set<String> roles,
            @JsonProperty("permissions") Set<String> permissions,
            @JsonProperty("requestId") String requestId,
            @JsonProperty("correlationId") String correlationId,
            @JsonProperty("issuedAt") long issuedAt,
            @JsonProperty("expiresAt") long expiresAt
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
