package com.agentcoon.dailyhaiku.client;

import com.agentcoon.dailyhaiku.api.HaikuDto;

public interface DailyHaikuGateway {

    HaikuDto randomHaiku();
}
