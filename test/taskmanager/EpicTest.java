package taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic;

    @BeforeEach
    void createEpic() {
        epic = new Epic("name1", "description1", TaskStatus.DONE);
        epic.setId(1);
    }

    @Test
    void equalsShouldReturnTrueWithSameId() {
        Epic epic2 = new Epic("name2", "description2", TaskStatus.NEW);
        epic2.setId(1);

        assertEquals(epic, epic2, "equals() should return true with same id");
    }

    @Test
    void equalsShouldReturnFalseWithDifferentId() {
        Epic epic2 = new Epic("name2", "description2", TaskStatus.NEW);
        epic2.setId(2);

        assertNotEquals(epic, epic2, "equals() should return false with different id");
    }

    @Test
    void equalsShouldReturnFalseWithDifferentType() {
        Task task = new Task("a", "b", TaskStatus.DONE);
        Subtask subtask = new Subtask("a", "b", TaskStatus.DONE, 0);
        task.setId(1);
        subtask.setId(1);

        assertNotEquals(epic, task, "equals() should return false with different type");
        assertNotEquals(epic, subtask, "equals() should return false with different type");
    }

    @Test
    void addSubtask() {
        Subtask subtask = new Subtask("a", "b", TaskStatus.DONE, 0);
        subtask.setId(2);
        Subtask subtask2 = new Subtask("a", "b", TaskStatus.NEW, 0);
        subtask2.setId(3);

        epic.addSubtask(subtask);
        epic.addSubtask(subtask2);

        assertEquals(2, epic.getSubtasks().size(), "Should be two subtasks");
        assertEquals(subtask, epic.getSubtasks().get(0), "1st subtask is different");
        assertEquals(subtask2, epic.getSubtasks().get(1), "2nd subtask is different");
    }

    @Test
    void removeSubtask() {
        Subtask subtask = new Subtask("a", "b", TaskStatus.DONE, 0);
        subtask.setId(2);
        Subtask subtask2 = new Subtask("a", "b", TaskStatus.NEW, 0);
        subtask2.setId(3);
        epic.addSubtask(subtask);
        epic.addSubtask(subtask2);

        epic.removeSubtask(subtask2);

        assertEquals(1, epic.getSubtasks().size(), "Should be one subtasks");
    }

    @Test
    void clearSubtasks() {
        Subtask subtask = new Subtask("a", "b", TaskStatus.DONE, 0);
        subtask.setId(2);
        Subtask subtask2 = new Subtask("a", "b", TaskStatus.NEW, 0);
        subtask2.setId(3);
        epic.addSubtask(subtask);
        epic.addSubtask(subtask2);

        epic.clearSubtasks();

        assertTrue(epic.getSubtasks().isEmpty(), "Subtasks should be empty");
    }

    @Test
    void updateFields() {
        Epic epic2 = new Epic("name2", "description2", TaskStatus.NEW);
        epic2.setId(1);

        epic.updateFields(epic2);

        assertEquals(epic2.toString(), epic.toString(), "Epics should have same fields");
    }

    @Test
    void shouldBeSameAfterUpdate() {
        Epic oldEpic = epic;
        Epic epic2 = new Epic("name2", "description2", TaskStatus.NEW);
        epic2.setId(1);

        epic.updateFields(epic2);

        assertSame(epic, oldEpic);
    }
}