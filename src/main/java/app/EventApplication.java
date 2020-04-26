package app;

import converter.EventConverter;
import service.EventService;

public class EventApplication {
    public static void main(String[] args) {

            var eventService = new EventService();
            eventService.runApp();
    }
}
