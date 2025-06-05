package com.akihabara.market.llm;


import com.akihabara.market.model.*;

public class LlmService {
	
	static String modelo = "mistralai/devstral-small:free";

	public static String sugerirNombreProducto(String tipo, String franquicia) {
        String prompt = "Sugiere un nombre llamativo y original para un producto otaku del tipo '" + tipo + "' basado en la franquicia '" + franquicia + "'.";

        PeticionOpenRouter.Mensaje[] mensajes = {
            new PeticionOpenRouter.Mensaje("user", prompt)
        };

        PeticionOpenRouter peticion = new PeticionOpenRouter(modelo, mensajes);

        ClienteApiRest.capturarRespuesta = null;
        ClienteApiRest.hacerPost("https://openrouter.ai/api/v1/chat/completions", peticion, RespuestaOpenRouter.class);

        return ClienteApiRest.capturarRespuesta != null ? ClienteApiRest.capturarRespuesta : "No se recibió respuesta de la IA.";
    }

}
