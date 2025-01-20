package taskmanager.historymanager;

import taskmanager.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    public static class TaskLinkedHashMap<T extends Task> {
        HashMap<Integer, Node<T>> nodesById = new HashMap<>();
        Node<T> head;
        Node<T> tail;

        public Node<T> removeNode(Node<T> node) {
            if (node == null) {
                return null;
            }
            nodesById.remove(node.data.getId());
            if (node != head) {
                node.prev.next = node.next;
            } else {
                head = node.next;
                if (head != null) {
                    head.prev = null;
                }
            }
            if (node != tail) {
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
                if (tail != null) {
                    tail.next = null;
                }
            }
            return node;
        }

        public void linkLast(T task) {
            removeNode(nodesById.get(task.getId()));

            Node<T> added = new Node<>(task, null, null);
            nodesById.put(task.getId(), added);

            if (tail == null) {
                head = added;
                tail = head;
            } else {
                added.prev = tail;
                tail.next = added;
                tail = added;
            }
        }

        public ArrayList<T> getTasks() {
            final ArrayList<T> result = new ArrayList<>();
            Node<T> temp = head;
            while (temp != null) {
                result.add(temp.data);
                temp = temp.next;
            }
            return result;
        }

        public int size() {
            return nodesById.size();
        }

        public Node<T> getNodeById(int id) {
            return nodesById.get(id);
        }
    }

    private final TaskLinkedHashMap<Task> history = new TaskLinkedHashMap<>();

    @Override
    public void add(Task task) {
        history.linkLast(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void remove(int id) {
        history.removeNode(history.getNodeById(id));
    }

    public Node<Task> removeNode(Node<Task> node) {
        return history.removeNode(node);
    }

}
