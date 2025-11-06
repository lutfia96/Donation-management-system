package lutfia.example.donation.management.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donors extends BaseModel {

    private String name;
    private String contact;
    private String email;
    private String address;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private Users user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "donor")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference

    private List<Donation> donations;

}
