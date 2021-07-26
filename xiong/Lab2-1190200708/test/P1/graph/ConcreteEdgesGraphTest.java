/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.text.CollationElementIterator;
import java.util.*;

/**
 * Tests for ConcreteEdgesGraph.
 * <p>
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * <p>
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    // Testing strategy for ConcreteEdgesGraph.add()
    //
    // Partition the inputs as follows:
    // whether the vertex is in the vertices
    //
    // Exhaustive Cartesian coverage of partitions.

    // the vertex is in the vertices
    @Test
    public void testAddWithVertex() {
        Graph<String> graph = emptyInstance();
        String vertex = "vertex1";
        graph.add(vertex);
        assertFalse(graph.add(vertex));
    }

    // the vertex is not in the vertices
    @Test
    public void testAddWithoutVertex() {
        Graph<String> graph = emptyInstance();
        String vertex = "vertex1";
        assertTrue(graph.add(vertex));
    }


    // Testing strategy for ConcreteEdgesGraph.set()
    //
    // Partition the inputs as follows:
    // whether the edge is in the graph
    // weight : >0 =0
    //
    //
    // Exhaustive Cartesian coverage of partitions.

    // the edge is in the graph
    // weight ==0
    @Test
    public void testSetHasEdgeNoWeight() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        graph.set(vertex1, vertex2, 3);
        assertEquals(3, graph.set(vertex1, vertex2, 0));
    }

    // the edge is in the graph
    // weight >0
    @Test
    public void testSetHasEdgeWeight() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        graph.set(vertex1, vertex2, 3);
        assertEquals(3, graph.set(vertex1, vertex2, 4));
        assertEquals(4, graph.set(vertex1, vertex2, 3));
    }

    // the edge is not in the graph
    // weight ==0
    @Test
    public void testSetHasnotEdgeNoWeight() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        assertEquals(0, graph.set(vertex1, vertex2, 0));
        assertEquals(0, graph.set(vertex1, vertex2, 0));
    }

    // the edge is not in the graph
    // weight >0
    @Test
    public void testSetHasnotEdgeWeight() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        assertEquals(0, graph.set(vertex1, vertex2, 3));
        assertEquals(3, graph.set(vertex1, vertex2, 4));
    }


    // Testing strategy for ConcreteEdgesGraph.remove()
    //
    // Partition the inputs as follows:
    // whether the vertex is in the graph
    //
    //
    // Exhaustive Cartesian coverage of partitions.

    // the vertex is in the graph
    @Test
    public void testRemoveHasVertex() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        graph.set(vertex1, vertex2, 3);
        assertTrue(graph.remove(vertex1));
        assertFalse(graph.remove(vertex1));
    }

    // the vertex is not in the graph
    @Test
    public void testRemoveHasNotVertex() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        String vertex3 = "vertex3";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.set(vertex1, vertex2, 3);
        assertFalse(graph.remove(vertex3));
    }

    // Testing strategy for ConcreteEdgesGraph.vertices()
    //
    // Partition the inputs as follows:
    // whether the graph is empty
    //
    //
    // Exhaustive Cartesian coverage of partitions.

    //the graph is empty
    @Test
    public void testVerticesEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertEquals(Collections.emptySet(), graph.vertices());
    }

    //the graph is not empty
    @Test
    public void testVerticesGraph() {
        Set<String> verticesSet = new HashSet<>();
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        String vertex3 = "vertex3";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        verticesSet.add(vertex1);
        verticesSet.add(vertex2);
        verticesSet.add(vertex3);
        assertTrue(verticesSet.equals(graph.vertices()));
    }

    // Testing strategy for ConcreteEdgesGraph.sources()
    //
    // Partition the inputs as follows:
    // whether the target has sources
    //
    //
    // Exhaustive Cartesian coverage of partitions.

    // the target has sources
    @Test
    public void testSourcesHasSources() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        String vertex3 = "vertex3";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex2, vertex1, 3);
        graph.set(vertex3, vertex1, 4);
        Map<String, Integer> map = new HashMap<>();
        map.put(vertex2, 3);
        map.put(vertex3, 4);
        assertTrue(map.equals(graph.sources(vertex1)));
    }

    // the target has not sources
    @Test
    public void testSourcesHasNotSources() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        String vertex3 = "vertex3";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex1, vertex2, 3);
        graph.set(vertex1, vertex3, 4);
        assertTrue(Collections.emptyMap().equals(graph.sources(vertex1)));
    }

    // Testing strategy for ConcreteEdgesGraph.targets()
    //
    // Partition the inputs as follows:
    // whether the target has targets
    //
    //
    // Exhaustive Cartesian coverage of partitions.

    // the target has targets
    @Test
    public void testSourcesHasTargets() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        String vertex3 = "vertex3";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex1, vertex2, 3);
        graph.set(vertex1, vertex3, 4);
        Map<String, Integer> map = new HashMap<>();
        map.put(vertex2, 3);
        map.put(vertex3, 4);
        assertTrue(map.equals(graph.targets(vertex1)));
    }

    // the target has not targets
    @Test
    public void testSourcesHasNotTargets() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        String vertex3 = "vertex3";
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex2, vertex1, 3);
        graph.set(vertex3, vertex1, 4);
        assertTrue(Collections.emptyMap().equals(graph.targets(vertex1)));
    }

    // Testing strategy for ConcreteEdgesGraph.toString()
    //
    // Partition the inputs as follows:
    // whether the graph is empty
    //
    // Exhaustive Cartesian coverage of partitions.

    // the graph is not empty
    @Test
    public void testToStringNotEmptyGraph() {
        Graph<String> graph = emptyInstance();
        String vertex1 = "vertex1";
        String vertex2 = "veretx2";
        String vertex3 = "vertex3";
        graph.set(vertex1, vertex2, 3);
        graph.set(vertex1, vertex3, 4);
        graph.set(vertex2, vertex3, 5);
        graph.set(vertex3, vertex2, 6);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Vertex: veretx2 Edge:  veretx2->vertex3:5\n");
        stringBuilder.append("Vertex: vertex1 Edge:  vertex1->veretx2:3 vertex1->vertex3:4\n");
        stringBuilder.append("Vertex: vertex3 Edge:  vertex3->veretx2:6"+"\n");

        assertTrue(stringBuilder.toString().equals(graph.toString()));
        //System.out.println(graph.toString());
    }

    // the graph is empty
    @Test
    public void testToStringEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.toString().isEmpty());
    }



    /*
     * Testing Edge...
     */


    // Testing strategy for Edge.getWeight()
    //
    // Partition the inputs as follows:
    // Whether the weght is null
    //
    // Exhaustive Cartesian coverage of partitions.

    //weight is null
    @Test(expected = NullPointerException.class)
    public void testGetWeightNoWeight() {
        Integer weight = null;
        String source = null;
        String target = null;
        Edge edge = new Edge(source, target, weight);
        edge.getWeight();
    }

    //weight is not null
    @Test
    public void testGetWeightWeight() {
        Integer weight = 3;
        String source = "source";
        String target = "target";
        Edge edge = new Edge(source, target, weight);
        assertEquals((Integer) 3, edge.getWeight());
    }


    // Testing strategy for Edge.getSource()
    //
    // Partition the inputs as follows:
    // Whether the source is null
    //
    // Exhaustive Cartesian coverage of partitions.

    //source is null
    @Test(expected = NullPointerException.class)
    public void testGetSourceNoSource() {
        Integer weight = null;
        String source = null;
        String target = null;
        Edge edge = new Edge(source, target, weight);
        edge.getSource();
    }

    //source is not null
    @Test
    public void testGetSourceSource() {
        Integer weight = 3;
        String source = "source";
        String target = "target";
        Edge edge = new Edge(source, target, weight);
        assertEquals(source, edge.getSource());
    }


    // Testing strategy for Edge.getTarget()
    //
    // Partition the inputs as follows:
    // Whether the target is null
    //
    // Exhaustive Cartesian coverage of partitions.

    //target is null
    @Test(expected = NullPointerException.class)
    public void testGetTargetNoTarget() {
        Integer weight = null;
        String source = null;
        String target = null;
        Edge edge = new Edge(source, target, weight);
        edge.getTarget();
    }

    //target is not null
    @Test
    public void testGetTargetTarget() {
        Integer weight = 3;
        String source = "source";
        String target = "target";
        Edge edge = new Edge(source, target, weight);
        assertEquals(target, edge.getTarget());
    }


    // Testing strategy for Edge.toString()
    //
    // Partition the inputs as follows:
    // Whether the Edge is empty
    //
    // Exhaustive Cartesian coverage of partitions.

    // the edge is not null
    @Test
    public void testToStringEdge() {
        Integer weight = 3;
        String source = "source";
        String target = "target";
        Edge edge = new Edge(source, target, weight);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" " + source + "->" + target + ":" + weight);
        assertTrue(stringBuilder.toString().equals(edge.toString()));
    }

    // the edge is null
    @Test(expected = NullPointerException.class)
    public void testToStringEmptyEdge() {
        Integer weight = null;
        String source = null;
        String target = null;
        Edge edge = new Edge(source, target, weight);
    }
}
