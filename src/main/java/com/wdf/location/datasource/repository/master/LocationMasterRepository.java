package com.wdf.location.datasource.repository.master;

import com.fasterxml.jackson.databind.JsonNode;
import com.wdf.location.datasource.model.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.websocket.server.PathParam;
import java.util.List;

public interface LocationMasterRepository extends CrudRepository<Location, Long> {

	Location findByUid(String uid);

	List<Location> findByParent(String parent);

	List<Location> findAllByUid(List<String> list);

	List<Location> findAllByUidIn(List<String> uidList);

	@Query(value = "update location", nativeQuery = true)
	void removeLocation(String location);

	@Query(value = "select l.requests from location l where l.requests is not null limit :x", nativeQuery = true)
	List<JsonNode> findXNonNullReports(@PathParam("x") String x);

}