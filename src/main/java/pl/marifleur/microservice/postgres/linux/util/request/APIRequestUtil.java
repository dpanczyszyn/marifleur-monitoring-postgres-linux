package pl.marifleur.microservice.postgres.linux.util.request;

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

    public ResponseEntity<String> sendAndReceiveResponse(
            String url,
            HttpMethod httpMethod,
            HttpHeaders httpHeaders,
            Object body) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity requestEntity = null;
        if (httpMethod == HttpMethod.GET || httpMethod == HttpMethod.DELETE) {
            requestEntity = RequestEntity
                    .method(httpMethod, url)
                    .headers(httpHeaders)
                    .build();
        } else if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) {
            String bodyAsJson = objectMapper.writeValueAsString(body);

            requestEntity = RequestEntity
                    .method(httpMethod, url)
                    .headers(httpHeaders)
                    .body(bodyAsJson);
        }

        RequestCallback requestCallback = restTemplate.httpEntityCallback(requestEntity);
        ResponseExtractor<ResponseEntity<String>> responseExtractor = restTemplate.responseEntityExtractor(String.class);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor);
    }
}
