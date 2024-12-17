package taskmanager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    static Epic someEpic;
    static Subtask subtask;

    @BeforeAll
    static void setUpEpic() throws Exception {
        someEpic = new Epic("a", "b", TaskStatus.NEW);
        someEpic.setId(0);
    }

    @BeforeEach
    void createSubtask() {
        subtask = new Subtask("a", "b", TaskStatus.NEW, 0);
        subtask.setId(1);
    }

    @Test
    void equalsShouldReturnTrueWithSameId() {
        Subtask subtask2 = new Subtask("c", "d", TaskStatus.DONE, 3);
        subtask2.setId(1);

        assertEquals(subtask, subtask2, "equals() should return true with same id");
    }

    @Test
    void equalsShouldReturnFalseWithDifferentId() {
        Subtask subtask2 = new Subtask("c", "d", TaskStatus.DONE, 0);
        subtask2.setId(2);

        assertEquals(subtask, subtask2, "equals() should return true with same id");
    }

    @Test
    void equalsShouldReturnFalseWithDifferentType() {
        Epic epic = new Epic("a", "b", TaskStatus.DONE);
        Task task = new Task("a", "b", TaskStatus.DONE);
        epic.setId(1);
        task.setId(1);

        assertNotEquals(subtask, epic, "equals() should return false with different type");
        assertNotEquals(subtask, task, "equals() should return false with different id");
    }

    @Test
    void updateFields() {
        Subtask subtask2 = new Subtask("name2", "description2", TaskStatus.NEW, 2);
        subtask2.setId(1);

        subtask.updateFields(subtask2);

        assertEquals(subtask.toString(), subtask2.toString(), "Subtasks should have same fields");
    }

    @Test
    void shouldBeSameAfterUpdate() {
        Subtask oldSubtask = subtask;
        Subtask subtask2 = new Subtask("name2", "description2", TaskStatus.NEW, 2);
        subtask2.setId(1);

        subtask.updateFields(subtask2);

        assertSame(subtask, oldSubtask);
    }
}