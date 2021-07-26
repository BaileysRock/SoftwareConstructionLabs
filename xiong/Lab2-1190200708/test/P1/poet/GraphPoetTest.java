/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import graph.Graph;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    // Testing strategy
    //
    // Partition the inputs as follows:
    // whether the corpus is not exist
    // whether the corpus is a empty file
    // Whether the input has the same edge
    // whether the graph is empty
    // Exhaustive Cartesian coverage of partitions.

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }


    // Testing strategy for the constructor of GraphPoet
    // Testing strategy for GraphPoet.toString()
    // Partition the inputs as follows:
    // whether the corpus is not exist
    // whether the corpus is a empty file
    //
    // Exhaustive Cartesian coverage of partitions.

    // whether the corpus is not exist
    @Test(expected = IOException.class)
    public void testGraphPoetNotExistCorpus() throws IOException {
        final String fileName = "NotExist.txt";
        File file = new File(fileName);
        GraphPoet graphPoet = new GraphPoet(file);
    }

    // the corpus is exist
    // the corpus is a empty file
    @Test
    public void testGraphPoetEmptyFile() throws IOException {
        final String fileName = "EmptyFile.txt";
        File file = new File(fileName);
        GraphPoet graphPoet = new GraphPoet(file);
        assertTrue(graphPoet.toString().isEmpty());
    }

    // the corpus is exist
    // the corpus is not a empty file
    @Test
    public void testGraphPoetFile() throws IOException {
        final String fileName = "File1.txt";
        File file = new File(fileName);
        GraphPoet graphPoet = new GraphPoet(file);
        StringBuilder stringBuilder = new StringBuilder("Vertex: new Edge:  new->worldsto:1 new->life:1 new->civilizations:1\n");
        stringBuilder.append("Vertex: explore Edge:  explore->strange:1\n");
        stringBuilder.append("Vertex: worldsto Edge:  worldsto->seek:1\n");
        stringBuilder.append("Vertex: and Edge:  and->new:1\n");
        stringBuilder.append("Vertex: to Edge:  to->explore:1\n");
        stringBuilder.append("Vertex: civilizations Edge: \n");
        stringBuilder.append("Vertex: seek Edge:  seek->out:1\n");
        stringBuilder.append("Vertex: strange Edge:  strange->new:1\n");
        stringBuilder.append("Vertex: life Edge:  life->and:1\n");
        stringBuilder.append("Vertex: out Edge:  out->new:1\n");
        //System.out.println(graphPoet.toString());
        assertEquals(stringBuilder.toString(), graphPoet.toString());
    }


    // Testing strategy for GraphPoet.poem()
    //
    // Partition the inputs as follows:
    // Whether the input has the same edge
    //
    // Exhaustive Cartesian coverage of partitions.

    // Whether the input has the same edge
    @Test
    public void testPoemWithSameEdge() throws IOException {
        final String fileName = "FileHasSameEdge.txt";
        File file = new File(fileName);
        GraphPoet graphPoet = new GraphPoet(file);
        StringBuilder stringBuilder = new StringBuilder("Seek to explore interesting new civilizations and exciting synergies!");
        //System.out.println(graphPoet.poem(new String("Seek to explore new and exciting synergies!")));
        assertEquals(stringBuilder.toString(), graphPoet.poem(new String("Seek to explore new and exciting synergies!")));
    }

    // Whether the input has not the same edge
    @Test
    public void testPoemWithoutSameEdge() throws IOException {
        final String fileName = "FileHasNotSameEdge.txt";
        File file = new File(fileName);
        GraphPoet graphPoet = new GraphPoet(file);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Seek to explore strange new life and exciting synergies!");
        assertEquals(stringBuilder.toString(), graphPoet.poem(new String("Seek to explore new and exciting synergies!")));
    }


}
