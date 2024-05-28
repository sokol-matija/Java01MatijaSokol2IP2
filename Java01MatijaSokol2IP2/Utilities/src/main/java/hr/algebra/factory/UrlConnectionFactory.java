package hr.algebra.factory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnectionFactory {

    private static final int TIMEOUT = 10000;
    private static final String REQUEST_MOTHOD = "GET";
    private static final String USER_AGENT = "User-Agent";
    private static final String MOZILLA = "Mozila/5.0";

    private UrlConnectionFactory() {
    }

    public static HttpURLConnection getHttpURLConnection(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod(REQUEST_MOTHOD);
        con.setRequestProperty(USER_AGENT, MOZILLA);
        return con;
    }

}
