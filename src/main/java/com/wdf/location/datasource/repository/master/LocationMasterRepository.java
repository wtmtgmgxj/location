package com.wdf.location.datasource.repository.master;

import com.wdf.location.datasource.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationMasterRepository extends CrudRepository<Location, Long> {

	Location findByUid(String uid);

}