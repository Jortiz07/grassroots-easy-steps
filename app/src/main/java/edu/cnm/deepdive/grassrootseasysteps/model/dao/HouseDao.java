package edu.cnm.deepdive.grassrootseasysteps.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.grassrootseasysteps.model.entity.House;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface HouseDao {

  @Insert
  Single<Long> insert(House house);

  @Insert
  Single<List<Long>> insert(Collection<House> houses);

  @Update
  Single<Integer> update(House house);

  @Delete
  Single<Integer> delete(House... houses);

  @Query("SELECT * FROM House ORDER BY date DESC")
  LiveData<List<House>> select();

}
