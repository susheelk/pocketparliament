package tech.susheelkona.pocketparliament.services;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Map;

/** An extensible service for communicating with APIs
 *
 * @author Susheel Kona
 */

abstract class HttpService {
    public static final String OPEN_PARL = "https://api.openparliament.ca/";
    public static final String BILLSEARCH = "https://billsearch.herokuapp.com/";
//    public static final String BILLSEARCH = "https://localhost:8080/";

    private String stringifyParameters(Map<String, String> parameters) {
        StringBuffer buffer = new StringBuffer("?");
        for(Map.Entry<String, String> entry: parameters.entrySet()) {
            buffer.append(entry.getKey()+"="+entry.getValue()+"&");
        }

        return buffer.toString();
    }

    public String doRequest(String source, String endpoint, Map<String, String> parameters) throws Exception {
        StringBuffer urlBuilder = new StringBuffer(source+endpoint);
        if (parameters != null) {
            urlBuilder.append(stringifyParameters(parameters));
        }
        URL url = new URL(urlBuilder.toString());
        Log.i("HttpRequest", "Sending to "+url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("ACCEPT", "application/json"); // OpenParl provides both HTML and JSON

        int statusCode = connection.getResponseCode();

        if (statusCode == HttpURLConnection.HTTP_OK || statusCode == 202) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String line;
            while((line = in.readLine()) != null ) {
                response.append(line);
            }

            in.close();
            Log.i("Response", response.toString());
            return response.toString();
        }
        else {
            throw new Exception("Http status code was "+statusCode);
        }
    }

    public String doRequest(String source, String endpoint) throws Exception{
        return doRequest(source, endpoint, null);
    }

}
