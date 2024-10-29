package io.microshare.appconfig.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import io.microshare.appconfig.TestUtil;
import io.microshare.appconfig.domain.AppConfig;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

@QuarkusTest
public class AppConfigResourceTest {

    private static final TypeRef<AppConfig> ENTITY_TYPE = new TypeRef<>() {};

    private static final TypeRef<List<AppConfig>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {};

    private static final String DEFAULT_CONFIG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TYPE_1 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TYPE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TYPE_2 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TYPE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENCRYPTED = false;
    private static final Boolean UPDATED_ENCRYPTED = true;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochSecond(0L).truncatedTo(ChronoUnit.SECONDS);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.SECONDS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochSecond(0L).truncatedTo(ChronoUnit.SECONDS);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.SECONDS);

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    String adminToken;

    AppConfig appConfig;

    @Inject
    LiquibaseFactory liquibaseFactory;

    @BeforeAll
    static void jsonMapper() {
        RestAssured.config = RestAssured.config()
            .objectMapperConfig(objectMapperConfig().defaultObjectMapper(TestUtil.jsonbObjectMapper()));
    }

    @BeforeEach
    public void authenticateAdmin() {
        this.adminToken = TestUtil.getAdminToken();
    }

    @BeforeEach
    public void databaseFixture() {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppConfig createEntity() {
        var appConfig = new AppConfig();
        appConfig.configKey = DEFAULT_CONFIG_KEY;
        appConfig.configValue = DEFAULT_CONFIG_VALUE;
        appConfig.subType = DEFAULT_SUB_TYPE;
        appConfig.subType1 = DEFAULT_SUB_TYPE_1;
        appConfig.subType2 = DEFAULT_SUB_TYPE_2;
        appConfig.description = DEFAULT_DESCRIPTION;
        appConfig.encrypted = DEFAULT_ENCRYPTED;
        appConfig.createDate = DEFAULT_CREATE_DATE;
        appConfig.updateDate = DEFAULT_UPDATE_DATE;
        appConfig.enabled = DEFAULT_ENABLED;
        return appConfig;
    }

    @BeforeEach
    public void initTest() {
        appConfig = createEntity();
    }

    @Test
    public void createAppConfig() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the AppConfig
        appConfig = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        // Validate the AppConfig in the database
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeCreate + 1);
        var testAppConfig = appConfigList.stream().filter(it -> appConfig.id.equals(it.id)).findFirst().get();
        assertThat(testAppConfig.configKey).isEqualTo(DEFAULT_CONFIG_KEY);
        assertThat(testAppConfig.configValue).isEqualTo(DEFAULT_CONFIG_VALUE);
        assertThat(testAppConfig.subType).isEqualTo(DEFAULT_SUB_TYPE);
        assertThat(testAppConfig.subType1).isEqualTo(DEFAULT_SUB_TYPE_1);
        assertThat(testAppConfig.subType2).isEqualTo(DEFAULT_SUB_TYPE_2);
        assertThat(testAppConfig.description).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppConfig.encrypted).isEqualTo(DEFAULT_ENCRYPTED);
        assertThat(testAppConfig.createDate).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testAppConfig.updateDate).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testAppConfig.enabled).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    public void createAppConfigWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the AppConfig with an existing ID
        appConfig.id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the AppConfig in the database
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkConfigKeyIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        appConfig.configKey = null;

        // Create the AppConfig, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the AppConfig in the database
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkConfigValueIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        appConfig.configValue = null;

        // Create the AppConfig, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the AppConfig in the database
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEncryptedIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        appConfig.encrypted = null;

        // Create the AppConfig, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the AppConfig in the database
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEnabledIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        appConfig.enabled = null;

        // Create the AppConfig, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the AppConfig in the database
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void updateAppConfig() {
        // Initialize the database
        appConfig = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the appConfig
        var updatedAppConfig = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs/{id}", appConfig.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .body()
            .as(ENTITY_TYPE);

        // Update the appConfig
        updatedAppConfig.configKey = UPDATED_CONFIG_KEY;
        updatedAppConfig.configValue = UPDATED_CONFIG_VALUE;
        updatedAppConfig.subType = UPDATED_SUB_TYPE;
        updatedAppConfig.subType1 = UPDATED_SUB_TYPE_1;
        updatedAppConfig.subType2 = UPDATED_SUB_TYPE_2;
        updatedAppConfig.description = UPDATED_DESCRIPTION;
        updatedAppConfig.encrypted = UPDATED_ENCRYPTED;
        updatedAppConfig.createDate = UPDATED_CREATE_DATE;
        updatedAppConfig.updateDate = UPDATED_UPDATE_DATE;
        updatedAppConfig.enabled = UPDATED_ENABLED;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedAppConfig)
            .when()
            .put("/api/app-configs/" + appConfig.id)
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the AppConfig in the database
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeUpdate);
        var testAppConfig = appConfigList.stream().filter(it -> updatedAppConfig.id.equals(it.id)).findFirst().get();
        assertThat(testAppConfig.configKey).isEqualTo(UPDATED_CONFIG_KEY);
        assertThat(testAppConfig.configValue).isEqualTo(UPDATED_CONFIG_VALUE);
        assertThat(testAppConfig.subType).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testAppConfig.subType1).isEqualTo(UPDATED_SUB_TYPE_1);
        assertThat(testAppConfig.subType2).isEqualTo(UPDATED_SUB_TYPE_2);
        assertThat(testAppConfig.description).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppConfig.encrypted).isEqualTo(UPDATED_ENCRYPTED);
        assertThat(testAppConfig.createDate).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testAppConfig.updateDate).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testAppConfig.enabled).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    public void updateNonExistingAppConfig() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .put("/api/app-configs/" + Long.MAX_VALUE)
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the AppConfig in the database
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAppConfig() {
        // Initialize the database
        appConfig = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the appConfig
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/app-configs/{id}", appConfig.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var appConfigList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract()
            .as(LIST_OF_ENTITY_TYPE);

        assertThat(appConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAllAppConfigs() {
        // Initialize the database
        appConfig = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        // Get all the appConfigList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", hasItem(appConfig.id.intValue()))
            .body("configKey", hasItem(DEFAULT_CONFIG_KEY))
            .body("configValue", hasItem(DEFAULT_CONFIG_VALUE))
            .body("subType", hasItem(DEFAULT_SUB_TYPE))
            .body("subType1", hasItem(DEFAULT_SUB_TYPE_1))
            .body("subType2", hasItem(DEFAULT_SUB_TYPE_2))
            .body("description", hasItem(DEFAULT_DESCRIPTION))
            .body("encrypted", hasItem(DEFAULT_ENCRYPTED.booleanValue()))
            .body("createDate", hasItem(TestUtil.formatDateTime(DEFAULT_CREATE_DATE)))
            .body("updateDate", hasItem(TestUtil.formatDateTime(DEFAULT_UPDATE_DATE)))
            .body("enabled", hasItem(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    public void getAppConfig() {
        // Initialize the database
        appConfig = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(appConfig)
            .when()
            .post("/api/app-configs")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract()
            .as(ENTITY_TYPE);

        var response = // Get the appConfig
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/app-configs/{id}", appConfig.id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract()
                .as(ENTITY_TYPE);

        // Get the appConfig
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs/{id}", appConfig.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is(appConfig.id.intValue()))
            .body("configKey", is(DEFAULT_CONFIG_KEY))
            .body("configValue", is(DEFAULT_CONFIG_VALUE))
            .body("subType", is(DEFAULT_SUB_TYPE))
            .body("subType1", is(DEFAULT_SUB_TYPE_1))
            .body("subType2", is(DEFAULT_SUB_TYPE_2))
            .body("description", is(DEFAULT_DESCRIPTION))
            .body("encrypted", is(DEFAULT_ENCRYPTED.booleanValue()))
            .body("createDate", is(TestUtil.formatDateTime(DEFAULT_CREATE_DATE)))
            .body("updateDate", is(TestUtil.formatDateTime(DEFAULT_UPDATE_DATE)))
            .body("enabled", is(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    public void getNonExistingAppConfig() {
        // Get the appConfig
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/app-configs/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
