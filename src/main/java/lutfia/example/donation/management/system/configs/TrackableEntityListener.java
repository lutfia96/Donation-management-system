package lutfia.example.donation.management.system.configs;

import jakarta.persistence.PreRemove;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.model.BaseModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;

@Slf4j
public class TrackableEntityListener {

//    @PostPersist
//    public void postCreate(Object entity) {
//        track(entity, EntityAction.CREATE);
//    }
//
//    @PostUpdate
//    public void Update(Object entity) {
//        track(entity, EntityAction.UPDATE);
//    }
//
//    @PostRemove
//    public void postRemove(Object entity) {
//        track(entity, EntityAction.DELETE);
//    }

//    @PreRemove
//    public void preRemove(Object entity) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            try {
//                Field removedByField = BaseModel.class.getDeclaredField("removedBy");
//                removedByField.setAccessible(true);
//                removedByField.set(entity, auth.getName());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

//    @Async("taskExecutor")
//    @Transactional
//    public void track(Object entity, EntityAction action) {
//        AuditLogService service = BeanUtil.getBean(AuditLogService.class);
//        service.saveEntityLog(entity, action);
//    }
}
