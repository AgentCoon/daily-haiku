package com.agentcoon.dailyhaiku.acceptancetest

import com.agentcoon.dailyhaiku.api.HaikuDto
import com.agentcoon.dailyhaiku.client.DailyHaikuAPIGateway
import com.agentcoon.dailyhaiku.client.DailyHaikuGateway
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.response.Response
import com.jayway.restassured.specification.RequestSpecification
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import static com.agentcoon.dailyhaiku.api.HaikuDto.HaikuDtoBuilder.aHaikuDto
import static com.jayway.restassured.RestAssured.given
import static groovyx.net.http.Method.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

class DailyHaikuDriver {

    Logger logger = LoggerFactory.getLogger(getClass())

    def http = new HTTPBuilder(TestParams.APP_BASE_URL + ":" + TestParams.APP_PORT + "/v1/")

    RequestSpecification appSpec = new RequestSpecBuilder().setBaseUri(TestParams.APP_BASE_URL)
            .setPort(TestParams.APP_PORT).setBasePath(TestParams.APP_CONTEXT_PATH + "/v1/").build()

    DailyHaikuGateway dailyHaiku = new DailyHaikuAPIGateway(TestParams.APP_BASE_URL + ":" + TestParams.APP_PORT)

    private ObjectMapper mapper = new ObjectMapper();

    void createHaiku(String haikuAuthor, String haikuBody) {

        http.request(POST, ContentType.JSON) {
            uri.path = "haiku"
            body = [author: haikuAuthor, body: haikuBody]

            response.success = { resp ->
                logger.info("success response for haiku create")
            }

            response.failure = { resp ->
                fail("Request failed with status ${resp.status}")
            }
        }
    }

    Long createHaikuWithId(String haikuAuthor, String haikuBody) {

        String json = mapper.writeValueAsString(buildHaiku(haikuAuthor, haikuBody))

        Response response = given().spec(appSpec).contentType("application/json")
                .body(json).expect().statusCode(201).when().post("haiku")

        logger.info("Create haiku response {}", response.getStatusCode())

        String location = response.getHeader("Location")

        assertNotNull(location)
        return location.substring(location.lastIndexOf("/") + 1).trim().toLong()
    }

    void haikuWithIdExists(Long id) {
        logger.info("Haiku with id {} exists", id)

        String json = given().spec(appSpec).expect().statusCode(200)
                .when().get("haiku/{id}", id).asString()

        HaikuDto dto = mapper.readValue(json, HaikuDto.class)
        assertNotNull(dto)
        assertEquals(id, dto.getId())
    }

    void fetchRandomHaiku() {

        logger.info("Can fetch a random haiku")

        HaikuDto haikuDto = dailyHaiku.randomHaiku()

        assertNotNull(haikuDto)
    }

    HaikuDto buildHaiku(String author, String body) {
        return aHaikuDto()
                .withAuthor(author)
                .withBody(body).build()
    }
}
