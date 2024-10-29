package io.microshare.appconfig.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigTypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfigTypeAllPropertiesEquals(ConfigType expected, ConfigType actual) {
        assertConfigTypeAutoGeneratedPropertiesEquals(expected, actual);
        assertConfigTypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfigTypeAllUpdatablePropertiesEquals(ConfigType expected, ConfigType actual) {
        assertConfigTypeUpdatableFieldsEquals(expected, actual);
        assertConfigTypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfigTypeAutoGeneratedPropertiesEquals(ConfigType expected, ConfigType actual) {
        assertThat(expected)
            .as("Verify ConfigType auto generated properties")
            .satisfies(e -> assertThat(e.id).as("check id").isEqualTo(actual.id));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfigTypeUpdatableFieldsEquals(ConfigType expected, ConfigType actual) {
        assertThat(expected)
            .as("Verify ConfigType relevant properties")
            .satisfies(e -> assertThat(e.conType).as("check conType").isEqualTo(actual.conType));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfigTypeUpdatableRelationshipsEquals(ConfigType expected, ConfigType actual) {}
}
