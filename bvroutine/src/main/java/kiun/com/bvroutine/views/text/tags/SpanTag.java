package kiun.com.bvroutine.views.text.tags;

import java.util.Map;

public class SpanTag extends TextTagBase{

    @Override
    protected String color(Map<String, String> attributes) {
        return attributes.get("color");
    }

    @Override
    protected String size(Map<String, String> attributes) {
        return attributes.get("size");
    }

    @Override
    protected String backgroundColor(Map<String, String> attributes) {
        return attributes.get("background");
    }

    @Override
    protected String show(Map<String, String> attributes) {
        return attributes.get("visible");
    }
}
