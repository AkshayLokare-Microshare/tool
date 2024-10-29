package io.microshare.appconfig.web.rest;

import static jakarta.ws.rs.core.UriBuilder.fromPath;

import io.microshare.appconfig.domain.AppConfig;
import io.microshare.appconfig.domain.Authority;
import io.microshare.appconfig.security.AuthoritiesConstants;
import io.microshare.appconfig.service.AppConfigService;
import io.microshare.appconfig.service.AuthorityService;
import io.microshare.appconfig.service.UserService;
import io.microshare.appconfig.web.rest.errors.BadRequestAlertException;
import io.microshare.appconfig.web.util.HeaderUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller to manage authorities.
 */
@Path("/api/authorities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    @ConfigProperty(name = "application.name")
    String applicationName;

    @Inject
    AuthorityService authorityService;

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @GET
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return authorityService.getAuthorities();
    }
}
