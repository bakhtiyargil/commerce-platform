package az.baxtiyargil.commerce.gateway.component;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ConditionalWebFilter implements WebFilter {

    private final WebFilter delegate;
    private final ServerWebExchangeMatcher excludeMatcher;

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange,
                                      @NonNull WebFilterChain chain) {
        return excludeMatcher.matches(exchange)
                .flatMap(matchResult -> matchResult.isMatch()
                        ? chain.filter(exchange)
                        : delegate.filter(exchange, chain)
                );
    }
}