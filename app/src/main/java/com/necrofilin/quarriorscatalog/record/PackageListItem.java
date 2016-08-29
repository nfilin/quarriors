package com.necrofilin.quarriorscatalog.record;

import android.content.res.Resources;

import com.necrofilin.quarriorscatalog.ResourceXmlParser;
import com.necrofilin.quarriorscatalog.XmlResource;

import java.util.HashMap;

/**
 * Created by stas on 25.08.16.
 */
public class PackageListItem extends XmlResource {
    public PackageListItem() {
    }

    public CardPackage getDeck() {
        return deck;
    }

    public void setDeck(CardPackage deck) {
        this.deck = deck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    CardPackage deck;
    String title;

    @Override
    public void setResource(String key, int value, Resources resources) {
        switch (key) {
            case "deck":
                deck = (CardPackage) ResourceXmlParser.parse(resources, value);
                break;
            default:
                super.setResource(key, value, resources);
        }
    }

    @Override
    public void setString(String key, String value) {
        switch (key) {
            case "title":
                setTitle(value);
                break;
            default:
                super.setString(key, value);
        }
    }

    @Override
    public HashMap<String, String> getAttributeMap() {
        HashMap<String, String> map = super.getAttributeMap();
        map.put("deck", ResourceXmlParser.PARAM_TYPE_RESOURCE);
        map.put("title", ResourceXmlParser.PARAM_TYPE_STRING);
        return map;
    }
}
