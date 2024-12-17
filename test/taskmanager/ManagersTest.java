package taskmanager;

import org.junit.jupiter.api.Test;
import taskmanager.historymanager.HistoryManager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    void runTaskManager(TaskManager tm) {
        int t1 = tm.addTask(new Task("a", "b", TaskStatus.NEW));
        int t2 = tm.addTask(new Task("a", "b", TaskStatus.NEW));
        int e1 = tm.addEpic(new Epic("a", "b", TaskStatus.NEW));
        int s1 = tm.addSubtask(new Subtask("c", "d", TaskStatus.NEW, e1));
        tm.updateTask(new Task(tm.getTask(t1), TaskStatus.DONE));

        tm.getSubtask(s1);
        tm.getTask(t1);
        tm.getEpic(e1);

        tm.removeTask(t2);
        tm.getSubtasksOfEpic(e1);
        tm.clearSubtasks();
        tm.clearEpics();
        tm.clearEpics();

        tm.getHistory();
    }

    @Test
    void defaultHistoryManagerIsNotNull() {
        HistoryManager hm = Managers.getDefaultHistory();
        assertNotNull(hm);
    }

    @Test
    void defaultHistoryManagerIsFunctional() {
        HistoryManager hm = Managers.getDefaultHistory();

        assertDoesNotThrow(() -> hm.add(new Task("_", "_", TaskStatus.NEW)));
        assertDoesNotThrow(() -> hm.add(new Epic("_", "_", TaskStatus.NEW)));
        assertDoesNotThrow(() -> hm.add(new Subtask("_", "_", TaskStatus.NEW, 0)));

        assertDoesNotThrow(hm::getHistory);

    }

    @Test
    void defaultManagerIsNotNull() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager);
    }

    @Test
    void testDefaultManagerIsFunctional() {
        TaskManager tm = Managers.getDefault();

        assertDoesNotThrow(() -> this.runTaskManager(tm));
    }
}