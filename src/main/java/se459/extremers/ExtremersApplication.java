package se459.extremers;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import net.minidev.json.JSONObject;
import se459.extremers.logger.RecordLog;
import se459.extremers.logger.RecordLogRepository;

@SpringBootApplication
public class ExtremersApplication {
	private static final Logger log = LoggerFactory.getLogger(ExtremersApplication.class);

    @Bean
    public CommandLineRunner navigateNodes(RecordLogRepository repository) {
        log.info("--- printRecordLogs ---");
        return (args) -> {
                      


            log.info("---");
        };
    }

	@Bean
    public CommandLineRunner printRecordLogs(RecordLogRepository repository) {
        log.info("--- printRecordLogs ---");
        return (args) -> {
            // create a JSON Object to save under content key
            String content;
            JSONObject json = new JSONObject();
            
            // add fields below: schema can be fluid throughout development
            json.put("createdOn", LocalDateTime.now().toString());
            json.put("action", "SWEEP");
            json.put("type", "ERROR");
            json.put("message", "Ran out of power during Sweep operation");

            // cast to string
            content = json.toString();

            // make a new log object and add id, content strings
            RecordLog newLog = new RecordLog("1", content); 

            // adds to mongodb
            repository.save(newLog);

            // looks up RecordLog id=1 in db and writes console
            log.info(String.valueOf(repository.findById("1")));
            log.info("---");
        };
    }


	public static void main(String[] args) {
		SpringApplication.run(ExtremersApplication.class, args);
	}

}
