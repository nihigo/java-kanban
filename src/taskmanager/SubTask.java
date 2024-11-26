package taskmanager;

public class SubTask extends Task {
    private final int epicID;

    public SubTask(SubTask subTask, TaskStatus newStatus) {
        super(subTask, newStatus);
        this.epicID = subTask.epicID;
    }

    public SubTask(String name, String description, TaskStatus status, int epicID) {
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