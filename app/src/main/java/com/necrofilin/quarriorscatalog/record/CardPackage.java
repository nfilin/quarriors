package com.necrofilin.quarriorscatalog.record;

import com.necrofilin.quarriorscatalog.ResourceXmlParser;
import com.necrofilin.quarriorscatalog.XmlResource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stas on 25.08.16.
 */
public class CardPackage extends XmlResource {
    public CardPackage() {
    }

    public ArrayList<Card> getCards() {
        if (cards == null)
            cards = new ArrayList<Card>();
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    ArrayList<Card> cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    @Override
    public HashMap<String, String> getAttributeMap() {
        HashMap<String, String> map = super.getAttributeMap();
        map.put("name", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("cards", ResourceXmlParser.PARAM_TYPE_ARRAY);
        return map;
    }

    @Override
    public ArrayList<? extends XmlResource> getArray(String key) {
        switch (key) {
            case "cards":
                return getCards();
            default:
                return super.getArray(key);
        }
    }

    @Override
    public void setString(String key, String value) {
        switch (key) {
            case "name":
                setName(value);
                break;
            default:
                super.setString(key, value);
        }
    }

}
