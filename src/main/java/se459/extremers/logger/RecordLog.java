package se459.extremers.logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "logs")
public class RecordLog {
    @Id
    private String id;
    private String content;

    /*
        Sample RecordLog
        RecordLog(
            id=1,
            content={
                "action":"SWEEP",
                "type":"ERROR",
                "message":"Ran out of power during Sweep operation",
                "createdOn":"2021-10-11T11:21:50.217542"
            }
        )
    */
}
