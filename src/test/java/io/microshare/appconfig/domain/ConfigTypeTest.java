package io.microshare.appconfig.domain;

import static io.microshare.appconfig.domain.AppConfigTestSamples.*;
import static io.microshare.appconfig.domain.ConfigTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.microshare.appconfig.TestUtil;
import org.junit.jupiter.api.Test;

class ConfigTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigType.class);
        ConfigType configType1 = getConfigTypeSample1();
        ConfigType configType2 = new ConfigType();
        assertThat(configType1).isNotEqualTo(configType2);

        configType2.id = configType1.id;
        assertThat(configType1).isEqualTo(configType2);

        configType2 = getConfigTypeSample2();
        assertThat(configType1).isNotEqualTo(configType2);
    }
}
