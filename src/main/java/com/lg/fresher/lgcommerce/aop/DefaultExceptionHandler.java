package com.lg.fresher.lgcommerce.aop;

import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.exception.BusinessException;
import com.lg.fresher.lgcommerce.exception.DuplicateDataException;
import com.lg.fresher.lgcommerce.exception.auth.RefreshTokenException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.exception.InvalidRequestException;
import com.lg.fresher.lgcommerce.exception.data.DataNotFoundException;
import com.lg.fresher.lgcommerce.exception.data.DeactiveDataException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;


@ControllerAdvice
@SuppressWarnings("JavaDoc")
public class DefaultExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    public static final String SEPARATOR_COMMA = ",";

    public static String getRootCauseMessage(Throwable t) {
        List<Throwable> list = ExceptionUtils.getThrowableList(t);
        Assert.notEmpty(list, () -> "No exception information");
        return list.get(list.size() - 1).getMessage();
    }

    public static String getBindingError(BindingResult result) {
        if (result == null || !result.hasErrors()) {
            return null;
        }
        List<ObjectError> errors = result.getAllErrors();
        List<String> allErrors = new ArrayList<>(errors.size());
        for (ObjectError error : errors) {
            if (StringUtils.isNotEmpty(error.getDefaultMessage())) {
                allErrors.add(error.getDefaultMessage().replace("\"", "'"));
            }
        }
        return StringUtils.join(allErrors, SEPARATOR_COMMA);
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Object validExceptionHandler(Exception ex) {
        BindingResult br = null;
        if (ex instanceof BindException) {
            br = ((BindException) ex).getBindingResult();
        }
        Map<String, Object> map = new HashMap<>(8);
        if (br != null && br.hasErrors()) {
            map.put("code", Status.FAIL_VALIDATION.code());
            String validateErrorMsg = getBindingError(br);
            map.put("msg", validateErrorMsg);
            map.put("ok", false);
            map.put("data", null);
            log.warn("Data validation failed, {}: {}", br.getObjectName(), validateErrorMsg);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        List<String> errors = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("code", Status.FAIL_INVALID_PARAM.code());
        map.put("msg", Status.FAIL_INVALID_PARAM);
        map.put("ok", false);

        String errorMessage = String.format("Data validation failed, '%s': required '%s'",
                ex.getName(),
                Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
        map.put("errors", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public Object handleDataNotFoundException(HttpServletRequest request, Exception e) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map;
        if (e instanceof DataNotFoundException) {
            map = ((DataNotFoundException) e).toMap();
        } else if (e.getCause() instanceof DataNotFoundException) {
            map = ((DataNotFoundException) e.getCause()).toMap();
        } else {
            map = buildGeneralErrorResponse(e, status);
        }
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeactiveDataException.class)
    public Object handleDeactiveDataException(HttpServletRequest request, Exception e) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map;
        if (e instanceof DataNotFoundException) {
            map = ((DataNotFoundException) e).toMap();
        } else if (e.getCause() instanceof DataNotFoundException) {
            map = ((DataNotFoundException) e.getCause()).toMap();
        } else {
            map = buildGeneralErrorResponse(e, status);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public Object handleDuplicateDataException(HttpServletRequest request, Exception e) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map;
        if (e instanceof DuplicateDataException) {
            map = ((DuplicateDataException) e).toMap();
        } else if (e.getCause() instanceof DataNotFoundException) {
            map = ((DuplicateDataException) e.getCause()).toMap();
        } else {
            map = buildGeneralErrorResponse(e, status);
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public Object handleInvalidRequestException(HttpServletRequest request, Exception e) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map;
        if (e instanceof InvalidRequestException) {
            map = ((InvalidRequestException) e).toMap();
        } else if (e.getCause() instanceof InvalidRequestException) {
            map = ((InvalidRequestException) e.getCause()).toMap();
        } else {
            map = buildGeneralErrorResponse(e, status);
        }
        log.warn("Request processing exception", e);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public Object handleAuthorizationDeniedException(HttpServletRequest request, Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", Status.FAIL_NO_PERMISSION.code());
        map.put("msg", Status.FAIL_NO_PERMISSION);
        map.put("ok", false);
        map.put("data", null);
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, Exception e) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map;
        if (e instanceof BusinessException) {
            map = ((BusinessException) e).toMap();
        } else if (e.getCause() instanceof BusinessException) {
            map = ((BusinessException) e.getCause()).toMap();
        } else if (e.getCause() instanceof InvalidRequestException) {
            map = ((InvalidRequestException) e.getCause()).toMap();
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } else {
            map = buildGeneralErrorResponse(e, status);
        }
        log.warn("Request processing exception", e);
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public Object handleSearchException(HttpServletRequest request, Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", Status.FAIL_SEARCH_INVALID_PARAM.label());
        map.put("code", Status.FAIL_SEARCH_INVALID_PARAM.code());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefreshTokenException.class)
    public Object handleRefreshTokenException(HttpServletRequest request, Exception e) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map;
        if (e instanceof RefreshTokenException) {
            map = ((RefreshTokenException) e).toMap();
        } else if (e.getCause() instanceof RefreshTokenException) {
            map = ((RefreshTokenException) e.getCause()).toMap();
        } else {
            map = buildGeneralErrorResponse(e, status);
        }
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    private Map<String, Object> buildGeneralErrorResponse(Exception e, HttpStatus status) {
        Map<String, Object> map;
        map = new HashMap<>(8);
        map.put("code", status.value());
        String msg = buildMsg(status, e);
        map.put("msg", msg);
        map.put("ok", false);
        map.put("data", null);
        return map;
    }

    protected String buildMsg(HttpStatus status, Exception e) {

        return e == null ? status.getReasonPhrase() : getRootCauseMessage(e);
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
