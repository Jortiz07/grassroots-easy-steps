package edu.cnm.deepdive.grassrootseasysteps.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    indices = {
        @Index(value = "address", unique = true),
        @Index(value = "resident_name", unique = true),
        @Index(value = {"latitute", "longitude"}, unique = true)
    }
)
public class House {

  @ColumnInfo(name = "house_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  @ColumnInfo(name = "zip_code", index = true)
  private long zipCode;

  @NonNull
  private double latitude;

  @NonNull
  private double longitude;

  @NonNull
  @ColumnInfo(name = "resident_name", collate = ColumnInfo.NOCASE)
  private String residentName;

  @NonNull
  @ColumnInfo(name = "party_affiliation", collate = ColumnInfo.NOCASE, index = true)
  private String partyAffiliation;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String address;

  @NonNull
  @ColumnInfo(name = "visit_date")
  private Date visitDate = new Date();

  public long getZipCode() {
    return zipCode;
  }

  public void setZipCode(long zipCode) {
    this.zipCode = zipCode;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
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

  public Date getVisitDate() {
    return visitDate;
  }

  public void setVisitDate(Date visitDate) {
    this.visitDate = visitDate;
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


