package de.szut.lf8_starter.employee_management;

import de.szut.lf8_starter.employee_management.model.EmployeeResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeManagementApiClient {

    private final RestTemplate restTemplate;
    private final String jwt;

    private String baseUrl = "https://employee-api.szut.dev";

    public EmployeeManagementApiClient() {
        restTemplate = new RestTemplate();
        jwt = getJwt();
    }

    public Optional<EmployeeResponseDto> getEmployeeById(Long id) {
        try {
            var url = baseUrl + "/employees/" + id;

            var headers = new HttpHeaders();
            headers.setBearerAuth(jwt);  // Authorization: Bearer <token>

            var entity = new HttpEntity<>(headers);

            var response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    EmployeeResponseDto.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return Optional.of(response.getBody());
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

//
//    public boolean VerifyEmployeeExists(int employee_id) {
//
//    }
//
//    public boolean VerifyEmployeeIsQualified(int employee_id, int... qualifications) {
//
//    }

    public String getJwt() {
        var baseUrl = "https://authentik.szut.dev";

        var clientId = "hitec_api_client";
        var username = "john";
        var appPassword = "nt7su3vuTaxtsmKdlhr2RCbRD4tis5i7zBFJbbTWyeTjrRqTpQ513z73ZlV3";

        var tokenUrl = baseUrl + "/application/o/token/";

        var form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("username", username);
        form.add("password", appPassword);
        form.add("client_id", clientId);
        form.add("scope", "openid");

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var request = new HttpEntity<>(form, headers);

        var response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new RuntimeException("Failed to get JWT: " + response.getStatusCode());
        }
    }
}
