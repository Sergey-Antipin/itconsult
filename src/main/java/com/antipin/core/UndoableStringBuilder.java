package com.antipin.core;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/*
For use with UTF8 only
*/

public class UndoableStringBuilder {

    private byte[] value;

    private int count;

    private final StateHistory history = new StateHistory();

    public UndoableStringBuilder() {
        value = new byte[16];
    }

    public UndoableStringBuilder(String s) {
        int len = s.length();
        int capacity = (len < Integer.MAX_VALUE - 16)
                ? len + 16 : Integer.MAX_VALUE;
        value = new byte[capacity];
        append(s);
    }

    public UndoableStringBuilder append(Object o) {
        return append(String.valueOf(o));
    }

    public UndoableStringBuilder append(String s) {
        save();
        if (s == null) {
            s = "null";
        }
        int len = s.length();
        ensureCapacity(count + len);
        System.arraycopy(s.getBytes(StandardCharsets.UTF_8), 0, value, count, len);
        count += len;
        return this;
    }

    public UndoableStringBuilder append(char c) {
        save();
        ensureCapacity(count + 1);
        value[count++] = (byte) c;
        return this;
    }

    public void ensureCapacity(int minCapacity) {
        int oldCapacity = value.length;
        if (minCapacity > oldCapacity) {
            value = Arrays.copyOf(value, minCapacity);
        }
    }

    @Override
    public String toString() {
        if (count == 0) {
            return "";
        }
        return new String(Arrays.copyOfRange(value, 0, count));
    }

    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }

    public UndoableStringBuilder undo() {
        Snapshot snapshot = history.restore();
        if (snapshot != null) {
            count = snapshot.count();
            value = Arrays.copyOf(snapshot.value(), count);
        }
        return this;
    }

    private void save() {
        history.save(new Snapshot(value, count));
    }

    private record Snapshot(byte[] value, int count) {

    }

    private static class StateHistory {

        private final Deque<Snapshot> stack = new LinkedList<>();

        private void save(Snapshot snapshot) {
            stack.push(snapshot);
        }

        private Snapshot restore() {
            return stack.poll();
        }
    }
}
