package fr.enelia.dashboardapi.services.impl;

import de.bytefish.fcmjava.http.client.IFcmClient;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import de.bytefish.fcmjava.requests.notification.NotificationPayload;
import de.bytefish.fcmjava.responses.FcmMessageResponse;
import de.bytefish.fcmjava.responses.FcmMessageResultItem;
import fr.enelia.dashboardapi.entities.Utilisateur;
import fr.enelia.dashboardapi.helpers.FirebaseResponse;
import fr.enelia.dashboardapi.helpers.HeaderRequestInterceptor;
import fr.enelia.dashboardapi.services.NotificationService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private static final String FIREBASE_SERVER_KEY = "AIzaSyAhxNtuFSVPNGEzl2ushH9ci9jJUsKHr3k";
    private int id;

    @Override
    public void sendNotification(String title, String notifBody, Iterable<Utilisateur> utilisateurs, JSONObject data) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        for (Utilisateur utilisateur:utilisateurs) {
            if (utilisateur.getToken() != null && utilisateur.getToken().length() > 0) {
                LOGGER.info("Envoi notification à " + utilisateur.getUsername());

                JSONObject body = new JSONObject();
                // JsonArray registration_ids = new JsonArray();
                // body.put("registration_ids", registration_ids);
                body.put("to", utilisateur.getToken());
                body.put("priority", "high");
                // body.put("dry_run", true);

                JSONObject notification = new JSONObject();
                notification.put("body", notifBody);
                notification.put("title", title);
                notification.put("sound", "default");
                // notification.put("icon", "myicon");


                body.put("notification", notification);
                body.put("data", data);

                HttpEntity<String> entity = new HttpEntity<>(body.toString());


                FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity, FirebaseResponse.class);
                CompletableFuture<FirebaseResponse> pushNotification = CompletableFuture.completedFuture(firebaseResponse);
                CompletableFuture.allOf(pushNotification).join();

                try {
                    FirebaseResponse response = pushNotification.get();
                    if (response.getSuccess() == 1) {
                        LOGGER.info("push notification sent ok!");
                    } else {
                        LOGGER.error("error sending push notifications: " + firebaseResponse.toString());
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
