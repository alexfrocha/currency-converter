package me.alexfrocha.currency;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dados {

    private double amount;
    private String fromCurrency;
    private String toCurrency;

    public Dados(String fromCurrency, String toCurrency, Double amount) throws Exception {
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public static String Response(String url) throws Exception {
        HttpURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.connect();
        if(conn.getResponseCode() != 200) System.out.println("HTTP error: " + conn.getResponseCode());
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String output = "";
        String line;
        while((line = br.readLine()) != null) output += line;
        conn.disconnect();
        return output;
    }

    public static JSONObject parseJSON(String JSON) {
        return new JSONObject(JSON);
    }

    public double getAmount() throws Exception {
        JSONObject API = parseJSON(Response("https://api.fastforex.io/convert?from=" + this.fromCurrency + "&to=" + this.toCurrency + "&amount=" + this.amount + "&api_key=720b506b35-75db7c8f9b-rfh8uq"));
        return API.getDouble("amount");
    }

    public double getResult() throws Exception {
        JSONObject API = parseJSON(Response("https://api.fastforex.io/convert?from=" + this.fromCurrency + "&to=" + this.toCurrency + "&amount=" + this.amount + "&api_key=720b506b35-75db7c8f9b-rfh8uq"));
        return API.getJSONObject("result").getDouble(toCurrency);
    }

    public static List<String> getCurrencies() throws Exception{
        JSONObject API = parseJSON(Response("https://api.fastforex.io/currencies?api_key=720b506b35-75db7c8f9b-rfh8uq"));
        List<String> currencies = new ArrayList<String>();
        API.getJSONObject("currencies").names().forEach(e -> currencies.add(String.valueOf(e)));
        return currencies;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }
}
