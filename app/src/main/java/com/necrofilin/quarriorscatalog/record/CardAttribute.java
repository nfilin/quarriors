package com.necrofilin.quarriorscatalog.record;

import android.content.res.XmlResourceParser;

import com.necrofilin.quarriorscatalog.ResourceXmlParser;
import com.necrofilin.quarriorscatalog.XmlResource;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;

/**
 * Created by stas on 23.08.16.
 */
public class CardAttribute extends XmlResource {
    public CardAttribute() {
    }

    public static final String TYPE_IMMEDIATE = "immediate";
    public static final String TYPE_ATTACH = "attach";
    public static final String TYPE_REACTION = "reaction";

    enum Type {
        IMMEDIATE,
        ATTACH,
        REACTION;
    }

    String mod;
    Type type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text;

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public HashMap<String, String> getAttributeMap() {
        HashMap<String, String> map = super.getAttributeMap();
        map.put("mod", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("type", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("text", ResourceXmlParser.PARAM_TYPE_TEXT);
        return map;
    }

    @Override
    public void setString(String key, String value) {
        switch (key) {
            case "mod":
                setMod(value);
                break;
            case "text":
                setText(value);
                break;
            case "type":
                switch (value) {
                    case TYPE_ATTACH:
                        setType(Type.ATTACH);
                        break;
                    case TYPE_IMMEDIATE:
                        setType(Type.IMMEDIATE);
                        break;
                    case TYPE_REACTION:
                        setType(Type.REACTION);
                        break;
                }
                break;
            default:
                super.setString(key, value);
        }
    }
}
