package com.fsd.projmgmt.repositories;

import com.fsd.projmgmt.models.Prj;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrjRepository extends MongoRepository<Prj, String> {

}