package lutfia.example.donation.management.system.coreService;


import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.model.BaseModel;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class SimpleSearchService<T extends BaseModel> {

    public Specification<T> createSpecification(
            Class<T> entity, Map<String, String> search, Boolean isActive) {
        Specification<T> specification;
        specification = Specification.unrestricted();

        boolean isOr = search.containsKey("searchType") && search.get("searchType").equals("or");

        List<String> allowedProps =
                Arrays.stream(entity.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        if (!search.isEmpty()) {
            for (String key : search.keySet()) {
                if (allowedProps.contains(key) && !search.get(key).isEmpty()) {
                    try {
                        Field field = entity.getDeclaredField(key);
                        specification =
                                isOr
                                        ? specification.or(
                                        (root, query, builder) -> {
                                            if (field.getType() == Long.class) {
                                                return builder.equal(root.get(key), Long.parseLong(search.get(key)));
                                            } else if (field.getType() == Boolean.class) {
                                                Boolean val = search.get(key).equalsIgnoreCase("true");
                                                return builder.equal(root.get(key), val);
                                            } else {
                                                return builder.like(
                                                        builder.lower(root.get(key)),
                                                        "%" + search.get(key).toLowerCase() + "%");
                                            }
                                        })
                                        : specification.and(
                                        (root, query, builder) -> {
                                            if (field.getType() == Long.class) {
                                                return builder.equal(root.get(key), Long.parseLong(search.get(key)));
                                            } else if (field.getType() == Boolean.class) {
                                                Boolean val = search.get(key).equalsIgnoreCase("true");
                                                return builder.equal(root.get(key), val);
                                            } else {
                                                return builder.like(
                                                        builder.lower(root.get(key)),
                                                        "%" + search.get(key).toLowerCase() + "%");
                                            }
                                        });
                    } catch (Exception e) {
                        log.error(e.toString());
                    }
                }
            }
        }
        Specification<T> all = isActive ? getActiveEntries() : getDeletedEntries();
        return all.and(specification);
    }

    /*
     * Since we don't want others to change their implementations of this method
     * we need to overload this and pass true as the default value for isActive
     */
    public Specification<T> createSpecification(Class<T> entity, Map<String, String> search) {
        return createSpecification(entity, search, true);
    }

    public Specification<T> getActiveEntries() {
        return (root, query, builder) -> builder.equal(root.get("isDeleted"), false);
    }

    public Specification<T> getDeletedEntries() {
        return (root, query, builder) -> builder.equal(root.get("isDeleted"), true);
    }

}
