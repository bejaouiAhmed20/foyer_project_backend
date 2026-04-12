package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin("*") 
public class ChatbotController {

    @PostMapping("/chat")
    public Map<String, String> getEmpathicResponse(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message").toLowerCase();
        String response;

        // Empathic Logic: Detecting stress vs. calm
        if (userMessage.contains("stress") || userMessage.contains("examen") || userMessage.contains("bruit")) {
            response = "Je sens que tu es stressé, Omar. Pour tes révisions, je te conseille le Bloc Esprit, c'est le plus calme.";
        } else if (userMessage.contains("besoin d'aide") || userMessage.contains("perdu")) {
            response = "Pas de panique ! Je peux t'aider à trouver une chambre disponible rapidement.";
        } else {
            response = "Salut ! Je suis ton assistant IA. Comment te sens-tu dans ton foyer aujourd'hui ?";
        }

        return Map.of("reply", response);
    }
}