package io.microshare.appconfig.service;

import io.microshare.appconfig.domain.Authority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class AuthorityService {

    private final Logger log = LoggerFactory.getLogger(AuthorityService.class);

    public List<String> getAuthorities() {
        return Authority.<Authority>streamAll().map(authority -> authority.name).collect(Collectors.toList());
    }
}
