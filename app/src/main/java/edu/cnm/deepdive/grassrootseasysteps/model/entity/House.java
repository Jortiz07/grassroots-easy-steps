package edu.cnm.deepdive.grassrootseasysteps.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    indices = {
        @Index(value = "address", unique = true),
        @Index(value = "resident_name", unique = true)
    }
)
public class House {

  @ColumnInfo(name = "house_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "zip_code")
  private long zipCode;

  @ColumnInfo(name = "resident_name", collate = ColumnInfo.NOCASE)
  private String residentName;

  @ColumnInfo(name = "party_affiliation", collate = ColumnInfo.NOCASE)
  private String partyAffiliation;

  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String address;

  private Date date;

  public long getZipCode() {
    return zipCode;
  }

  public void setZipCode(long zipCode) {
    this.zipCode = zipCode;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getResidentName() {
    return residentName;
  }

  public void setResidentName(String residentName) {
    this.residentName = residentName;
  }

  public String getPartyAffiliation() {
    return partyAffiliation;
  }

  public void setPartyAffiliation(String partyAffiliation) {
    this.partyAffiliation = partyAffiliation;
  }
}


