package taskmanager;

public class Task {
    /* Ревьюеру:
        Насчет того что Эпик и Сабтаск должны быть именно наследниками Таска,
        я читал тз, но мне кажется что лучше все же сделать Таск наследником абстрактного класса,
        т.к. это позволит на уровне компиляции избежать ошибок при передаче наследника как аргумент
        (например addTask(new Epic(...)) ). Иначе я вынужден кидать дополнительные исключения
        или просто не сообщать пользователю об ошибке, что потенциально затрудняет использование ТаскМенеджера для пользователя

        Можете, пожалуйста, объяснить в чем преимущество структуры проекта из тз?
        или это замечание из серии "не соответствует тз" без логического обоснования?
     */

    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;

    public Task(Task other, TaskStatus newStatus) {
        this.name = other.name;
        this.description = other.description;
        this.id = other.id;
        this.status = newStatus;
    }

    public Task(String name, String description, TaskStatus status) {
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


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return this.id == ((Task) o).id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{name='" + name + "', id='" + id + "', status=" + status + "}";
    }
}
