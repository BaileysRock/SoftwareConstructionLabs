/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Assert;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    

    /*
     * Testing strategy:
     *
     * Partition the inputs as follows:
     * 是否为空图： empty graph？ yes 、no
     * 当前的边是否在图中：whether edge is in the graph？ yes 、no
     * 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
     * value of weight？ =0、>0
     * 顶点是否存在源顶点：whether vertex has source? yes 、no
     * 顶点是否存在目标顶点：whether vertex has target? yes 、no
     *
     * Exhaustive Cartesian coverage of partitions.
     */

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     * 获取新的空图
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    
    // TODO other tests for instance methods of Graph
    /*
     * Testing strategy:
     *
     * Partition the inputs as follows:
     * 是否为空图： empty graph: yes 、no
     * 当前的顶点是否在图中：whether vertex is in the graph: yes 、no
     * value of weight: =0、>0
     *
     * Exhaustive Cartesian coverage of partitions.
     */

    // 顶点不在图中，the vertex is not in the graph
    // empty graph: yes
    // value of weight = 0
    @Test
    public void testAddnoVertex(){
        final String vertex = "v1";
        final Graph<String> graph = emptyInstance();
        assertTrue(graph.add(vertex));
        assertThat(graph.vertices(),hasItem(vertex));
        assertFalse(graph.add(vertex));
    }

    // 顶点在图中，the vertex is in the graph
    // empty graph: no
    // value of weight = 0
    @Test
    public void testAddhasVertex(){
        final String vertex = "v1";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex);
        assertFalse(graph.add(vertex));
        assertThat(graph.vertices(),hasItem(vertex));
    }

    /*
     * Testing strategy:
     *
     * Partition the inputs as follows:
     * 是否为空图： empty graph？ yes 、no
     * 当前的边是否在图中：whether edge is in the graph？ yes 、no
     * 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
     * value of weight？ =0、>0
     *
     * Exhaustive Cartesian coverage of partitions.
     */

    // 顶点在图中，the vertex is in the graph
    // 边不在图中，the edge is not in the graph
    // empty graph: no
    // value of weight = 0
    @Test
    public void testSetnoWeightnoEdgehasVertex(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        assertThat(graph.vertices(),hasItem(vertex1));
        assertThat(graph.vertices(),hasItem(vertex2));
        assertEquals(0,graph.set(vertex1,vertex2,0));
    }

    // 顶点不在图中，the vertex is not in the graph
    // 边不在图中，the edge is not in the graph
    // empty graph: yes
    // value of weight = 0
    @Test
    public void testSetnoWeightnoEdgenoVertex(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final Graph<String> graph = emptyInstance();
        assertTrue(graph.vertices().isEmpty());
        graph.add(vertex1);
        graph.add(vertex2);
        assertThat(graph.vertices(),hasItem(vertex1));
        assertThat(graph.vertices(),hasItem(vertex2));
        assertEquals(0,graph.set(vertex1,vertex2,0));
    }

    // 顶点在图中，the vertex is in the graph
    // 边在图中，the edge is in the graph
    // empty graph: no
    // value of weight = 0
    @Test
    public void testSetnoWeighthasEdgehasVertex(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.set(vertex1,vertex2,3);
        assertThat(graph.vertices(),hasItem(vertex1));
        assertThat(graph.vertices(),hasItem(vertex2));
        assertEquals(3,graph.set(vertex1,vertex2,0));
        assertThat(graph.vertices(),hasItem(vertex1));
        assertThat(graph.vertices(),hasItem(vertex2));

    }

    // 顶点在图中，the vertex is in the graph
    // 边在图中，the edge is in the graph
    // empty graph: no
    // value of weight != 0
    @Test
    public void testSethasWeighthasEdgehasVertex(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.set(vertex1,vertex2,3);
        assertEquals(3,graph.set(vertex1,vertex2,1));
        assertThat(graph.vertices(),hasItem(vertex1));
        assertThat(graph.vertices(),hasItem(vertex2));
    }

    // 顶点在图中，the vertex is in the graph
    // 边不在图中，the edge is not in the graph
    // empty graph: no
    // value of weight != 0
    @Test
    public void testSethasWeightnoEdgehasVertex(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        assertThat(graph.vertices(),hasItem(vertex1));
        assertThat(graph.vertices(),hasItem(vertex2));
        assertEquals(0,graph.set(vertex1,vertex2,3));
        assertThat(graph.vertices(),hasItem(vertex1));
        assertThat(graph.vertices(),hasItem(vertex2));
    }



    // 顶点不在图中，the vertex is not in the graph
    // 边不在图中，the edge is not in the graph
    // empty graph: yes
    // value of weight != 0
    @Test
    public void testSethasWeightnoEdgenoVertex(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final Graph<String> graph = emptyInstance();
        assertTrue(graph.vertices().isEmpty());
        graph.add(vertex1);
        graph.add(vertex2);
        assertThat(graph.vertices(),hasItem(vertex1));
        assertThat(graph.vertices(),hasItem(vertex2));
        assertEquals(0,graph.set(vertex1,vertex2,3));
    }


    /*
     * Testing strategy:
     *
     * Partition the inputs as follows:
     * 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
     *
     * Exhaustive Cartesian coverage of partitions.
     */

    // 顶点在图中，the vertex is in the graph
    @Test
    public void testRemovehasVertex(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        assertTrue(graph.remove(vertex1));
        assertFalse(graph.vertices().contains(vertex1));

    }

    // 顶点不在图中，the vertex is not in the graph
    @Test
    public void testRemovenoVertex(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        assertFalse(graph.remove(vertex2));
        assertTrue(graph.remove(vertex1));
        assertEquals(Collections.emptySet(),graph.vertices());
    }



    /*
     * Testing strategy:
     *
     * Partition the inputs as follows:
     * 是否为空图： empty graph？ yes 、no
     *
     * Exhaustive Cartesian coverage of partitions.
     */

    // empty graph: yes
    @Test
    public void testVerticesemptyGraph(){
        final Graph<String> graph = emptyInstance();
        assertEquals(Collections.emptySet(),graph.vertices());
    }

    // empty graph: no
    @Test
    public void testVerticesGraph(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final String vertex3 = "v3";
        final Graph<String> graph = emptyInstance();
        assertEquals(Collections.emptySet(),graph.vertices());
        graph.add(vertex1);
        assertThat(graph.vertices(),hasItem(vertex1));
        graph.add(vertex2);
        assertThat(graph.vertices(),hasItem(vertex2));
        graph.add(vertex3);
        assertThat(graph.vertices(),hasItem(vertex3));

    }


    /*
     * Testing strategy:
     *
     * Partition the inputs as follows:
     * 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
     * 顶点是否存在源顶点：whether vertex has source? yes 、no
     *
     * Exhaustive Cartesian coverage of partitions.
     */

    // 顶点在图中，the vertex is in the graph
    // 顶点存在源顶点，vertex has source
    @Test
    public void testSourcehasVerticeshasSource(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final String vertex3 = "v3";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex1,vertex2,2);
        graph.set(vertex3,vertex2,3);
        Integer a =2;
        Integer b =3;
        assertEquals(a,graph.sources(vertex2).get(vertex1));
        assertEquals(b,graph.sources(vertex2).get(vertex3));
    }

    // 顶点在图中，the vertex is in the graph
    // 顶点不存在源顶点，vertex has not source
    @Test
    public void testSourcehasVerticesnoSource(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final String vertex3 = "v3";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        assertEquals(Collections.emptyMap(),graph.sources(vertex1));
    }

    // 顶点不在图中，the vertex is not in the graph
    @Test
    public void testSourcenoVertices(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final String vertex3 = "v3";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        assertEquals(Collections.emptyMap(),graph.sources(vertex2));
    }

    /*
     * Testing strategy:
     *
     * Partition the inputs as follows:
     * 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
     * 顶点是否存在目标顶点：whether vertex has target? yes 、no
     *
     * Exhaustive Cartesian coverage of partitions.
     */
    // 顶点在图中，the vertex is in the graph
    // 顶点存在目标顶点，the vertex has target
    @Test
    public void testTargetshasVerticeshasTarget(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final String vertex3 = "v3";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex1,vertex2,2);
        graph.set(vertex1,vertex3,3);
        Integer a = 2;
        Integer b = 3;
        assertEquals(a,graph.targets(vertex1).get(vertex2));
        assertEquals(b,graph.targets(vertex1).get(vertex3));

    }

    // 顶点在图中，the vertex is in the graph
    // 顶点不存在目标顶点，the vertex has not target
    @Test
    public void testTargetshasVerticesnoTarget(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final String vertex3 = "v3";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex1,vertex2,2);
        graph.set(vertex1,vertex3,3);
        Integer a = 2;
        Integer b = 3;
        assertEquals(Collections.emptyMap(),graph.targets(vertex2));
        assertEquals(Collections.emptyMap(),graph.targets(vertex3));
    }

    // 顶点不在图中，the vertex is in the graph
    @Test
    public void testTargetsnoVertices(){
        final String vertex1 = "v1";
        final String vertex2 = "v2";
        final String vertex3 = "v3";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        assertEquals(Collections.emptyMap(),graph.targets(vertex2));

    }

}
