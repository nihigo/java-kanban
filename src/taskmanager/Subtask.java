package taskmanager;

public class Subtask extends SuperTask {
    private final int epicID;

    public Subtask(Subtask subTask, TaskStatus newStatus) {
        super(subTask, newStatus);
        this.epicID = subTask.epicID;
    }

    public Subtask(String name, String description, TaskStatus status, int epicID) {
        super(name, description, status);
        this.epicID = epicID;
    }

    public int getEpicId() {
        return epicID;
    }

    @Override
    public String toString() {
        return "SubTask{name='" + name + "', status='" + status + "', id=" + id + ", epicID=" + epicID + "}";
    }
}
