package com.lg.fresher.lgcommerce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.fresher.lgcommerce.constant.Status;
import com.lg.fresher.lgcommerce.model.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ResponseUtil {

    private ResponseUtil() {
        //Prevent from inheritance
    }

    /**
     * Exception message response (suitable for handling exception responses in filters)
     *
     * @param response   HttpServletResponse
     * @param resultCode Response result code
     */
    public static void writeErrMsg(HttpServletResponse response, Status resultCode) {
        // Set HTTP status based on different result codes
        int status =
                switch (resultCode) {
                    case FAIL_INVALID_ACCOUNT, FAIL_INVALID_TOKEN -> HttpStatus.UNAUTHORIZED.value();
                    case FAIL_TOKEN_ACCESS_FORBIDDEN -> HttpStatus.FORBIDDEN.value();
                    default -> HttpStatus.BAD_REQUEST.value();
                };

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        ObjectMapper mapper = new ObjectMapper();
        try (PrintWriter writer = response.getWriter()) {
            String jsonResponse = mapper.writeValueAsString(CommonResponse.fail(resultCode.label(), resultCode.code(), null));
            writer.print(jsonResponse);
            writer.flush(); // Ensure the response content is written to the output stream
        } catch (IOException e) {
            log.error("Exception handling response failed", e);
        }
    }
}
