package com.example.callcenter1.config;

import com.example.callcenter1.model.log.Log;
import com.example.callcenter1.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.callcenter1.repository.operator.OperatorRepository;
import com.example.callcenter1.repository.customer.CustomerRepository;
import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.model.customer.Customer;
import java.util.Optional;
import com.example.callcenter1.security.JwtUtil;
import com.example.callcenter1.repository.operator.OperatorCustomerRepository;
import java.util.List;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Autowired
    private LogService logService;
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OperatorCustomerRepository operatorCustomerRepository;

    private String maskPasswordInJson(String requestBody) {
        // "password":"her_deger" -> "password":"*****"
        return requestBody.replaceAll(
            "(\\\"password\\\"\\s*:\\s*\\\")[^\\\"]*(\\\")",
            "$1*****$2"
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
        String requestBody = new String(wrappedRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        requestBody = maskPasswordInJson(requestBody); // Şifreyi maskele
        CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        Log log = new Log();
        log.setUri(request.getRequestURI());
        log.setMethod(request.getMethod());
        log.setRequestBody(requestBody);
        log.setResponseBody(responseBody);
        log.setResponseStatus(wrappedResponse.getStatus());
        log.setLogDescription("Otomatik log: " + request.getMethod() + " " + request.getRequestURI());

        // Kullanıcı emailini JWT'den al
        String email = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                email = jwtUtil.getOperatorNameFromToken(token);
            } catch (Exception ignored) {}
        }
        if (email != null && !email.equals("anonymousUser")) {
            Optional<Operator> operatorOpt = operatorRepository.findByOperatorEmail(email);
            operatorOpt.ifPresent(operator -> {
                log.setOperatorId(operator.getOperatorId());
                log.setOperator(operator);
                // operatorId'ye bağlı customerId'yi operator_customer tablosundan çek
                List<Integer> customerIds = operatorCustomerRepository.findCustomerIdsByOperatorId(operator.getOperatorId());
                if (customerIds != null && !customerIds.isEmpty()) {
                    log.setCustomerId(customerIds.get(0));
                }
            });
            Optional<Customer> customerOpt = customerRepository.findByCustomerEmail(email);
            customerOpt.ifPresent(customer -> {
                log.setCustomerId(customer.getCustomerId());
                log.setCustomer(customer);
            });
        }
        logService.createLog(log);

        // Response'u orijinal response'a yaz ve flush et
        byte[] content = wrappedResponse.getContentAsByteArray();
        response.getOutputStream().write(content);
        response.getOutputStream().flush();
    }

    // Request wrapper
    public static class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
        private byte[] cachedBody;
        public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            InputStream requestInputStream = request.getInputStream();
            this.cachedBody = requestInputStream.readAllBytes();
        }
        @Override
        public ServletInputStream getInputStream() {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }
                @Override
                public boolean isReady() {
                    return true;
                }
                @Override
                public void setReadListener(ReadListener listener) {}
            };
        }
        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }

    // Response wrapper
    public static class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {
        private ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private ServletOutputStream outputStream;
        private PrintWriter writer;
        public CachedBodyHttpServletResponse(HttpServletResponse response) {
            super(response);
        }
        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            if (this.outputStream == null) {
                this.outputStream = new ServletOutputStream() {
                    @Override
                    public void write(int b) throws IOException {
                        baos.write(b);
                    }
                    @Override
                    public boolean isReady() {
                        return true;
                    }
                    @Override
                    public void setWriteListener(WriteListener listener) {}
                };
            }
            return this.outputStream;
        }
        @Override
        public PrintWriter getWriter() throws IOException {
            if (this.writer == null) {
                this.writer = new PrintWriter(new OutputStreamWriter(baos, getCharacterEncoding()), true);
            }
            return this.writer;
        }
        public byte[] getContentAsByteArray() {
            return baos.toByteArray();
        }
    }
} 