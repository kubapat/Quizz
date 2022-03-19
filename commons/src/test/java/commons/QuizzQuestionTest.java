package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizzQuestionTest {
    @Test
    public void checkConstructor() {
        var p = new QuizzQuestion("a", new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), 0);
        assertEquals("a", p.getQuestion());
        assertEquals(new Activity("a", "a", "1", 10l, "a"), p.getFirstChoice());
        assertEquals(new Activity("a", "a", "1", 10l, "a"), p.getSecondChoice());
        assertEquals(new Activity("a", "a", "1", 10l, "a"), p.getThirdChoice());
    }

    @Test
    public void equalsHashCode() {
        var a = new QuizzQuestion("a", new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), 1);
        var b = new QuizzQuestion("a", new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), 1);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new QuizzQuestion("a", new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), 0);
        var b = new QuizzQuestion("b", new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), 0);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var a = new QuizzQuestion("a", new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), new Activity("a", "a", "1", 10l, "a"), 0).toString();
        assertTrue(a.contains("a"));
        assertTrue(a.contains("\n"));
        assertTrue(a.contains("question"));
    }
}


