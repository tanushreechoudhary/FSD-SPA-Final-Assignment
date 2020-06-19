package com.fsd.projmgmt.controllers;
import javax.validation.Valid;
import com.fsd.projmgmt.models.Prj;
import com.fsd.projmgmt.repositories.PrjRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PrjController {

    @Autowired
    PrjRepository prjRepository;

    @GetMapping("/projs")
    public List<Prj> getAllPrj() {
         /*Sort sortByCreatedAtDesc = new Sort(Sort.DEFAULT_DIRECTION, "createdAt");*/
        return prjRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @PostMapping("/projs")
    public Prj createPrj(@Valid @RequestBody final Prj prj) {
        prj.setCompleted(false);
        return prjRepository.save(prj);
    }

    @GetMapping(value = "/projs/{id}")
    public ResponseEntity<Prj> getPrjById(@PathVariable("id") final String id) {
        return prjRepository.findById(id).map(prj -> ResponseEntity.ok().body(prj))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/projs/{id}")
    public ResponseEntity<Prj> updatePrj(@PathVariable("id") final String id, @RequestBody final Prj prj) {
        return prjRepository.findById(id).map(prjData -> {
            prjData.setTitle(prj.getTitle());
            prjData.setCompleted(prj.getCompleted());
            final Prj updatedPrj = prjRepository.save(prjData);
            return ResponseEntity.ok().body(updatedPrj);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/projs/{id}")
    public ResponseEntity<?> deletePrj(@PathVariable("id") final String id) {
        return prjRepository.findById(id)
                .map(prj -> {
                    prjRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}