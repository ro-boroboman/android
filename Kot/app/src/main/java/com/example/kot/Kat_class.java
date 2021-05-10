package com.example.kot;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nik on 2/21/2018.
 */

public class Kat_class {
    private static final String TAG = "Kat_class";
    private static final String API_KEY = "5bacc683222718c3156335a637ea060f";

    public String getJSONString(String UrlSpec) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlSpec)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public List<Gallery_item> fetchItems() {
        List<Gallery_item> galleryItems = new ArrayList<>();
        try {
            String url = Uri.parse("https://api.thecatapi.com/images/search")
                    .buildUpon()
                    .appendQueryParameter("mime_types", "jpg,png")

                    .appendQueryParameter("api_key", API_KEY)



                    .build().toString();
            String jsonString = getJSONString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(galleryItems,jsonBody);

        } catch (JSONException e) {
            Log.e(TAG, "JSON exception");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "input output exception");
            e.printStackTrace();
        }
        return galleryItems;
    }

    private void parseItems(List<Gallery_item> items, JSONObject jsonObject) throws JSONException {
        JSONObject photosJsonObject = jsonObject.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            Gallery_item item = new Gallery_item();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));

            item.setUrl(photoJsonObject.getString("url_s"));
            items.add(item);
        }

    }
}

