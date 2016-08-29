package com.necrofilin.quarriorscatalog.record;

import android.content.res.Resources;

import com.necrofilin.quarriorscatalog.ResourceXmlParser;
import com.necrofilin.quarriorscatalog.XmlResource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stas on 23.08.16.
 */
public class Card extends XmlResource {
    public Card() {
    }

    public enum Type {
        QUIDDITY,
        SPELL,
        CREATURE;

    }

    public static final String TYPE_QUIDDITY = "quiddity";
    public static final String TYPE_SPELL = "spell";
    public static final String TYPE_CREATURE = "creature";

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getGlory() {
        return glory;
    }

    public void setGlory(int glory) {
        this.glory = glory;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    boolean base;
    int cost;
    int glory;
    Dice dice;
    String title;
    Type type;
    ArrayList<CardAttribute> attributes;
    ArrayList<CardAttribute> notes;

    public ArrayList<CardAttribute> getAttributes() {
        if (attributes == null)
            attributes = new ArrayList<CardAttribute>();
        return attributes;
    }

    public ArrayList<CardAttribute> getNotes() {
        if (notes == null)
            notes = new ArrayList<CardAttribute>();
        return notes;
    }

    public void setAttributes(ArrayList<CardAttribute> attributes) {
        this.attributes = attributes;
    }


    @Override
    public HashMap<String, String> getAttributeMap() {
        HashMap<String, String> map = super.getAttributeMap();
        map.put("base", ResourceXmlParser.PARAM_TYPE_BOOLEAN);
        map.put("cost", ResourceXmlParser.PARAM_TYPE_INTEGER);
        map.put("glory", ResourceXmlParser.PARAM_TYPE_INTEGER);
        map.put("dice", ResourceXmlParser.PARAM_TYPE_RESOURCE);
        map.put("title", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("type", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("attributes", ResourceXmlParser.PARAM_TYPE_ARRAY);
        map.put("notes", ResourceXmlParser.PARAM_TYPE_ARRAY);
        return map;
    }

    @Override
    public void setResource(String key, int id, Resources resources) {
        switch (key) {
            case "dice":
                setDice((Dice) ResourceXmlParser.parse(resources, id));
                break;
            default:
                super.setResource(key, id, resources);
        }
    }

    @Override
    public void setBoolean(String key, boolean value) {
        switch (key) {
            case "base":
                setBase(value);
                break;
            default:
                super.setBoolean(key, value);
        }
    }

    @Override
    public void setInteger(String key, int value) {
        switch (key) {
            case "cost":
                setCost(value);
                break;
            case "glory":
                setGlory(value);
                break;
            default:
                super.setInteger(key, value);
        }
    }

    @Override
    public void setString(String key, String value) {
        switch (key) {
            case "title":
                setTitle(value);
                break;
            case "type":
                switch (value) {
                    case TYPE_CREATURE:
                        setType(Type.CREATURE);
                        break;
                    case TYPE_SPELL:
                        setType(Type.SPELL);
                        break;
                    case TYPE_QUIDDITY:
                        setType(Type.CREATURE);
                        break;
                }
                break;
            default:
                super.setString(key, value);
        }
    }

    @Override
    public ArrayList<? extends XmlResource> getArray(String key) {
        switch (key) {
            case "attributes":
                return getAttributes();
            case "notes":
                return getNotes()   ;
            default:
                return super.getArray(key);
        }
    }
}
