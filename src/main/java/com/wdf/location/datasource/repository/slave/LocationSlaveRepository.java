package com.wdf.location.datasource.repository.slave;

import com.wdf.location.datasource.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationSlaveRepository extends CrudRepository<Location, Long> {

}