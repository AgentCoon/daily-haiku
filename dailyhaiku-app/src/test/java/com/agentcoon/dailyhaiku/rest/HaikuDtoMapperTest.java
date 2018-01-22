package com.agentcoon.dailyhaiku.rest;

import com.agentcoon.dailyhaiku.api.HaikuDto;
import com.agentcoon.dailyhaiku.domain.Haiku;
import org.junit.Test;

import static com.agentcoon.dailyhaiku.api.HaikuDto.HaikuDtoBuilder.aHaikuDto;
import static com.agentcoon.dailyhaiku.domain.Haiku.HaikuBuilder.aHaiku;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HaikuDtoMapperTest {

    private HaikuDtoMapper mapper = new HaikuDtoMapper();

    @Test
    public void fromDto() {

        String author = "Author";
        String body = "Body";

        HaikuDto dto = aHaikuDto()
                .withAuthor(author)
                .withBody(body).build();

        Haiku haiku = mapper.from(dto);

        assertEquals(author, haiku.getAuthor());
        assertEquals(body, haiku.getBody());
        assertNull(haiku.getId());
    }

    @Test
    public void fromDtoWithId() {

        Long id = 1L;
        String author = "Author";
        String body = "Body";

        HaikuDto dto = aHaikuDto()
                .withAuthor(author)
                .withBody(body).build();

        Haiku haiku = mapper.from(id, dto);

        assertEquals(id, haiku.getId());
        assertEquals(author, haiku.getAuthor());
        assertEquals(body, haiku.getBody());
    }

    @Test
    public void fromHaiku() {

        Long id = 1L;
        String author = "Author";
        String body = "Body";

        Haiku haiku = aHaiku()
                .withId(id)
                .withAuthor(author)
                .withBody(body).build();

        HaikuDto dto = mapper.from(haiku);

        assertEquals(id, dto.getId());
        assertEquals(author, dto.getAuthor());
        assertEquals(body, dto.getBody());
    }
}
