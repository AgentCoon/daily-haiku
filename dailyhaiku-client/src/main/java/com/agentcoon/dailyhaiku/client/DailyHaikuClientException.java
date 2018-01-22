package com.agentcoon.dailyhaiku.client;

public class DailyHaikuClientException extends RuntimeException {

    private static final long serialVersionUID = 3388659785314022567L;

    public DailyHaikuClientException(String message) {
        super(message);
    }

    public DailyHaikuClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
