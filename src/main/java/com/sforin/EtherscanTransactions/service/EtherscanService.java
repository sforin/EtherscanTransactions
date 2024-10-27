package com.sforin.EtherscanTransactions.service;

import com.sforin.EtherscanTransactions.dto.EtherscanResponseDTO;
import com.sforin.EtherscanTransactions.enums.ResponseDTOStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Service class to interact with the Etherscan API for retrieving
 * Ethereum transactions and balances.
 */
@Service
public class EtherscanService {
    @Value("${etherscan.api.key}")
    private String apiKey;

    @Value("${etherscan.api.url}")
    private String etherscanApiUrl;

    @Value("${etherscan.api.paging.url}")
    private String etherscanApiPagingUrl;

    /**
     * Retrieves transactions from the Etherscan API for the specified Ethereum address,
     * starting from a given block number.
     *
     * @param address     the Ethereum address for which to retrieve transactions
     * @param startblock  the block number from which to start fetching transactions
     * @return a ResponseEntity containing the EtherscanResponseDTO with the transaction data or an error response
     */
    public ResponseEntity<EtherscanResponseDTO> getTransactions(String address, Long startblock) {
        String url = etherscanApiUrl.replace("<ADDRESS>", address)
                .replace("<STARTBLOCK>", startblock.toString())
                .replace("<APIKEY>", apiKey);
        return getTransactions(url);
    }

    /**
     * Retrieves paginated transactions from the Etherscan API for the specified Ethereum address,
     * starting from a given block number and pagination parameters.
     *
     * @param address     the Ethereum address for which to retrieve transactions
     * @param startblock  the block number from which to start fetching transactions
     * @param numPages    the number of pages to retrieve
     * @param size        the number of transactions per page
     * @return a ResponseEntity containing the EtherscanResponseDTO with the transaction data
     *         or an error response
     */
    public ResponseEntity<EtherscanResponseDTO> getPagesTransactions(String address, Long startblock, int numPages, int size) {
        String url = etherscanApiPagingUrl.replace("<ADDRESS>", address)
                .replace("<STARTBLOCK>", startblock.toString())
                .replace("<PAGENUMBER>", Integer.toString(numPages))
                .replace("<OFFSET>", Integer.toString(size))
                .replace("<APIKEY>", apiKey);
        System.out.println(url);
        return getTransactions(url);
    }

    /**
     * Sends a GET request to the specified URL to retrieve transactions.
     *
     * @param url the URL to send the GET request to
     * @return a ResponseEntity containing the EtherscanResponseDTO with the transaction data
     *         or an error response
     */
    public ResponseEntity<EtherscanResponseDTO> getTransactions(String url) {

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
