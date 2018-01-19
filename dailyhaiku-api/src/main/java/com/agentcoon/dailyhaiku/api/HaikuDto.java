package com.agentcoon.dailyhaiku.api;

public class HaikuDto {

    private Long id;
    private String author;
    private String body;

    public HaikuDto() { }

    private HaikuDto(Long id, String author, String body) {
        this.id = id;
        this.author = author;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public static final class HaikuDtoBuilder {

        private Long id;
        private String author;
        private String body;

        private HaikuDtoBuilder() {}

        public static HaikuDtoBuilder aHaikuDto() {
            return new HaikuDtoBuilder();
        }

        public HaikuDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public HaikuDtoBuilder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public HaikuDtoBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public HaikuDto build() {
            return new HaikuDto(id, author, body);
        }
    }
}
