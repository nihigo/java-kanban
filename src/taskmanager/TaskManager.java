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
            int newID = generateID();
            epic.setId(newID);
            epics.put(epic.id, epic);

            return newID;
        } else {
            throw new NullPointerException("Epic is null");
        }
    }

    public int addSubtask(Subtask subtask) {
        if (subtask != null) {
            int newID = generateID();
            subtask.setId(newID);
            subtasks.put(newID, subtask);

            int epicID = subtask.getEpicId();
            if (epics.containsKey(epicID)) {
                Epic epic = epics.get(epicID);
                epic.addSubtask(subtask);
                calculateEpicStatus(epic);
            }

            return newID;
        } else {
            throw new NullPointerException("subtask is null");
        }
    }

    public int addTask(Task task) {
        if (task != null && task.getClass() == Task.class) {
            int newID = generateID();
            task.setId(newID);
            tasks.put(newID, task);

            return newID;
        } else {
            throw new NullPointerException("task is either null or not Task class");
        }
    }

    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else {
            throw new NoSuchElementException("Task with id " + id + " not found");
        }
    }

    public Subtask getSubtask(int id) {
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else {
            throw new NoSuchElementException("Subtask with id " + id + " not found");
        }
    }

    public Epic getEpic(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            throw new NoSuchElementException("Epic with id " + id + " not found");
        }
    }

    public void removeTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            throw new NoSuchElementException("Task with id " + id + " is not found. Probably wrong type");
        }
    }

    public void removeSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubtask(subtask);
            calculateEpicStatus(epic);
            subtasks.remove(id);
        } else {
            throw new NoSuchElementException("Subtask with id " + id + " is not found. Probably wrong type");
        }
    }

    public void removeEpic(int id) {
        if (epics.containsKey(id)) {
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
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            calculateEpicStatus(epic);
        }
    }

    public void clearEpics() {
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
        if (newTask != null && newTask.getClass() == Task.class) {
            int id = newTask.getId();
            if (newTask.equals(getTask(id))) {
                tasks.put(id, newTask);
            } else {
                throw new IllegalArgumentException("this task is not added");
            }
        } else {
            throw new NullPointerException("task is null");
        }
    }

    public void updateEpic(Epic newEpic) {
        if (newEpic != null) {
            int id = newEpic.getId();
            Epic oldEpic = getEpic(id);
            if (newEpic.equals(oldEpic)) {
                updateSubtasksOfEpic(newEpic, oldEpic);
                epics.put(id, newEpic);
            } else {
                throw new IllegalArgumentException("this epic is not added");
            }
        } else {
            throw new NullPointerException("epic is null");
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
            throw new IllegalArgumentException("this subtask is either not added yet or null");
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

    private void updateSubtasksOfEpic(Epic oldEpic, Epic newEpic) {
        ArrayList<Subtask> oldSubtasks = oldEpic.getSubtasks();
        ArrayList<Subtask> newSubtasks = newEpic.getSubtasks();
        if (!oldSubtasks.equals(newSubtasks)) {
            for (Subtask subtask : oldSubtasks) {
                if (!newSubtasks.contains(subtask)) {
                    subtasks.remove(subtask.getId());
                }
            }
            for (Subtask subtask : newSubtasks) {
                subtasks.put(subtask.getId(), subtask);
            }
        }
        if (!newEpic.getSubtasks().isEmpty()) {
            calculateEpicStatus(newEpic);
        }
    }

    private int generateID() {
        return ++taskCounter;
    }

}
