import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

        //Add currency codes
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "CAD");
        currencyCodes.put(3, "EUR");
        currencyCodes.put(4, "HKD");
        currencyCodes.put(5, "INR");
        currencyCodes.put(6, "AFN");
        currencyCodes.put(7, "AMD");
        currencyCodes.put(8, "JPY");
        currencyCodes.put(9, "GBP");
        currencyCodes.put(10, "AUD");

        String fromCode, toCode;
        double amount;
        Integer from, to;

        Scanner ss = new Scanner(System.in);
        System.out.println("Welcome to the currency converter!\nCurrency converting from?");

        System.out.println("1:USD (US Dollars)\t2:CAD (Canadian Dollars)\t3:EUR (Euros)\t4:HKD (Hong Kong Dollar)\t5:INR (Indian Rupees)\t6:AFN (Afghan Afghani)\t7:AMD (Armenian Dram)\n8:JPY (Japanese Yen)\t9:GBP (Pound Sterling)\t10:AUD (Australian Dollar)");
        from = ss.nextInt();
        while (from < 1 || from > 10) {
            System.out.println("Please give an appropriate input...");
            System.out.println("1:USD (US Dollars)\t2:CAD (Canadian Dollars)\t3:EUR (Euros)\t4:HKD (Hong Kong Dollar)\t5:INR (Indian Rupees)\t6:AFN (Afghan Afghani)\t7:AMD (Armenian Dram)\n8:JPY (Japanese Yen)\t9:GBP (Pound Sterling)\t10:AUD (Australian Dollar)");
            from = ss.nextInt();
        }
        fromCode = currencyCodes.get(from);

        System.out.println("Currency converting to?");
        System.out.println("1:USD (US Dollars)\t2:CAD (Canadian Dollars)\t3:EUR (Euros)\t4:HKD (Hong Kong Dollar)\t5:INR (Indian Rupees)\t6:AFN (Afghan Afghani)\t7:AMD (Armenian Dram)\n8:JPY (Japanese Yen)\t9:GBP (Pound Sterling)\t10:AUD (Australian Dollar)");
        to = ss.nextInt();
        while (to < 1 || to > 10) {
            System.out.println("Please give an appropriate input...");
            System.out.println("1:USD (US Dollars)\t2:CAD (Canadian Dollars)\t3:EUR (Euros)\t4:HKD (Hong Kong Dollar)\t5:INR (Indian Rupees)\t6:AFN (Afghan Afghani)\t7:AMD (Armenian Dram)\n8:JPY (Japanese Yen)\t9:GBP (Pound Sterling)\t10:AUD (Australian Dollar)");
            to = ss.nextInt();
        }
        toCode = currencyCodes.get(to);

        System.out.println("Amount you wish to convert?");
        amount = ss.nextFloat();

        sendHttpGETRequest(fromCode, toCode, amount);
        System.out.println("Thank you for using our currency converter!");

    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        DecimalFormat f = new DecimalFormat("00.00");

        String GET_URL = "https://api.currencyapi.com/v3/latest?apikey=cur_live_60UgTtWNscDvfGUlALieyMn1KzBnl3bzsVsK7EKR&base_currency=" + fromCode + "&currencies=" + toCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // IF SUCCESS
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            JSONObject obj = new JSONObject(response.toString());
            JSONObject data = obj.getJSONObject("data");
            JSONObject currencyInfo = data.getJSONObject(toCode);
            Double exchangeRate = currencyInfo.getDouble("value");
//            System.out.println(data);
//            System.out.println(exchangeRate);
            System.out.println();
            System.out.println(f.format(amount) + fromCode + " = " + f.format(amount * exchangeRate) + toCode);
        }
        else {
            System.out.println("GET request was unsuccessful due to unprecedented reasons...");
        }
    }
}
