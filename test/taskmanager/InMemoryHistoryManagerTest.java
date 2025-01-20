package taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.historymanager.HistoryManager;
import taskmanager.historymanager.InMemoryHistoryManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {
    HistoryManager historyManager;

    @BeforeEach
    void createHistoryManager() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void shouldAddTasksWithDifferentIds() {
        Task task1 = new Task("a", "b", TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("b", "c", TaskStatus.NEW);
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(List.of(task1, task2), historyManager.getHistory());
    }

    @Test
    void shouldNotAddTasksWithSameId() {
        Task task1 = new Task("a", "b", TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("b", "c", TaskStatus.NEW);
        task2.setId(1);

        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void shouldAddAnyTypeOfTask() {
        Epic epic = new Epic("a", "b", TaskStatus.NEW);
        epic.setId(1);
        Subtask subtask = new Subtask("a", "b", TaskStatus.NEW, 1);
        subtask.setId(2);
        Task task = new Task("a", "b", TaskStatus.NEW);
        task.setId(3);

        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.add(task);

        assertEquals(List.of(epic, subtask, task), historyManager.getHistory());
    }

    @Test
    void shouldContainMoreThan10Tasks() {
        for (int i = 0; i < 15; i++) {
            Task task = new Task("a", "b", TaskStatus.NEW);
            task.setId(i);
            historyManager.add(task);
        }

        List<Task> result = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Task task = new Task("a", "b", TaskStatus.NEW);
            task.setId(i);
            result.add(task);
        }

        assertEquals(result, historyManager.getHistory());
    }
    
    @Test
    void shouldRemoveTasks() {
        Task task1 = new Task("a", "b", TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("b", "c", TaskStatus.NEW);
        task2.setId(2);
        Task task3 = new Task("c", "d", TaskStatus.NEW);
        task3.setId(3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(1);

        assertEquals(List.of(task2, task3), historyManager.getHistory());
    }

    @Test
    void shouldUpdateOrderWhenAddingExistingTask() {
        Task task1 = new Task("a", "b", TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("b", "c", TaskStatus.NEW);
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);

        assertEquals(List.of(task2, task1), historyManager.getHistory());
    }
}