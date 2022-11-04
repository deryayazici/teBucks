package com.techelevator.tebucks;

import com.techelevator.tebucks.model.CredentialsDto;
import com.techelevator.tebucks.model.TokenDto;
import org.jboss.logging.BasicLogger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationService {
    private static final String API_BASE_URL = "https://te-pgh-api.azurewebsites.net/";
    private final RestTemplate restTemplate = new RestTemplate();

    public String login(String team09, String password) {
        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setUsername(team09);
        credentialsDto.setPassword(password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CredentialsDto> entity = new HttpEntity<>(credentialsDto, headers);
        String token = null;
        try {
            ResponseEntity<TokenDto> response = restTemplate.exchange(API_BASE_URL + "api/Login", HttpMethod.POST, entity, TokenDto.class);
            TokenDto body = response.getBody();
            if (body != null) {
                token = body.getToken();
            }
        } catch (RestClientResponseException | ResourceAccessException e) {

        }
        return token;
    }

}
