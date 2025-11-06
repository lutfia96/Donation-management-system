package lutfia.example.donation.management.system.coreService;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object data;
    private Integer status;
    private String message;
    private int page;
    private int size;
    private Long total;
    private String[] errors;

    public static CustomApiResponse ok(Object data) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setData(data);
        return response;
    }
    public static CustomApiResponse errorHandle(String msg, int code) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(code);
        response.setMessage(msg);
        return response;
    }
    public static CustomApiResponse ok(String message, Object data) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static CustomApiResponse ok(String message, Object data, int page, int size, Long totalElements) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setData(data);
        response.setMessage(message);
        response.setTotal(totalElements);
        response.setPage(page);
        response.setSize(size);
        return response;
    }

    public static CustomApiResponse created(String message, Object data) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.CREATED.value());
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static CustomApiResponse accepted(String message, Object data) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.ACCEPTED.value());
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static CustomApiResponse badRequest(String message, Object data) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static <T> CustomApiResponse ok(Page<T> page) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setData(page.getContent());
        response.setPage(page.getNumber());
        response.setTotal(page.getTotalElements());
        response.setSize(page.getSize());
        return response;
    }

    public static CustomApiResponse ok(String message) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(message);
        return response;
    }

    public static CustomApiResponse noContent(String message) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.NO_CONTENT.value());
        response.setMessage(message);
        return response;
    }

    public static CustomApiResponse accessDenied(String message) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(message);
        return response;
    }

    public static CustomApiResponse notFound(String message) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(message);
        return response;
    }

    public static CustomApiResponse ok(Optional data) {
        if (data.isPresent()) {
            CustomApiResponse response = new CustomApiResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setData(data.get());
            return response;
        } else {
            throw new ValidationException("Resource not found");
        }

    }

    public static CustomApiResponse found(String message, String[] error) {
        CustomApiResponse response = new CustomApiResponse();
        response.setStatus(HttpStatus.FOUND.value());
        response.setMessage(message);
        response.setErrors(error);
        return response;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    public CustomApiResponse errors(String message) {
        this.message = message;
        return this;
    }
}

