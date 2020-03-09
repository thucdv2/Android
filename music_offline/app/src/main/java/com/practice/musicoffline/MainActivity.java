package com.practice.musicoffline;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

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

        new RetrieveFeedTask().execute(inputFileName);

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                AssetManager assetManager = getAssets();
                InputStream inputStream = null;
                try {
                    inputStream = assetManager.open(urls[0]);
                } catch (IOException e) {
                    Log.e("tag", e.getMessage());
                }
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
            XMLParser parser = new XMLParser();
            ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
            ret = ret.replace("<hr>", "");
            Document doc = parser.getDomElement(ret); // getting DOM element

            NodeList nl = doc.getElementsByTagName(MainActivity.KEY_SONG);
            // looping through all song nodes <song>
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);
                // adding each child node to HashMap key => value
                map.put(MainActivity.KEY_ID, parser.getValue(e, MainActivity.KEY_ID));
                map.put(MainActivity.KEY_TITLE, parser.getValue(e, MainActivity.KEY_TITLE));
                map.put(MainActivity.KEY_ARTIST, parser.getValue(e, MainActivity.KEY_ARTIST));
                map.put(MainActivity.KEY_DURATION, parser.getValue(e, MainActivity.KEY_DURATION));
                map.put(MainActivity.KEY_THUMB_URL, parser.getValue(e, MainActivity.KEY_THUMB_URL));

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


