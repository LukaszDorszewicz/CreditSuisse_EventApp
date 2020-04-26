package service;

import connection.DatabaseConnection;
import converter.EventConverter;
import lombok.RequiredArgsConstructor;
import model.EventFromJson;
import model.EventToSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EventService {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final EventConverter eventConverter = new EventConverter();

    public void runApp() {
        try {
            var eventsFromJson = eventConverter.getEventsFromJson();
            List<EventToSave> finalEvents = convertToFinalEvents(eventsFromJson);

            logger.info("FILES TO PUT IN DATABASE");
            finalEvents.forEach(System.out::println);

            databaseConnection.createEventTable();
        } catch (SQLException e) {
            logger.error("");
        }
    }

    public List<EventToSave> convertToFinalEvents(List<EventFromJson> eventsFromJson) {
        return new ArrayList<>(eventsFromJson
                .stream()
                .collect(Collectors.groupingBy(EventFromJson::getId))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> createFinalEvent(v.getValue())
                ))
                .values());
    }

    public EventToSave createFinalEvent(List<EventFromJson> eventsWithSameId) {
        return EventToSave.builder()
                .id(eventsWithSameId.get(0).getId())
                .host(eventsWithSameId.get(0).getHost())
                .type(eventsWithSameId.get(0).getType())
                .duration(Math.abs(eventsWithSameId.get(0).getTimestamp().intValue()
                        - eventsWithSameId.get(1).getTimestamp().intValue()))
                .alert(Math.abs(eventsWithSameId.get(0).getTimestamp()
                        - eventsWithSameId.get(1).getTimestamp()) > 4L)
                .build();
    }
}
