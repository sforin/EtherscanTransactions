package com.sforin.EtherscanTransactions.controller;

import com.sforin.EtherscanTransactions.dto.APIResponseDTO;
import com.sforin.EtherscanTransactions.dto.TransactionResponseDTO;
import com.sforin.EtherscanTransactions.enums.ResponseDTOStatus;
import com.sforin.EtherscanTransactions.service.AddressService;
import com.sforin.EtherscanTransactions.service.EtherscanService;
import com.sforin.EtherscanTransactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    @Autowired
    private  EtherscanService etherscanService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AddressService addressService;

    /**
     * Retrieves all transactions associated with a specified Ethereum address.
     * This method first invokes an update to fetch the latest transactions for the
     * provided address from the external Etherscan API. If the update is successful,
     * it retrieves all transactions linked to the address from the database and returns them.
     * The response status reflects whether the address or transactions were found.
     *
     * @param address the Ethereum address for which all transactions are to be retrieved
     * @return a ResponseEntity containing an APIResponseDTO with the status, message,
     *         and list of TransactionResponseDTO objects
     */

    @GetMapping("/getall/{address}")
    public ResponseEntity<APIResponseDTO> getAllTransactions(@PathVariable String address) {
        ResponseEntity<APIResponseDTO> updateResponse = transactionService.updateTransactions(address);
        //if the update goes well
        if (updateResponse.getStatusCode().is2xxSuccessful() && !Objects.requireNonNull(updateResponse.getBody()).getStatus().equals(ResponseDTOStatus.ETHERSCAN_NOT_WORKING)) {
            List<TransactionResponseDTO> transactions = transactionService.getTransactionsByAddress(address);
            ResponseDTOStatus status;
            if(!addressService.existsAddress(address)) {
                status = ResponseDTOStatus.ADDRESS_NOT_FOUND;
            }
            else {
                if(updateResponse.getBody().getStatus().equals(ResponseDTOStatus.TRANSACTION_NOT_FOUND)) {
                    status = ResponseDTOStatus.TRANSACTION_NOT_FOUND;
                }
                else {
                    status = ResponseDTOStatus.OK;
                }
            }
            APIResponseDTO apiResponse = new APIResponseDTO(
                    status,
                    updateResponse.getBody().getMessage(),
                    transactions
            );
            return ResponseEntity.ok(apiResponse);
        }
        return updateResponse;
    }

    /**
     * Updates transactions associated with the specified Ethereum address.
     * This method calls the external Etherscan API to fetch and store only the latest
     * transactions for the provided address. It does not retrieve the previously stored
     * transactions from the database.
     *
     * @param address the Ethereum address for which to update transactions
     * @return a ResponseEntity containing an APIResponseDTO with the update status
     */

    @GetMapping("/getnew/{address}")
    public ResponseEntity<APIResponseDTO> getNewTransactions(@PathVariable String address) {
        return transactionService.updateTransactions(address);
    }

    /**
     * Retrieves a paginated list of all transactions associated with a specified
     * Ethereum address.
     *
     * @param address The Ethereum address for which to retrieve transactions.
     *                This must be a valid Ethereum address.
     * @param page The page number to retrieve (0-based index).
     * @param size The number of transactions to return per page. This must be a
     *             positive integer.
     * @return A ResponseEntity containing an APIResponseDTO object that includes
     *         the status of the request, a message, and the list of
     *         TransactionResponseDTOs for the specified address.
     */
    @GetMapping("/getall/page/{page}/size/{size}/{address}")
    public ResponseEntity<APIResponseDTO> getAllTransactions(@PathVariable String address, @PathVariable int page, @PathVariable int size) {
        return transactionService.updateTransactions(address, page, size);
    }
}
