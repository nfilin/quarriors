package com.necrofilin.quarriorscatalog;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stas on 25.08.16.
 */
public class XmlResource {
    public XmlResource(XmlResource resource) {
    }

    public XmlResource() {

    }

    public void setString(String key, String value) {
        Log.d("XmlResource.setString", key + ":" + value);
    }

    public void setInteger(String key, int value) {
        Log.d("XmlResource.setInteger", key + ":" + value);
    }

    public void setBoolean(String key, boolean value) {
        Log.d("XmlResource.setBoolean", key + ":" + value);
    }

    public void setResource(String key, int value, Resources resources) {
        Log.d("XmlResource.setResource", key + ":" + value);
    }

    public void setColor(String key, String value) {
        Log.d("XmlResource.setColor", key + ":" + value);
    }

    public ArrayList<? extends XmlResource> getArray(String key) {
        return null;
    }

    public HashMap<String, String> getAttributeMap() {
        return new HashMap<String, String>();
    }
}