package kiun.com.bvroutine.views.text;

import android.text.Editable;
import org.xml.sax.XMLReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.text.Html;
import kiun.com.bvroutine.views.text.tags.TagBase;
import kiun.com.bvroutine.views.text.tags.TagFactory;

public class HtmlTag implements Html.TagHandler {

    private int startIndex = 0;
    private int endIndex = 0;

    private Map<String, String> attributes;

    public HtmlTag() {}

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

        TagBase tagBase = TagFactory.create(tag);
        if (tagBase != null) {
            if (opening) {
                startHandleTag(tagBase, output, xmlReader);
                attributes = parseAttributes(xmlReader);
            } else {
                endEndHandleTag(tagBase, output, attributes, startIndex);
            }
        }
    }

    public void startHandleTag(TagBase tagBase, Editable output, XMLReader xmlReader) {
        startIndex = output.length();
        if (startIndex > 0 && endIndex == 0){
            endIndex = startIndex;
            endEndHandleTag(tagBase, output, attributes, 0);
        }
    }

    public void endEndHandleTag(TagBase tagBase, Editable output, Map<String, String> attributes, int start) {
        endIndex = output.length();
        tagBase.endHandleTag(output, attributes, start, endIndex);
    }

    /**
     * 解析所有属性值
     *
     * @param xmlReader
     */
    private Map<String, String> parseAttributes(final XMLReader xmlReader) {

        final Map<String, String> attributes = new HashMap<>();
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[]) dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer) lengthField.get(atts);

            for (int i = 0; i < len; i++) {
                attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
            }
        } catch (Exception e) {
        }
        return attributes;
    }
}
