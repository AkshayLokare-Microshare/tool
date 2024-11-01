package io.microshare.appconfig.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AppConfigAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppConfigAllPropertiesEquals(AppConfig expected, AppConfig actual) {
        assertAppConfigAutoGeneratedPropertiesEquals(expected, actual);
        assertAppConfigAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppConfigAllUpdatablePropertiesEquals(AppConfig expected, AppConfig actual) {
        assertAppConfigUpdatableFieldsEquals(expected, actual);
        assertAppConfigUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppConfigAutoGeneratedPropertiesEquals(AppConfig expected, AppConfig actual) {
        assertThat(expected)
            .as("Verify AppConfig auto generated properties")
            .satisfies(e -> assertThat(e.id).as("check id").isEqualTo(actual.id));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppConfigUpdatableFieldsEquals(AppConfig expected, AppConfig actual) {
        assertThat(expected)
            .as("Verify AppConfig relevant properties")
            .satisfies(e -> assertThat(e.configKey).as("check configKey").isEqualTo(actual.configKey))
            .satisfies(e -> assertThat(e.configValue).as("check configValue").isEqualTo(actual.configValue))
            .satisfies(e -> assertThat(e.subType).as("check subType").isEqualTo(actual.subType))
            .satisfies(e -> assertThat(e.subType1).as("check subType1").isEqualTo(actual.subType1))
            .satisfies(e -> assertThat(e.subType2).as("check subType2").isEqualTo(actual.subType2))
            .satisfies(e -> assertThat(e.description).as("check description").isEqualTo(actual.description))
            .satisfies(e -> assertThat(e.encrypted).as("check encrypted").isEqualTo(actual.encrypted))
            .satisfies(e -> assertThat(e.createDate).as("check createDate").isEqualTo(actual.createDate))
            .satisfies(e -> assertThat(e.updateDate).as("check updateDate").isEqualTo(actual.updateDate))
            .satisfies(e -> assertThat(e.enabled).as("check enabled").isEqualTo(actual.enabled));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppConfigUpdatableRelationshipsEquals(AppConfig expected, AppConfig actual) {
        assertThat(expected)
            .as("Verify AppConfig relationships")
            .satisfies(e -> assertThat(e.configType).as("check configType").isEqualTo(actual.configType));
    }
}
