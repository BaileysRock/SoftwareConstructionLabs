import org.junit.Test;

import javax.xml.ws.WebEndpoint;

import static org.junit.Assert.*;

public class FriendshipGraphTest {

    // Testing strategy for GraphPoet.addVertex()
    //
    // Partition the inputs as follows:
    // Whether the grapg is empty
    //
    // Exhaustive Cartesian coverage of partitions.

    // Whether the input has the same edge

    @Test
    public void testAddVertex() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        assertTrue(graph.addVertex(rachel));
        assertTrue(graph.addVertex(ross));
        assertTrue(graph.addVertex(ben));
        assertTrue(graph.addVertex(kramer));
        assertFalse(graph.addVertex(kramer));
        assertFalse(graph.addVertex(ben));
    }


    // Testing strategy for GraphPoet.addVertex()
    //
    // Partition the inputs as follows:
    // Whether the edge is in the graph
    //
    // Exhaustive Cartesian coverage of partitions.

    // the edge is in the graph
    @Test
    public void addEdgeInTheGraph() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addEdge(rachel, ross);
        graph.addEdge(ben, kramer);
        assertFalse(graph.addEdge(rachel, ross));
        assertFalse(graph.addEdge(ben, kramer));
    }
    // the edge is not in the graph
    @Test
    public void addEdgeNotInTheGraph() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        assertTrue(graph.addEdge(rachel, ross));
        assertTrue(graph.addEdge(ben, kramer));

    }

    // Testing strategy for GraphPoet.getDistance()
    //
    // Partition the inputs as follows:
    // Is it possible to go from A to B
    //
    // Exhaustive Cartesian coverage of partitions.

    // A can get to B
    @Test
    public void getDistancePerson1ToPerson2() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        Person a = new Person("A");
        Person b = new Person("B");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addEdge(rachel, kramer);
        graph.addEdge(ross, rachel);
        graph.addEdge(kramer, a);
        graph.addEdge(a,kramer);
        graph.addEdge(ross,ben );
        graph.addEdge(ben,ross);
        graph.addEdge(ben,b);
        graph.addEdge(b,a);
        graph.addEdge(b,ben);
        graph.addEdge(a,b);
        assertEquals(2,graph.getDistance(ben,a));
        assertEquals(3,graph.getDistance(ben,kramer));
        assertEquals(2,graph.getDistance(b,kramer));
        assertEquals(5,graph.getDistance(kramer,rachel));
    }
    //A can not get to B
    @Test
    public void getDistancePerson1NotToPerson2()
    {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        Person a = new Person("A");
        Person b = new Person("B");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addEdge(rachel, kramer);
        graph.addEdge(ross, rachel);
        graph.addEdge(kramer, a);
        graph.addEdge(a,kramer);
        graph.addEdge(ross,ben );
        graph.addEdge(ben,ross);
        graph.addEdge(ben,b);
        graph.addEdge(b,a);
        graph.addEdge(a,b);
        assertEquals(0,graph.getDistance(a,a));
        assertEquals(-1,graph.getDistance(a,rachel));
    }

}