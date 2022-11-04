package com.techelevator.tebucks;

import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.TxLogDto;
import org.jboss.logging.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestInternalRevenueService implements InternalRevenueService {
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public static String API_BASE_URL = "https://te-pgh-api.azurewebsites.net/";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private TxLogDto txLogDto;

    @Override
    public TxLogDto logTransfer(TxLogDto txLogDto) {


        HttpEntity<TxLogDto> entity = new HttpEntity<>(txLogDto, makeEntity(txLogDto).getHeaders());


        String url = API_BASE_URL;

        try {
            ResponseEntity<TxLogDto> response = restTemplate.exchange(url + "api/TxLog", HttpMethod.POST, entity, TxLogDto.class);

            return response.getBody();

        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + ":" + e.getStatusText());


        } catch (ResourceAccessException e) {

            System.out.println(e.getMessage());
        }
        return null;
    }
    private HttpEntity<TxLogDto> makeEntity(TxLogDto txLogDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(txLogDto, headers);
    }


}

