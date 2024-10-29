package io.microshare.appconfig.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A AppConfig.
 */
@Entity
@Table(name = "app_config")
@Cacheable
@RegisterForReflection
public class AppConfig extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @NotNull
    @Column(name = "config_key", nullable = false)
    public String configKey;

    @NotNull
    @Column(name = "config_value", nullable = false)
    public String configValue;

    @Column(name = "sub_type")
    public String subType;

    @Column(name = "sub_type_1")
    public String subType1;

    @Column(name = "sub_type_2")
    public String subType2;

    @Column(name = "description")
    public String description;

    @NotNull
    @Column(name = "encrypted", nullable = false)
    public Boolean encrypted;

    @Column(name = "create_date")
    public Instant createDate;

    @Column(name = "update_date")
    public Instant updateDate;

    @NotNull
    @Column(name = "enabled", nullable = false)
    public Boolean enabled;

    @ManyToOne(optional = false)
    @JoinColumn(name = "config_type_id")
    @NotNull
    public ConfigType configType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppConfig)) {
            return false;
        }
        return id != null && id.equals(((AppConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "AppConfig{" +
            "id=" +
            id +
            ", configKey='" +
            configKey +
            "'" +
            ", configValue='" +
            configValue +
            "'" +
            ", subType='" +
            subType +
            "'" +
            ", subType1='" +
            subType1 +
            "'" +
            ", subType2='" +
            subType2 +
            "'" +
            ", description='" +
            description +
            "'" +
            ", encrypted='" +
            encrypted +
            "'" +
            ", createDate='" +
            createDate +
            "'" +
            ", updateDate='" +
            updateDate +
            "'" +
            ", enabled='" +
            enabled +
            "'" +
            "}"
        );
    }

    public AppConfig update() {
        return update(this);
    }

    public AppConfig persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static AppConfig update(AppConfig appConfig) {
        if (appConfig == null) {
            throw new IllegalArgumentException("appConfig can't be null");
        }
        var entity = AppConfig.<AppConfig>findById(appConfig.id);
        if (entity != null) {
            entity.configKey = appConfig.configKey;
            entity.configValue = appConfig.configValue;
            entity.subType = appConfig.subType;
            entity.subType1 = appConfig.subType1;
            entity.subType2 = appConfig.subType2;
            entity.description = appConfig.description;
            entity.encrypted = appConfig.encrypted;
            entity.createDate = appConfig.createDate;
            entity.updateDate = appConfig.updateDate;
            entity.enabled = appConfig.enabled;
            entity.configType = appConfig.configType;
        }
        return entity;
    }

    public static AppConfig persistOrUpdate(AppConfig appConfig) {
        if (appConfig == null) {
            throw new IllegalArgumentException("appConfig can't be null");
        }
        if (appConfig.id == null) {
            persist(appConfig);
            return appConfig;
        } else {
            return update(appConfig);
        }
    }
}
