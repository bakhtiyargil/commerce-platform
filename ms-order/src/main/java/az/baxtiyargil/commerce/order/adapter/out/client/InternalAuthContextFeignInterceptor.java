package az.baxtiyargil.commerce.order.adapter.out.client;

import az.baxtiyargil.commerce.lib.security.ServiceAuthContextHolder;
import az.baxtiyargil.commerce.lib.security.component.AuthContextSigner;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import static az.baxtiyargil.commerce.lib.security.SecurityHeader.AUTH_CONTEXT_HEADER;

@RequiredArgsConstructor
public class InternalAuthContextFeignInterceptor implements RequestInterceptor {

    private final AuthContextSigner authContextSigner;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServiceAuthContextHolder.getOptional().ifPresent(ctx -> {
            String signed = authContextSigner.sign(ctx);
            requestTemplate.header(AUTH_CONTEXT_HEADER, signed);
        });
    }
}
