package com.agentcoon.dailyhaiku.domain;

public interface HaikuRepository {

    Long save(Haiku haiku);

    void update(Haiku haiku);

    void deleteById(Long id);

    Haiku findById(Long id);

    Haiku getRandom();
}
