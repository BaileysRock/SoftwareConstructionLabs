package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalVertexParamsException;
import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import java.util.List;
import java.util.Objects;
import vertex.Person;
import vertex.Vertex;

/**
 * Describe the interaction between two persons if the former had commented Weibo of the latter. And
 * the weight of the edge indicates how often the two interact.
 *
 * @author Zhu Mingyan
 */
public class CommentTie extends DirectedEdge {

  /**
   * Constructor of CommentTie with string label and weight of it.
   *
   * @param label non-null string
   * @param weight requires weight > 0 && weight <= 1
   * @throws InputFileAgainException when weight <= 0 or weight > 1.
   */
  public CommentTie(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    if (weight <= 0 || weight > 1) {
      throw new IllegalEdgeParamsException("Weight of Comment Connection is illegal!");
    }
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    //        assert this.getWeight() > 0;
    super.checkRep();
  }

  /**
   * Fill in CommentTie and the size of vertices should be 2. All vertices should be an instance of
   * Person. CommentTie should not be a loop.
   *
   * @return true if add vertices successfully, otherwise false
   */
  @Override
  public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
    if (vertices == null) {
      throw new IllegalVertexParamsException("Add vertices into CommentTie with null vertices");
    }
    if (vertices.size() != 2) {
      throw new IllegalVertexParamsException(
          "Add vertices into CommentTie with " + vertices.size() + " vertices");
    }
    assert vertices.size() == 2;
    if (vertices.get(0).equals(vertices.get(1))) {
      throw new IllegalEdgeParamsException("CommentTie can not be loop!");
    }
    boolean answer = true;
    for (Vertex vertex : vertices) {
      answer = answer && vertex instanceof Person;
      if (!answer) {
        throw new IllegalVertexTypeException(
            "Add " + (vertex == null ? "null" : vertex.getClass().getSimpleName())
                + " into CommentTie");
      }
    }
    return answer && super.addVertices(vertices);
  }

  @Override
  public String toString() {
    return "CommentTie" + super.toString().replace("DirectedEdge", "");
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof CommentTie)) {
      return false;
    }
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "CommentTie");
  }
}
