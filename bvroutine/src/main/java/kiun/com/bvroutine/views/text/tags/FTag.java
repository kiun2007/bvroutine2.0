package kiun.com.bvroutine.views.text.tags;

import java.util.Map;

public class FTag extends TextTagBase {

    @Override
    protected String color(Map<String, String> attributes) {
        return attributes.get("c");
    }

    @Override
    protected String size(Map<String, String> attributes) {
        return attributes.get("s");
    }

    @Override
    protected String backgroundColor(Map<String, String> attributes) {
        return attributes.get("bc");
    }

    @Override
    protected String show(Map<String, String> attributes) {
        return attributes.get("v");
    }
}
