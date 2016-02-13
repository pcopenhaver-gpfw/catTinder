package com.example.cattinder.data;

import android.net.Uri;

import io.realm.RealmObject;
import io.realm.annotations.Required;
/**
 */
public class CatDal extends RealmObject {
    @Required
    private String link;
    private String snippet;

    public CatDal() {
        this(Uri.EMPTY, "");
    }

    public CatDal(Uri link, String snippet) {
        this.link = link.toString();
        this.snippet = snippet;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
