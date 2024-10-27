package com.sforin.EtherscanTransactions.service;

import com.sforin.EtherscanTransactions.dto.APIResponseDTO;
import com.sforin.EtherscanTransactions.dto.TransactionResponseDTO;
import com.sforin.EtherscanTransactions.enums.LogOperation;
import com.sforin.EtherscanTransactions.enums.ResponseDTOStatus;
import com.sforin.EtherscanTransactions.model.Address;
import com.sforin.EtherscanTransactions.dto.EtherscanResponseDTO;
import com.sforin.EtherscanTransactions.model.Transaction;
import com.sforin.EtherscanTransactions.repository.AddressRepository;
import com.sforin.EtherscanTransactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

/**
 * Service class for managing Ethereum transactions and interacting with the Etherscan API.
 * This class handles operations related to fetching, updating, and saving transactions,
 * as well as managing Ethereum addresses.
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private EtherscanService etherscanService;
    @Autowired
    private LogService logService;

    /**
     * Retrieves all transactions from the database.
     *
     * @return a list of all transactions.
     */
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    /**
     * Retrieves a list of transactions for a specific Ethereum address.
     *
     * @param address the Ethereum address for which transactions are to be retrieved.
     * @return a list of TransactionResponseDTO containing transaction details.
     */
    public List<TransactionResponseDTO> getTransactionsByAddress(String address){
        Optional<Address> currAddress = addressRepository.findByAddress(address);
        List<Transaction> currTransaction = transactionRepository.findByAddress(currAddress.orElseThrow().getId());
        return transactionRepository.getTransactionResponseDTO(currTransaction.get(0).getAddress());
    }

    /**
     * Updates transactions for a given Ethereum address by fetching them from the Etherscan API.
     *
     * @param ethereumAddress the Ethereum address for which transactions are to be updated.
     * @return a ResponseEntity containing the APIResponseDTO with the update status.
     */
    public ResponseEntity<APIResponseDTO> updateTransactions(String ethereumAddress) {
        return updateTransactions(ethereumAddress, instant -> etherscanService.getTransactions(ethereumAddress, instant.getEpochSecond()));
    }

    /**
     * Updates transactions for a given Ethereum address by fetching them from the Etherscan API with pagination.
     *
     * @param ethereumAddress the Ethereum address for which transactions are to be updated.
     * @param numPages the number of pages to fetch.
     * @param offset the offset for pagination.
     * @return a ResponseEntity containing the APIResponseDTO with the update status.
     */
    public ResponseEntity<APIResponseDTO> updateTransactions(String ethereumAddress, int numPages, int offset) {
        return updateTransactions(ethereumAddress, instant -> etherscanService.getPagesTransactions(ethereumAddress, 0L, numPages, offset));
    }

    /**
     * Helper method to update transactions by providing a custom transaction supplier function.
     *
     * @param ethereumAddress the Ethereum address for which transactions are to be updated.
     * @param transactionSupplier a function that supplies the transactions to be fetched.
     * @return a ResponseEntity containing the APIResponseDTO with the update status.
     */
    private ResponseEntity<APIResponseDTO> updateTransactions(String ethereumAddress, Function<Instant, ResponseEntity<EtherscanResponseDTO>> transactionSupplier) {
        try {
            Address address = addressRepository.findByAddress(ethereumAddress).orElseGet(() -> createNewAddress(ethereumAddress));

            Instant instant = address.getLastUpdateAt().toInstant();
            ResponseEntity<EtherscanResponseDTO> newTransactions = transactionSupplier.apply(instant);

            if (Objects.requireNonNull(newTransactions.getBody()).getStatus().equals(ResponseDTOStatus.ETHERSCAN_NOT_WORKING)) {
                APIResponseDTO apiResponseDTO = new APIResponseDTO(ResponseDTOStatus.TRANSACTION_NOT_FOUND,
                        "NEW TRANSACTIONS NOT FOUND",
                        Collections.emptyList());
                return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
            }

            List<TransactionResponseDTO> newTransactionsResponse = saveNewTransactions(address, newTransactions.getBody().getResult());

            return ResponseEntity.ok(new APIResponseDTO(ResponseDTOStatus.OK, "Transactions updated successfully", newTransactionsResponse));
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
     * Creates a new Address entry in the database if it does not exist.
     *
     * @param ethereumAddress the Ethereum address to be created.
     * @return the newly created Address object.
     */
    private Address createNewAddress(String ethereumAddress) {
        Address newAddress = new Address();
        newAddress.setAddress(ethereumAddress);
        newAddress.setCreatedAt(Timestamp.from(Instant.now()));
        newAddress.setLastUpdateAt(Timestamp.from(Instant.ofEpochSecond(0)));
        newAddress.setBalance(Objects.requireNonNull(addressService.fetchBalance(ethereumAddress).getBody()).getResult());
        addressRepository.save(newAddress);
        newAddress.setId(addressRepository.findByAddress(ethereumAddress).orElseThrow().getId());
        logService.saveLog(newAddress.getCreatedAt(), LogOperation.NEW_ADDRESS.getDescription(), ethereumAddress);
        return newAddress;
    }

    /**
     * Saves newly fetched transactions into the database and updates the address's last update timestamp.
     *
     * @param address the Address object associated with the transactions.
     * @param transactions the list of transactions to be saved.
     * @return a list of TransactionResponseDTO containing details of the saved transactions.
     */
    private List<TransactionResponseDTO> saveNewTransactions(Address address, List<Transaction> transactions) {
        Timestamp downloadTransactionsTimestamp = Timestamp.from(Instant.now());
        List<TransactionResponseDTO> transactionResponseDTOList = new ArrayList<TransactionResponseDTO>();
        for (Transaction newTransaction : Objects.requireNonNull(transactions)) {
            Transaction transaction = getTransaction(newTransaction, address);
            transactionRepository.save(transaction);
            transactionResponseDTOList.add(getTransactionResponseDTO(address,newTransaction));
            logService.saveLog(downloadTransactionsTimestamp, LogOperation.TRANSACTION_DOWNLOAD.getDescription(), transaction.getHash());
        }
        address.setLastUpdateAt(downloadTransactionsTimestamp);
        addressRepository.save(address);
        return transactionResponseDTOList;
    }

    /**
     * Converts a Transaction object to a Transaction entity and associates it with the specified Address.
     *
     * @param newTransaction the new Transaction object to be converted.
     * @param address the Address object to be associated with the transaction.
     * @return a Transaction entity populated with the relevant data.
     */
    private Transaction getTransaction(Transaction newTransaction, Address address) {
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

    /**
     * Creates a TransactionResponseDTO from the provided Address and Transaction.
     *
     * @param address the Address associated with the transaction.
     * @param transaction the Transaction object to be converted into DTO.
     * @return a TransactionResponseDTO populated with the relevant transaction details.
     */
    private TransactionResponseDTO getTransactionResponseDTO(Address address, Transaction transaction) {
        return new TransactionResponseDTO(
                address.getAddress(),
                transaction.getBlockNumber(),
                transaction.getTimeStamp(),
                transaction.getHash(),
                transaction.getFrom(),
                transaction.getTo(),
                transaction.getValue(),
                transaction.getGasUsed(),
                address.getBalance()
        );
    }
}
