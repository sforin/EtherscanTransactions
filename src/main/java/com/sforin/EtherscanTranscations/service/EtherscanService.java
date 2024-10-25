package com.sforin.EtherscanTranscations.service;

import com.sforin.EtherscanTranscations.model.EtherscanResponse;
import com.sforin.EtherscanTranscations.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Transaction> getTransactions(String address) {
        String url = etherscanApiUrl.replace("<ADDRESS>", address)
                                    .replace("<APIKEY>", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EtherscanResponse> response = restTemplate.getForEntity(url, EtherscanResponse.class);

        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            List<Transaction> results = response.getBody().getResult();
            return response.getBody().getResult();
        }
        else{
            return Collections.emptyList();
        }
    }
}
