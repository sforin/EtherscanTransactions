package com.sforin.EtherscanTransactions.service;

import com.sforin.EtherscanTransactions.dto.BalanceResponseDTO;
import com.sforin.EtherscanTransactions.enums.ResponseDTOStatus;
import com.sforin.EtherscanTransactions.model.Address;
import com.sforin.EtherscanTransactions.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AddressService {
    @Value("${etherscan.api.key}")
    private String apiKey;
    @Value("${etherscan.api.balance.url}")
    private String etherscanApiPagingUrl;
    @Autowired
    private AddressRepository addressRepository;

    public boolean existsAddress(String address) {
        return addressRepository.findByAddress(address).isPresent();
    }

    public Address getAddressById(int id) {
        return addressRepository.findById(id).orElse(new Address());
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public void addAddress(Address address) {
        addressRepository.save(address);
    }

    public void updateAddress(Address address) {
        addressRepository.save(address);
    }

    /**
     * Fetches the balance of the specified Ethereum address by making a
     * request to the Etherscan API.
     * <p>
     * This method constructs a request URL using the provided Ethereum address
     * and the API key, then sends a GET request to retrieve the balance. It
     * returns a ResponseEntity containing a BalanceResponseDTO object, which
     * includes the status of the response, a message, and the balance if
     * successfully retrieved. If an error occurs during the request, it
     * returns an appropriate HTTP status code along with a corresponding error
     * message.
     *
     * @param address The Ethereum address for which the balance is to be fetched.
     *                This should be a valid Ethereum address formatted as a
     *                string.
     * @return A ResponseEntity containing a BalanceResponseDTO. The response
     *         may have:
     *         - HTTP status 200 OK if the balance was fetched successfully,
     *         - HTTP status 503 Service Unavailable if the Etherscan service is
     *           down,
     *         - An appropriate HTTP error status if an HTTP error occurs during
     *           the request.
     */
    public ResponseEntity<BalanceResponseDTO> fetchBalance(String address) {
        String url = etherscanApiPagingUrl.replace("<ADDRESS>", address)
                .replace("<APIKEY>", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        try{
            return restTemplate.getForEntity(url, BalanceResponseDTO.class);

        }catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new BalanceResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "Service unavailable, please try again later", null));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new BalanceResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "HTTP error occurred: " + e.getStatusCode(), null));
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BalanceResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "An internal server error occurred", null));
        }
    }




}
