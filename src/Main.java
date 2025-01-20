import taskmanager.*;
import taskmanager.historymanager.InMemoryHistoryManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("-----TESTING TASKHASHLIST-----");
        InMemoryHistoryManager hm = new InMemoryHistoryManager();
        InMemoryTaskManager tm = new InMemoryTaskManager();
        int t1 = tm.addTask(new Task("a", "b", TaskStatus.NEW));
        int t2 = tm.addTask(new Task("a", "b", TaskStatus.NEW));
        int e1 = tm.addEpic(new Epic("a", "b", TaskStatus.NEW));
        int s1 = tm.addSubtask(new Subtask("a", "b", TaskStatus.NEW, e1));
        int s2 = tm.addSubtask(new Subtask("a", "b", TaskStatus.NEW, e1));
        int s3 = tm.addSubtask(new Subtask("a", "b", TaskStatus.NEW, e1));
        int e2 = tm.addEpic(new Epic("a", "b", TaskStatus.NEW));

        System.out.println(tm.getHistory());

        tm.getTask(t1);
        System.out.println(tm.getHistory());
        tm.getEpic(e1);
        System.out.println(tm.getHistory());
        tm.getSubtask(s1);
        System.out.println(tm.getHistory());
        tm.getSubtask(s2);
        System.out.println(tm.getHistory());
        tm.getSubtask(s3);
        System.out.println(tm.getHistory());
        tm.getTask(t1);
        System.out.println(tm.getHistory());
        tm.getSubtask(s3);
        System.out.println(tm.getHistory());
        tm.getEpic(e2);
        System.out.println(tm.getHistory());

        tm.clearSubtasks();
        tm.removeTask(t2);
        System.out.println(tm.getHistory());
        tm.removeTask(t1);
        System.out.println(tm.getHistory());

    }
}
