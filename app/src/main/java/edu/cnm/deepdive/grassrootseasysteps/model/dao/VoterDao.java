package edu.cnm.deepdive.grassrootseasysteps.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.grassrootseasysteps.model.entity.Voter;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface VoterDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Voter voter);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Voter> voters);

  @Update
  Single<Integer> update(Voter voters);

  @Delete
  Single<Integer> delete(Voter... voters);

  @Query("SELECT * FROM Voter ORDER BY name DESC")
  LiveData<List<Voter>> select();

}
