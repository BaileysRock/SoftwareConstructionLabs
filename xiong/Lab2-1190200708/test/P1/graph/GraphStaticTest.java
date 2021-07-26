/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * <p>
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {

    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }


    // TODO test other vertex label types in Problem 3.2

    // test vertex label is Integer
    @Test
    public void testVertexLabelInteger() {
        final Graph<Integer> graph = Graph.empty();
        assertTrue(graph.add(0));
        assertTrue(graph.add(1));
        assertTrue(graph.add(2));
        assertEquals(0, graph.set(0, 1, 1));
        assertEquals(0, graph.set(1, 2, 2));
        assertFalse(graph.remove(3));
        graph.set(0, 1, 1);
        Set<Integer> set = new HashSet<Integer>();
        set.add(0);
        set.add(1);
        set.add(2);
        assertTrue(set.equals(graph.vertices()));
        Map<Integer, Integer> mapTarget = new HashMap<>();
        mapTarget.put(1, 1);
        assertTrue(mapTarget.equals(graph.targets(0)));
        Map<Integer, Integer> mapSource = new HashMap<>();
        mapSource.put(0, 1);
        assertTrue(mapSource.equals(graph.sources(1)));
        StringBuilder stringBuilder = new StringBuilder("Vertex: 0 Edge:  0->1:1\n");
        stringBuilder.append("Vertex: 1 Edge:  1->2:2\n");
        stringBuilder.append("Vertex: 2 Edge: "+"\n");
        assertEquals(stringBuilder.toString(),graph.toString());
        //System.out.println(graph.toString());
    }


    // test vertex label is Character
    @Test
    public void testVertexLabelCharacter() {
        final Graph<Character> graph = Graph.empty();
        assertTrue(graph.add('a'));
        assertTrue(graph.add('b'));
        assertTrue(graph.add('c'));
        assertEquals(0, graph.set('a', 'b', 1));
        assertEquals(0, graph.set('b', 'c', 2));
        assertFalse(graph.remove('d'));
        Set<Character> set = new HashSet<Character>();
        set.add('a');
        set.add('b');
        set.add('c');
        assertTrue(set.equals(graph.vertices()));
        Map<Character, Integer> mapTarget = new HashMap<>();
        mapTarget.put('b', 1);
        assertTrue(mapTarget.equals(graph.targets('a')));
        Map<Character, Integer> mapSource = new HashMap<>();
        mapSource.put('a', 1);
        assertTrue(mapSource.equals(graph.sources('b')));
        StringBuilder stringBuilder = new StringBuilder("Vertex: a Edge:  a->b:1\n");
        stringBuilder.append("Vertex: b Edge:  b->c:2\n");
        stringBuilder.append("Vertex: c Edge: " + "\n");
        assertEquals(stringBuilder.toString(), graph.toString());


    }

}
