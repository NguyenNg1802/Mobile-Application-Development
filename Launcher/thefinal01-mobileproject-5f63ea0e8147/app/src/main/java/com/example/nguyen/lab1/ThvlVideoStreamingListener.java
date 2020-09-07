package com.example.nguyen.lab1;

/**
 * Created by PC on 3/1/2019.
 */

public interface ThvlVideoStreamingListener {
    void onThvlStreamResponse(String link, String channelName);
    void onThvlStreamError(String message, String channelName);
}