package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Utilisateur;
import org.json.simple.JSONObject;


public interface NotificationService {

    void sendNotification(String title, String body, Iterable<Utilisateur> utilisateurs, JSONObject data);
}
