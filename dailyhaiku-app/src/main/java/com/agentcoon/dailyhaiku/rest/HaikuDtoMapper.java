package com.agentcoon.dailyhaiku.rest;

import com.agentcoon.dailyhaiku.api.HaikuDto;
import com.agentcoon.dailyhaiku.domain.Haiku;

import javax.inject.Singleton;

import static com.agentcoon.dailyhaiku.api.HaikuDto.HaikuDtoBuilder.aHaikuDto;
import static com.agentcoon.dailyhaiku.domain.Haiku.HaikuBuilder.aHaiku;

@Singleton
public class HaikuDtoMapper {

    public Haiku from(HaikuDto dto) {
        return aHaiku().withAuthor(dto.getAuthor())
                .withBody(dto.getBody())
                .build();
    }

    public Haiku from(Long id, HaikuDto dto) {
        return aHaiku().withId(id)
                .withAuthor(dto.getAuthor())
                .withBody(dto.getBody())
                .build();
    }

    public HaikuDto from(Haiku haiku) {
        return aHaikuDto()
                .withId(haiku.getId())
                .withAuthor(haiku.getAuthor())
                .withBody(haiku.getBody())
                .build();
    }
}
