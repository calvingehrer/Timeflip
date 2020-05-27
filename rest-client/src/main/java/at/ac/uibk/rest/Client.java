package at.ac.uibk.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Date;

/**
 * Client class to execute http requests
 */
public class Client implements Runnable {

    private final JSONArray historyEntries;
    private final CredentialsProvider credentialsProvider;
    private final String messagingServiceUri;

    public Client(String username, String password, JSONArray historyEntries, String messagingServiceUri) {
        this.historyEntries = historyEntries;
        this.messagingServiceUri = messagingServiceUri;

        credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
    }


    /**
     * Send every HistoryEntry in the list to the backend server
     */
    @Override
    public void run() {
        try {
            for(Object historyEntry : historyEntries){
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(historyEntry);
                    transmitMessage(json);
                }catch (JsonProcessingException e){
                    e.printStackTrace();
                }
            }
            System.out.println("\n -- Sending done!" + "(" + new Date() + ") --");
        } catch (IOException e) {
            // ignore
        }
    }


    /**
     * Takes a HistoryEntry Object as input and creates JSONObject for transmission.
     * Executes http post.
     *
     * @param historyEntry the HistoryEntry to be transmitted
     * @return true if transmission was successful, false otherwise
     * @throws IOException
     */
    public boolean transmitMessage(Object historyEntry) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();

        HttpPost httpPost = new HttpPost(messagingServiceUri);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        JSONObject requestJson = new JSONObject();
        requestJson.put("history", historyEntries);

        httpPost.setEntity(new StringEntity(historyEntry.toString()));

        HttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            HttpEntity entity = response.getEntity();
            String responseString = IOUtils.toString(entity.getContent());
            JSONObject responseJson = new JSONObject(responseString);

            Long id = responseJson.getLong("id");
            String macAddress = responseJson.getString("macAddress");

            System.out.printf("Sending... Id: #%d, MAC-Address: %s\n", id, macAddress);
            return true;
        } else {
            System.err.printf("Error posting message, service returned status code %d\n", response.getStatusLine().getStatusCode());
            return false;
        }
    }

}

