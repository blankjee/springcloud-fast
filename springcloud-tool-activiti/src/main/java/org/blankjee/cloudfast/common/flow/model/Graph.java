package org.blankjee.cloudfast.common.flow.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/10 11:17
 */
public class Graph {
    /**
     * 初始节点
     */
    private Node initial;

    public Node getInitial() {
        return initial;
    }

    public void setInitial(Node initial) {
        this.initial = initial;
    }

    /**
     * 递归访问遍历各个节点
     * @param node
     * @param nodes
     */
    public void visitNode(Node node, List<Node> nodes) {
        nodes.add(node);
        for (Edge edge : node.getOutgoingEdges()) {
            Node nextNode = edge.getDest();
            visitNode(nextNode, nodes);
        }
    }

    /**
     * 获取所有的节点信息
     * @return
     */
    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        visitNode(initial, nodes);
        return nodes;
    }

    /**
     * 遍历访问各个边
     * @param node
     * @param edges
     */
    public void visitEdge(Node node, List<Edge> edges) {
        for (Edge edge : node.getOutgoingEdges()) {
            edges.add(edge);
            Node nextNode = edge.getDest();
            visitEdge(nextNode, edges);
        }
    }

    /**
     * 获取所有的边信息
     * @return
     */
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        visitEdge(initial, edges);
        return edges;
    }

    /**
     * 根据ID获取节点信息
     * @param id
     * @return
     */
    public Node findById(String id) {
        for (Node node : this.getNodes()) {
            if (id.equals(node.getId())) {
                return node;
            }
        }
        return null;
    }
}
