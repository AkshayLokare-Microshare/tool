package io.microshare.appconfig.service;

import io.microshare.appconfig.domain.AppConfig;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class AppConfigService {

    private final Logger log = LoggerFactory.getLogger(AppConfigService.class);

    @Transactional
    public AppConfig persistOrUpdate(AppConfig appConfig) {
        log.debug("Request to save AppConfig : {}", appConfig);
        return AppConfig.persistOrUpdate(appConfig);
    }

    /**
     * Delete the AppConfig by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete AppConfig : {}", id);
        AppConfig.findByIdOptional(id).ifPresent(appConfig -> {
            appConfig.delete();
        });
    }

    /**
     * Get one appConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AppConfig> findOne(Long id) {
        log.debug("Request to get AppConfig : {}", id);
        return AppConfig.findByIdOptional(id);
    }

    /**
     * Get all the appConfigs.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<AppConfig> findAll(Page page) {
        log.debug("Request to get all AppConfigs");
        return new Paged<>(AppConfig.findAll().page(page));
    }
}
