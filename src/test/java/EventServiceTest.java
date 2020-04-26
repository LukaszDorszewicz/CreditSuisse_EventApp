import model.EventFromJson;
import model.EventToSave;
import org.junit.Before;
import org.junit.Test;
import service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EventServiceTest {

    private List<EventFromJson> eventsFromJson;
    private EventService eventService = new EventService();

    @Before
    public void init() {
        eventsFromJson = new ArrayList<>();
        eventsFromJson.add(EventFromJson.builder()
                .id("scsmbstgra")
                .state("STARTED")
                .timestamp(1491377495212L)
                .type("APPLICATION_LOG")
                .host("12345")
                .build());

        eventsFromJson.add(EventFromJson.builder()
                .id("scsmbstgrb")
                .state("STARTED")
                .timestamp(1491377495213L)
                .build());

        eventsFromJson.add(EventFromJson.builder()
                .id("scsmbstgrc")
                .state("FINISHED")
                .timestamp(1491377495218L)
                .build());

        eventsFromJson.add(EventFromJson.builder()
                .id("scsmbstgra")
                .state("FINISHED")
                .timestamp(1491377495217L)
                .type("APPLICATION_LOG")
                .host("12345")
                .build());

        eventsFromJson.add(EventFromJson.builder()
                .id("scsmbstgrc")
                .state("STARTED")
                .timestamp(1491377495210L)
                .build());

        eventsFromJson.add(EventFromJson.builder()
                .id("scsmbstgrb")
                .state("FINISHED")
                .timestamp(1491377495216L)
                .build());
    }

    @Test
    public void shouldHaveSameIdAsEventsFromFile() {

        //given
        List<EventFromJson> eventsWithSameId = eventsFromJson
                .stream()
                .filter(e -> e.getId().equals("scsmbstgra"))
                .collect(Collectors.toList());

        //when
        EventToSave eventToSave = eventService.createFinalEvent(eventsWithSameId);

        //then
        assertEquals(eventToSave.getId(), eventsWithSameId.get(0).getId());
    }

    @Test
    public void shouldReturnEventsToSaveListSizeHalvedThanEventsFromFileSize() {

        //when
        List<EventToSave> eventToSaveList = eventService.convertToFinalEvents(eventsFromJson);

        //then
        assertEquals(eventsFromJson.size(), 6);
        assertEquals(eventToSaveList.size(), 3);
    }
}
