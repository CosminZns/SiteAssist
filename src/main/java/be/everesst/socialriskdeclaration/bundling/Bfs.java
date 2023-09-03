package be.everesst.socialriskdeclaration.bundling;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;


class LinkNode {

    String url;
    Set<LinkNode> childLinks;

    LinkNode(String url) {
        this.url = url;
        this.childLinks = new HashSet<>();
    }

    void addChild(LinkNode child) {
        childLinks.add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkNode linkNode = (LinkNode) o;
        return Objects.equals(url, linkNode.url) && Objects.equals(childLinks, linkNode.childLinks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, childLinks);
    }
}

public class Bfs {
    public static final String ROOT_URL = "https://tomblomfield.com/";
    private static final Set<String> visitedUrls = new HashSet<>();
    private static final Map<String, LinkNode> linksMap = new HashMap<>();
    private static final int NUM_THREADS = 4; // Adjust the number of threads as needed


    public static void main(String[] args) {
        crawl(new LinkNode(ROOT_URL));
        printSiteMap();
    }

    private static void crawl(LinkNode rootNode) {
        Queue<LinkNode> queue = new LinkedList<>();
        queue.add(rootNode);
        while (!queue.isEmpty()) {
            LinkNode currentNode = queue.poll();
            String currentUrl = currentNode.url;
            if (visitedUrls.contains(currentUrl)) {
                continue;
            }
            visitedUrls.add(currentUrl);
            try {
                Document document = Jsoup.connect(currentUrl).get();
                Elements links = document.select("a[href]");
                List<String> linksOfTheCurrentPage = getLinksOfTheCurrentPage(currentUrl, links);
                linksOfTheCurrentPage.forEach(link -> addNewLinkNode(queue, currentNode, currentUrl, link));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void printSiteMap() {
        List<Map<String, Object>> jsonList = convertToJsonList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonList);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Map<String, Object>> convertToJsonList() {
        return linksMap.values()
                .stream()
                .map(Bfs::convertToJson)
                .toList();
    }

    private static Map<String, Object> convertToJson(LinkNode node) {
        Map<String, Object> jsonNode = new HashMap<>();
        jsonNode.put("url", node.url);
        List<String> childList = node.childLinks.stream().map(childLink -> childLink.url).toList();
        jsonNode.put("childLinks", childList);
        return jsonNode;
    }

    private static void addNewLinkNode(Queue<LinkNode> queue, LinkNode currentNode, String currentUrl, String link) {
        LinkNode childNode = new LinkNode(link);
        currentNode.addChild(childNode);
        linksMap.put(currentUrl, currentNode);
        queue.add(childNode);
    }

    private static List<String> getLinksOfTheCurrentPage(String currentUrl, Elements links) {
        return links.stream()
                .map(element -> element.attr("href"))
                .filter(Bfs::isValidLink)
                .filter(link -> !link.equals(currentUrl))
                .map(Bfs::transformLink)
                .toList();
    }

    private static String transformLink(String link) {
        if (link.startsWith("/")) {
            return "https://tomblomfield.com" + link;
        }
        return link;
    }

    private static boolean isValidLink(String link) {
        return (link.startsWith("/") || link.startsWith("https://tomblomfield")) && !link.equals("/");
    }
}
