package com.necrofilin.quarriorscatalog;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stas on 25.08.16.
 */
public class ResourceXmlParser implements XmlResourceParser {
    public static final String PARAM_TYPE_STRING = "String";
    public static final String PARAM_TYPE_BOOLEAN = "Boolean";
    public static final String PARAM_TYPE_INTEGER = "Integer";
    public static final String PARAM_TYPE_RESOURCE = "Resource";
    public static final String PARAM_TYPE_TEXT = "[Text]";
    public static final String PARAM_TYPE_ARRAY = "Array";
    public static final String PARAM_TYPE_COLOR = "Color";

    public static XmlResource parse(Resources resources, int id) {
        return parse(resources.getXml(id), resources);
    }

    public static XmlResource parse(XmlResourceParser xml, Resources resources) {
        int eventType = -1;
        String txt = null;
        String tag = null;
        ArrayList<? extends XmlResource> nodes = new ArrayList<XmlResource>();
        ArrayList<Integer> parentIndexes = new ArrayList<Integer>();
        int nodeIndex = -1;
        int parentIndex = -1;
        ArrayList<String> parentParamNames = new ArrayList<String>();
        ArrayList<String> parentParamTypes = new ArrayList<String>();
        ArrayList<String> tags = new ArrayList<String>();
        XmlResource currentNode = null;
        XmlResource node;
        Class<?> nodeClass;

        Map<String, String> attributeMap = new HashMap<String, String>();

        while (eventType != END_DOCUMENT) {
            try {
                eventType = xml.getEventType();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            switch (eventType) {
                case START_TAG:
                    tag = xml.getName();
                    if (tag.equals("resources")) {
                        try {
                            xml.nextTag();
                        } catch (XmlPullParserException | IOException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    if (attributeMap.get(tag) != null) {
                        parentParamNames.add(nodeIndex, tag);
                        parentParamTypes.add(nodeIndex, attributeMap.get(tag));

                        try {
                            xml.nextTag();
                        } catch (XmlPullParserException | IOException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    parentIndex = nodeIndex;
                    nodeIndex = nodes.size();

                    if (nodeIndex != -1) {
                        tags.add(nodeIndex, tag);
                        parentIndexes.add(nodeIndex, parentIndex);
                        parentParamNames.add(nodeIndex, null);
                        parentParamTypes.add(nodeIndex, null);
                    }

                    try {
                        nodeClass = Class.forName("com.necrofilin.quarriorscatalog.record." + tag);
                    } catch (ClassNotFoundException e) {
                        try {
                            nodeClass = Class.forName(tag);
                        } catch (ClassNotFoundException e1) {
                            nodeClass = XmlResource.class;
                        }
                    }
                    Constructor<?> ctor;
                    Constructor<?>[] ctors;
                    ctors = nodeClass.getConstructors();
                    ctor = ctors.length > 0 ? ctors[0] : null;

                    try {
                        node = (XmlResource) (ctor.newInstance());
                    } catch (IllegalArgumentException | NullPointerException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        node = null;
                    }
                    ((ArrayList<XmlResource>) nodes).add(node);
                    currentNode = node;


                    if (parentIndex != -1 && parentParamNames.size() > parentIndex && parentParamNames.get(parentIndex) != null) {
                        switch (parentParamTypes.get(parentIndex)) {
                            case PARAM_TYPE_ARRAY:
                                ArrayList<XmlResource> list = (ArrayList<XmlResource>) nodes.get(parentIndex).getArray(parentParamNames.get(parentIndex));
                                if (list != null)
                                    list.add(currentNode);
                                break;
                        }
                    }
                    if (currentNode != null) {
                        attributeMap = currentNode.getAttributeMap();

                        Log.d("PageList.Parse.tag", tag == null ? "null" : tag);


                        String attributeName;
                        String attributeType;
                        for (int i = 0; i < xml.getAttributeCount(); ++i) {
                            attributeName = xml.getAttributeName(i);
                            attributeType = xml.getAttributeType(i);
                            if (attributeMap.get(attributeName) != null)
                                switch (attributeMap.get(attributeName)) {
                                    case PARAM_TYPE_STRING:
                                        String val = xml.getAttributeValue(i).trim();
                                        int resourceId = 0;
                                        if (val.length() > 9 && val.substring(0, 8).equals("@string/")) {
                                            resourceId = xml.getAttributeResourceValue(i, 0);
                                        }
                                        if (resourceId != 0) {
                                            currentNode.setString(attributeName, resources.getString(resourceId));
                                        } else {
                                            currentNode.setString(attributeName, val);
                                        }
                                        break;
                                    case PARAM_TYPE_BOOLEAN:
                                        currentNode.setBoolean(attributeName, xml.getAttributeBooleanValue(i, false));
                                        break;
                                    case PARAM_TYPE_INTEGER:
                                        currentNode.setInteger(attributeName, xml.getAttributeIntValue(i, 0));
                                        break;
                                    case PARAM_TYPE_RESOURCE:
                                        currentNode.setResource(attributeName, xml.getAttributeResourceValue(i, 0), resources);
                                        break;
                                    case PARAM_TYPE_COLOR:
                                        currentNode.setColor(attributeName, xml.getAttributeValue(i));
                                        break;
                                }
                            Log.d("PageList.Parse." + i, "attributeName" + (attributeName == null ? "null" : attributeName));
                            Log.d("PageList.Parse." + i, "attributeType" + (attributeType == null ? "null" : attributeType));
                        }
                    }
                    try {
                        xml.nextTag();
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case TEXT:
                    txt = xml.getText();
                    if (txt != null) {
                        txt = txt.replaceAll("[\n\u0020\t]+", " ").trim();
                        Log.d("PageList.Parse.txt", txt == null ? "null" : txt);
                        if (txt.length() > 9 && txt.substring(0, 8).equals("@string/")) {

                            int resId = resources.getIdentifier(txt, "string", "com.necrofilin.quarriorscatalog");
                            if (resId != 0) {
                                txt = resources.getString(resId).replaceAll("[\n\u0020\t]+", " ").trim();
                            }
                        }
                    }

                    if (currentNode != null) {
                        attributeMap = currentNode.getAttributeMap();

                        for (String key : attributeMap.keySet()) {
                            if (attributeMap.get(key).equals(PARAM_TYPE_TEXT)) {
                                currentNode.setString(key, txt);
                            }
                        }

                    }
                    try {
                        xml.nextTag();
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case END_TAG:
                    tag = xml.getName();
                    if (nodeIndex != -1 && tag.equals(tags.get(nodeIndex))) {
                        nodeIndex = parentIndexes.get(nodeIndex);
                        if (nodeIndex != -1)
                            currentNode = nodes.get(nodeIndex);
                        if (currentNode != null) {
                            attributeMap = currentNode.getAttributeMap();
                        }
                    }
                case START_DOCUMENT:
                default:
                    try {
                        xml.next();
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }
            }


        }
        return nodes.size() > 0 ? nodes.get(0) : null;
    }

    public void close() {

    }

    public int getAttributeNameResource(int i) {
        return 0;
    }

    public int getAttributeListValue(String s, String s1, String[] strings, int i) {
        return 0;
    }

    public boolean getAttributeBooleanValue(String s, String s1, boolean b) {
        return false;
    }

    public int getAttributeResourceValue(String s, String s1, int i) {
        return 0;
    }

    public int getAttributeIntValue(String s, String s1, int i) {
        return 0;
    }

    public int getAttributeUnsignedIntValue(String s, String s1, int i) {
        return 0;
    }

    public float getAttributeFloatValue(String s, String s1, float v) {
        return 0;
    }

    public int getAttributeListValue(int i, String[] strings, int i1) {
        return 0;
    }

    public boolean getAttributeBooleanValue(int i, boolean b) {
        return false;
    }

    public int getAttributeResourceValue(int i, int i1) {
        return 0;
    }

    public int getAttributeIntValue(int i, int i1) {
        return 0;
    }

    public int getAttributeUnsignedIntValue(int i, int i1) {
        return 0;
    }

    public float getAttributeFloatValue(int i, float v) {
        return 0;
    }

    public String getIdAttribute() {
        return null;
    }

    public String getClassAttribute() {
        return null;
    }

    public int getIdAttributeResourceValue(int i) {
        return 0;
    }

    public int getStyleAttribute() {
        return 0;
    }

    public void setFeature(String s, boolean b) throws XmlPullParserException {

    }

    public boolean getFeature(String s) {
        return false;
    }

    public void setProperty(String s, Object o) throws XmlPullParserException {

    }

    public Object getProperty(String s) {
        return null;
    }

    public void setInput(Reader reader) throws XmlPullParserException {

    }

    public void setInput(InputStream inputStream, String s) throws XmlPullParserException {

    }

    public String getInputEncoding() {
        return null;
    }

    public void defineEntityReplacementText(String s, String s1) throws XmlPullParserException {

    }

    public int getNamespaceCount(int i) throws XmlPullParserException {
        return 0;
    }

    public String getNamespacePrefix(int i) throws XmlPullParserException {
        return null;
    }

    public String getNamespaceUri(int i) throws XmlPullParserException {
        return null;
    }

    public String getNamespace(String s) {
        return null;
    }

    public int getDepth() {
        return 0;
    }

    public String getPositionDescription() {
        return null;
    }

    public int getLineNumber() {
        return 0;
    }

    public int getColumnNumber() {
        return 0;
    }

    public boolean isWhitespace() throws XmlPullParserException {
        return false;
    }

    public String getText() {
        return null;
    }

    public char[] getTextCharacters(int[] ints) {
        return new char[0];
    }

    public String getNamespace() {
        return null;
    }

    public String getName() {
        return null;
    }

    public String getPrefix() {
        return null;
    }

    public boolean isEmptyElementTag() throws XmlPullParserException {
        return false;
    }

    public int getAttributeCount() {
        return 0;
    }

    public String getAttributeNamespace(int i) {
        return null;
    }

    public String getAttributeName(int i) {
        return null;
    }

    public String getAttributePrefix(int i) {
        return null;
    }

    public String getAttributeType(int i) {
        return null;
    }

    public boolean isAttributeDefault(int i) {
        return false;
    }

    public String getAttributeValue(int i) {
        return null;
    }

    public String getAttributeValue(String s, String s1) {
        return null;
    }

    public int getEventType() throws XmlPullParserException {
        return 0;
    }

    public int next() throws XmlPullParserException, IOException {
        return 0;
    }

    public int nextToken() throws XmlPullParserException, IOException {
        return 0;
    }

    public void require(int i, String s, String s1) throws XmlPullParserException, IOException {

    }

    public String nextText() throws XmlPullParserException, IOException {
        return null;
    }

    public int nextTag() throws XmlPullParserException, IOException {
        return 0;
    }
}
