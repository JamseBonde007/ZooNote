package weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class Weather {


    public void getWeather() {
        try {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=Bratislava&appid=753cb58a0e7309276357da1b45fa03df";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject (response.toString ());
            System.out.println(json);

            JSONObject jsonMain= new JSONObject(json.getJSONObject("main").toString());
            System.out.println("weather = " + (jsonMain.getDouble("temp") - 273.15));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
