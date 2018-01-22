package com.agentcoon.dailyhaiku.acceptancetest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification

class TestSpec extends Specification {

    Logger logger = LoggerFactory.getLogger(getClass())

    @Shared
    DailyHaikuDriver dailyHaiku = new DailyHaikuDriver()

    def "Fetch a random haiku"() {

        String haikuAuthor = "Jack Kerouac"
        String haikuBody = "Nightfall,\n" +
                "Too dark to read the page\n" +
                "Too cold."

        logger.info("TestSpec: Fetch a random haiku")

        when: "A new haiku is created"
        dailyHaiku.createHaiku(haikuAuthor, haikuBody)

        then: "A random haiku can be fetched"
        dailyHaiku.fetchRandomHaiku()
    }
}
