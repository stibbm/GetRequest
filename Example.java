import java.util.Map;
import java.util.HashMap;


public class Example {
    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<>();
        String url = "https://youtube.com";
        String response = get(url, headers);
        System.out.println(response);
    }


    public String get(String url, Map<String, String> headers) {
        try {
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {

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