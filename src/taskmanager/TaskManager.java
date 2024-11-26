package taskmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TaskManager {
    private int taskCounter = 0;

    private final HashMap<Integer, Task> simpleTasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, EpicTask> epicTasks = new HashMap<>();

    public int addTask(Task task) {
        if (task.getClass() == EpicTask.class) {
            epicTasks.put(taskCounter, (EpicTask) task);
        } else if (task.getClass() == SubTask.class) {
            SubTask subTask = (SubTask) task;
            if (epicTasks.containsKey(subTask.getEpicId())) {
                epicTasks.get(subTask.getEpicId()).addSubtask(subTask);
                subTasks.put(taskCounter, subTask);
            }
        } else if (task.getClass() == Task.class) {
            simpleTasks.put(taskCounter, task);
        }
        task.setId(taskCounter);
        return taskCounter++;
    }

    public Task getTaskByID(int id) {
        if (simpleTasks.containsKey(id)) {
            return simpleTasks.get(id);
        } else if (epicTasks.containsKey(id)) {
            return epicTasks.get(id);
        } else {
            return subTasks.getOrDefault(id, null);
        }
    }

    public void removeTask(int id) {
        simpleTasks.remove(id);
        epicTasks.remove(id);
        subTasks.remove(id);
    }

    public void clearAll() {
        simpleTasks.clear();
        epicTasks.clear();
        subTasks.clear();
    }

    public ArrayList<SubTask> getSubtasksOfEpic(int epicId) {
        if (epicTasks.containsKey(epicId)) {
            return epicTasks.get(epicId).getSubtasks();
        }
        return null;
    }

    public ArrayList<Task> getAllSimpleTasks() {
        return new ArrayList<>(simpleTasks.values());
    }

    public ArrayList<Task> getAllEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    public ArrayList<Task> getAllSubtasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void updateTask(Task newTask) {
        int id = newTask.getId();
        Task oldTask = getTaskByID(id);

        if (Objects.equals(oldTask, newTask)) {
            if (newTask.getClass() != EpicTask.class) {
                oldTask.setStatus(newTask.getStatus());
                if (newTask.getClass() == SubTask.class) {
                    EpicTask epicTask = epicTasks.get(((SubTask) newTask).getEpicId());
                    this.calculateEpicStatus(epicTask);
                }
            }
        }
    }

    private void calculateEpicStatus(EpicTask epic) {
        int sumStatus = 0;
        ArrayList<SubTask> subtasksOfEpic = epic.getSubtasks();
        for (SubTask subTask : subtasksOfEpic) {
            sumStatus += switch (subTask.getStatus()) {
                case NEW -> 0;
                case IN_PROGRESS -> 1;
                case DONE -> 2;
            };
        }
        if (sumStatus == 2 * subtasksOfEpic.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (sumStatus == 0) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
