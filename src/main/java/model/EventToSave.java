package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventToSave {
    private String id;
    private Integer duration;
    private String type;
    private String host;
    private boolean alert;
}