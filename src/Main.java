import taskmanager.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager tm = new TaskManager();
        int task1 = tm.addTask(new Task("task_1", "do smth 1", TaskStatus.NEW));
        int task2 = tm.addTask(new Task("task_2", "do smth 2", TaskStatus.NEW));
        int epic1 = tm.addEpic(new Epic("epic_1", "epic descr. 1", TaskStatus.NEW));
        int sub1 = tm.addSubtask(new Subtask("sub_1", "do subtask 1", TaskStatus.NEW, epic1));
        int sub2 = tm.addSubtask(new Subtask("sub_2", "do subtask 2", TaskStatus.NEW, epic1));
        int epic2 = tm.addEpic(new Epic("epic_2", "epic descr. 2", TaskStatus.NEW));
        int sub3 = tm.addSubtask(new Subtask("sub_3", "do subtask 3", TaskStatus.NEW, epic2));

        System.out.println(tm.getAllTasks());
        System.out.println(tm.getAllEpics());
        System.out.println(tm.getAllSubtasks());


        tm.updateSubtask(new Subtask(tm.getSubtask(sub1), TaskStatus.IN_PROGRESS));
        tm.updateSubtask(new Subtask(tm.getSubtask(sub2), TaskStatus.DONE));
        tm.updateTask(new Task(tm.getTask(task1), TaskStatus.IN_PROGRESS));
        tm.updateTask(new Task(tm.getTask(task2), TaskStatus.DONE));
        tm.updateSubtask(new Subtask(tm.getSubtask(sub3), TaskStatus.DONE));

        System.out.println("\n\nUPDATED\n\n");

        System.out.println(tm.getAllTasks());
        System.out.println(tm.getAllEpics());
        System.out.println(tm.getAllSubtasks());

        tm.removeSubtask(sub1);
        tm.removeEpic(epic2);
        System.out.println("\nDeleted Epic2 (with subtasks) and Subtask1 (Epic1 must be DONE now)\n");
        System.out.println(tm.getAllTasks());
        System.out.println(tm.getAllEpics());
        System.out.println(tm.getAllSubtasks());

    }
}
