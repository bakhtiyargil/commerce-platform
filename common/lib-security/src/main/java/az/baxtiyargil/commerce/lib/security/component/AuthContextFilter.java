package az.baxtiyargil.commerce.lib.security.component;

import az.baxtiyargil.commerce.lib.security.ServiceAuthContext;
import az.baxtiyargil.commerce.lib.security.ServiceAuthContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import static az.baxtiyargil.commerce.lib.security.SecurityHeader.AUTH_CONTEXT_HEADER;

@Slf4j
@RequiredArgsConstructor
public class AuthContextFilter extends OncePerRequestFilter {

    private final AuthContextSigner signer;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            String signedContext = request.getHeader(AUTH_CONTEXT_HEADER);

            if (signedContext == null || signedContext.isBlank()) {
                log.warn("Missing X-Auth-Context header — request URI: {}", request.getRequestURI());
                sendUnauthorized(response, "Missing authentication context");
                return;
            }

            ServiceAuthContext context = signer.verify(signedContext);
            ServiceAuthContextHolder.set(context);

            setSpringSecurityContext(context);
            log.debug("Auth context validated — userId={}, correlationId={}", context.userId(), context.correlationId());

            chain.doFilter(request, response);
        } catch (AuthContextSigner.SignerException e) {
            log.warn("Auth context validation failed: {}", e.getMessage());
            sendUnauthorized(response, "Invalid authentication context");
        } finally {
            ServiceAuthContextHolder.clear();
            SecurityContextHolder.clearContext();
        }
    }

    private void setSpringSecurityContext(ServiceAuthContext context) {
        List<SimpleGrantedAuthority> roleAuthorities = context.roles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

        List<SimpleGrantedAuthority> permissionAuthorities = context.permissions().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        List<SimpleGrantedAuthority> allAuthorities = Stream.concat(
                roleAuthorities.stream(),
                permissionAuthorities.stream()
        ).toList();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        context.userId(),
                        null,
                        allAuthorities
                );

        authentication.setDetails(context);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(String.format(
                "{\"error\":\"UNAUTHORIZED\",\"message\":\"%s\"}", message
        ));
    }
}
