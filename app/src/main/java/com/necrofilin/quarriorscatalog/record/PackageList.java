package com.necrofilin.quarriorscatalog.record;

import com.necrofilin.quarriorscatalog.ResourceXmlParser;
import com.necrofilin.quarriorscatalog.XmlResource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stas on 23.08.16.
 */
public class PackageList extends XmlResource {

    public PackageList() {
    }

    public ArrayList<PackageListItem> getItems() {
        if (items == null) {
            items = new ArrayList<PackageListItem>();
        }
        return items;
    }

    public void setItems(ArrayList<PackageListItem> items) {
        this.items = items;
    }

    ArrayList<PackageListItem> items = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    @Override
    public ArrayList<? extends XmlResource> getArray(String key) {
        switch (key) {
            case "items":
                return getItems();
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

    @Override
    public HashMap<String, String> getAttributeMap() {
        HashMap<String, String> map = super.getAttributeMap();
        map.put("name", ResourceXmlParser.PARAM_TYPE_STRING);
        map.put("items", ResourceXmlParser.PARAM_TYPE_ARRAY);
        return map;
    }
}
