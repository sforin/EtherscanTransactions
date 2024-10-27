package com.sforin.EtherscanTranscations.service;

import com.sforin.EtherscanTranscations.dto.APIResponseDTO;
import com.sforin.EtherscanTranscations.dto.TransactionResponseDTO;
import com.sforin.EtherscanTranscations.enums.LogOperation;
import com.sforin.EtherscanTranscations.enums.ResponseDTOStatus;
import com.sforin.EtherscanTranscations.model.Address;
import com.sforin.EtherscanTranscations.dto.EtherscanResponseDTO;
import com.sforin.EtherscanTranscations.model.Transaction;
import com.sforin.EtherscanTranscations.repository.AddressRepository;
import com.sforin.EtherscanTranscations.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EtherscanService etherscanService;
    @Autowired
    private LogService logService;

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    /**
     *
     * @param address
     * @return
     */
    public List<TransactionResponseDTO> getTransactionsByAddress(String address){
        Optional<Address> currAddress = addressRepository.findByAddress(address);
        List<Transaction> currTransaction = transactionRepository.findByAddress(currAddress.orElseThrow().getId());
        return transactionRepository.getTransactionResponseDTO(currTransaction.get(0).getAddress());
    }

    /**
     *
     * @param ethereumAddress
     * @return
     */
    public ResponseEntity<APIResponseDTO> updateTransactions(String ethereumAddress){
        try {
            ResponseEntity<EtherscanResponseDTO> newTransactions = etherscanService.getTransactions(ethereumAddress);

            if(Objects.requireNonNull(newTransactions.getBody()).getStatus().equals(ResponseDTOStatus.ETHERSCAN_NOT_WORKING)) {
                APIResponseDTO apiResponseDTO = new APIResponseDTO(ResponseDTOStatus.ADDRESS_NOT_FOUND,
                        "ETHEREUM ADDRESS NOT FOUND",
                        Collections.emptyList());
                return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
            }
            //TODO: add comments
            Address address = addressRepository.findByAddress(ethereumAddress).orElseGet(() -> {
                Address newAddress = new Address();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                newAddress.setAddress(ethereumAddress);
                newAddress.setCreatedAt(timestamp);
                newAddress.setLastUpdateAt(timestamp);
                addressRepository.save(newAddress);
                logService.saveLog(timestamp, LogOperation.NEW_ADDRESS.getDescription(), ethereumAddress);
                return newAddress;
            });

            if (address.getId() != null) {
                Timestamp updateTimestamp = new Timestamp(System.currentTimeMillis());
                address.setLastUpdateAt(updateTimestamp);
                addressRepository.save(address);
                logService.saveLog(updateTimestamp, LogOperation.UPDATE_ADDRESS.getDescription(), ethereumAddress);
            }
            Timestamp downloadTransactionsTimestamp = new Timestamp(System.currentTimeMillis());
            List<Transaction> transactions = Objects.requireNonNull(newTransactions.getBody()).getResult();

            for (Transaction newTransaction : Objects.requireNonNull(transactions)) {
                if (transactionRepository.findByHash(newTransaction.getHash()).isEmpty()) {
                    Transaction transaction = getTransaction(newTransaction, address);
                    transactionRepository.save(transaction);
                    logService.saveLog(downloadTransactionsTimestamp, LogOperation.TRANSACTION_DOWNLOAD.getDescription(), transaction.getHash());
                }
            }

            address.setLastUpdateAt(new Timestamp(System.currentTimeMillis()));
            addressRepository.save(address);

            return ResponseEntity.ok(new APIResponseDTO(ResponseDTOStatus.OK, "Transactions updated successfully", null));
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new APIResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "Service unavailable, please try again later", null));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new APIResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "HTTP error occurred: " + e.getStatusCode(), null));
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponseDTO(ResponseDTOStatus.ETHERSCAN_NOT_WORKING, "An internal server error occurred", null));
        }
    }

    /**
     *
     * @param newTransaction
     * @param address
     * @return
     */
    private static Transaction getTransaction(Transaction newTransaction, Address address) {
        Transaction transaction = new Transaction();
        transaction.setHash(newTransaction.getHash());
        transaction.setFrom(newTransaction.getFrom());
        transaction.setTo(newTransaction.getTo());
        transaction.setValue(newTransaction.getValue());
        transaction.setBlockNumber(newTransaction.getBlockNumber());
        transaction.setAddress(address.getId());
        transaction.setGasUsed(newTransaction.getGasUsed());
        transaction.setTimeStamp(newTransaction.getTimeStamp());
        return transaction;
    }
}
