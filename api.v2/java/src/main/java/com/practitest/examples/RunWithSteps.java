package com.practitest.examples;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.binary.Base64;
import java.io.*;

public class RunWithSteps {
    private static final String URI = "https://api.practitest.com/api/v2/projects/4575/runs.json";
    private static final String DEVELOPER_EMAIL = "YOUR EMAIL";
    private static final String API_TOKEN = "YOUR TOKEN";

    public final static void main(String[] args) throws Exception {

        byte[] encoding = Base64.encodeBase64((DEVELOPER_EMAIL + ":" + API_TOKEN).getBytes());

        HttpClient httpclient = new DefaultHttpClient();

        String json_str = "{" +
          "\"data\" : {\"attributes\" : {" +
            "\"instance-id\": 101200" +
          "}, " +
           "\"steps\": { \"data\":" +
              "[{\"name\": \"step one\", \"expected-results\": \"my result\", \"status\": \"FAILED\"}, " +
              "{\"name\": \"step two\", \"expected-results\": \"my second result\", \"status\": \"PASSED\"}]" +
            "}" +  //closing data
          "} "  +//closing steps
          "}"; //closing initial data
        HttpPost request = new HttpPost(URI);
        request.setEntity(new StringEntity(json_str));
        request.setHeader("Authorization", "Basic " + new String(encoding));
        request.addHeader("content-type", "application/json");
        System.out.println("executing request " + request.getURI());
        try {
        // Create a response handler
            HttpResponse response = httpclient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            if (statusCode == 200) {
                System.out.println("SUCCESS: " + responseBody);
            } else {
                System.out.println("ERROR: " + statusCode + ": " + responseBody);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------");
        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }
    private static String filenameToBase64(String fileName){
      File originalFile = new File(fileName);
      String encodedBase64 = null;
      try {
          FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
          byte[] bytes = new byte[(int)originalFile.length()];
          fileInputStreamReader.read(bytes);
          encodedBase64 = new String(Base64.encodeBase64(bytes));
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return encodedBase64;
    }
}
