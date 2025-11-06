package lutfia.example.donation.management.system.coreService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PermissionInitializer implements CommandLineRunner {

    private final EndpointScannerService endpointScannerService;

    public PermissionInitializer(EndpointScannerService endpointScannerService) {
        this.endpointScannerService = endpointScannerService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Running PermissionInitializer...");
        endpointScannerService.scanAndInsertPermissions();
        log.info("PermissionInitializer completed.");
    }

}
