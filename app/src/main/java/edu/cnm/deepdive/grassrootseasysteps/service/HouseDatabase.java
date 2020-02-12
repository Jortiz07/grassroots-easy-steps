package edu.cnm.deepdive.grassrootseasysteps.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.grassrootseasysteps.model.dao.HouseDao;
import edu.cnm.deepdive.grassrootseasysteps.model.dao.VoterDao;
import edu.cnm.deepdive.grassrootseasysteps.model.entity.House;
import edu.cnm.deepdive.grassrootseasysteps.model.entity.Voter;
import edu.cnm.deepdive.grassrootseasysteps.service.HouseDatabase.Converters;
import java.util.Date;

@Database(
    entities = {House.class, Voter.class},
    version = 1,
    exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class HouseDatabase extends RoomDatabase {

  private static final String DB_NAME = "house_db";

  private static Application context;

  public static void setContext(Application context) {HouseDatabase.context = context; }

  public static HouseDatabase getInstance() {return InstanceHolder.INSTANCE;}

  public abstract HouseDao getHouseDao();

  public abstract VoterDao getVoterDao();

  private static class InstanceHolder {

    private static final HouseDatabase INSTANCE = Room.databaseBuilder(
        context, HouseDatabase.class, DB_NAME)
        .build();
  }
  public static class Converters {

    @TypeConverter
    public static Long fromDate(Date date) {
      return (date != null) ? date.getTime() : null;
    }

    @TypeConverter
    public static Date fromLong(Long value) {
      return (value != null) ? new Date(value) : null;
    }

  }
}
