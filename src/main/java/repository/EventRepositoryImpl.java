package repository;

import connection.DatabaseConnection;
import model.EventToSave;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventRepositoryImpl implements EventRepository {

    public void addEvent(EventToSave eventToSave) {
        final String sql = "insert into event(id,duration,type,host,alert) values (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, eventToSave.getId());
            preparedStatement.setInt(2, eventToSave.getDuration());
            preparedStatement.setString(3, eventToSave.getType());
            preparedStatement.setString(4, eventToSave.getHost());
            preparedStatement.setBoolean(5, eventToSave.isAlert());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
