/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import graph.Graph;

/**
 * A graph-based poetry generator.
 *
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 *
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 *
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 *
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 *
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    // ???graph???????????????
    // Representation invariant:
    // graph?????????
    // Safety from rep exposure:
    // ???????????????
    // ??????private???final???????????????

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        //????????????
        String path = System.getProperty("user.dir") + "\\src\\P1\\poet\\" + corpus;
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String Line;
        while ((Line = bufferedReader.readLine()) != null) {
            stringBuilder.append(Line);
        }
        bufferedReader.close();
        //??????
        List<String> words = new LinkedList<>();
        words.addAll(Arrays.asList(stringBuilder.toString().toLowerCase().split(" ", 0)));
        words.removeAll(Collections.singleton(new String()));
        String vertexFront = null;
        String vertexRear = null;
        for (int i = 0; i < words.size() - 1; i++) {
            vertexFront = words.get(i);
            vertexRear = words.get(i + 1);
            //?????????????????????vertexFront???vertexRear??????
            if (graph.targets(vertexFront).containsKey(vertexRear)) {
                graph.set(vertexFront, vertexRear, graph.targets((vertexFront)).get(vertexRear) + 1);
            }
            //??????????????????
            else {
                graph.set(vertexFront, vertexRear, 1);
            }
        }
    }

    /**
     * ???????????????
     */
    private void checkRep() {
        assert !graph.vertices().isEmpty();
    }


    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String inputString = new String(input);
        List<String> inputWords = new LinkedList<>();
        inputWords.addAll(Arrays.asList(inputString.substring(0, input.length() - 1).split(" ")));
        // ???????????????????????????
        inputWords.removeAll(Collections.singleton(new String()));
        StringBuilder stringBuilder = new StringBuilder();
        String wordFront = null;
        String wordRear = null;
        int weightBiger = 0;
        int weight = 0;
        String vertexWeightBiger = new String();
        for (int i = 0; i < inputWords.size() - 1; i++) {
            wordFront = inputWords.get(i);
            wordRear = inputWords.get(i + 1);
            stringBuilder.append(wordFront + " ");
            for (String vertexName : graph.vertices()) {
                if (graph.targets(wordFront.toLowerCase()).containsKey(vertexName)) {
                    if (graph.targets(vertexName).containsKey(wordRear.toLowerCase())) {
                        weight = graph.targets(wordFront.toLowerCase()).get(vertexName) + graph.targets(vertexName).get(wordRear.toLowerCase());
                        if (weight > weightBiger) {
                            weightBiger = weight;
                            vertexWeightBiger = vertexName;
                        }
                    }
                }
            }
            if (!vertexWeightBiger.isEmpty()) {
                stringBuilder.append(vertexWeightBiger + " ");
            }
            weight = 0;
            weightBiger = 0;
            vertexWeightBiger = new String();
        }
        if (wordRear != null) {
            stringBuilder.append(wordRear + "!");
        }
        checkRep();
        return stringBuilder.toString();
    }

    public String toString() {
        return graph.toString();
    }

}
