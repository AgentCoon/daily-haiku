package com.agentcoon.dailyhaiku.domain;

import org.junit.Before;
import org.junit.Test;

import static com.agentcoon.dailyhaiku.domain.Haiku.HaikuBuilder.aHaiku;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class HaikuServiceTest {

    private HaikuRepository haikuRepository;

    private HaikuService haikuService;

    @Before
    public void setUp() {
        haikuRepository = mock(HaikuRepository.class);

        haikuService = new HaikuService(haikuRepository);
    }

    @Test
    public void saveTest() {
        Long id = 1L;

        Haiku haiku = aHaiku().build();

        when(haikuRepository.save(haiku)).thenReturn(id);

        Long result = haikuService.save(haiku);
        assertEquals(id, result);
    }

    @Test
    public void updateTest() {
        Haiku haiku = aHaiku().build();

        haikuService.update(haiku);
        verify(haikuRepository, times(1)).update(any(Haiku.class));
    }

    @Test
    public void deleteTest() {
        Long id = 1L;

        haikuService.delete(id);
        verify(haikuRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void findByIdTest() {
        Long id = 1L;

        Haiku haiku = aHaiku().withId(id).build();

        when(haikuRepository.findById(id)).thenReturn(haiku);

        Haiku result = haikuService.findById(id);
        assertEquals(id, result.getId());
    }
}
