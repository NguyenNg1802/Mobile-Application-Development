package com.example.nguyen.lab1;

import android.content.Context;
import android.os.AsyncTask;

import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by PC on 3/1/2019.
 */

public class VtvStreamingAsyncTask extends AsyncTask<String, String, String> {

    private Context mContext;
    private VtvVideoStreamingListener mListener;
    private String channelName;
    private final String regex = "\\{\\\"items\\\":\\[.*\\}\\]";

    public VtvStreamingAsyncTask(Context context, VtvVideoStreamingListener videoStreamingListener, String channelName) {
        mContext = context;
        mListener = videoStreamingListener;
        this.channelName = channelName;
    }


    @Override
    protected String doInBackground(String... f_urls) {
        try {
            StringBuilder contentBuilder = new StringBuilder();
            URL url = new URL(f_urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                contentBuilder.append(line);
            }

            return contentBuilder.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void onPostExecute(String vtvChannels_raw) {

        String channelLink = "";
        try {
            channelLink = findStreamUrl(vtvChannels_raw, channelName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mListener == null) {
            return;
        }

        if (channelLink.length() > 0) {
            mListener.onVtvStreamResponse(channelLink, channelName);
        }
        else {
            mListener.onVtvStreamError("Cannot get stream resource, please try again", channelName);
        }
    }

    private String findStreamUrl(String response, String channelName) throws JSONException {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(response);

        String url_item_json;
        if(m.find()){
            url_item_json = m.group() + "}";

            JSONObject obj = new JSONObject(url_item_json);
            String channel_url;
            JSONArray arr = obj.getJSONArray("items");

            for (int i = 0; i < arr.length(); i++)
            {
                String channel = arr.getJSONObject(i).getString("name");
                if(channel.equals(channelName)) return channel_url = arr.getJSONObject(i).getString("url");

            }
            return "";
        }
        return "";
    }

}
