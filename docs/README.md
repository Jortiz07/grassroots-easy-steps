## Description

This app is able to take addresses from a file/user-input and find the shortest route to all the addresses specified to make traveling to those locations more efficient.

It will also allow group members to add flags (No soliciting, Dogs, etc) to houses for future reference and or exclusion.

Group members will also be able to track houses previously visited to avoid repeated visits.

Users would be able to save routes and or export a file with a list of houses visited and the response perceived from the encounter.

## Intended users

* Local grassroots organizers (specially in rural areas).
* Community outreach groups.
* Local Politicians trying to collect enough signatures to be on the ballot.
* Local delivery services who don't have their own navigation system.

### [User stories](user-stories.md)

## External services

* TomTom Map API :
  Provides map titles for the app and zoom functionality.
  Without it our map titles wont be displayed.
  [API Info](https://developer.tomtom.com/maps-api)

* TomTom Search API :
   Provide the address search feature.
   Without it we will not be able to track addresses.
   [API Info](https://developer.tomtom.com/search-api)

* TomTom Routing API :
   Allows the app to route from point A-Z.
   Without it the app will not be able to create the a route to visit the addresses given efficiently.
   [API Info](https://developer.tomtom.com/routing-api)
   
[Alternatives](alternative.md)


## Design documentation

### [Wireframe diagram](wireframe.md)

### [Entity-relationship diagram](erd.md)

## Implementation

### [Data definition language (DDL)](ddl.md)

# Entity and DAO classes

### [House.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/model/entity/House.java)

### [Voter.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/model/entity/Voter.java)

### [HouseDao.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/model/dao/HouseDao.java)

### [VoterDao.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/model/dao/VoterDao.java)