package com.necrofilin.quarriorscatalog.record;

import com.necrofilin.quarriorscatalog.ResourceXmlParser;
import com.necrofilin.quarriorscatalog.XmlResource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stas on 23.08.16.
 */
public class DiceSide extends XmlResource {
    public DiceSide() {
    }

    public static final String TYPE_QUIDDITY = "quiddity";
    public static final String TYPE_REROLL = "reroll";
    public static final String TYPE_DRAW = "draw";
    public static final String TYPE_CREATURE = "creature";
    public static final String TYPE_SPELL = "spell";
    public static final String TYPE_OR = "or";
    public static final String TYPE_AND = "and";

    enum Type {
        QUIDDITY,
        REROLL,
        DRAW,
        CREATURE,
        SPELL,
        OR,
        AND
    }

    int value;
    String mod;
    Type type;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

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

    public ArrayList<DiceSide> getSub() {
        if (sub == null)
            sub = new ArrayList<DiceSide>();
        return sub;
    }

    public void setSub(ArrayList<DiceSide> sub) {
        this.sub = sub;
    }

    ArrayList<DiceSide> sub;

    @Override
    public void setString(String key, String value) {
        switch (key) {
            case "mod":
                setMod(value);
                break;
            case "type":
                switch (value) {
                    case TYPE_AND:
                        setType(Type.AND);
                        break;
                    case TYPE_CREATURE:
                        setType(Type.CREATURE);
                        break;
                    case TYPE_DRAW:
                        setType(Type.DRAW);
                        break;
                    case TYPE_OR:
                        setType(Type.OR);
                        break;
                    case TYPE_QUIDDITY:
                        setType(Type.QUIDDITY);
                        break;
                    case TYPE_REROLL:
                        setType(Type.REROLL);
                        break;
                    case TYPE_SPELL:
                        setType(Type.SPELL);
                        break;
                }
                break;
            default:
                super.setString(key, value);
        }
    }

    @Override
    public void setInteger(String key, int value) {
        switch (key) {
            case "value":
                setValue(value);
                break;
            default:
                super.setInteger(key, value);
        }
    }

    @Override
    public ArrayList<? extends XmlResource> getArray(String key) {
        switch (key) {
            case "sub":
                return getSub();
            default:
                return super.getArray(key);
        }
    }

    @Override
    public HashMap<String, String> getAttributeMap() {
        HashMap<String, String> map = super.getAttributeMap();
        map.put("mod", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("value", ResourceXmlParser.PARAM_TYPE_INTEGER);
        map.put("type", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("sub", ResourceXmlParser.PARAM_TYPE_ARRAY);
        return map;
    }
}
