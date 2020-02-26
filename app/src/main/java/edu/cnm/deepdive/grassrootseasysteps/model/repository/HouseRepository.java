package edu.cnm.deepdive.grassrootseasysteps.model.repository;

import android.app.Application;
import edu.cnm.deepdive.grassrootseasysteps.model.entity.House;
import edu.cnm.deepdive.grassrootseasysteps.service.HouseDatabase;
import java.io.File;
import java.util.Set;

public class HouseRepository {

  private final HouseDatabase database;

  private static Application context;

  private HouseRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = HouseDatabase.getInstance();
  }

  public static void setContext(Application context) {
    HouseRepository.context = context;
  }

  public static HouseRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

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
