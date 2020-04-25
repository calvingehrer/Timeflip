package com.example.setup;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class RestClient implements Runnable {

    private final String username;
    private final JSONArray historyArray;
    private final CredentialsProvider credentialsProvider;
    private final String messagingServiceUri;

    public RestClient(String username, String password, JSONArray historyArray, String messagingServiceUri) {
        this.username = username;
        this.historyArray = historyArray;
        this.messagingServiceUri = messagingServiceUri;

        credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
    }

    @Override
    public void run() {
        try {

            transmitMessage(historyArray.toString());

        } catch (IOException e) {
            // ignore
        }
    }

    public boolean transmitMessage(String message) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();

        HttpPost httpPost = new HttpPost(messagingServiceUri);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        JSONObject requestJson = new JSONObject();
        requestJson.put("content", message);
        //requestJson.put("sender", username);

        httpPost.setEntity(new StringEntity(requestJson.toString()));

        HttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            HttpEntity entity = response.getEntity();
            String responseString = IOUtils.toString(entity.getContent());
            JSONObject responseJson = new JSONObject(responseString);

            Long id = responseJson.getLong("id");
            String content = responseJson.getString("content");
            String sender = responseJson.getString("sender");

            System.out.printf("Posted message #%d from %s: %s\n", id, sender, content);
            return true;
        } else {
            System.err.printf("Error posting message, service returned status code %d\n", response.getStatusLine().getStatusCode());
            return false;
        }
    }

}
