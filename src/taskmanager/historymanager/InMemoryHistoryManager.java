package taskmanager.historymanager;

import taskmanager.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    HashMap<Integer, Node<Task>> nodesById = new HashMap<>();
    Node<Task> head;
    Node<Task> tail;

    public Node<Task> removeNode(Node<Task> node) {
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

    @Override
    public void remove(int id) {
        removeNode(nodesById.get(id));
    }

    @Override
    public void add(Task task) {
        removeNode(nodesById.get(task.getId()));

        Node<Task> added = new Node<>(task, null, null);
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

    @Override
    public ArrayList<Task> getHistory() {
        final ArrayList<Task> result = new ArrayList<>();
        Node<Task> temp = head;
        while (temp != null) {
            result.add(temp.data);
            temp = temp.next;
        }
        return result;
    }
}
