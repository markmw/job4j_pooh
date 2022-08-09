package ru.job4j.pooh;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        Map<String, String> values = parse(content);
        return new Req(
                values.get("httpRequestType"),
                values.get("poohMode"),
                values.get("sourceName"),
                values.get("param")
        );
    }

    private static Map<String, String> parse(String content) {
        Map<String, String> result = new HashMap<>();
        Scanner scanner = new Scanner(content);
        String firstLine = scanner.nextLine();
        String[] massiveBySpaces = firstLine.split(" ", 3);
        Path sourcePath = Path.of(massiveBySpaces[1]);
        result.put("httpRequestType", massiveBySpaces[0]);
        result.put("poohMode", sourcePath.getName(0).toString());
        result.put("sourceName", sourcePath.getName(1).toString());
        if (sourcePath.getNameCount() > 2) {
            result.put("param", sourcePath.getName(2).toString());
        } else {
            result.put("param", getLastLine(scanner));
        }
        return result;
    }

    private static String getLastLine(Scanner scanner) {
        String current = scanner.nextLine();
        while (!current.isEmpty()) {
            current = scanner.nextLine();
        }
        StringBuilder result = new StringBuilder();
        while (scanner.hasNextLine()) {
            result.append(scanner.nextLine());
        }
        return result.toString();
    }

    public String getHttpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
