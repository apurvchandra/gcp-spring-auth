package hello;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class AzureAdAuthoritiesExtractor implements Converter<Map<String, Object>, Collection<GrantedAuthority>> {

    private static final String AUTHORITIES_KEY = "roles";

    @Override
    public Collection<GrantedAuthority> convert(Map<String, Object> map) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (map.containsKey(AUTHORITIES_KEY)) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(map.get(AUTHORITIES_KEY).toString());
        }
        return authorities;
    }

    public UsernamePasswordAuthenticationToken extractAuthenticationToken(OidcIdToken idToken) {
        Map<String, Object> claims = idToken.getClaims();
        Collection<GrantedAuthority> authorities = convert(claims);
        User principal = new User(idToken.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, idToken.getTokenValue(), authorities);
    }
}
