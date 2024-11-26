import taskmanager.*;
import taskmanager.TaskStatus;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager tm = new TaskManager();

        int task1 = tm.addTask(new Task("task_1", "do smth 1", TaskStatus.NEW));
        int task2 = tm.addTask(new Task("task_2", "do smth 2", TaskStatus.NEW));
        int epic1 = tm.addTask(new EpicTask("epic_1", "epic descr. 1", TaskStatus.NEW));
        int sub1 = tm.addTask(new SubTask("sub_1", "do subtask 1", TaskStatus.NEW, epic1));
        int sub2 = tm.addTask(new SubTask("sub_2", "do subtask 2", TaskStatus.NEW, epic1));
        int epic2 = tm.addTask(new EpicTask("epic_2", "epic descr. 2", TaskStatus.NEW));
        int sub3 = tm.addTask(new SubTask("sub_3", "do subtask 3", TaskStatus.NEW, epic2));

        System.out.println(tm.getAllSimpleTasks());
        System.out.println(tm.getAllEpicTasks());
        System.out.println(tm.getAllSubtasks());


        tm.updateTask(new SubTask((SubTask) tm.getTaskByID(sub1), TaskStatus.IN_PROGRESS));
        tm.updateTask(new SubTask((SubTask) tm.getTaskByID(sub2), TaskStatus.IN_PROGRESS));
        tm.updateTask(new Task(tm.getTaskByID(task1), TaskStatus.IN_PROGRESS));
        tm.updateTask(new Task(tm.getTaskByID(task2), TaskStatus.DONE));
        tm.updateTask(new SubTask((SubTask) tm.getTaskByID(sub3), TaskStatus.DONE));

        System.out.println("\n\nUPDATED\n\n");

        System.out.println(tm.getAllSimpleTasks());
        System.out.println(tm.getAllEpicTasks());
        System.out.println(tm.getAllSubtasks());
    }
}
