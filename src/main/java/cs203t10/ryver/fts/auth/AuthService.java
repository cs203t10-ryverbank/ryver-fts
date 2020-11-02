package cs203t10.ryver.fts.auth;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import cs203t10.ryver.fts.security.SecurityUtils;

import static cs203t10.ryver.fts.security.SecurityConstants.AUTH_HEADER_KEY;
import static cs203t10.ryver.fts.security.SecurityConstants.BEARER_PREFIX;

@Service
public class AuthService {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    private String getAuthHostUrl() {
        // Find an instance of the ryver market service
        List<ServiceInstance> instances = discoveryClient.getInstances("ryver-auth");
        if (instances.size() == 0) {
            //throw new NoInstanceException("ryver-auth");
            System.out.println("no ryver-auth");
        }

        return instances.get(0).getUri().toString();
    }

    private HttpEntity<String> getUserHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        String jwt = SecurityUtils.getCurrentSessionJWT();

        //set header to AUTH: Bearer ...
        headers.set(AUTH_HEADER_KEY, BEARER_PREFIX + jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return entity;
    }

    public boolean customerExists(Integer customerId) {
        String url = getAuthHostUrl();
        HttpEntity<String> req = getUserHttpEntity();
        try {
            restTemplate.exchange(url +  "/customers/{customerId}",
                    HttpMethod.GET, req, String.class, customerId);
            return true;
        } catch (RestClientResponseException e) {
            System.out.println("Is forbidden? " + (e.getRawStatusCode() == HttpStatus.FORBIDDEN.value()));
            return false;
        }
    }
}

