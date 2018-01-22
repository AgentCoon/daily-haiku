package com.agentcoon.dailyhaiku.client;

import com.agentcoon.dailyhaiku.api.HaikuDto;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DailyHaikuAPIGateway implements DailyHaikuGateway {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String VERSION_ONE = "v1";

    private static final String HAIKU = VERSION_ONE + "/haiku";

    private JsonUtils jsonUtils;

    private OkHttpClient client;

    private HttpUrl baseUrl;

    public DailyHaikuAPIGateway(String baseUrl) {

        this.baseUrl = HttpUrl.parse(baseUrl);
        this.jsonUtils = new JsonUtils();

        // OkHttpClient includes automatic retry of some errors
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public HaikuDto randomHaiku() {

        HttpUrl url = urlBuilderFor(HAIKU).build();
        Request request = new Request.Builder().url(url).get().build();
        Response response = send(request, "fetch random haiku");

        try {
            return jsonUtils.fromJson(response.body().string(), HaikuDto.class);
        } catch (IOException e) {
            logger.error("IOException", e);
            throw new DailyHaikuClientException("Failed to fetch random haiku, " + response);
        }
    }

    private Response send(Request request, String description) {

        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            logger.error("IOException", e);
            throw new DailyHaikuClientException("Failed to " + description + ", " + response);
        }

        if (!response.isSuccessful()) {
            logger.error("Unexpected code " + response);
            throw new DailyHaikuClientException("Failed to " + description + ", " + response);
        }

        return response;
    }

    private HttpUrl.Builder urlBuilderFor(String path) {
        return baseUrl.newBuilder().addPathSegments(path);
    }
}
