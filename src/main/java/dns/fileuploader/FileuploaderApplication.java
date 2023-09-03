package dns.fileuploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FileuploaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileuploaderApplication.class, args);
    }

}
