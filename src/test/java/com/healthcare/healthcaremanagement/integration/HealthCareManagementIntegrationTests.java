package com.healthcare.healthcaremanagement.integration;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Integration for API test implementation.
 */
@ActiveProfiles("test")
@Sql({"/sql/purge.sql", "/sql/seed.sql"})
@RunWith(SpringRunner.class)
@SuppressWarnings("PMD.TooManyMethods")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HealthCareManagementIntegrationTests {

    private static final String BASE_ENDPOINT = "http://localhost:8090/healthcare-management";
    private static final String EXAM = "/exam";
    private static final String INSTITUTION = "/healthCareInstitution/";
    private static final String USER = "/user";


    private TestRestTemplate testRestTemplate = new TestRestTemplate("admin@admin", "admin");
    ;

    private String payload;

    private HttpEntity<String> entity;

    private ResponseEntity<String> response;

    /**
     * Read json.
     *
     * @param filename file name input
     * @return String file content
     */
    private static String readJson(final String filename) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + filename), "UTF-8");
        } catch (IOException exception) {
            return null;
        }
    }

    /**
     * Build Http headers.
     *
     * @return Http Headers object
     */
    private HttpHeaders buildHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Before
    public void setup() {
        testRestTemplate.withBasicAuth("admin@admin", "test");
    }


    /**
     * Test scenario when insert new institution.
     */
    @Test
    public void shouldReturn200WhenInsertNewInstitution() {
        payload = readJson("request/insertInstitutionSuccess_1.json");
        entity = new HttpEntity<String>(payload, buildHttpHeaders());


        response = testRestTemplate.exchange(BASE_ENDPOINT + INSTITUTION, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(RESPONSE_BINARY_DATA_LEFT_1_AND_RIGHT_2_SIDES, response.getBody());


    }
}
