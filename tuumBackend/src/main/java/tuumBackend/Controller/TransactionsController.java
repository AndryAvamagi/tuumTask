package tuumBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuumBackend.Mapper.Mapper;
import tuumBackend.Model.Customer;
import tuumBackend.Model.Transaction;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    @Autowired
    private Mapper Mapper;


    @GetMapping("/allTransactions")
    public ResponseEntity<ArrayList<Transaction>> getAllTransactions(){
        return new ResponseEntity<>(Mapper.findAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/allCustomers")
    public ResponseEntity<ArrayList<Customer>> getAllCustomers(){
        return new ResponseEntity<>(Mapper.findAllCustomers(), HttpStatus.OK);
    }
}
