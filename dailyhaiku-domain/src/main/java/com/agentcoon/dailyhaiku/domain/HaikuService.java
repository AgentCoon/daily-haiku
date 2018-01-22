package com.agentcoon.dailyhaiku.domain;

import javax.inject.Inject;

public class HaikuService {

    private HaikuRepository haikuRepository;

    @Inject
    public HaikuService(HaikuRepository haikuRepository) {
        this.haikuRepository = haikuRepository;
    }

    public Haiku findById(Long id) {
        return haikuRepository.findById(id);
    }

    public Haiku getRandom() {
        return haikuRepository.getRandom();
    }

    public Long save(Haiku haiku) {
        return haikuRepository.save(haiku);
    }

    public void update(Haiku haiku) {
        haikuRepository.update(haiku);
    }

    public void delete(Long id) {
        haikuRepository.deleteById(id);
    }
}
