package az.baxtiyargil.commerce.auth.adapter.in.mapper;

import az.baxtiyargil.commerce.auth.adapter.in.dto.TokenResponse;
import az.baxtiyargil.commerce.auth.domain.AuthToken;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface AuthMapper {

    default TokenResponse toTokenResponse(AuthToken t) {
        return new TokenResponse(t.accessToken(),
                t.refreshToken(),
                t.expiresIn(),
                t.refreshExpiresIn(),
                t.tokenType()
        );
    }

}
