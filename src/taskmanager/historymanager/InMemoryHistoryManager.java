package taskmanager.historymanager;

import taskmanager.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history.reversed());
    }
}
