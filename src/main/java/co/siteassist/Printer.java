package co.siteassist;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Printer {

    private final Map<String, LinkNode> linksMap;

    public Printer(Map<String, LinkNode> linksMap) {
        this.linksMap = linksMap;
    }

    public void printSiteMap() {
        List<Map<String, Object>> jsonList = convertToJsonList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonList);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, Object>> convertToJsonList() {
        return linksMap.values()
                .parallelStream()
                .map(this::convertToJson)
                .toList();
    }

    private Map<String, Object> convertToJson(LinkNode node) {
        Map<String, Object> jsonNode = new LinkedHashMap<>();
        jsonNode.put("url", node.getUrl());
        List<String> childList = node.getChildLinks().parallelStream().map(LinkNode::getUrl).toList();
        jsonNode.put("childLinks", childList);
        return jsonNode;
    }
}
