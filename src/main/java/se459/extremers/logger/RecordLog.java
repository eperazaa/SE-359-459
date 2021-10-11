package se459.extremers.logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "logs")
public class RecordLog {
    @Id
    private String id;
    private String content;
    private LocalDateTime createdOn;

    static void logIt(String logId, String logContent, RecordLogRepository repository) {
        Optional<RecordLog> oldLog = repository.findById(logId);
        if (oldLog.isPresent()) {
            repository.delete(oldLog.get());
        }

        RecordLog recordLog = new RecordLog();
        recordLog.setId(logId);
        recordLog.setContent(logContent);
        recordLog.setCreatedOn(LocalDateTime.now());
        repository.save(recordLog);
    }
}
