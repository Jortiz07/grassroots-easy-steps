package edu.cnm.deepdive.grassrootseasysteps.model.repository;

import edu.cnm.deepdive.grassrootseasysteps.model.entity.House;
import java.io.File;
import java.util.Set;

public class HouseRepository {

  private String parseAddress(String address) {
    // TODO Implement method to convert user input into multiple searches without the need
    //  for repetition.
    return null;
  }

  private File exportFile(Set<House> houses) {
    // TODO Implement method that exports a file containing the list of houses that were visited
    //  on a given date.
    return null;
}
  private static class InstanceHolder {
    private static final HouseRepository INSTANCE = new HouseRepository();
  }
}
