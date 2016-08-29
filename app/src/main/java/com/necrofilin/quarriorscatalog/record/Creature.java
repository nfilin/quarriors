package com.necrofilin.quarriorscatalog.record;

import com.necrofilin.quarriorscatalog.ResourceXmlParser;

import java.util.HashMap;

/**
 * Created by stas on 23.08.16.
 */
public class Creature extends DiceSide {
    int level;
    String attack;
    String defense;
    Type type = Type.CREATURE;

    public Creature() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String deffense) {
        this.defense = deffense;
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
        map.put("attack", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("defense", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("level", ResourceXmlParser.PARAM_TYPE_INTEGER);
        return map;
    }

    @Override
    public void setString(String key, String value) {
        switch (key) {
            case "attack":
                setAttack(value);
                break;
            case "defense":
                setDefense(value);
                break;
            default:
                super.setString(key, value);
        }
    }

    @Override
    public void setInteger(String key, int value) {
        switch (key) {
            case "level":
                setLevel(value);
                break;
            default:
                super.setInteger(key, value);
        }
    }
}
