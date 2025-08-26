package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowDependency;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowNode;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义 Map 序列化 converter
 */
public class MediaWorkflowMapConverter implements Converter {
    @Override
    public boolean canConvert(Class type) {
        return Map.class.isAssignableFrom(type);
    }

    // 序列化 Map 类型
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Map<String, ?> map = (Map<String, ?>) source;
        // 遍历 entry，key 作为节点名，value 作为节点值
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            writer.startNode(entry.getKey());
            context.convertAnother(entry.getValue());
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String mapNodeName = reader.getNodeName(); // e.g. "Dependencies" or "Nodes"
        Map<String, Object> map = new LinkedHashMap<>();

        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String key = reader.getNodeName();
            if ("Dependencies".equals(mapNodeName)) {
                // Dependencies 下只有 String 类型的 value
                String text = reader.getValue();
                MediaWorkflowDependency dep = new MediaWorkflowDependency();
                dep.setValue(text != null ? text.trim() : null);
                map.put(key, dep);
            } else if ("Nodes".equals(mapNodeName)) {
                // Nodes 下是复杂对象 -> 解析为 MediaWorkflowNode
                MediaWorkflowNode node = (MediaWorkflowNode) context.convertAnother(map, MediaWorkflowNode.class);
                map.put(key, node);
            } else {
                // 其他情况
                Object value = context.convertAnother(map, Object.class);
                map.put(key, value);
            }
            reader.moveUp();
        }

        return map;
    }
}
