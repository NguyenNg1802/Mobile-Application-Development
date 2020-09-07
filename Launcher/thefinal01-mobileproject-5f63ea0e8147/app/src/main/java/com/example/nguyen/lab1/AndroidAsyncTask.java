package com.example.nguyen.lab1;

import android.content.Context;
import android.os.AsyncTask;

import com.loopj.android.http.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class AndroidAsyncTask extends AsyncTask<String, String, String> {
    private Context mContext;
    private ThvlVideoStreamingListener mListener;
    private String channelName;
    public AndroidAsyncTask(Context context, ThvlVideoStreamingListener thvlVideoStreamingListener, String channelName) {
        mContext = context;
        mListener = thvlVideoStreamingListener;
        this.channelName = channelName;
    }



    @Override
    protected String doInBackground(String...f_url) {
        String url = f_url[0];
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String firstResponse = convertStreamToString(instream);

                //LinkModel objLink = new Gson().fromJson(firstResponse, LinkModel.class);

                instream.close();
                return firstResponse;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void onPostExecute(String path) {
        if (mListener == null) {
            return;
        }

        if (path.length() > 0) {
            mListener.onThvlStreamResponse(path, channelName);
        }
        else {
            mListener.onThvlStreamError("Cannot get stream resource, please try again", channelName);
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
