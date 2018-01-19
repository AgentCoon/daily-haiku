package com.agentcoon.dailyhaiku.rest;

import com.agentcoon.dailyhaiku.api.HaikuDto;
import com.agentcoon.dailyhaiku.domain.Haiku;
import com.agentcoon.dailyhaiku.domain.HaikuService;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.URISyntaxException;

import static com.agentcoon.dailyhaiku.api.HaikuDto.HaikuDtoBuilder.aHaikuDto;
import static com.agentcoon.dailyhaiku.domain.Haiku.HaikuBuilder.aHaiku;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class HaikuResourceTest {

    private HaikuService haikuService;
    private HaikuDtoMapper mapper;

    private UriInfo uriInfo;
    private UriBuilder uriBuilder;

    private HaikuResource haikuResource;

    @BeforeClass
    public static void ensureServiceLocatorPopulated() {
        // workaround for https://github.com/HubSpot/dropwizard-guice/issues/95
        JerseyGuiceUtils.reset();
    }

    @Before
    public void setUp() throws URISyntaxException {
        haikuService = mock(HaikuService.class);
        mapper = mock(HaikuDtoMapper.class);

        uriInfo = mock(UriInfo.class);
        uriBuilder = mock(UriBuilder.class);

        when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(new URI("test"));

        haikuResource = new HaikuResource(haikuService, mapper);
    }

    @Test
    public void saveHaikuTest() {
        Long haikuId = 1L;
        Haiku haiku = aHaiku().withId(haikuId).build();
        HaikuDto haikuDto = aHaikuDto().build();

        when(haikuService.save(haiku)).thenReturn(haikuId);
        when(mapper.from(haikuDto)).thenReturn(haiku);

        Response response = haikuResource.save(haikuDto, uriInfo);

        assertEquals(201, response.getStatus());
        verify(haikuService, times(1)).save(haiku);
    }

    @Test
    public void findByIdTest() {

        Long id = 123L;
        String author = "Haiku author";
        Haiku haiku = aHaiku().withId(id).withAuthor(author).build();
        HaikuDto dto = aHaikuDto().withAuthor(author).build();

        when(haikuService.findById(id)).thenReturn(haiku);
        when(mapper.from(haiku)).thenReturn(dto);

        Response response = haikuResource.getById(id);

        HaikuDto result = (HaikuDto) response.getEntity();

        assertEquals(200, response.getStatus());
        assertEquals(author, result.getAuthor());
    }

    @Test
    public void findByIdWhenNotFoundTest() {

        Long id = 123L;

        when(haikuService.findById(id)).thenReturn(null);

        Response response = haikuResource.getById(id);
        assertEquals(404, response.getStatus());
    }
}
