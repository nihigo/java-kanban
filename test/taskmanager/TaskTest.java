package taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;

    @BeforeEach
    void createTask() {
        task = new Task("a", "b", TaskStatus.NEW);
        task.setId(1);
    }

    @Test
    void equalsShouldReturnTrueWithSameId() {
        Task task2 = new Task("c", "d", TaskStatus.DONE);
        task2.setId(1);

        assertEquals(task, task2, "equals() should return true with same id");
    }

    @Test
    void equalsShouldReturnFalseWithDifferentId() {
        Task task2 = new Epic("a", "b", TaskStatus.NEW);
        task2.setId(2);

        assertNotEquals(task, task2, "equals() should return false with different id");
    }

    @Test
    void equalsShouldReturnFalseWithDifferentType() {
        Epic epic = new Epic("a", "b", TaskStatus.DONE);
        Subtask subtask = new Subtask("a", "b", TaskStatus.DONE, 0);
        epic.setId(1);
        subtask.setId(1);

        assertNotEquals(task, epic, "equals() should return false with different type");
        assertNotEquals(task, subtask, "equals() should return false with different type");
    }

    @Test
    void updateFields() {
        Task task2 = new Task("name2", "description2", TaskStatus.NEW);
        task2.setId(1);

        task.updateFields(task2);

        assertEquals(task.toString(), task2.toString(), "Tasks should have same fields");
    }

    @Test
    void shouldBeSameAfterUpdate() {
        Task oldTask = task;
        Task task2 = new Task("name2", "description2", TaskStatus.NEW);
        task2.setId(1);

        task.updateFields(task2);

        assertSame(task, oldTask);
    }
}