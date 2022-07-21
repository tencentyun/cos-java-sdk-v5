package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.image.ImageStyleResponse;
import com.qcloud.cos.model.ciModel.image.StyleRule;
import org.xml.sax.Attributes;

import java.util.List;


public class GetImageStyleHandler extends CIAbstractHandler {
    public ImageStyleResponse response = new ImageStyleResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        List<StyleRule> styleRule = response.getStyleRule();
        if (in("StyleList") && "StyleRule".equals(name)) {
            styleRule.add(new StyleRule());
        }
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        StyleRule styleRule = response.getStyleRule().get(response.getStyleRule().size() - 1);
        if (in("StyleList", "StyleRule")) {
            switch (name) {
                case "StyleName":
                    styleRule.setStyleName(getText());
                    break;
                case "StyleBody":
                    styleRule.setStyleBody(getText());
                    break;
                default:
                    break;
            }
        }
    }

    public ImageStyleResponse getResponse() {
        return response;
    }

}
