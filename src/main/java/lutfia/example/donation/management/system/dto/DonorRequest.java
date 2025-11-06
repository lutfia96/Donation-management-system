package lutfia.example.donation.management.system.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class DonorRequest {
    private String name;
    private String contact;
    private String email;
    private String address;
    private Long userId;
    private OffsetDateTime createdAt;
   // private String createdBy;

    //private LocalDateTime updatedAt = LocalDateTime.now();

}
