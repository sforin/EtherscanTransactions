package com.sforin.EtherscanTranscations.service;

import com.sforin.EtherscanTranscations.dto.EtherscanResponseDTO;
import com.sforin.EtherscanTranscations.enums.ResponseDTOStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class EtherscanService {
    @Value("${etherscan.api.key}")
    private String apiKey;

    @Value("${etherscan.api.url}")
    private String etherscanApiUrl;

    /**
     *
     * @param address
     * @return
     */
    public ResponseEntity<EtherscanResponseDTO> getTransactions(String address) {
        String url = etherscanApiUrl.replace("<ADDRESS>", address)
                                    .replace("<APIKEY>", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        try{
            return restTemplate.getForEntity(url, EtherscanResponseDTO.class);

        }catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new EtherscanResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "Service unavailable, please try again later", null));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new EtherscanResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "HTTP error occurred: " + e.getStatusCode(), null));
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EtherscanResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "An internal server error occurred", null));
        }
    }
}
