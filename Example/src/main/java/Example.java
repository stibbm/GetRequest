import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class Example {
  public static void main(String[] args) {
    String apiKey = "";
    String cityId = "833";
    String urlTemplate = "api.openweathermap.org/data/2.5/weather?id={city id}&appid={API key}";
    Map<String, String> headers = new HashMap<>();
    String url = urlTemplate
        .replace("{city id}", cityId)
        .replace("{API key}", apiKey);


    String response = get(url, headers);
    System.out.println(response);
  }


  public static String get(String url, Map<String, String> headers) {
    try {
      ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

        @Override
        public String handleResponse(final HttpResponse response) throws
            ClientProtocolException, IOException {

          int status = response.getStatusLine().getStatusCode();
          if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
          }
          return null;
        }
      };

      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet(url);
      for (String key : headers.keySet()) {
        httpGet.addHeader(key, headers.get(key));
      }
      String response = httpClient.execute(httpGet, responseHandler);
      return response;

    }catch (final Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
