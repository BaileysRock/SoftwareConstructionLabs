/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    // 由顶点为vertices、边为edges组成的图
    // 即从vertices、edges到有向图的映射
    // Representation invariant:
    // edges.getWeight>0,且edges中不存在两条相同的边
    // vertices中的顶点不为空
    // Safety from rep exposure:
    // 使用private和final修饰变量


    /**
     * 构造函数，构造空图
     */
    public ConcreteEdgesGraph()
    {
        this.edges.clear();
        this.vertices.clear();
        checkRep();
    }


    /**
     * 检查不变量
     */
    private void checkRep()
    {
        for(Edge<L> edge1:edges)
        {
            assert edge1.getWeight()>=0;
            for(Edge<L> edge2:edges)
            {
                if(edge1.getSource().equals(edge2.getSource()))
                {
                    if(edge1.getTarget().equals(edge2.getTarget()))
                    {
                        assert edges.indexOf(edge1)==edges.indexOf(edge2);
                    }
                }
            }
        }
        for(L vertex:vertices)
        {
            assert vertex!=null;
        }
    }
    
    @Override
    public boolean add(L vertex) {
        return vertices.add(vertex);
    }
    
    @Override public int set(L source, L target, int weight) {
        //边在图中的情况
        for(Edge<L> edge:edges)
        {
            if(edge.getSource().equals(source) &&edge.getTarget().equals(target))
            {

                    Edge<L> edgeAdd = new Edge(source,target,weight);
                    edges.remove(edge);
                    edges.add(edgeAdd);
                    checkRep();
                    return edge.getWeight();

            }
        }
        //边不在图中的情况
        vertices.add(source);
        vertices.add(target);
        if(weight!=0)
        {
            Edge<L> edge = new Edge(source,target,weight);
            edges.add(edge);
        }

        checkRep();
        return 0;

    }
    
    @Override public boolean remove(L vertex) {
        return vertices.remove(vertex);
    }
    
    @Override public Set<L> vertices() {
        Set<L> verticesSet = new HashSet<>();
        for(L vertex:vertices)
        {
            verticesSet.add(vertex);
        }
        checkRep();
        return verticesSet;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer> sourceMap = new HashMap<>();
        for(Edge<L> edge:edges)
        {
            if(edge.getTarget().equals(target))
            {
                sourceMap.put(edge.getSource(),edge.getWeight());

            }
        }
        checkRep();
        return sourceMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L,Integer> targetMap = new HashMap<>();
        for(Edge<L> edge:edges)
        {
            if(edge.getSource().equals(source))
            {
                targetMap.put(edge.getTarget(),edge.getWeight());
            }
        }
        checkRep();
        return targetMap;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for(L vertex:this.vertices)
        {
            stringBuilder.append("Vertex: "+vertex+" Edge: ");
            for(Edge<L> edge:edges)
            {
                if(edge.getSource().equals(vertex))
                {
                    stringBuilder.append(edge.toString());
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
    
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {

    private final L source;
    private final L target;
    private final Integer weight;

    // Abstraction function:
    // 从source到target，且权值为weight的有向边
    // Representation invariant:
    // weight>0,且source!=null,target!=null
    // Safety from rep exposure:
    // 使用private和final修饰变量，且使用变量的类型均为不可变类型。
    // 避免从外部直接修改的风险。


    /**
     * 构造函数
     * @param sourceConstruct 起点
     * @param targetConstruct 终点
     * @param weightConstruct 权值
     */
    public Edge(final L sourceConstruct,final L targetConstruct,final int weightConstruct)
    {
        this.source = sourceConstruct;
        this.target = targetConstruct;
        this.weight = weightConstruct;
        checkRep();
    }


    /**
     * 检查不变量
     */
    private void checkRep()
    {
        assert this.weight>=0;
        assert this.target!=null;
        assert this.source!=null;
        //自环
        //assert !this.source.equals(this.target)
    }


    /**
     * @return 返回该边的权值
     */
    public Integer getWeight()
    {
        return new Integer(this.weight);
    }

    /**
     * @return 该边的源顶点
     */
    public L getSource()
    {
        return this.source;
    }

    /**
     * @return 该边的目标顶点
     */
    public L getTarget()
    {
        return this.target;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder(" ");
        stringBuilder.append(this.source+"->");
        stringBuilder.append(""+this.target);
        stringBuilder.append(":"+this.weight);
        return stringBuilder.toString();
    }

    
}
