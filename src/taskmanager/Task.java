package taskmanager;

public class Task extends SuperTask {
    public Task(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public Task(Task task, TaskStatus newStatus) {
        super(task, newStatus);
    }

    @Override
    public String toString() {
        return "Task{name='" + name + "', status='" + status + "', id=" + id + "}";
    }
}
