package taskmanager;

import java.util.ArrayList;

public interface TaskManager {
    public int addEpic(Epic epic);

    public int addSubtask(Subtask subtask);

    public int addTask(Task task);

    public Task getTask(int id);

    public Subtask getSubtask(int id);

    public Epic getEpic(int id);

    public void removeTask(int id);

    public void removeSubtask(int id);

    public void removeEpic(int id);

    public void clearTasks();

    public void clearSubtasks();

    public void clearEpics();

    public ArrayList<Subtask> getSubtasksOfEpic(int epicId);

    public ArrayList<Task> getAllTasks();

    public ArrayList<Epic> getAllEpics();

    public ArrayList<Subtask> getAllSubtasks();

    public void updateTask(Task newTask);

    public void updateEpic(Epic newEpic);

    public void updateSubtask(Subtask newSubtask);

    public ArrayList<Task> getHistory();
}
