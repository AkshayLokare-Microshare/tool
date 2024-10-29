package io.microshare.appconfig.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppConfig getAppConfigSample1() {
        AppConfig appConfig = new AppConfig();
        appConfig.id = 1L;
        appConfig.configKey = "configKey1";
        appConfig.configValue = "configValue1";
        appConfig.subType = "subType1";
        appConfig.subType1 = "subType11";
        appConfig.subType2 = "subType21";
        appConfig.description = "description1";
        return appConfig;
    }

    public static AppConfig getAppConfigSample2() {
        AppConfig appConfig = new AppConfig();
        appConfig.id = 2L;
        appConfig.configKey = "configKey2";
        appConfig.configValue = "configValue2";
        appConfig.subType = "subType2";
        appConfig.subType1 = "subType12";
        appConfig.subType2 = "subType22";
        appConfig.description = "description2";
        return appConfig;
    }

    public static AppConfig getAppConfigRandomSampleGenerator() {
        AppConfig appConfig = new AppConfig();
        appConfig.id = longCount.incrementAndGet();
        appConfig.configKey = UUID.randomUUID().toString();
        appConfig.configValue = UUID.randomUUID().toString();
        appConfig.subType = UUID.randomUUID().toString();
        appConfig.subType1 = UUID.randomUUID().toString();
        appConfig.subType2 = UUID.randomUUID().toString();
        appConfig.description = UUID.randomUUID().toString();
        return appConfig;
    }
}
