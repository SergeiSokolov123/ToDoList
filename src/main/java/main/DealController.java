package main;

import main.model.Deal;
import main.model.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DealController {

    @Autowired
    private DealRepository dealRepository;

    @GetMapping("/deals/")
    public List<Deal> list() {
        Iterable<Deal> DealIterable = dealRepository.findAll();
        List<Deal> dealList = new ArrayList<>();
        DealIterable.forEach(dealList::add);
        return dealList;
    }

    @PostMapping("/deals/")
    public ResponseEntity<Integer> addDeal(Deal deal) {
        deal.setText("Купить мозги");
        deal.setDate("Завтра");
        Deal newDeal = dealRepository.save(deal);
        return new ResponseEntity<>(newDeal.getId(), HttpStatus.OK);
    }

    @GetMapping("/deals/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Optional<Deal> dealOptional = dealRepository.findById(id);
        if (!dealOptional.isPresent()) {
            return new ResponseEntity<>("Дело не найдено", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(dealOptional, HttpStatus.OK);
    }

    @DeleteMapping("/deals/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Deal> dealOptional = dealRepository.findById(id);
        if (!dealOptional.isPresent()) {
            return new ResponseEntity<>("Удаление невозможно. Дело с указанным id не найдено.", HttpStatus.NOT_FOUND);
        }
        dealRepository.deleteById(id);
        return new ResponseEntity<>("Удаление успешно", HttpStatus.OK);

    }

    @DeleteMapping("/deals/")
    public ResponseEntity<String> clear() {
        dealRepository.deleteAll();
        return new ResponseEntity<>("Удалил все дела", HttpStatus.OK);
    }

    @PutMapping("/deals/{id}")
    public ResponseEntity<String> update(@PathVariable int id) {
        Optional<Deal> dealOptional = dealRepository.findById(id);
        if (!dealOptional.isPresent()) {
            return new ResponseEntity<>("Обновление не выполнено. Дело с указанным id не найдена", HttpStatus.NOT_FOUND);
        }
        Deal updatedDeal = dealOptional.get();
        updatedDeal.setText("Купить новый ноутбук");
        updatedDeal.setDate("На новый год");
        dealRepository.save(updatedDeal);

        return new ResponseEntity<>("Обновлено успешно", HttpStatus.OK);
    }

    @PutMapping("/deals/")
    public ResponseEntity<String> updateAll() {
        Iterable<Deal> dealIterable = dealRepository.findAll();
        List<Deal> dealList = new ArrayList<>();
        dealIterable.forEach(dealList::add);
        if (dealList.isEmpty()) {
            return new ResponseEntity<>("Невозможно провести обновление списка дел (список пуст).",
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
        dealIterable.forEach(deal -> {
            String newDealName = deal.getText() + "(Updated name)";
            deal.setDate("newDealDate");
        });
        dealRepository.saveAll(dealIterable);
        return new ResponseEntity<>("Наименование всех дел обновлено)",
                HttpStatus.OK);
    }

}