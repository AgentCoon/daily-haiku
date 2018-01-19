package com.agentcoon.dailyhaiku.infrastructure.jdbi.haiku;

import com.agentcoon.dailyhaiku.domain.Haiku;
import com.agentcoon.dailyhaiku.inttest.EmbeddedDb;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.agentcoon.dailyhaiku.domain.Haiku.HaikuBuilder.aHaiku;
import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class JdbiHaikuRepositoryTest {

    private JdbiHaikuRepository haikuRepository;

    @Rule
    public EmbeddedDb embeddedDb = new EmbeddedDb();

    @Before
    public void setUp() {
        haikuRepository = embeddedDb.repository(JdbiHaikuRepository.class);
    }

    @Test
    public void createAndFindByIdHaikuTest() {
        String author = "Haiku author";
        String body = "Haiku body";

        Haiku haiku = aHaiku().withAuthor(author)
                .withBody(body).build();

        Long savedHaikuId = haikuRepository.save(haiku);

        assertNotNull(savedHaikuId);

        Haiku retrievedHaiku = haikuRepository.findById(savedHaikuId);

        assertEquals(savedHaikuId, retrievedHaiku.getId());
        assertEquals(author, retrievedHaiku.getAuthor());
        assertEquals(body, retrievedHaiku.getBody());
    }

    @Test
    public void getRandomHaikuTest() {
        String author = "Haiku author";

        Haiku haiku = aHaiku().withAuthor(author).build();

        Long savedHaikuId1 = haikuRepository.save(haiku);
        Long savedHaikuId2 = haikuRepository.save(haiku);

        assertNotEquals(savedHaikuId1, savedHaikuId2);

        List<Haiku> retrievedHaiku = new ArrayList<>();

        for(int i = 0; i < 1000; i++) {
            retrievedHaiku.add(haikuRepository.getRandom());
        }

        assertTrue(retrievedHaiku.stream()
                .filter(h -> h.getId().equals(savedHaikuId1))
                .collect(toList()).size() > 0);

        assertTrue(retrievedHaiku.stream()
                .filter(h -> h.getId().equals(savedHaikuId2))
                .collect(toList()).size() > 0);
    }
}
