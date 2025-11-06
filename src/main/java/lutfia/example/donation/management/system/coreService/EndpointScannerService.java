package lutfia.example.donation.management.system.coreService;



import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.endpoint.api.DonorsApi;
import lutfia.example.donation.management.system.endpoint.api.RoleApi;
import lutfia.example.donation.management.system.model.Permission;
import lutfia.example.donation.management.system.repository.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class EndpointScannerService {

//    @Autowired
//    private PermissionCategoryRepo permissionCategoryRepository;
//
    @Autowired
    private PermissionRepo permissionRepository;

    /**
     * Scans all interfaces implemented by controllers and extracts simplified permissions.
     */
    public void scanAndInsertPermissions() {
        // Get all interfaces implemented by controllers
        Set<Class<?>> apiInterfaces = getApiInterfaces();
        log.info("Found {} controllers.", apiInterfaces.size());

        // Scan each interface
        for (Class<?> apiInterface : apiInterfaces) {
            // Extract the category name from the interface's RequestMapping
            String categoryName = getCategoryName(apiInterface);
            log.info("Scanning controller: {} for category: {}", apiInterface.getSimpleName(), categoryName);

            // Get all methods in the interface
            for (Method method : apiInterface.getDeclaredMethods()) {

                // Check if the method is annotated with a request mapping
                if (method.isAnnotationPresent(GetMapping.class) ||
                        method.isAnnotationPresent(PostMapping.class) ||
                        method.isAnnotationPresent(PutMapping.class) ||
                        method.isAnnotationPresent(DeleteMapping.class)) {

                    // Extract the HTTP method and endpoint
                    String httpMethod = getHttpMethod(method);
                    String endpoint = getEndpoint(method);

                    // Map the endpoint to a simplified permission name
                    String permissionName = mapToSimplifiedPermissionName(httpMethod, endpoint);

                    // Insert the permission into the database
                    if (permissionName != null) {
                        insertPermission(categoryName, permissionName);
                    }
                }
            }
        }
    }

    /**
     * Inserts a permission into the database if it doesn't already exist.
     */
    private void insertPermission(String categoryName, String permissionName) {
//        // Insert or fetch the category
//        PermissionCategory category = permissionCategoryRepository.findByName(categoryName);
//        if (category == null) {
//            category = new PermissionCategory();
//            category.setName(categoryName);
//            category.setPermissions(new ArrayList<>());
//            permissionCategoryRepository.save(category);
//        }

        // Insert the permission
        Permission existingPermission = permissionRepository.findByName(permissionName);
        if (existingPermission == null) {
            Permission permission = new Permission();
            permission.setName(permissionName);
            permission.setName(permissionName);//formatTitle(permissionName));
           // permission.setPermissionCategory(category);
            permissionRepository.save(permission);

            // Add the permission to the category's list
           // category.getPermissions().add(permission);
        }

        // Save the updated category
       // permissionCategoryRepository.save(category);
    }

    // Helper method to format title
    private String formatTitle(String permissionName) {
        return Arrays.stream(permissionName.split("-")) // Split by "-"
                .map(this::capitalizeFirstLetter) // Capitalize each word
                .collect(Collectors.joining(" ")); // Join with spaces
    }

    // Helper method to capitalize first letter
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }



    /**
     * Extracts the category name from the interface's RequestMapping.
     */
    private String getCategoryName(Class<?> apiInterface) {
        // Check for @RequestMapping on the interface
        RequestMapping requestMapping = apiInterface.getAnnotation(RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            String path = requestMapping.value()[0];

            // Remove the "/api/v1/" prefix
            if (path.startsWith("/api/v1/")) {
                path = path.replace("/api/v1/", "");
            }

            // Convert first letter to uppercase and the rest to lowercase
            return path.substring(0, 1).toUpperCase() + path.substring(1).toLowerCase();
        }
        return "Default"; // Fallback name
    }


    /**
     * Extracts the HTTP method from the method annotations.
     */
    private String getHttpMethod(Method method) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            return "GET";
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            return "POST";
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            return "PUT";
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            return "DELETE";
        }
        return "UNKNOWN";
    }

    /**
     * Extracts the endpoint path from the method annotations.
     */
    /**
     * Extracts the endpoint path from the method annotations.
     */
    private String getEndpoint(Method method) {
        // Get base path from the interface's RequestMapping
        String basePath = "";
        RequestMapping interfaceMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        if (interfaceMapping != null && interfaceMapping.value().length > 0) {
            basePath = interfaceMapping.value()[0];
        }

        // Append method-level path
        if (method.isAnnotationPresent(GetMapping.class)) {
            String[] values = method.getAnnotation(GetMapping.class).value();
            return basePath + (values.length > 0 ? values[0] : "");
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            String[] values = method.getAnnotation(PostMapping.class).value();
            return basePath + (values.length > 0 ? values[0] : "");
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            String[] values = method.getAnnotation(PutMapping.class).value();
            return basePath + (values.length > 0 ? values[0] : "");
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            String[] values = method.getAnnotation(DeleteMapping.class).value();
            return basePath + (values.length > 0 ? values[0] : "");
        }

        return basePath;
    }

    /**
     * Maps the HTTP method and endpoint to a simplified permission name.
     */
