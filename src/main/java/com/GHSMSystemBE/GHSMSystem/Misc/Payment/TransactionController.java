package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@Tag(name = "Transaction Management", description = "API endpoints for transaction management operations")
@RequestMapping("/transaction/")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @Operation(summary = "Get all transactions", description = "Retrieves a list of all transactions in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all transactions",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAll() {
        return  ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get transactions by customer ID", description = "Retrieves a list of transactions for a specific customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))),
            @ApiResponse(responseCode = "400", description = "Invalid customer ID supplied", content = @Content)
    })
    @GetMapping("/customerId/{id}")
    public ResponseEntity<List<Transaction>> getByCustomer(
            @Parameter(description = "Customer ID to filter transactions", required = true)
            @PathVariable("id") String customer) {
        if (customer == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(service.getByCustomerName(customer));
        }
    }

    @Operation(summary = "Get transactions by payment status", description = "Retrieves a list of transactions filtered by payment status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))),
            @ApiResponse(responseCode = "400", description = "Invalid payment status supplied", content = @Content)
    })
    @GetMapping("/paymentStatus/{status}")
    public ResponseEntity<List<Transaction>> getByPaymentStatus(
            @Parameter(description = "Payment status to filter transactions", required = true)
            @PathVariable("status") String status) {
        if (status == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(service.getByPaymentStatus(status));
        }
    }

    @Operation(summary = "Get transaction by order ID", description = "Retrieves a transaction using its order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the transaction",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content)
    })
    @GetMapping("/orderId/{id}")
    public ResponseEntity<Transaction> getByOrderId(
            @Parameter(description = "Order ID to retrieve the transaction", required = true)
            @PathVariable("id") String orderId) {
        if (orderId == null) {
            return ResponseEntity.notFound().build();
        } else {
            Transaction transaction = service.getByOrderId(orderId);
            if (transaction == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(transaction);
        }
    }
}