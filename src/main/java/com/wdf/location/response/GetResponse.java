package com.wdf.location.response;

import com.wdf.location.datasource.model.Location;
import lombok.Data;

import java.util.List;

@Data
public class GetResponse {

	private Location location;

	private Location parent;

	private List<Location> children;

}
