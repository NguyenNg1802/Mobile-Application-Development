package com.example.nguyen.lab1;

/**
 * Created by PC on 3/1/2019.
 */

public interface VtvVideoStreamingListener {
    void onVtvStreamResponse(String link, String channelName);
    void onVtvStreamError(String message, String channelName);
}
