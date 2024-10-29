package io.microshare.appconfig.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ConfigTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ConfigType getConfigTypeSample1() {
        ConfigType configType = new ConfigType();
        configType.id = 1L;
        configType.conType = "conType1";
        return configType;
    }

    public static ConfigType getConfigTypeSample2() {
        ConfigType configType = new ConfigType();
        configType.id = 2L;
        configType.conType = "conType2";
        return configType;
    }

    public static ConfigType getConfigTypeRandomSampleGenerator() {
        ConfigType configType = new ConfigType();
        configType.id = longCount.incrementAndGet();
        configType.conType = UUID.randomUUID().toString();
        return configType;
    }
}
