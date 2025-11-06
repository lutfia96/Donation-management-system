package lutfia.example.donation.management.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long donorId;
    private String name;
    private String purpose;
    private Double amount ;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private Donors donor;
}
