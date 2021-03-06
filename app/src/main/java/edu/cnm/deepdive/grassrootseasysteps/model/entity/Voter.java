package edu.cnm.deepdive.grassrootseasysteps.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = House.class,
           parentColumns = "house_id",
            childColumns = "house_id",
            onDelete = ForeignKey.RESTRICT
        )
    }, indices = {
    @Index(value = "name", unique = true),
    @Index(value = "house_id"),
}

)
public class Voter {

  @ColumnInfo(name = "voter_id")
  @PrimaryKey(autoGenerate = true)
  private long voterId;

  @ColumnInfo(name = "house_id")
  private long houseId;

  @NonNull
  @ColumnInfo(name = "time_stamp", index = true)
  private Date timeStamp = new Date();

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  @ColumnInfo(name = "political_agenda", collate = ColumnInfo.NOCASE)
  private String politicalAgenda;

  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String question;

  @ColumnInfo(index = true)
  private Boolean support;

  @ColumnInfo(index = true)
  private boolean dog;

  @ColumnInfo(name = "soliciting_sign", index = true)
  private boolean solicitingSign;

  public long getHouseId() {
    return houseId;
  }

  public void setHouseId(long houseId) {
    this.houseId = houseId;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getPoliticalAgenda() {
    return politicalAgenda;
  }

  public void setPoliticalAgenda(String politicalAgenda) {
    this.politicalAgenda = politicalAgenda;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Boolean isSupport() {
    return support;
  }

  public void setSupport(Boolean support) {
    this.support = support;
  }

  public boolean isDog() {
    return dog;
  }

  public void setDog(boolean dog) {
    this.dog = dog;
  }

  public boolean isSolicitingSign() {
    return solicitingSign;
  }

  public void setSolicitingSign(boolean solicitingSign) {
    this.solicitingSign = solicitingSign;
  }

  public long getVoterId() {
    return voterId;
  }

  public void setVoterId(long voterId) {
    this.voterId = voterId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
