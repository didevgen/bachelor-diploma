package ua.nure.providence.services.notifications.onesignal;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ua.nure.providence.dtos.notifications.OneSignalDTO;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.history.History;
import ua.nure.providence.services.notifications.INotification;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by User on 03.06.2017.
 */
@Component
public class OneSignalNotification implements INotification {

    private String appId = "276dc1e0-08e7-41c3-8db9-d8a5e4186676";

    private String authorization = "M2QzNGIyYjUtODVmYS00NzE1LTk3OTAtOTUyYmQyNjNlZDBj";

    @Override
    public void sendNotification(History history, List<String> keys, CardHolder holder) {
        try {
            String json = new Gson().toJson(new OneSignalDTO(this.appId, history, keys));

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + this.authorization);
            con.setRequestMethod("POST");
            byte[] sendBytes = json.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
