// package com.wdf.location.convert;
//
// import com.wdf.location.datasource.model.Location;
// import com.wdf.location.response.GetResponse;
//
// import java.util.List;
// import java.util.stream.Collectors;
//
// public class GetConverter {
//
// public GetResponse convert(Pair<Location, List<Location>> location) {
// GetResponse response = new GetResponse();
// response.setLocation(location);
//
// response.setParent(
// locations.stream().filter(x ->
// x.getUid().equalsIgnoreCase(location.getParent())).findAny().get());
// response.setChildren(locations.stream().filter(x ->
// !x.getUid().equalsIgnoreCase(location.getParent()))
// .collect(Collectors.toList()));
//
// }
//
// }
