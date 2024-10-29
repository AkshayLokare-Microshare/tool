package io.microshare.appconfig.domain;

import static io.microshare.appconfig.domain.AppConfigTestSamples.*;
import static io.microshare.appconfig.domain.ConfigTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.microshare.appconfig.TestUtil;
import org.junit.jupiter.api.Test;

class AppConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConfig.class);
        AppConfig appConfig1 = getAppConfigSample1();
        AppConfig appConfig2 = new AppConfig();
        assertThat(appConfig1).isNotEqualTo(appConfig2);

        appConfig2.id = appConfig1.id;
        assertThat(appConfig1).isEqualTo(appConfig2);

        appConfig2 = getAppConfigSample2();
        assertThat(appConfig1).isNotEqualTo(appConfig2);
    }
}
