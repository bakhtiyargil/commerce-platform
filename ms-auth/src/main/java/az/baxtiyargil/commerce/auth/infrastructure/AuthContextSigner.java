package az.baxtiyargil.commerce.auth.infrastructure;

import az.baxtiyargil.commerce.auth.domain.ServiceAuthContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
public class AuthContextSigner {

    private static final String ALGORITHM = "HmacSHA256";

    private final byte[] secretBytes;
    private final ObjectMapper objectMapper;

    public AuthContextSigner(@Value("${auth.hmac.secret}") String secret, ObjectMapper objectMapper) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("auth.hmac.secret must be at least 32 characters. Check your environment.");
        }
        this.secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.objectMapper = objectMapper;
    }

    public String sign(ServiceAuthContext ctx) {
        try {
            String json = objectMapper.writeValueAsString(ctx);
            String encodedJson = b64(json.getBytes(StandardCharsets.UTF_8));
            String hmac = hmac(encodedJson);
            return encodedJson + "." + hmac;
        } catch (Exception e) {
            throw new SignerException("Failed to sign ServiceAuthContext", e);
        }
    }

    public ServiceAuthContext verify(String signed) {
        int dot = signed.lastIndexOf('.');
        if (dot < 0) {
            throw new SignerException("Malformed context token — no separator found");
        }

        String encodedJson = signed.substring(0, dot);
        String givenHmac = signed.substring(dot + 1);
        String expectedHmac = hmac(encodedJson);

        if (!constantTimeEquals(expectedHmac, givenHmac)) {
            throw new SignerException("HMAC verification failed — context was tampered");
        }

        try {
            byte[] jsonBytes = Base64.getUrlDecoder().decode(encodedJson);
            ServiceAuthContext ctx = objectMapper.readValue(jsonBytes, ServiceAuthContext.class);

            if (ctx.isExpired()) {
                throw new SignerException("ServiceAuthContext has expired");
            }
            return ctx;
        } catch (SignerException e) {
            throw e;
        } catch (Exception e) {
            throw new SignerException("Failed to deserialise ServiceAuthContext", e);
        }
    }

    private String hmac(String data) {
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(secretBytes, ALGORITHM));
            return b64(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new SignerException("HMAC computation failed", e);
        }
    }

    private String b64(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /** MessageDigest-style XOR comparison — O(n) regardless of first difference. */
    private boolean constantTimeEquals(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            diff |= (a.charAt(i) ^ b.charAt(i));
        }
        return diff == 0;
    }

    public static class SignerException extends RuntimeException {
        public SignerException(String msg) {
            super(msg);
        }

        public SignerException(String msg, Throwable t) {
            super(msg, t);
        }
    }
}
