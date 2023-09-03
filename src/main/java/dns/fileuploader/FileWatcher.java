package dns.fileuploader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class FileWatcher {

    private final Path dirPath;

    public FileWatcher(@Value("${fileuploader.watch.path}") Path dirPath) {
        this.dirPath = dirPath;
    }

    @SuppressWarnings("unchecked")
    @Scheduled(fixedDelayString = "${fileuploader.watch.interval}")
    public void watch() throws IOException, InterruptedException {

        log.info("Monitoring [{}] for new files ...", dirPath);

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            dirPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key = watchService.take();

            if (nonNull(key)) {
                key.pollEvents().forEach(event -> {
                    Path filename = ((WatchEvent<Path>) event).context();
                    log.info("Found new file [{}]...", filename);
                });

                key.reset();
            }
        }
    }
}
