package com.practice.musicoffline;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.practice.musicoffline.model.AudioModel;
import com.practice.musicoffline.repo.AudioRepository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // All static variables
    static final String inputFileName = "music.xml";
    // XML node keys
    static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_DURATION = "duration";
    static final String KEY_THUMB_URL = "thumb_url";

    ListView list;
    LazyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new RetrieveFeedTask().execute(inputFileName);
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;
        AudioRepository audioRepository;

        protected String doInBackground(String... urls) {
            try {
                AssetManager assetManager = getAssets();
                InputStream inputStream = null;
//               inputStream = assetManager.open(urls[0]);

                audioRepository = new AudioRepository();
                audioRepository.getAllAudioFromDevice(MainActivity.this);
                return readTextFile(inputStream);
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        private String readTextFile(InputStream inputStream) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte buf[] = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {

            }
            return outputStream.toString();
        }

        protected void onPostExecute(String ret) {
            List<AudioModel> audioModels = new ArrayList<AudioModel>(audioRepository.getAudioModelMap().values());
            ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

            for (int i = 0; i < audioModels.size(); i++) {
                AudioModel audioModel = audioModels.get(i);
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put(MainActivity.KEY_ID, audioModel.getId() + "");
                map.put(MainActivity.KEY_TITLE, audioModel.getName());
                map.put(MainActivity.KEY_ARTIST, audioModel.getArtist());
                map.put(MainActivity.KEY_DURATION, audioModel.getDuration());
                map.put(MainActivity.KEY_THUMB_URL, audioModel.getPath());

                // adding HashList to ArrayList
                songsList.add(map);
            }

            list = (ListView) findViewById(R.id.list);

            // Getting adapter by passing xml data ArrayList
            adapter = new LazyAdapter(MainActivity.this, songsList);
            list.setAdapter(adapter);

            // Click event for single list row
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {


                }
            });
        }
    }
}


