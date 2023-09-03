package be.everesst.socialriskdeclaration.bundling;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LinkNode {

    private final String url;
    private final Set<LinkNode> childLinks;

    public LinkNode(String url) {
        this.url = url;
        this.childLinks = new HashSet<>();
    }

    void addChild(LinkNode child) {
        childLinks.add(child);
    }

    public String getUrl() {
        return url;
    }

    public Set<LinkNode> getChildLinks() {
        return childLinks;
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
