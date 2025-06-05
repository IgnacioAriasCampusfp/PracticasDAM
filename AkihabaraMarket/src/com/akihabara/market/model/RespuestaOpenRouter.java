package com.akihabara.market.model;
public class RespuestaOpenRouter {
    public Choice[] choices;

    public static class Choice {
        public Message message;

        public static class Message {
            public String role;
            public String content;
        }
    }
}
