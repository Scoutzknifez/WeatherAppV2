package com.scoutzknifez.weatherappv2.structures;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Queue<T> {
    private ArrayList<T> queue = new ArrayList<>();
    private int maxSize = 10;

    public Queue(int maxSize) {
        setMaxSize(maxSize);
    }

    public T peek() {
        return queue.size() > 0 ? queue.get(0) : null;
    }

    public void push(T newElement) {
        queue.add(0, newElement);
        if (queue.size() > maxSize)
            queue.remove(maxSize);
    }
}
