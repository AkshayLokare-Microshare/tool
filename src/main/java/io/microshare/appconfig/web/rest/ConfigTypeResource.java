package io.microshare.appconfig.web.rest;

import static jakarta.ws.rs.core.UriBuilder.fromPath;

import io.microshare.appconfig.domain.ConfigType;
import io.microshare.appconfig.service.ConfigTypeService;
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
 * REST controller for managing {@link io.microshare.appconfig.domain.ConfigType}.
 */
@Path("/api/config-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ConfigTypeResource {

    private final Logger log = LoggerFactory.getLogger(ConfigTypeResource.class);

    private static final String ENTITY_NAME = "configType";

    @ConfigProperty(name = "application.name")
    String applicationName;

    @Inject
    ConfigTypeService configTypeService;

    /**
     * {@code POST  /config-types} : Create a new configType.
     *
     * @param configType the configType to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new configType, or with status {@code 400 (Bad Request)} if the configType has already an ID.
     */
    @POST
    public Response createConfigType(@Valid ConfigType configType, @Context UriInfo uriInfo) {
        log.debug("REST request to save ConfigType : {}", configType);
        if (configType.id != null) {
            throw new BadRequestAlertException("A new configType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = configTypeService.persistOrUpdate(configType);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /config-types} : Updates an existing configType.
     *
     * @param configType the configType to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated configType,
     * or with status {@code 400 (Bad Request)} if the configType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configType couldn't be updated.
     */
    @PUT
    @Path("/{id}")
    public Response updateConfigType(@Valid ConfigType configType, @PathParam("id") Long id) {
        log.debug("REST request to update ConfigType : {}", configType);
        if (configType.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = configTypeService.persistOrUpdate(configType);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, configType.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /config-types/:id} : delete the "id" configType.
     *
     * @param id the id of the configType to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteConfigType(@PathParam("id") Long id) {
        log.debug("REST request to delete ConfigType : {}", id);
        configTypeService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /config-types} : get all the configTypes.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of configTypes in body.
     */
    @GET
    public Response getAllConfigTypes(
        @BeanParam PageRequestVM pageRequest,
        @BeanParam SortRequestVM sortRequest,
        @Context UriInfo uriInfo
    ) {
        log.debug("REST request to get a page of ConfigTypes");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<ConfigType> result = configTypeService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }

    /**
     * {@code GET  /config-types/:id} : get the "id" configType.
     *
     * @param id the id of the configType to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the configType, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")
    public Response getConfigType(@PathParam("id") Long id) {
        log.debug("REST request to get ConfigType : {}", id);
        Optional<ConfigType> configType = configTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configType);
    }
}
