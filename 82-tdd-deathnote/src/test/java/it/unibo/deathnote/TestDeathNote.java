package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.lang.Thread.sleep;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

class TestDeathNote<E> {

    private DeathNote deathNote;
    private final static String DEFAULT_NAME1 = "Luca";
    private final static String DEFAULT_NAME2 = "Alessio";

    @BeforeEach
    void setUp() {
        deathNote = new DeathNoteImplementation();
    }

    @Test
    public void test1() {
        for(int i : List.of(-1, 0, DeathNote.RULES.size() + 1 )) {
            try {
                deathNote.getRule(i);
            } catch (IllegalArgumentException e) {
                assertNotNull(e.getMessage());
                assertFalse(e.getMessage().isEmpty());
                assertFalse(e.getMessage().isBlank());
            }
        }
    }

    @Test
    public void test2() {
        for(final String rule : DeathNote.RULES){
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    public void test3() {
        assertFalse(deathNote.isNameWritten(DEFAULT_NAME1));
        deathNote.writeName(DEFAULT_NAME1);
        assertTrue(deathNote.isNameWritten(DEFAULT_NAME1));
        assertFalse(deathNote.isNameWritten(DEFAULT_NAME2));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    public void test4() {
        assertThrows(IllegalArgumentException.class, () -> {
            deathNote.getDeathCause(DEFAULT_NAME1);
        });
        deathNote.writeName(DEFAULT_NAME1);
        assertEquals("heart attack", deathNote.getDeathCause(DEFAULT_NAME1));
        deathNote.writeName(DEFAULT_NAME2);
        deathNote.writeDeathCause("karting accident");
        assertEquals("karting accident", deathNote.getDeathCause(DEFAULT_NAME2));
        try {
            sleep(100); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(deathNote.writeDeathCause("Suicide"));
        assertEquals("karting accident", deathNote.getDeathCause(DEFAULT_NAME2));
    }

    @Test
    public void test5() {
        assertThrows(IllegalStateException.class, () -> {
            deathNote.writeDetails("heart attack");
        });
        deathNote.writeName(DEFAULT_NAME1);
        assertTrue(deathNote.getDeathDetails(DEFAULT_NAME1).isEmpty());
        deathNote.writeDetails("ran for too long");
        assertEquals("ran for too long", deathNote.getDeathDetails(DEFAULT_NAME1));
        deathNote.writeName(DEFAULT_NAME2);
        try {
            sleep(6100); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(deathNote.writeDetails("spoke for too long"));
        assertEquals("ran for too long", deathNote.getDeathDetails(DEFAULT_NAME1));
    }

}