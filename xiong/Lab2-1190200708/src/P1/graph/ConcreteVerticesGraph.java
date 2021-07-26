/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.lang.annotation.Target;
import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    // 由vertices组成的顶点
    // 即由List<Vertex>类型数据到加权有向图的映射
    // Representation invariant:
    // vertices中不存在重复的顶点
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制


    /**
     * 构造函数，构造出空图
     */
    public ConcreteVerticesGraph() {
        this.vertices.clear();
        checkRep();
    }

    /**
     * 检查不变量
     */
    private void checkRep() {
        for (Vertex vertex1 : vertices) {
            for (Vertex vertex2 : vertices) {
                if (vertex1.isEqualsVertex(vertex2)) {
                    assert (vertices.indexOf(vertex1) == vertices.indexOf(vertex2));
                }
                continue;
            }
        }
    }

    @Override
    public boolean add(L vertex) {
        for (Vertex vertexOld : vertices) {
            if (vertexOld.isEqualsName(vertex)) {
                checkRep();
                return false;
            }
        }
        Vertex vertexNew = new Vertex(vertex);
        vertices.add(vertexNew);
        checkRep();
        return true;
    }

    @Override
    public int set(L source, L target, int weight) {
        // 当前顶点在图中
        for (Vertex vertex : vertices) {
            if (vertex.isEqualsName(source)) {
                // 目标顶点也在图中
                for (Vertex vertexTarget : vertices) {
                    if (vertexTarget.isEqualsName(target)) {
                        int preWeight = vertex.weight(target);
                        if (weight != 0) {
                            vertex.writeTarget(target, weight);
                        } else {
                            vertex.remove(target);
                        }
                        checkRep();
                        return preWeight;
                    }
                }
                //目标顶点不在图中
                Vertex vertexNewtarget = new Vertex(target);
                vertices.add(vertexNewtarget);
                Map<L, Integer> map = new HashMap<L, Integer>();
                map.put(target, weight);
                if (weight != 0) {
                    vertex.writeTarget(target, weight);
                }
                checkRep();
                return 0;
            }
        }
        // 当前顶点不在图中
        // 目标顶点在图中
        for (Vertex vertexTarget : vertices) {
            if (vertexTarget.isEqualsName(target)) {
                Vertex vertexSource = new Vertex(source);
                vertexSource.writeTarget(vertexTarget.Name(), weight);
                this.vertices.add(vertexSource);
                return 0;
            }
            continue;
        }
        // 当前顶点不在图中
        // 目标顶点不在图中
        Vertex vertexSource = new Vertex(source);
        Vertex vertexTarget = new Vertex(target);
        vertexSource.writeTarget(vertexTarget.Name(), weight);
        vertices.add(vertexSource);
        vertices.add(vertexTarget);
        return 0;
    }

    @Override
    public boolean remove(L vertex) {
        for (Vertex vertexGraph : vertices) {
            if (vertexGraph.isEqualsName(vertex)) {
                vertices.remove(vertexGraph);
                for(Vertex vertex1:vertices)
                {
                    if(vertex1.Target().containsKey(vertex))
                    {
                        vertex1.remove(vertex1.Name());
                    }
                }
                checkRep();
                return true;
            }
            continue;
        }
        checkRep();
        return false;
    }

    @Override
    public Set<L> vertices() {
        Set<L> verticesSet = new HashSet<L>();
        for (Vertex<L> vertex : vertices) {
            verticesSet.add(vertex.Name());
        }
        checkRep();
        return verticesSet;
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sourceMap = new HashMap<L, Integer>();
        for (Vertex<L> vertex : vertices) {
            for (Map.Entry<L, Integer> mapVertex : vertex.Target().entrySet()) {
                if (mapVertex.getKey().equals(target)) {
                    sourceMap.put(vertex.Name(), mapVertex.getValue());
                }
            }



        }
        checkRep();
        return sourceMap;

    }

    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> sourceMap = new HashMap<L, Integer>();
        for (Vertex vertex : vertices) {
            if (vertex.Name().equals(source)) {
                sourceMap.putAll(vertex.Target());
                checkRep();
                return sourceMap;
            }
        }
        checkRep();
        return sourceMap;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Vertex<L> vertex : vertices) {
            str.append(vertex.toString());
            str.append("\n");
        }
        checkRep();
        return str.toString();
    }

}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex <L>{

    private final L vertex;
    private final Map<L, Integer> TargetEdge = new HashMap<>();


    // Abstraction function:
    // The weight from point vertex to point TargetEdge.KeySet is TargetEdge.values()
    // Representation invariant:
    // vertex.isEmpty is not true
    // Safety from rep exposure:
    // All fields are private;
    // vertex is String, so is guaranteed immutable;
    // TargetEdge is Map with final, so is guaranted immutable;
    // make defensive copies to avoid sharing the TargetEdge object with clients.

    // Abstraction function:
    // 由顶点的名字及其对应目标顶点的映射所对应的实际顶点表示
    // 即由String、Map组成的抽象数据类型对应的顶点
    // Representation invariant:
    // vertex不为空且TargetEdge中的weight>0
    // Safety from rep exposure:
    // 使用private、final修饰的变量
    // 防御性复制



    /**
     * Initialize the Vertex class.
     *
     * @param VertexName is a String.
     */
    public Vertex(final L VertexName) {
        this.vertex = VertexName;
        checkRep();
    }


    /**
     * Check that the rep invariant is true
     */
    private void checkRep() {
        if (!TargetEdge.isEmpty()) {
            for (L VertexName : TargetEdge.keySet()) {
                assert !this.vertex.equals(VertexName);
            }
            for (Integer VertexWeight:TargetEdge.values())
            {
                assert VertexWeight>0;
            }
        }
    }


    /**
     * determine the current two Vertex object, is the same name
     *
     * @param vertexAnother is an element of type vertex
     * @return
     */
    public boolean isEqualsVertex(Vertex vertexAnother) {
        checkRep();
        return this.vertex.equals(vertexAnother.vertex);
    }

    /**
     * Determine whether the name of the current Vertex object is the same as vertexAnother
     *
     * @param vertexAnother is the new Vertex name
     * @return
     */
    public boolean isEqualsName(L vertexAnother) {
        checkRep();
        return this.vertex.equals(vertexAnother);
    }


    /**
     * If VertexAnother exists in the targetEdge, and weight==0, remove this edge,
     * otherwise update the value of weight
     *
     * @param vertexAnother The name of the target vertex to be updated
     * @param weight        >=0
     */
    public void writeTarget(L vertexAnother, Integer weight) {
        for (Map.Entry<L, Integer> map : this.TargetEdge.entrySet()) {
            if (map.getKey().equals(vertexAnother)) {
                this.TargetEdge.remove(map.getKey());
                Map<L, Integer> mapNew = new HashMap<>();
                mapNew.put(vertexAnother, weight);
                this.TargetEdge.putAll(mapNew);
            }
        }
        if (weight != 0) {
            Map<L, Integer> mapNew = new HashMap<>();
            mapNew.put(vertexAnother, weight);
            this.TargetEdge.putAll(mapNew);
        }
        checkRep();
    }

    /**
     * Returns the name of the Vertex object
     *
     * @return Map of all target vertices
     */
    public L Name()
    {
        return this.vertex;
    }


    /**
     * Returns the target Vertex of the Vertex object
     *
     * @return Map of all target vertices
     */
    public Map<L, Integer> Target() {
        Map<L, Integer> map = new HashMap<>();
        for (Map.Entry<L, Integer> mapVertex : this.TargetEdge.entrySet()) {
            map.put(mapVertex.getKey(), mapVertex.getValue());
        }
        checkRep();
        return map;
    }

    /**
     * @param vertexAnother
     * @return 当前顶点到vertexAnother顶点的权值
     */
    public Integer weight(L vertexAnother) {
        for (Map.Entry<L, Integer> mapVertex : this.TargetEdge.entrySet()) {
            if (mapVertex.getKey().equals(vertexAnother)) {
                return mapVertex.getValue();
            }
        }
        return 0;
    }

    /**
     * 去掉该顶点的目标顶点
     * @param vertexTarget 待删除的顶点的目标顶点
     * @return
     */
    public boolean remove(L vertexTarget) {
        for (Map.Entry<L, Integer> mapVertex : TargetEdge.entrySet())
        {
            if (mapVertex.getKey().equals(vertexTarget)) {
                return TargetEdge.remove(mapVertex.getKey(), mapVertex.getValue());
            }
            continue;
        }
        return false;
    }


    /**
     * Represents the Vertex object as a string
     *
     * @return the representation of Vertex
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Vertex: "+vertex+" Edge:");
        for (Map.Entry<L, Integer> map : this.TargetEdge.entrySet()) {
            string.append(" ");
            string.append(vertex);
            string.append("->");
            string.append(map.getKey());
            string.append(":");
            string.append(map.getValue());
        }
        checkRep();
        return string.toString();
    }

}
