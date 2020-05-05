package repository;

import model.EventToSave;

public interface EventRepository {
    void addEvent(EventToSave eventToSave);
}
