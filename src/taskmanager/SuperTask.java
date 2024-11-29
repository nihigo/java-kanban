package taskmanager;

abstract class SuperTask {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;

    public SuperTask(SuperTask other, TaskStatus newStatus) {
        this.name = other.name;
        this.description = other.description;
        this.id = other.id;
        this.status = newStatus;
    }

    public SuperTask(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }


    void setStatus(TaskStatus status) {
        this.status = status;
    }

    void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return this.id == ((SuperTask) o).id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
