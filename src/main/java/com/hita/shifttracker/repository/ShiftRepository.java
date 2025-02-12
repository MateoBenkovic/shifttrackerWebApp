package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.Shift;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends CrudRepository<Shift, Integer> {
}
