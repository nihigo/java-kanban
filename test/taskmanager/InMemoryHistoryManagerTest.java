package taskmanager;

import org.junit.jupiter.api.Test;
import taskmanager.historymanager.HistoryManager;
import taskmanager.historymanager.InMemoryHistoryManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void shouldAddTasks() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("a", "b", TaskStatus.NEW);
        Task task2 = new Task("b", "c", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        ArrayList<Task> result = new ArrayList<>();
        result.add(task2);
        result.add(task1);

        assertEquals(result, historyManager.getHistory());
    }

    @Test
    void shouldAddAnyTypeOfTask() {
        HistoryManager hm = new InMemoryHistoryManager();
        Epic epic = new Epic("a", "b", TaskStatus.NEW);
        Subtask subtask = new Subtask("a", "b", TaskStatus.NEW, 0);
        Task task = new Task("a", "b", TaskStatus.NEW);

        hm.add(epic);
        hm.add(subtask);
        hm.add(task);

        ArrayList<Task> result = new ArrayList<>();
        result.add(task);
        result.add(subtask);
        result.add(epic);

        assertEquals(result, hm.getHistory());
    }

    @Test
    void shouldRemoveOldestWhenOver10Tasks() {
        HistoryManager hm = new InMemoryHistoryManager();
        for (int i = 0; i < 15; i++) {
            Task task = new Task("a", "b", TaskStatus.NEW);
            task.setId(i);
            hm.add(task);
        }

        ArrayList<Task> result = new ArrayList<>();
        for (int i = 14; i >= 5; i--) {
            Task task = new Task("a", "b", TaskStatus.NEW);
            task.setId(i);
            result.add(task);
        }

        assertEquals(result, hm.getHistory());
    }
}