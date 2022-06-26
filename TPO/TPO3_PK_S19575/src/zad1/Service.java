/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Service {
    private static final String KEY = "cc02c08afcb74b1c83a72a2eae52991c";
    static HashMap<String, String> map;
    static Set<String> set;
    Locale country;

    Service(String country) {
        this.country = new Locale("en", map.get(country));
    }

    static void initCountries() throws IOException {
        map = new HashMap<>();
        String request = "https://api.exchangerate.host/latest";
        JSONObject currenciesOb = new JSONObject(getUrlConnection(request));
        set = currenciesOb.getJSONObject("rates").keySet();

        String[] isoCountries = Locale.getISOCountries();

        for(String iso : isoCountries) {
            Locale locale = new Locale("", iso);
            if(Currency.getInstance(locale) != null && set.contains(Currency.getInstance(locale).getCurrencyCode()))
                map.put(locale.getDisplayCountry(), iso);
        }
    }

    String getWeather(String city) throws IOException {
        String request = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country.getISO3Country()
                + "&units=metric&appid=" + KEY;
        JSONObject weatherOb = new JSONObject(getUrlConnection(request));
        return weatherOb.getJSONArray("weather").getJSONObject(0).getString("main") + ", " +
                weatherOb.getJSONArray("weather").getJSONObject(0).getString("description") +
                "\nTemperature: " + (int) weatherOb.getJSONObject("main").getDouble("temp") +
                " C\nMinimum temperature: " + (int) weatherOb.getJSONObject("main").getDouble("temp_min")
                + " C\nMaximum temperature: " + (int) weatherOb.getJSONObject("main").getDouble("temp_max") + " C";
    }


    Double getNBPRate() throws IOException {
        if (country.getISO3Country().equals("POL")) {
            return 1.0;
        }
        String currentCurrency = Currency.getInstance(country).getCurrencyCode();
        String request = "http://www.nbp.pl/kursy/kursya.html";
        String resp = getUrlConnection(request);
        String tab = resp.substring(resp.indexOf("/kursy/xml/"),
                resp.indexOf("\"", resp.indexOf("/kursy/xml/")));
        request = "http://www.nbp.pl" + tab;
        resp = getUrlConnection(request);
        JSONObject kursOb = XML.toJSONObject(resp);
        JSONArray kurses = kursOb.getJSONObject("tabela_kursow").getJSONArray("pozycja");
        boolean currencyFound = false;
        double rate = 0;
        {
            Iterator<Object> iterator = kurses.iterator();
            while (iterator.hasNext()) {
                Object kurs = iterator.next();
                if (!(kurs instanceof JSONObject)) {
                    continue;
                }
                if (!((JSONObject) kurs).getString("kod_waluty").equals(currentCurrency)) {
                    continue;
                }
                currencyFound = true;
                rate = Double.parseDouble(((JSONObject) kurs).getString("kurs_sredni").replace(",", "."))
                        / ((JSONObject) kurs).getInt("przelicznik");
                break;
            }
        }

        if (currencyFound) {
            return rate;
        }

        request = "http://www.nbp.pl/kursy/kursyb.html";
        resp = getUrlConnection(request);
        tab = resp.substring(resp.indexOf("/kursy/xml/"),
                resp.indexOf("\"", resp.indexOf("/kursy/xml/")));
        request = "http://www.nbp.pl" + tab;
        resp = getUrlConnection(request);
        kursOb = XML.toJSONObject(resp);
        kurses = kursOb.getJSONObject("tabela_kursow").getJSONArray("pozycja");
        rate = 0;
        Iterator<Object> iterator = kurses.iterator();
        while (iterator.hasNext()) {
            Object kurs = iterator.next();
            if (kurs instanceof JSONObject) {
                if (((JSONObject) kurs).getString("kod_waluty").equals(currentCurrency)) {
                    rate = ((JSONObject) kurs).getDouble("kurs_sredni")
                            / ((JSONObject) kurs).getInt("przelicznik");
                    break;
                }
            }
        }
        return rate;
    }


    public static String getUrlConnection(String page) throws IOException {
        URL url = new URL(page);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();
        System.out.println(page + " answer = " + httpURLConnection.getResponseCode());
        return stringBuilder.toString();
    }
}
