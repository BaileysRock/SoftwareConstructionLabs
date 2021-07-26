/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * A mutable weighted directed graph with labeled vertices.
 * Vertices have distinct labels of an immutable type {@code L} when compared
 * using the {@link Object#equals(Object) equals} method.
 * Edges are directed and have a positive weight of type {@code int}.
 * 
 * <p>PS2 instructions: this is a required ADT interface.
 * You MUST NOT change the specifications or add additional methods.
 * 
 * @param <L> type of vertex labels in this graph, must be immutable
 */
public interface Graph<L> {
    
    /**
     * Create an empty graph.
     * 创建一个空的图
     * @param <L> type of vertex labels in the graph, must be immutable
     * @return a new empty weighted directed graph
     */

    public static <L> Graph<L> empty() {

        return new ConcreteEdgesGraph<>();
    }
    
    /**
     * Add a vertex to this graph.
     * 添加一条顶点到这个图，若改图没有包含该顶点，则返回true，否则返回false
     * @param vertex label for the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    public boolean add(L vertex);

    /**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     * 添加，改变，或删除一个加权有向边在这个图中
     * 如果weight！=0 添加一条边，或更新该边的权值
     * 如果给定标签的顶点没有出现，则将带有给定的标签的顶点将被添加到图中
     * 如果weight == 0，如果存在这条边，则去除掉这条边
     * 返回：之前边的权值，如果没有这样的边则为零
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    public int set(L source, L target, int weight);
    
    /**
     * Remove a vertex from this graph; any edges to or from the vertex are
     * also removed.
     * 从图中去掉一个顶点，任何和这个顶点相关的边都删除
     * 返回：如果此图包含带有给定标签的顶点，则为True;否则为false(且此图未被修改)
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)
     */
    public boolean remove(L vertex);
    
    /**
     * Get all the vertices in this graph.
     * 得到这个图中所有的顶点
     * 返回：这个图中所有顶点的标签
     * @return the set of labels of vertices in this graph
     */
    public Set<L> vertices();
    
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 获取带有指向目标顶点的边的源顶点以及这些边的权值。
     * 返回：一个映射，其中键集是顶点的标签集
     * 这样这个图包括从该顶点到目标的一条边
     * 每个键的值是从该键到目标的边的(非零)权值。
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    public Map<L, Integer> sources(L target);
    
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 从源顶点获得带有有向边的目标顶点以及这些边的权值。
     * 返回：一个映射，其中键集是顶点的标签集
     * 这样这个图包括从源到该顶点的一条边
     * 每个键的值是从源到键的边的(非零)权值
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key
     */
    public Map<L, Integer> targets(L source);
}
