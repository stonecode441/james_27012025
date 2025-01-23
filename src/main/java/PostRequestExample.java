import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created By Chivo
 */


public class PostRequestExample {
    public void createEmployer() {
        String apiUrl = "https://dummy.restapiexample.com/api/v1/create";
        String payload = """
                {
                    "status": "success",
                    "data": {
                        "name": "Sample Name",
                        "salary": "1230999",
                        "age": "52",
                        "id": 25
                    }
                }
                """;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(payload.getBytes());
                os.flush();
            }

            System.out.println("Response Code: " + connection.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
