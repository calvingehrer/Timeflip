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
import java.sql.Timestamp;

public class RestClient implements Runnable {

    private final String macAddress;
    private final JSONArray historyArray;
    private final Timestamp historyReadTime;
    private final CredentialsProvider credentialsProvider;
    private final String messagingServiceUri;

    public RestClient(String username, String password, String macAddress, JSONArray historyArray, Timestamp historyReadTime, String messagingServiceUri) {
        this.macAddress = macAddress;
        this.historyArray = historyArray;
        this.historyReadTime = historyReadTime;
        this.messagingServiceUri = messagingServiceUri;

        credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
    }

    @Override
    public void run() {
        try {

            transmitMessage(macAddress, historyReadTime.toString(), historyArray.toString());

        } catch (IOException e) {
            // ignore
        }
    }

    public boolean transmitMessage(String address, String historyReadTime, String history) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();

        HttpPost httpPost = new HttpPost(messagingServiceUri);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        JSONObject requestJson = new JSONObject();
        requestJson.put("macAddress", address);
        requestJson.put("history", history);
        requestJson.put("historyReadTime", historyReadTime);

        httpPost.setEntity(new StringEntity(requestJson.toString()));

        HttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            HttpEntity entity = response.getEntity();
            String responseString = IOUtils.toString(entity.getContent());
            JSONObject responseJson = new JSONObject(responseString);

            Long id = responseJson.getLong("id");
            String macAddress = responseJson.getString("macAddress");
            String hist = responseJson.getString("history");
            String histReadTime = responseJson.getString("historyReadTime");

            System.out.printf("Id: #%d, MAC-Address: %s, ReadTime: %s, History: %s\n", id, macAddress, histReadTime, hist);
            return true;
        } else {
            System.err.printf("Error posting message, service returned status code %d\n", response.getStatusLine().getStatusCode());
            return false;
        }
    }

}
