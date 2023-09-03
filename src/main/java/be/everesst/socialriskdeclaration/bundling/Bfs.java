package be.everesst.socialriskdeclaration.bundling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;


public class Bfs {

    public static final String ROOT_URL = "https://tomblomfield.com/";
    private static final Set<String> visitedUrls = new HashSet<>();
    private static final Map<String, LinkNode> linksMap = new LinkedHashMap<>();


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        crawl(new LinkNode(ROOT_URL));
        long endTime = System.currentTimeMillis();
        double executionTimeInSeconds = (endTime - startTime) / 1000.0; // Convert to seconds
        System.out.println("Execution Time (in seconds): " + executionTimeInSeconds);
        Printer printer = new Printer(linksMap);
        printer.printSiteMap();
    }

    private static void crawl(LinkNode rootNode) {
        Queue<LinkNode> queue = new LinkedList<>();
        queue.add(rootNode);
        while (!queue.isEmpty()) {
            LinkNode currentNode = queue.poll();
            String currentUrl = currentNode.getUrl();
            if (!visitedUrls.contains(currentUrl)) {
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
    }

    private static void addNewLinkNode(Queue<LinkNode> queue, LinkNode currentNode, String currentUrl, String link) {
        LinkNode childNode = new LinkNode(link);
        currentNode.addChild(childNode);
        linksMap.put(currentUrl, currentNode);
        queue.add(childNode);
    }

    private static List<String> getLinksOfTheCurrentPage(String currentUrl, Elements links) {
        return links.parallelStream()
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
