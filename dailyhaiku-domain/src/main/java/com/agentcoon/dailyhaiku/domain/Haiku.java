package com.agentcoon.dailyhaiku.domain;

public class Haiku {

    private Long id;
    private String author;
    private String body;

    private Haiku(Long id, String author, String body) {
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

    public static final class HaikuBuilder {

        private Long id;
        private String author;
        private String body;

        private HaikuBuilder() {}

        public static HaikuBuilder aHaiku() {
            return new HaikuBuilder();
        }

        public HaikuBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public HaikuBuilder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public HaikuBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public Haiku build() {
            return new Haiku(id, author, body);
        }
    }
}
