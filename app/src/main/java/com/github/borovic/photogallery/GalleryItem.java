package com.github.borovic.photogallery;

/**
 * Created by vborovic on 3/1/17.
 */

public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;

    @Override
    public String toString() {
        return mCaption;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }
}
