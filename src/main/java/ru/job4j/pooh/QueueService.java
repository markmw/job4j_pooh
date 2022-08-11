package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedDeque<String>> store = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String type = req.getHttpRequestType();
        String source = req.getSourceName();
        Resp rsl = new Resp("", ResponseMessage.NOT_IMPL.getValue());
        if (ResponseMessage.GET.getValue().equals(type)) {
            String value = store.getOrDefault(source, new ConcurrentLinkedDeque<>()).poll();
            String status = ResponseMessage.OK.getValue();
            String text = value;
            if (value == null) {
                status = ResponseMessage.NOT_FOUND.getValue();
                text = ResponseMessage.EMPTY.getValue();
            }
            rsl = new Resp(text, status);
        } else if (ResponseMessage.POST.getValue().equals(type)) {
            String param = req.getParam();
            store.putIfAbsent(source, new ConcurrentLinkedDeque<>());
            store.get(source).add(param);
            rsl = new Resp(req.getParam(), ResponseMessage.OK.getValue());
        }
        return rsl;
    }
}
