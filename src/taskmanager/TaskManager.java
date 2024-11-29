package taskmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

public class TaskManager {
    private int taskCounter = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    public int addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(taskCounter);
            epics.put(taskCounter, epic);
        } else {
            throw new NullPointerException("Epic is null");
        }
        return taskCounter++;
    }

    public int addSubtask(Subtask subtask) {
        if (subtask != null) {
            subtask.setId(taskCounter);
            subtasks.put(taskCounter, subtask);
            int epicID = subtask.getEpicId();
            if (epics.containsKey(epicID)) {
                Epic epic = epics.get(epicID);
                epic.addSubtask(subtask);
                calculateEpicStatus(epic);
                subtasks.put(taskCounter, subtask);
            }
        } else {
            throw new NullPointerException("subtask is null");
        }
        return taskCounter++;
    }

    public int addTask(Task task) {
        if (task != null && task.getClass() == Task.class) {
            task.setId(taskCounter);
            tasks.put(taskCounter, task);
        } else {
            throw new IllegalArgumentException("Task class must be Task");
        }
        return taskCounter++;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void removeTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            throw new NoSuchElementException("Task with id " + id + " is not found. Probably wrong type");
        }
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic epic = epics.get(subtasks.get(id).getEpicId());
            epic.removeSubtask(subtasks.get(id));
            calculateEpicStatus(epic);
            subtasks.remove(id);
        } else {
            throw new NoSuchElementException("Subtask with id " + id + " is not found. Probably wrong type");
        }

    }

    public void removeEpic(int id) {
        if (epics.containsKey(id)) {
            // Не уверен, что стоит их удалять, но они инвалидируются.
            // Поэтому удаление, возможно, поможет избежать некоторых ошибок
            for (Subtask subtask : getSubtasksOfEpic(id)) {
                subtasks.remove(subtask.getId());
            }
            epics.remove(id);
        } else {
            throw new NoSuchElementException("Epic with id " + id + " is not found. Probably wrong type");
        }
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void clearSubtasks() {
        subtasks.clear();
        // Аналогично
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
        }
    }

    public void clearEpics() {
        // Не уверен, что это корректное поведение метода.
        // Но при удалении Эпиков инвалидируются все Сабтаски, так что тоже очищаю
        subtasks.clear();
        epics.clear();
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            return epics.get(epicId).getSubtasks();
        }
        return null;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }


    public void updateTask(Task newTask) {
        int id = newTask.getId();
        Task oldTask = getTask(id);
        if (Objects.equals(oldTask, newTask) && newTask.getClass() == Task.class) {
           tasks.put(id, newTask);
        } else {
            throw new IllegalArgumentException("newTask is either not Task type or null");
        }
    }

    public void updateEpic(Epic newEpic) {
        int id = newEpic.getId();
        Epic oldEpic = getEpic(id);
        if (Objects.equals(oldEpic, newEpic)) {
            ArrayList<Subtask> oldSubtasks = oldEpic.getSubtasks();
            ArrayList<Subtask> newSubtasks = newEpic.getSubtasks();
            if (!oldSubtasks.equals(newSubtasks)) {
                for (Subtask subtask : newSubtasks) {
                    if (!subtasks.containsKey(subtask.getId())) {
                        subtasks.put(subtask.getId(), subtask);
                    }
                }
            }
            epics.put(id, newEpic);
            if (!newEpic.getSubtasks().isEmpty()) {
                calculateEpicStatus(newEpic);
            }
        } else {
            throw new IllegalArgumentException("this newEpic is either not added yet or null");
        }
    }

    public void updateSubtask(Subtask newSubtask) {
        int id = newSubtask.getId();
        Subtask oldSubtask = getSubtask(id);
        if (Objects.equals(oldSubtask, newSubtask)) {
            subtasks.put(id, newSubtask);

            Epic oldEpic = getEpic(oldSubtask.getEpicId());
            oldEpic.removeSubtask(oldSubtask);

            Epic newEpic = getEpic(newSubtask.getEpicId());
            newEpic.addSubtask(newSubtask);
            calculateEpicStatus(newEpic);
        } else {
            throw new IllegalArgumentException("this newSubtask is either not added yet or null");
        }
    }

    private void calculateEpicStatus(Epic epic) {
        int sumStatus = 0;
        ArrayList<Subtask> subtasksOfEpic = epic.getSubtasks();
        for (Subtask subtask : subtasksOfEpic) {
            sumStatus += switch (subtask.getStatus()) {
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