//    private String mapToSimplifiedPermissionName(String httpMethod, String endpoint) {
//        // Extract the last part of the endpoint (e.g., "ticket" from "/api/v1/ticket")
//        String[] parts = endpoint.split("/");
//        String resource = parts[parts.length - 1];
//
//        // Map to simplified permission name
//        switch (httpMethod.toUpperCase()) {
//            case "POST":
//                if(endpoint.contains("login")){
//                    return "auth-" + resource;
//                }else{
//                    return "add-" + resource;
//                }
//            case "PUT":
//                return "update-" + resource;
//            case "DELETE":
//                return "delete-" + resource;
//            case "GET":
//                if (endpoint.contains("{id}")) {
//                    return "view-" + resource;
//                } else if (endpoint.contains("all")) {
//                    return "view-all-" + resource + "s";
//                } else if (endpoint.contains("my")) {
//                    return "view-my-" + resource + "s";
//                } else {
//                    return "view-" + resource;
//                }
//            default:
//                return null;
//        }
//    }

    private String mapToSimplifiedPermissionName(String httpMethod, String endpoint) {
        // Split endpoint into parts and remove empty parts
        endpoint = endpoint.replaceFirst("^/api/v1/", "");
        String[] parts = endpoint.split("/");

        // Extract the actual resource name
        String resource = extractResource(parts);

        // Convert endpoint path to a hyphen-separated format
        String formattedPath = formatPath(parts);

        // Map to simplified permission name
        switch (httpMethod.toUpperCase()) {
            case "POST":
                if (endpoint.contains("login")) {
                    return "auth-" + formattedPath;
                } else {
                    return "create-" + formattedPath;
                }
            case "PUT":
                return "update-" + formattedPath;
            case "DELETE":
                return "delete-" + formattedPath;
            case "GET":
                if (endpoint.matches(".*/\\{id\\}$")) {
                    return "view-" + formattedPath;
                } else {
                    return "view-" + formattedPath;
                }
            default:
                return null;
        }
    }

    // Helper method to extract the correct resource name
    private String extractResource(String[] parts) {
        for (int i = parts.length - 1; i >= 0; i--) {
            if (!parts[i].isEmpty() && !parts[i].startsWith("{") && !isCommonWord(parts[i])) {
                return parts[i];
            }
        }
        return "Unknown"; // Fallback in case extraction fails
    }

    // Helper method to check if a word is a common non-resource term
    private boolean isCommonWord(String word) {
        return word.equalsIgnoreCase("all") || word.equalsIgnoreCase("my") || word.equalsIgnoreCase("by-support") || word.equalsIgnoreCase("level");
    }

    // Helper method to capitalize the first letter of each word and join with "-"
    private String formatPath(String[] parts) {
        return Arrays.stream(parts)
                .filter(part -> !part.isEmpty() && !part.startsWith("{")) // Remove empty and path variables
                .map(this::lowerFirstLetter) // Capitalize each part
                .collect(Collectors.joining("-")); // Join with "-"
    }

    // Helper method to capitalize the first letter
    private String lowerFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }


    /**
     * Returns a set of interfaces implemented by controllers.
     */
    private Set<Class<?>> getApiInterfaces() {
        // Example: Manually list your API interfaces
        Set<Class<?>> apiInterfaces = new HashSet<>();
          apiInterfaces.add(RoleApi.class);
          apiInterfaces.add(DonorsApi.class);
//        apiInterfaces.add(RolePermissionAPI.class);
//        apiInterfaces.add(TicketApi.class);
//        apiInterfaces.add(UserApi.class);
//        apiInterfaces.add(MinistryApi.class);
//        apiInterfaces.add(InstitutionApi.class);
//        apiInterfaces.add(LevelApi.class);
//        apiInterfaces.add(PermissionAPI.class);
//        apiInterfaces.add(SystemApi.class);
        // Add more interfaces here
        return apiInterfaces;
    }
}
