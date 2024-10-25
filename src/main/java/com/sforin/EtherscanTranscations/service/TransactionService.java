package com.sforin.EtherscanTranscations.service;

import com.sforin.EtherscanTranscations.model.Address;
import com.sforin.EtherscanTranscations.model.Transaction;
import com.sforin.EtherscanTranscations.repository.AddressRepository;
import com.sforin.EtherscanTranscations.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EtherscanService etherscanService;

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    /**
     *
     * @param address
     * @return
     */
    public List<Transaction> getTransactionsByAddress(String address){
        Optional<Address> currAddress = addressRepository.findByAddress(address);
        if(currAddress.isEmpty()){
            //TODO: check if really nead to create another Address
            Address newAddress = new Address();
            newAddress.setAddress(address);
            newAddress.setCreatedAt(LocalDate.from(LocalDateTime.now()));
            newAddress.setLastUpdateAt(LocalDate.from(LocalDateTime.now()));
            addressRepository.save(newAddress);
            currAddress = addressRepository.findByAddress(address);
        }
        return transactionRepository.findByAddress(currAddress.orElseThrow().getId());
    }

    /**
     *
     * @param ethereumAddress
     */
    public void updateTransactions(String ethereumAddress){
        Address address;
        Optional<Address> addressOptional = addressRepository.findByAddress(ethereumAddress);
        if(addressOptional.isPresent()){
            address = addressOptional.get();
        }
        else{
            address = new Address();
            address.setAddress(ethereumAddress);
            address.setCreatedAt(LocalDate.from(LocalDateTime.now()));
            address.setLastUpdateAt(LocalDate.from(LocalDateTime.now()));
            addressRepository.save(address);
            address = addressRepository.findByAddress(ethereumAddress).orElseThrow();
        }
        List<Transaction> newTransactions = etherscanService.getTransactions(ethereumAddress);
        for(Transaction newTransaction : newTransactions){
            if(transactionRepository.findByHash(newTransaction.getHash()).isEmpty()){
                Transaction transaction = getTransaction(newTransaction, address);
                transactionRepository.save(transaction);
            }
        }

        address.setLastUpdateAt(LocalDate.from(LocalDateTime.now()));
        addressRepository.save(address);
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
