package com.hita.shifttracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "day_labels")
@Data
public class DayLabel {

    @Id
    @Column(name = "day_type_code", nullable = false, length = 1)
    private String dayTypeCode;

    @Column(name = "day_type_name", nullable = false, length = 50)
    private String dayTypeName;
}
