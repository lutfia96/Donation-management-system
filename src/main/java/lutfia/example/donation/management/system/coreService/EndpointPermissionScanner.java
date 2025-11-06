//package lutfia.example.donation.management.system.coreService;
//
//
//import jakarta.persistence.EntityListeners;
//import lutfia.example.donation.management.system.configs.TrackableEntityListener;
//import lutfia.example.donation.management.system.model.Permission;
//import lutfia.example.donation.management.system.repository.PermissionRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//import org.springframework.web.util.pattern.PathPattern;
//
//import java.util.Map;
//import java.util.Set;
//
//@Component
//@EntityListeners({ AuditingEntityListener.class, TrackableEntityListener.class })
//public class EndpointPermissionScanner implements ApplicationListener<ApplicationReadyEvent> {
//
//    @Autowired
//    private RequestMappingHandlerMapping handlerMapping;
//
//    @Autowired
//    private PermissionRepo permissionRepository;
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
//
//        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
//            RequestMappingInfo info = entry.getKey();
//            Set<PathPattern> paths = info.getPathPatternsCondition().getPatterns();
//            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
//
//            for (PathPattern path : paths) {
//                for (RequestMethod method : methods) {
//                    String permissionName = method.name() + ":" + path;
//
//                    permissionRepository.findByName(permissionName)
//                            .orElseGet(() -> permissionRepository.save(new Permission(permissionName, "Auto-generated")));
//                }
//            }
//        }
//    }
//}
