package com.agentcoon.dailyhaiku.domain;

public interface HaikuRepository {

    Haiku findById(Long id);

    Haiku getRandom();

    Long save(Haiku haiku);
}
