import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    // Testing strategy for Person.getName
    //
    // Partition the inputs as follows:
    // whether Person is null
    //
    // Exhaustive Cartesian coverage of partitions.

    // Person is null
    @Test(expected = NullPointerException.class)
    public void getNamePersonNull() {
        Person person  = null;
        String personName = person.getName();
        assertEquals(new String(),personName);
    }
    // Person is not null
    @Test
    public void getNamePersonNotNull() {
        Person person  = new Person("PERSON");

        assertEquals("PERSON",person.getName());

    }
}