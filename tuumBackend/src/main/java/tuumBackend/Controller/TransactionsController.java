package tuumBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuumBackend.Mapper.TransactionsMapper;
import tuumBackend.Model.Transaction;

import java.net.http.HttpResponse;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    @Autowired
    private TransactionsMapper transactionsMapper;


    @GetMapping("/all")
    public ResponseEntity<ArrayList<Transaction>> getAll(){
        return new ResponseEntity<>(transactionsMapper.findAll(), HttpStatus.OK);
    }
}
