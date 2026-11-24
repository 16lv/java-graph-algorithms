package agency.shss7.algolab.structures;

import java.util.*;

/**
 * Binary min-heap supporting O(log V) decrease-key and priority updates via an
 * internal key-to-heap-index map. Essential for optimal Dijkstra and Prim runs.
 */
public class IndexedMinHeap<K> {

    public record Entry<K>(K key, double priority) {}

    private final List<Entry<K>> heap = new ArrayList<>();
    private final Map<K, Integer> positionByKey = new HashMap<>();

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public boolean contains(K key) {
        return key != null && positionByKey.containsKey(key);
    }

    public Entry<K> peekMin() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }

    public double priorityOf(K key) {
        Objects.requireNonNull(key, "Heap key must not be null");
        Integer index = positionByKey.get(key);
        if (index == null) {
            throw new NoSuchElementException("Key not present in heap: " + key);
        }
        return heap.get(index).priority();
    }

    public void insertOrDecreaseKey(K key, double priority) {
        Objects.requireNonNull(key, "Heap key must not be null");
        if (Double.isNaN(priority)) {
            throw new IllegalArgumentException("Priority must not be NaN");
        }
        Integer existingIndex = positionByKey.get(key);
        if (existingIndex != null) {
            if (priority < heap.get(existingIndex).priority()) {
                heap.set(existingIndex, new Entry<>(key, priority));
                siftUp(existingIndex);
            }
            return;
        }
        int idx = heap.size();
        heap.add(new Entry<>(key, priority));
        positionByKey.put(key, idx);
        siftUp(idx);
    }

    public Entry<K> pollMin() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        Entry<K> min = heap.get(0);
        int lastIdx = heap.size() - 1;
        Entry<K> last = heap.remove(lastIdx);
        positionByKey.remove(min.key());

        if (!heap.isEmpty()) {
            heap.set(0, last);
            positionByKey.put(last.key(), 0);
            siftDown(0);
        }
        return min;
    }

    /**
     * Removes a key from the heap in O(log V) time while preserving index bijection.
     */
    public boolean remove(K key) {
        if (key == null) {
            return false;
        }
        Integer idx = positionByKey.remove(key);
        if (idx == null) {
            return false;
        }
        int lastIdx = heap.size() - 1;
        Entry<K> last = heap.remove(lastIdx);
        if (idx < lastIdx) {
            heap.set(idx, last);
            positionByKey.put(last.key(), idx);
            siftUp(idx);
            siftDown(idx);
        }
        return true;
    }

    private void siftUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) >>> 1;
            if (heap.get(idx).priority() >= heap.get(parent).priority()) {
                break;
            }
            swap(idx, parent);
            idx = parent;
        }
    }

    private void siftDown(int idx) {
        int n = heap.size();
        while (true) {
            int left = (idx << 1) + 1;
            int right = left + 1;
            int smallest = idx;

            if (left < n && heap.get(left).priority() < heap.get(smallest).priority()) {
                smallest = left;
            }
            if (right < n && heap.get(right).priority() < heap.get(smallest).priority()) {
                smallest = right;
            }
            if (smallest == idx) {
                break;
            }
            swap(idx, smallest);
            idx = smallest;
        }
    }

    private void swap(int i, int j) {
        Entry<K> a = heap.get(i);
        Entry<K> b = heap.get(j);
        heap.set(i, b);
        heap.set(j, a);
        positionByKey.put(b.key(), i);
        positionByKey.put(a.key(), j);
    }
}
