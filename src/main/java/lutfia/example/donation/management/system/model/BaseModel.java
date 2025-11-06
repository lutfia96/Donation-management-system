package lutfia.example.donation.management.system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lutfia.example.donation.management.system.configs.TrackableEntityListener;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners({ AuditingEntityListener.class, TrackableEntityListener.class })
public abstract class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, updatable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
    private OffsetDateTime createdAt;


    @CreatedBy
    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "created_by",nullable = true)
    private String createdBy;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @LastModifiedBy
    @JsonIgnore
    @Column(name = "updated_by",nullable = true)
    private String updatedBy;

  //  @Transient private String removedBy;

    public <T> T toDao(T dao) {
        if (dao == null) return null;
        BeanUtils.copyProperties(this, dao);
        return dao;
    }

    public <T> T toDao(Class<T> claz) {
        T dao = null;
        try {
            dao = claz.newInstance();
            BeanUtils.copyProperties(this, dao);
        } catch (Exception e) {
        }
        return dao;
    }

    public <T> T toDto(T dto) {
        BeanUtils.copyProperties(this, dto);
        return dto;
    }

    public <T> BaseModel copyFromDto(T dto) {
        BeanUtils.copyProperties(dto, this, "uuid", "id");
        return this;
    }

    public static <T extends BaseModel, Z> T newFromDto(Z dao) {
        try {
            // Create a new instance using reflection
            T instance = (T) Class.forName(BaseModel.class.getName()).newInstance();
            BeanUtils.copyProperties(dao, instance, "uuid", "id");
            return instance;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return null;
    }

    @Column(name = "is_deleted")
    private boolean isDeleted;



}

