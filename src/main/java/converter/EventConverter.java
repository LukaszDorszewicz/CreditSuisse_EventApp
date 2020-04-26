package converter;

import com.google.gson.Gson;
import model.EventFromJson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventConverter {
    private static final String FILE_NAME = "events.json";

    public static List<EventFromJson> getEventsFromJson() {
        List<EventFromJson> events = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            events = stream
                    .map(v -> new Gson().fromJson(v, EventFromJson.class))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

}
