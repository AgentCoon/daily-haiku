package com.agentcoon.dailyhaiku.acceptancetest

import com.agentcoon.dailyhaiku.api.HaikuDto
import com.agentcoon.dailyhaiku.client.DailyHaikuAPIGateway
import com.agentcoon.dailyhaiku.client.DailyHaikuGateway

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.Method.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

class DailyHaikuDriver {

    Logger logger = LoggerFactory.getLogger(getClass())

    def http = new HTTPBuilder(TestParams.APP_BASE_URL + ":" + TestParams.APP_PORT + "/v1/")

    DailyHaikuGateway dailyHaiku = new DailyHaikuAPIGateway(TestParams.APP_BASE_URL + ":" + TestParams.APP_PORT)

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

    void fetchRandomHaiku() {

        logger.info("Can fetch a random haiku")

        HaikuDto haikuDto = dailyHaiku.randomHaiku()

        assertNotNull(haikuDto)
    }
}
