package taskmanager;

import java.util.ArrayList;

public class EpicTask extends Task {
    private final ArrayList<SubTask> subtasks;

    public EpicTask(String name, String description, TaskStatus status) {
        super(name, description, status);
        subtasks = new ArrayList<>();
    }

    ArrayList<SubTask> getSubtasks() {
        return subtasks;
    }

    void addSubtask(SubTask subtask) {
        subtasks.add(subtask);
    }

    void removeSubtask(SubTask subtask) {
        subtasks.remove(subtask);
    }

    @Override
    public String toString() {
        return "EpicTask{name='" + name + "', status='" + status + "', id=" + id + ", subtasks=" + subtasks.toString() + "}";
    }
}
