package me.alexfrocha.currency;

import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        UiBooster display = new UiBooster();
        try {
            Form form = display.createForm("Conversor de Moeda")
                    .addSelection("De", Dados.getCurrencies())
                    .addText(null)
                    .addSelection("Para", Dados.getCurrencies())
                    .show();


            Dados dados = new Dados(form.getElements().get(0).toSelection().getValue(), form.getElements().get(2).toSelection().getValue(), Double.parseDouble(String.valueOf(form.getElements().get(1).getValue()).replace(",", ".")));
            display.showInfoDialog(dados.getToCurrency() + " " + dados.getResult());
        } catch (Exception e) {
            display.showErrorDialog("Ocorreu um erro ao tentar converter a moeda", "Conversor de Moeda");
        }
    }
}
