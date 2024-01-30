package pl.marifleur.microservice.postgres.linux.util.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

public class APIRequestUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Object sendAndReceiveResponse(
            String url,
            HttpMethod httpMethod,
            HttpHeaders httpHeaders,
            Object body) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity requestEntity = null;
        if (httpMethod == HttpMethod.GET || httpMethod == HttpMethod.DELETE) {
            requestEntity = RequestEntity
                    .method(httpMethod, url)
                    .headers(httpHeaders)
                    .build();
        } else if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) {
            try {
                String bodyAsJson = objectMapper.writeValueAsString(body);

                requestEntity = RequestEntity
                        .method(httpMethod, url)
                        .headers(httpHeaders)
                        .body(bodyAsJson);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        RequestCallback requestCallback = restTemplate.httpEntityCallback(requestEntity);
        ResponseExtractor<ResponseEntity<String>> responseExtractor = restTemplate.responseEntityExtractor(String.class);
        ResponseEntity<String> responseEntity = restTemplate.execute(url, httpMethod, requestCallback, responseExtractor);

        return responseEntity;
    }
}
