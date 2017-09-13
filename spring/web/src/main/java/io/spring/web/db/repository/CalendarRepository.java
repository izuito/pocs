package io.spring.web.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.spring.web.db.model.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
