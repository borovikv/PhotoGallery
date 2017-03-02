package com.github.borovic.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vborovic on 3/1/17.
 */

public class FlickrFetchr {
    private static final String API_KEY = "51be8bbbc0bd940a24777acb8b2ea648";
    // sicret = dc82ab5435d6e0ce

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(
                        connection.getResponseMessage() + ": with " + urlSpec
                );
            }

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<GalleryItem> fetchItems(){
        List<GalleryItem> items = new ArrayList<>();
        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsString = getUrlString(url);
            Log.i(getClass().getName(), jsString);
            JSONObject jsonBody = new JSONObject(jsString);
            parseItems(items, jsonBody);
        } catch (IOException|JSONException e) {
            Log.e(getClass().getName(), "", e);
        }
        return items;
    }

    public void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws JSONException {
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsArray = photosJsonObject.getJSONArray("photo");

        for (int i = 0; i < photoJsArray.length(); i++) {
            JSONObject photoJsObject = photoJsArray.getJSONObject(i);
            if (!photoJsObject.has("url_s")) {
                continue;
            }

            GalleryItem item = new GalleryItem();
            item.setId(photoJsObject.getString("id"));
            item.setCaption(photoJsObject.getString("title"));
            item.setUrl(photoJsObject.getString("url_s"));
            items.add(item);
        }
    }
}
