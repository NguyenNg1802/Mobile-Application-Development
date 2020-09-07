package com.example.nguyen.lab1;

import android.app.Dialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ThvlVideoStreamingListener, VtvVideoStreamingListener {
    VideoView videoView;
    ListView listView;
    private String defaultChannel = "VTV1";
    private String currentChannel = "";
    private String currentChannelType = "";
    private final String vtvUrl = "https://www.vtvgiaitri.vn/xem-tivi-truc-tuyen";
    final String listOfChannels[] = {"VTV1", "VTV2", "VTV3", "VTV4", "VTV5", "VTV6", "VTV7", "VTV8", "VTV9", "THVL1", "THVL2", "THVL3" };

    MainActivity pointer = this;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView)findViewById(R.id.VideoView);
        listView = (ListView) findViewById(R.id.list_view);
        //list view will appear when user touch on the videoview
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showChannelList();
                return false;
            }
        });
        if(savedInstanceState != null){
            currentChannel = savedInstanceState.getString("currentChannel");
            currentChannelType = savedInstanceState.getString("currentChannelType");
            if(currentChannel.length() > 0){
                if(currentChannelType.equals("VTV")){
                    getVtvStream(currentChannel);
                }
                else getThvlStream(currentChannel);
            }
            else{
                getVtvStream(defaultChannel);
            }
        }
        else{
            getVtvStream(defaultChannel);
        }
        //new VtvStreamingAsyncTask(getApplicationContext(), pointer, "VTV1").execute(vtvUrl);
        //new AndroidAsyncTask(this, this, "thvl1").execute("http://demo1.chipfc.com/thvli/Channel?name=thvl1");
    }

    private void showChannelList(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.list_of_channels_layout);
        listView = (ListView) dialog.findViewById(R.id.list_view);
        //Fill data to ListView

        final Integer imageIconIds[] = {R.drawable.vtv1, R.drawable.vtv2, R.drawable.vtv3, R.drawable.vtv4, R.drawable.vtv5, R.drawable.vtv6, R.drawable.vtv7, R.drawable.vtv8,
                R.drawable.vtv9, R.drawable.thvl1, R.drawable.thvl2, R.drawable.thvl3};
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.list_of_channels_row_layout,
                 R.id.text_channel_name,
                 listOfChannels);*/
        CustomAdapter customAdapter = new CustomAdapter(this, listOfChannels, imageIconIds);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String channel = listOfChannels[position];
                videoView.stopPlayback();
                //new AndroidAsyncTask(getApplicationContext(), pointer, "thvl3").execute("http://demo1.chipfc.com/thvli/Channel?name=thvl3");
                if(position < 9) getVtvStream(channel);
                else getThvlStream(channel);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if(videoView.isPlaying()){
            videoView.stopPlayback();
        }
        moveTaskToBack(true);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(currentChannel.length() > 0){
            if(currentChannelType.equals("VTV")){
                getVtvStream(currentChannel);
            }
            else getThvlStream(currentChannel);
        }

    }

    @Override
    public void onThvlStreamResponse(String link, String channelName) {
        LinkModel objLink = new Gson().fromJson(link, LinkModel.class);
        HashMap<String, String> headers = new HashMap<>();
        for(HeaderModel header: objLink.getHeaders()){
            headers.put(header.getHeader(), header.getValue());
        }
        Uri uri = Uri.parse(objLink.getLow_res());
        videoView.setVideoURI(uri,headers);
        currentChannel = channelName;
        currentChannelType = "THVL";
        videoView.start();
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    public void onThvlStreamError(String message, String channelName) {
        //Toast.makeText(this, message,Toast.LENGTH_LONG).show();
        //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        Toast.makeText(this, "Cannot get stream resource, attempting to reconnect", Toast.LENGTH_LONG).show();
        getThvlStream(channelName);
    }

    @Override
    public void onVtvStreamResponse(String link, String channelName) {
        videoView.setVideoPath(link);
        currentChannel = channelName;
        currentChannelType = "VTV";
        videoView.start();
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    public void onVtvStreamError(String message, String channelName) {
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        Toast.makeText(this, "Cannot get stream resource, attempting to reconnect", Toast.LENGTH_LONG).show();
        getVtvStream(channelName);
    }

    private void getThvlStream(String channelName){
        if(videoView.isPlaying()) videoView.stopPlayback();
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        new AndroidAsyncTask(getApplicationContext(), pointer, channelName).execute("http://demo1.chipfc.com/thvli/Channel?name=" + channelName);
    }
    private void getVtvStream(String channelName){
        if(videoView.isPlaying()) videoView.stopPlayback();
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        new VtvStreamingAsyncTask(getApplicationContext(), pointer, channelName).execute(vtvUrl);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("currentChannel", currentChannel);
        savedInstanceState.putString("currentChannelType", currentChannelType);
    }
}
