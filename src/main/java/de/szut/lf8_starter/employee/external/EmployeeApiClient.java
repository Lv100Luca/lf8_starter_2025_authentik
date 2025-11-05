package de.szut.lf8_starter.employee.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeApiClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public EmployeeApiClient(@Value("${employee.api.base-url:https://employee-api.szut.dev}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    public String getAllEmployees(String bearerToken) {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + bearerToken);
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
        return restTemplate.exchange(
                baseUrl + "/employees",
                org.springframework.http.HttpMethod.GET,
                entity,
                String.class
        ).getBody();
    }

    public String getEmployeeById(Long id, String bearerToken) {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + bearerToken);
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
        return restTemplate.exchange(
                baseUrl + "/employees/" + id,
                org.springframework.http.HttpMethod.GET,
                entity,
                String.class
        ).getBody();
    }
}