package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CASMap {
    public static void main(String[] args) {
        ConcurrentHashMap queue = new ConcurrentHashMap<>();
        String name = "weather";
        /* add if empty */
        queue.putIfAbsent(name, new ConcurrentLinkedDeque<>());
        /* put */
        queue.get(name).add(value);
        /* extract */
        var text = queue.get(name, emptyQueue()).poll();
    }
}
