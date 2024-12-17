package taskmanager;

import taskmanager.historymanager.HistoryManager;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int taskCounter = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
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

    @Override
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
            } else {
                throw new IllegalArgumentException("Epic of subtask must be existing Epic");
            }

            return newID;
        } else {
            throw new NullPointerException("subtask is null");
        }
    }

    @Override
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

    @Override
    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            historyManager.add(task);
            return task;
        } else {
            throw new NoSuchElementException("Task with id " + id + " not found");
        }
    }

    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            historyManager.add(subtask);
            return subtask;
        } else {
            throw new NoSuchElementException("Subtask with id " + id + " not found");
        }
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            historyManager.add(epic);
            return epic;
        } else {
            throw new NoSuchElementException("Epic with id " + id + " not found");
        }
    }

    @Override
    public void removeTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            throw new NoSuchElementException("Task with id " + id + " is not found. Probably wrong type");
        }
    }

    @Override
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

    @Override
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

    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public void clearSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            calculateEpicStatus(epic);
        }
    }

    @Override
    public void clearEpics() {
        subtasks.clear();
        epics.clear();
    }

    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            return epics.get(epicId).getSubtasks();
        }
        return null;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void updateTask(Task newTask) {
        if (newTask != null && newTask.getClass() == Task.class) {
            int id = newTask.getId();
            Task oldTask = tasks.get(id);
            if (newTask.equals(oldTask)) {
                oldTask.updateFields(newTask);
            } else {
                throw new IllegalArgumentException("this task is not added");
            }
        } else {
            throw new NullPointerException("task is null");
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (newEpic != null) {
            int id = newEpic.getId();
            Epic oldEpic = epics.get(id);
            if (newEpic.equals(oldEpic)) {
                updateSubtasksOfEpic(newEpic, oldEpic);
                oldEpic.updateFields(newEpic);
            } else {
                throw new IllegalArgumentException("this epic is not added");
            }
        } else {
            throw new NullPointerException("epic is null");
        }
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        int id = newSubtask.getId();
        Subtask oldSubtask = subtasks.get(id);
        if (Objects.equals(oldSubtask, newSubtask)) {
            subtasks.put(id, newSubtask);

            Epic oldEpic = epics.get(oldSubtask.getEpicId());
            oldEpic.removeSubtask(oldSubtask);

            Epic newEpic = epics.get(newSubtask.getEpicId());
            newEpic.addSubtask(newSubtask);
            calculateEpicStatus(oldEpic);
            calculateEpicStatus(newEpic);
            oldSubtask.updateFields(newSubtask);
        } else {
            throw new IllegalArgumentException("this subtask is either not added yet or null");
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
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
