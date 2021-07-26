/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.*;



/**
 * Tests for ConcreteVerticesGraph.
 * <p>
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * <p>
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {


    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */

    // Testing strategy for ConcreteVerticesGraph.add()
    //
    // Partition the inputs as follows:
    // 是否为空图： empty graph？ yes 、no
    // 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
    //
    // Exhaustive Cartesian coverage of partitions.


    // empty graph: no
    // the vertex is in the graph
    @Test
    public void testAddNotEmptyGraphWithVertex() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        graph.add(vertex1);
        assertTrue(graph.vertices().contains(vertex1));
        assertFalse(graph.add(vertex1));
    }

    // empty graph: yes
    // the vertex is not in the graph
    @Test
    public void testAddEmptyGraphWithoutVertex() {
        final Graph<String> graph = emptyInstance();
        assertTrue(graph.vertices().isEmpty());
        final String vertex1 = "vertex1";
        graph.add(vertex1);
        assertTrue(graph.vertices().contains(vertex1));
        assertFalse(graph.add(vertex1));
    }

    // empty graph: no
    // the vertex is not in the graph
    @Test
    public void testAddNotEmptyGraphWithoutVertex() {
        final Graph<String> graph = emptyInstance();
        assertTrue(graph.vertices().isEmpty());
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex1);
        assertTrue(graph.vertices().contains(vertex1));
        assertFalse(graph.vertices().contains(vertex2));
        graph.add(vertex2);
        assertTrue(graph.vertices().contains(vertex2));
    }


    // Testing strategy for ConcreteVerticesGraph.set()
    //
    // Partition the inputs as follows:
    // 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
    // value of weight？ =0、>0
    // 顶点是否存在目标顶点：whether vertex has target? yes 、no
    //
    // Exhaustive Cartesian coverage of partitions.


    // vertex is in the graph
    // value of weight > 0
    // vertex has target
    @Test
    public void testSetWithVertexHasWeightWithTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex1);
        graph.add(vertex2);
        assertEquals(0, graph.set(vertex1, vertex2, 3));
        assertEquals(3, graph.set(vertex1, vertex2, 3));
    }

    // vertex is in the graph
    // value of weight = 0
    // vertex has target
    @Test
    public void testSetWithVertexNoWeightWithTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex1);
        graph.add(vertex2);
        assertEquals(0, graph.set(vertex1, vertex2, 0));
    }

    // vertex is in the graph
    // value of weight > 0
    // vertex has not target
    @Test
    public void testSetWithVertexHasWeightWithoutTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex1);
        graph.set(vertex1, vertex2, 3);
        assertEquals(3, graph.set(vertex1, vertex2, 3));
        assertEquals(3, graph.set(vertex1, vertex2, 4));
        assertTrue(graph.vertices().contains(vertex2));
    }

    // vertex is in the graph
    // value of weight = 0
    // vertex has not target
    @Test
    public void testSetWithVertexNoWeightWithoutTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        graph.add(vertex1);
        final String vertex2 = "vertex2";
        assertFalse(graph.vertices().contains(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, 0));
        assertTrue(graph.vertices().contains(vertex2));
    }

    // vertex is not in the graph
    // value of weight > 0
    // vertex has target
    @Test
    public void testSetWithoutVertexHasWeightWithTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex2);
        assertTrue(graph.vertices().contains(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, 3));
        assertEquals(3, graph.set(vertex1, vertex2, 3));
        assertTrue(graph.vertices().contains(vertex1));
    }

    // vertex is not in the graph
    // value of weight = 0
    // vertex has target
    @Test
    public void testSetWithoutVertexNoWeightWithTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex2);
        assertTrue(graph.vertices().contains(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, 0));
        assertEquals(0, graph.set(vertex1, vertex2, 3));
        assertTrue(graph.vertices().contains(vertex1));
    }

    // vertex is not in the graph
    // value of weight > 0
    // vertex has not target
    @Test
    public void testSetWithoutVertexHasWeightWithoutTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        assertFalse(graph.vertices().contains(vertex1));
        assertFalse(graph.vertices().contains(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, 3));
        assertEquals(3, graph.set(vertex1, vertex2, 3));
        assertTrue(graph.vertices().contains(vertex1));
        assertTrue(graph.vertices().contains(vertex2));
    }

    // vertex is not in the graph
    // value of weight = 0
    // vertex has not target
    @Test
    public void testSetWithoutVertexNoWeightWithoutTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        assertFalse(graph.vertices().contains(vertex1));
        assertFalse(graph.vertices().contains(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, 0));
        assertEquals(0, graph.set(vertex1, vertex2, 0));
        assertTrue(graph.vertices().contains(vertex1));
        assertTrue(graph.vertices().contains(vertex2));
    }

    // Testing strategy for ConcreteVerticesGraph.remove()
    //
    // Partition the inputs as follows:
    // 是否为空图： empty graph？ yes 、no
    // 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // empty graph: yes
    // this vertex is in the graph: no
    @Test
    public void testRemoveEmptyGraphWithoutThisVertex() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        assertFalse(graph.remove(vertex1));
        graph.add(vertex1);
        assertTrue(graph.remove(vertex1));
    }

    // empty graph: no
    // this vertex is in the graph: no
    @Test
    public void testRemoveGraphWithoutThisVertex() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex2);
        assertFalse(graph.remove(vertex1));
        graph.add(vertex1);
        assertTrue(graph.remove(vertex1));
    }

    // empty graph: no
    // this vertex is in the graph: yes
    @Test
    public void testRemoveGraphWithThisVertex() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.set(vertex2,vertex1,10);
        assertTrue(graph.remove(vertex1));
        assertTrue(graph.remove(vertex2));
        assertFalse(graph.remove(vertex1));
        assertFalse(graph.remove(vertex2));

    }


    // Testing strategy for ConcreteVerticesGraph.vertices()
    //
    // Partition the inputs as follows:
    // 是否为空图： empty graph？ yes 、no
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // empty graph:yes
    @Test
    public void testVerticesEmptyGraph() {
        final Graph<String> graph = emptyInstance();
        assertEquals(Collections.emptySet(), graph.vertices());
    }

    // empty graph:no
    @Test
    public void testVerticesGraph() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        graph.add(vertex1);
        graph.add(vertex2);
        for (final String vertex : graph.vertices()) {
            assertTrue(vertex.equals(vertex1) || vertex.equals(vertex2));
        }
    }


    // Testing strategy for ConcreteVerticesGraph.sources()
    //
    // Partition the inputs as follows:
    // 是否为空图： empty graph？ yes 、no
    // 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
    // 顶点是否存在源顶点：whether vertex has source? yes 、no
    //
    // Exhaustive Cartesian coverage of partitions.
    //


    // empty graph:yes
    @Test
    public void testSourceEmptyGraph() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        assertEquals(Collections.emptyMap(), graph.sources(vertex1));
    }

    // vertex is in the graph
    // this vertex has source

    @Test
    public void testSourceWithVertexHasSource() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        final String vertex3 = "vertex3";
        final String vertex4 = "vertex4";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);
        graph.set(vertex2, vertex1, 3);
        graph.set(vertex3, vertex1, 4);
        graph.set(vertex4, vertex1, 5);
        final Map<String, Integer> map = new HashMap<>();
        map.put(vertex2, 3);
        map.put(vertex3, 4);
        map.put(vertex4, 5);
        assertEquals(map, graph.sources(vertex1));
    }

    // vertex is in the graph
    // this vertex has not source

    @Test
    public void testSourceWithVertexHasnotSource() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        final String vertex3 = "vertex3";
        final String vertex4 = "vertex4";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);
        graph.set(vertex1, vertex2, 3);
        graph.set(vertex1, vertex3, 4);
        graph.set(vertex1, vertex4, 5);
        assertEquals(Collections.emptyMap(), graph.sources(vertex1));
    }


    // Testing strategy for ConcreteVerticesGraph.targets()
    //
    // Partition the inputs as follows:
    // 是否为空图： empty graph？ yes 、no
    // 当前的顶点是否在图中：whether vertex is in the graph？ yes 、no
    // 顶点是否存在目标顶点：whether vertex has target? yes 、no
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // empty graph:yes
    @Test
    public void testTargetsEmpthGraph() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        assertEquals(Collections.emptyMap(), graph.targets(vertex1));
    }

    // vertex is in the graph
    // this vertex has not source
    @Test
    public void testTargetsHasVertexHasnotTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        final String vertex3 = "vertex3";
        final String vertex4 = "vertex4";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);
        graph.set(vertex2, vertex1, 3);
        graph.set(vertex3, vertex1, 4);
        graph.set(vertex4, vertex1, 5);
        assertEquals(Collections.emptyMap(), graph.targets(vertex1));

    }

    // vertex is in the graph
    // this vertex has source
    @Test
    public void testTargetsHasVertexHasTarget() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        final String vertex3 = "vertex3";
        final String vertex4 = "vertex4";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);
        graph.set(vertex1, vertex2, 3);
        graph.set(vertex1, vertex3, 4);
        graph.set(vertex1, vertex4, 5);
        final Map<String, Integer> map = new HashMap<>();
        map.put(vertex2, 3);
        map.put(vertex3, 4);
        map.put(vertex4, 5);
        assertEquals(map, graph.targets(vertex1));
    }


    // Testing strategy for ConcreteVerticesGraph.toString()
    //
    // Partition the inputs as follows:
    // empty graph: yes 、no
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // empty graph:yes
    @Test
    public void testToStringEmptyGraph() {
        final Graph<String> graph = emptyInstance();
        final String graphToString = new String();
        graph.toString();
        assertTrue(graphToString.equals(graph.toString()));
    }

    // empty graph:no
    @Test
    public void testToStringGraph() {
        final Graph<String> graph = emptyInstance();
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        final String vertex3 = "vertex3";
        final String vertex4 = "vertex4";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);
        graph.set(vertex1, vertex2, 1);
        graph.set(vertex1, vertex3, 5);
        graph.set(vertex2, vertex3, 2);
        graph.set(vertex3, vertex4, 3);
        graph.set(vertex4, vertex1, 4);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Vertex: vertex1 Edge: vertex1->vertex3:5 vertex1->vertex2:1\n");
        stringBuilder.append("Vertex: vertex2 Edge: vertex2->vertex3:2\n");
        stringBuilder.append("Vertex: vertex3 Edge: vertex3->vertex4:3\n");
        stringBuilder.append("Vertex: vertex4 Edge: vertex4->vertex1:4\n");
        assertEquals(stringBuilder.toString(), graph.toString());
        //System.out.println(graph.toString());
    }

    // TODO tests for ConcreteVerticesGraph.toString()

    /*
     * Testing Vertex...
     */


    // Testing strategy for Vertex.isEqualsVertex()
    //
    // Partition the inputs as follows:
    // whether vertexAnother is equal to Vertex
    //
    // Exhaustive Cartesian coverage of partitions.
    //


    // vertexAnother is equal to vertex
    @Test
    public void testIsEqualsVertexEqualAnother() {
        String vertexName = new String("vertex1");
        String vertexAnotherName = new String("vertex1");
        Vertex vertex = new Vertex(vertexName);
        Vertex vertexAnother = new Vertex(vertexAnotherName);
        assertTrue(vertex.isEqualsVertex(vertexAnother));
    }
    // vertexAnother is not equal to vertex
    @Test
    public void testIsEqualsVertexNotEqualAnother() {
        String vertexName = new String("vertex1");
        String vertexAnotherName = new String("vertex2");
        Vertex vertex = new Vertex(vertexName);
        Vertex vertexAnother = new Vertex(vertexAnotherName);
        assertFalse(vertex.isEqualsVertex(vertexAnother));
    }



    // vertex: null
    // vertexAnother:not null
    @Test
    public void testIsEqualsVertexNullVertexAnother() {
        String vertexName = new String();
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = new String("vertexAnother");
        Vertex vertexAnother = new Vertex(vertexAnotherName);
        assertFalse(vertex.isEqualsVertex(vertexAnother));
    }

    // vertex: not null
    // vertexAnother:not null
    @Test
    public void testIsEqualsVertexVertexAnother() {
        String vertexName = new String("vertex");
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = new String("vertexAnother");
        Vertex vertexAnother = new Vertex(vertexAnotherName);
        vertex.isEqualsVertex(vertexAnother);
        assertFalse(vertex.isEqualsVertex(vertexAnother));
    }


    // Testing strategy for Vertex.isEqualsName()
    //
    // Partition the inputs as follows:
    // whether vertexAnother's name is equal to Vertex
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    //vertexAnother's name is equal to Vertex
    @Test
    public void testIsEqualsNameVertexAnotherNull() {
        String vertexName = new String("vertex1");
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = new String("vertex1");
        assertTrue(vertex.isEqualsName(vertexAnotherName));
    }

    //vertexAnother's name is not equal to Vertex
    @Test(expected = AssertionError.class)
    public void testIsEqualsNameNullVertexAnother() {
        String vertexName = new String("vertex1");
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = new String("vertex2");
        assertTrue(vertex.isEqualsName(vertexAnotherName));
    }



    // Testing strategy for Vertex.writeTarget()
    //
    // Partition the inputs as follows:
    // whether vertexAnother is in the TargetEdge
    // weight:>0 =0
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // vertexAnother is in the TargetEdge
    // weight = 0
    @Test
    public void testWriteTargetInGraphNoWeight()
    {
        String vertexName = "vertex1";
        String vertexAnother = "vertex2";
        Vertex vertex = new Vertex(vertexName);
        vertex.writeTarget(vertexAnother,0);
        assertTrue(vertex.Target().isEmpty());
    }
    // vertexAnother is not in the TargetEdge
    // weight = 0
    @Test
    public void testWriteTargetNotInGraphNoWeight()
    {
        String vertexName = "vertex1";
        String vertexAnother = "vertex2";
        Vertex vertex = new Vertex(vertexName);
        vertex.writeTarget(vertexAnother,0);
        assertTrue(vertex.Target().isEmpty());
    }
    // vertexAnother is in the TargetEdge
    // weight > 0
    @Test
    public void testWriteTargetInGraphWeight()
    {
        String vertexName = "vertex1";
        String vertexAnother = "vertex2";
        Vertex vertex = new Vertex(vertexName);
        vertex.writeTarget(vertexAnother,10);
        assertFalse(vertex.Target().isEmpty());
        assertTrue(vertex.Target().values().contains(10));
        assertTrue(vertex.Target().keySet().contains(vertexAnother));
    }
    // vertexAnother is not in the TargetEdge
    // weight > 0
    @Test
    public void testWriteTargetNotInGraphWeight()
    {
        String vertexName = "vertex1";
        String vertexAnother = "vertex2";
        Vertex vertex = new Vertex(vertexName);
        assertTrue(vertex.Target().isEmpty());
        vertex.writeTarget(vertexAnother,10);
        assertTrue(vertex.Target().values().contains(10));
        assertTrue(vertex.Target().keySet().contains(vertexAnother));
    }






    // Testing strategy for Vertex.Name()
    //
    // whether Vertex is null
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // vertex is not null
    @Test
    public void testNameNotNull()
    {
        String vertexName = "vertex1";
        Vertex vertex = new Vertex(vertexName);
        assertTrue(vertex.Name().equals(vertexName));
    }
    // vertex is null
    @Test(expected = NullPointerException.class)
    public void testNameNull()
    {
        String vertexName = null;
        Vertex vertex = new Vertex(vertexName);
        assertTrue(vertex.Name().equals(vertexName));
    }


    // Testing strategy for Vertex.Target()
    //
    // Partition the inputs as follows:
    // wherger TargetEdge is null
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // TargetEdge is null
    @Test
    public void testTargetEdgeNull(){
        String vertexName = "vertex1";
        Vertex vertex = new Vertex(vertexName);
        assertEquals(Collections.emptyMap(),vertex.Target());
    }
    // TargetEdge is not null
    @Test
    public void testTargetEdgeNotNull()
    {
        String vertexName = "vertex1";
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = "vertex2";
        vertex.writeTarget(vertexAnotherName,10);
        //assertEquals(Collections.emptyMap(),vertexMap);
    }

    // Testing strategy for Vertex.weight()
    //
    // Partition the inputs as follows:
    // whether vertexAnother is in the TargetEdge
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // vertexAnother is in the TargetEdge
    @Test
    public void testWeightinTargetEdge(){
        String vertexName = "vertex1";
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = "vertex2";
        vertex.writeTarget(vertexAnotherName,10);
        assertEquals((Integer) 10,vertex.weight(vertexAnotherName));
    }
    // vertexAnother is not in the TargetEdge
    @Test
    public void testWeightNotinTargetEdge(){
        String vertexName = "vertex1";
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = "vertex2";
        assertEquals((Integer) 0,vertex.weight(vertexAnotherName));
    }

    // Testing strategy for Vertex.remove()
    //
    // Partition the inputs as follows:
    // vertexTarget is in the TargetEdge or not
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // vertexTarget is in the TargetEdge
    @Test
    public void testRemoveHasVertexTarget()
    {
        String vertexName = "vertex1";
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = "vertex2";
        vertex.writeTarget(vertexAnotherName,10);
        assertTrue(vertex.remove(vertexAnotherName));
        assertFalse(vertex.Target().containsKey(vertexAnotherName));

    }
    // vertexTarget is not in the TargetEdge
    @Test
    public void testRemoveHasNotVertexTarget()
    {
        String vertexName = "vertex1";
        Vertex vertex = new Vertex(vertexName);
        String vertexAnotherName = "vertex2";
        Vertex vertexAnother = new Vertex(vertexAnotherName);
        assertFalse(vertex.remove(vertexAnotherName));
    }



    // Testing strategy for Vertex.toString()
    //
    // Partition the inputs as follows:
    // vertex is null or not
    //
    // Exhaustive Cartesian coverage of partitions.
    //

    // vertex is not null
    @Test
    public void testToString()
    {
        String vertexName = "vertex1";
        Vertex vertex = new Vertex(vertexName);
        String vertex2 = "vertex2";
        vertex.writeTarget(vertex2,10);
        String vertex3 = "vertex3";
        vertex.writeTarget(vertex3,5);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Vertex: vertex1 Edge: vertex1->vertex3:5 vertex1->vertex2:10");
        assertEquals(stringBuilder.toString(),vertex.toString());
    }

}
