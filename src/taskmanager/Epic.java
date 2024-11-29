package taskmanager;

import java.util.ArrayList;

public class Epic extends SuperTask {
    private final ArrayList<Subtask> subtasks;

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
        subtasks = new ArrayList<>();
    }

    ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    void clearSubtasks() {
        subtasks.clear();
    }

    @Override
    public String toString() {
        return "EpicTask{name='" + name + "', status='" + status + "', id=" + id + ", subtasks=" + subtasks.toString() + "}";
    }
}
