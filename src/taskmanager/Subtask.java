package taskmanager;

public class Subtask extends Task {
    private int epicID;

    public Subtask(Subtask subtask, TaskStatus newStatus) {
        super(subtask, newStatus);
        this.epicID = subtask.epicID;
    }

    public Subtask(String name, String description, TaskStatus status, int epicID) {
        super(name, description, status);
        this.epicID = epicID;
    }

    void updateFields(Subtask other) {
        if (other != null) {
            super.updateFields(other);
            this.epicID = other.epicID;
        }
    }

    public int getEpicId() {
        return epicID;
    }

    @Override
    public String toString() {
        return "SubTask{name='" + name + "', status='" + status + "', id=" + id + ", epicID=" + epicID + "}";
    }
}
