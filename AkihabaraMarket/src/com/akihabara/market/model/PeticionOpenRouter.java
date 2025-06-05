package com.akihabara.market.model;
public class PeticionOpenRouter {
    private String model;
    private Mensaje[] messages;

    public PeticionOpenRouter(String model, Mensaje[] messages) {
        this.model = model;
        this.messages = messages;
    }

    public static class Mensaje {
        private String role;
        private String content;

        public Mensaje(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
