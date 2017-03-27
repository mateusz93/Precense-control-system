package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;

/**
 * @author Mateusz Wieczorek on 19.03.2017.
 *
 * Example: http://www.journaldev.com/7133/how-to-integrate-google-recaptcha-in-java-web-application
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReCaptchaService {

    @Value("${google.recaptcha.url}")
    private String url;

    @Value("${google.recaptcha.private}")
    private String secret;

    @Value("${google.recaptcha.user-agent}")
    private String userAgent;

    private final String IS_SUCCESS = "success";

    public boolean verify(HttpServletRequest request) throws IOException {
        String reCaptchaResponse = request.getParameter("g-recaptcha-response");

        if (StringUtils.isEmpty(reCaptchaResponse)) {
            return false;
        }

        URL obj = new URL(url);
        HttpsURLConnection con = getHttpsURLConnection(obj);

        String postParams = constructParams(reCaptchaResponse);

        sendPostRequest(con, postParams);
        String response = getResponse(con);

        JsonReader jsonReader = Json.createReader(new StringReader(response));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        return jsonObject.getBoolean(IS_SUCCESS);

    }

    private String constructParams(String reCaptchaResponse) {
        return "secret=" + secret + "&response=" + reCaptchaResponse;
    }

    private String getResponse(HttpsURLConnection con) throws IOException {
        StringBuilder response = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private void sendPostRequest(HttpsURLConnection con, String postParams) throws IOException {
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
    }

    private HttpsURLConnection getHttpsURLConnection(URL obj) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", userAgent);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        return con;
    }
}
