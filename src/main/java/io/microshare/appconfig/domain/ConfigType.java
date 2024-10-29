package io.microshare.appconfig.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConfigType.
 */
@Entity
@Table(name = "config_type")
@Cacheable
@RegisterForReflection
public class ConfigType extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @NotNull
    @Column(name = "con_type", nullable = false, unique = true)
    public String conType;

    @OneToMany(mappedBy = "configType")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @BatchSize(size = 20)
    @JsonbTransient
    public Set<AppConfig> conTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigType)) {
            return false;
        }
        return id != null && id.equals(((ConfigType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConfigType{" + "id=" + id + ", conType='" + conType + "'" + "}";
    }

    public ConfigType update() {
        return update(this);
    }

    public ConfigType persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static ConfigType update(ConfigType configType) {
        if (configType == null) {
            throw new IllegalArgumentException("configType can't be null");
        }
        var entity = ConfigType.<ConfigType>findById(configType.id);
        if (entity != null) {
            entity.conType = configType.conType;
            entity.conTypes = configType.conTypes;
        }
        return entity;
    }

    public static ConfigType persistOrUpdate(ConfigType configType) {
        if (configType == null) {
            throw new IllegalArgumentException("configType can't be null");
        }
        if (configType.id == null) {
            persist(configType);
            return configType;
        } else {
            return update(configType);
        }
    }
}
