package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedDeque<String>>>
            store = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String type = req.getHttpRequestType();
        String source = req.getSourceName();
        String param = req.getParam();
        Resp rsl = new Resp("", ResponseMessage.NOT_IMPL.getValue());
        if (ResponseMessage.GET.getValue().equals(type)) {
            String status = ResponseMessage.OK.getValue();
            String text;
            store.putIfAbsent(source, new ConcurrentHashMap<>());
            store.get(source).putIfAbsent(param, new ConcurrentLinkedDeque<>());
            String answer = store.get(source).get(param).poll();
            text = answer;
            if (answer == null) {
                status = ResponseMessage.NOT_FOUND.getValue();
                text = ResponseMessage.EMPTY.getValue();
            }
            rsl = new Resp(text, status);
        } else if (ResponseMessage.POST.getValue().equals(type)) {
            ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> topic = store.get(req.getSourceName());
            String status;
            if (topic != null) {
                topic.forEachValue(1, x -> x.add(req.getParam()));
                status = ResponseMessage.OK.getValue();
            } else {
                status = ResponseMessage.NOT_FOUND.getValue();
            }
            rsl = new Resp(ResponseMessage.EMPTY.getValue(), status);
        }
        return rsl;
    }
}
