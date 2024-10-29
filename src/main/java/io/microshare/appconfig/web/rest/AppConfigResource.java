package io.microshare.appconfig.web.rest;

import static jakarta.ws.rs.core.UriBuilder.fromPath;

import io.microshare.appconfig.domain.AppConfig;
import io.microshare.appconfig.service.AppConfigService;
import io.microshare.appconfig.service.Paged;
import io.microshare.appconfig.web.rest.errors.BadRequestAlertException;
import io.microshare.appconfig.web.rest.vm.PageRequestVM;
import io.microshare.appconfig.web.rest.vm.SortRequestVM;
import io.microshare.appconfig.web.util.HeaderUtil;
import io.microshare.appconfig.web.util.PaginationUtil;
import io.microshare.appconfig.web.util.ResponseUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller for managing {@link io.microshare.appconfig.domain.AppConfig}.
 */
@Path("/api/app-configs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AppConfigResource {

    private final Logger log = LoggerFactory.getLogger(AppConfigResource.class);

    private static final String ENTITY_NAME = "appConfig";

    @ConfigProperty(name = "application.name")
    String applicationName;

    @Inject
    AppConfigService appConfigService;

    /**
     * {@code POST  /app-configs} : Create a new appConfig.
     *
     * @param appConfig the appConfig to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new appConfig, or with status {@code 400 (Bad Request)} if the appConfig has already an ID.
     */
    @POST
    public Response createAppConfig(@Valid AppConfig appConfig, @Context UriInfo uriInfo) {
        log.debug("REST request to save AppConfig : {}", appConfig);
        if (appConfig.id != null) {
            throw new BadRequestAlertException("A new appConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = appConfigService.persistOrUpdate(appConfig);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /app-configs} : Updates an existing appConfig.
     *
     * @param appConfig the appConfig to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated appConfig,
     * or with status {@code 400 (Bad Request)} if the appConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appConfig couldn't be updated.
     */
    @PUT
    @Path("/{id}")
    public Response updateAppConfig(@Valid AppConfig appConfig, @PathParam("id") Long id) {
        log.debug("REST request to update AppConfig : {}", appConfig);
        if (appConfig.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = appConfigService.persistOrUpdate(appConfig);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appConfig.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /app-configs/:id} : delete the "id" appConfig.
     *
     * @param id the id of the appConfig to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteAppConfig(@PathParam("id") Long id) {
        log.debug("REST request to delete AppConfig : {}", id);
        appConfigService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /app-configs} : get all the appConfigs.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of appConfigs in body.
     */
    @GET
    public Response getAllAppConfigs(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of AppConfigs");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<AppConfig> result = appConfigService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }

    /**
     * {@code GET  /app-configs/:id} : get the "id" appConfig.
     *
     * @param id the id of the appConfig to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the appConfig, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")
    public Response getAppConfig(@PathParam("id") Long id) {
        log.debug("REST request to get AppConfig : {}", id);
        Optional<AppConfig> appConfig = appConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appConfig);
    }
}
