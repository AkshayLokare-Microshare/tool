package io.microshare.appconfig.service;

import io.microshare.appconfig.domain.ConfigType;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class ConfigTypeService {

    private final Logger log = LoggerFactory.getLogger(ConfigTypeService.class);

    @Transactional
    public ConfigType persistOrUpdate(ConfigType configType) {
        log.debug("Request to save ConfigType : {}", configType);
        return ConfigType.persistOrUpdate(configType);
    }

    /**
     * Delete the ConfigType by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete ConfigType : {}", id);
        ConfigType.findByIdOptional(id).ifPresent(configType -> {
            configType.delete();
        });
    }

    /**
     * Get one configType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ConfigType> findOne(Long id) {
        log.debug("Request to get ConfigType : {}", id);
        return ConfigType.findByIdOptional(id);
    }

    /**
     * Get all the configTypes.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<ConfigType> findAll(Page page) {
        log.debug("Request to get all ConfigTypes");
        return new Paged<>(ConfigType.findAll().page(page));
    }
}
