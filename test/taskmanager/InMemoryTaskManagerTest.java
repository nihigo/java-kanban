package taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    static InMemoryTaskManager tm;

    @BeforeEach
    void createTaskManager() {
        tm = new InMemoryTaskManager();
    }

    @Test
    void tasksAreAddedGood() {
        Task task = new Task("a", "b", TaskStatus.NEW);
        Epic epic = new Epic("a", "b", TaskStatus.NEW);
        tm.addTask(task);
        tm.addEpic(epic);
        Subtask subtask = new Subtask("a", "b", TaskStatus.NEW, epic.getId());
        tm.addSubtask(subtask);

        // result arrays
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);
        ArrayList<Epic> epics = new ArrayList<>();
        epics.add(epic);
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask);

        assertEquals(tasks, tm.getAllTasks());
        assertEquals(epics, tm.getAllEpics());
        assertEquals(subtasks, tm.getAllSubtasks());
    }

    @Test
    void taskManagerCanFindTasksById() {
        Task task = new Task("a", "b", TaskStatus.NEW);
        Epic epic = new Epic("a", "b", TaskStatus.NEW);
        tm.addTask(task);
        tm.addEpic(epic);
        Subtask subtask = new Subtask("a", "b", TaskStatus.NEW, epic.getId());
        tm.addSubtask(subtask);

        assertEquals(task, tm.getTask(task.getId()));
        assertEquals(epic, tm.getEpic(epic.getId()));
        assertEquals(subtask, tm.getSubtask(subtask.getId()));
    }

    @Test
    void everyIdIsUnique() {
        HashSet<Integer> ids = new HashSet<>();
        int epicId = tm.addEpic(new Epic("a", "b", TaskStatus.NEW));
        ids.add(epicId);
        int tasksCounter = 1;
        for (int i = 0; i < 10; i++) {
            ids.add(tm.addTask(new Task("a", "b", TaskStatus.NEW)));
            tasksCounter++;
        }
        for (int i = 0; i < 10; i++) {
            ids.add(tm.addEpic(new Epic("a", "b", TaskStatus.NEW)));
            tasksCounter++;
        }
        for (int i = 0; i < 10; i++) {
            ids.add(tm.addSubtask(new Subtask("a", "b", TaskStatus.NEW, epicId)));
            tasksCounter++;
        }
        assertEquals(tasksCounter, ids.size());
    }

    @Test
    void updateSubtaskTest() {
        Epic epic = new Epic("a", "b", TaskStatus.NEW);
        int epicId = tm.addEpic(epic);
        int subtask1 = tm.addSubtask(new Subtask("a", "b", TaskStatus.NEW, epicId));
        int subtask2 = tm.addSubtask(new Subtask("a", "b", TaskStatus.NEW, epicId));

        tm.updateSubtask(new Subtask(tm.getSubtask(subtask1), TaskStatus.DONE));
        assertEquals(epic.getStatus(), TaskStatus.IN_PROGRESS);
        tm.updateSubtask(new Subtask(tm.getSubtask(subtask2), TaskStatus.DONE));
        assertEquals(epic.getStatus(), TaskStatus.DONE);
    }

    @Test
    void EpicOfSubtaskMustBeEpic() {
        int taskId = tm.addTask(new Task("a", "b", TaskStatus.NEW));
        Subtask invalidSubtask = new Subtask("a", "b", TaskStatus.NEW, taskId);
        int epicId = tm.addEpic(new Epic("a", "b", TaskStatus.NEW));
        Subtask validSubtask = new Subtask("a", "b", TaskStatus.NEW, epicId);

        assertDoesNotThrow(() -> tm.addSubtask(validSubtask));
        assertThrows(IllegalArgumentException.class, () -> tm.addSubtask(invalidSubtask));
        assertThrows(IllegalArgumentException.class, () -> tm.addSubtask(
                new Subtask("a", "b", TaskStatus.NEW, validSubtask.getId()))
        );
    }

    @Test
    void getHistoryWorks() {
        Epic epic = new Epic("a", "b", TaskStatus.NEW);
        Task task = new Task("a", "b", TaskStatus.NEW);
        int taskId = tm.addTask(task);
        int epicId = tm.addEpic(epic);
        tm.getEpic(epicId);
        tm.getTask(taskId);

        ArrayList<Task> history = new ArrayList<>();
        history.add(epic);
        history.add(task);

        assertEquals(history, tm.getHistory());
    }

    @Test
    void historyDoesNotInvalidateWhenTaskUpdated() {
        Epic epic = new Epic("a", "b", TaskStatus.NEW);
        int epicId = tm.addEpic(epic);
        Task task = new Task("a", "b", TaskStatus.NEW);
        int taskId = tm.addTask(task);

        ArrayList<Task> history = new ArrayList<>();
        history.add(epic);
        history.add(task);

        tm.getEpic(epicId);
        tm.getTask(taskId);
        tm.updateTask(new Task(task, TaskStatus.IN_PROGRESS));

        assertEquals(history, tm.getHistory());
    }
}