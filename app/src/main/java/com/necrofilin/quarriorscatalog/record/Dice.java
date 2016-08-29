package com.necrofilin.quarriorscatalog.record;

import android.graphics.Color;
import android.util.Log;

import com.necrofilin.quarriorscatalog.ResourceXmlParser;
import com.necrofilin.quarriorscatalog.XmlResource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stas on 23.08.16.
 */
public class Dice extends XmlResource {
    int background;
    int foreground;

    public Dice() {
    }

    public ArrayList<DiceSide> getSides() {
        if(sides == null)
            sides = new ArrayList<DiceSide>();
        return sides;
    }

    public void setSides(ArrayList<DiceSide> sides) {
        this.sides = sides;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getForeground() {
        return foreground;
    }

    public void setForeground(int foreground) {
        this.foreground = foreground;
    }

    ArrayList<DiceSide> sides;

    @Override
    public HashMap<String, String> getAttributeMap() {
        HashMap<String, String> map = super.getAttributeMap();
        map.put("sides", ResourceXmlParser.PARAM_TYPE_ARRAY);
        map.put("background", ResourceXmlParser.PARAM_TYPE_COLOR);
        map.put("foreground", ResourceXmlParser.PARAM_TYPE_COLOR);
        return map;
    }

    @Override
    public ArrayList<? extends XmlResource> getArray(String key) {
        switch (key) {
            case "sides":
                return getSides();
            default:
                return super.getArray(key);
        }
    }
    int parseColor(String color){
        switch(color.length()){
            case 7:
            case 9:
                return Color.parseColor(color);
            case 4:
                StringBuilder sb = new StringBuilder();
                sb.append(color.charAt(0));
                sb.append(color.charAt(1));
                sb.append(color.charAt(1));
                sb.append(color.charAt(2));
                sb.append(color.charAt(2));
                sb.append(color.charAt(3));
                sb.append(color.charAt(3));
                return Color.parseColor(sb.toString());
            default:
                Log.e("Dice.BAD_COLOR", color);
                return 0;
        }
    }

    @Override
    public void setColor(String key, String value) {
        switch (key){
            case "background":
                setBackground(parseColor(value));
                break;
            case "foreground":
                setForeground(parseColor(value));
                break;
            default:
                super.setColor(key, value);
        }
    }
}
