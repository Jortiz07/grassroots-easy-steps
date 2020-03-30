## Introduction & Description

The purpose is this app is to make searching and routing multiple addresses more efficient for any type
of start-up/grassroots project.

I decided to start developing this app to try and make life simpler for people who go canvassing during 
election season. As I started trying to explain my ideas and started receiving feedback, I realized that
it didn't have to just be limited to that. I started realizing the full potential of this idea. It could
potentially help anyone who needed to go to multiple addresses. At first I was a bit scared that I
would succeed and the idea would be use to spread something that would make the community worse for
everyone who is a part of it, but I realized that it's a part of life, and that not all unintended
results are necessarily bad.

## Intended users

* Local grassroots organizers (specially in rural areas).
* Community outreach groups.
* Local Politicians trying to collect enough signatures to be on the ballot.
* Local delivery services who don't have their own navigation system.

### [User stories](user-stories.md)

## Current state of the app 

At its current state the app is able to route between two points selected  by a long click on the map
and can find convenient locations (i.e. Gas Stations) along that route.

The layout for the search-box is not in the right location (next to the search button), and needs to be
constrain to the right location. A batch search and routing needs to implemented; without it the app is 
far away from being a fully functional prototype. A new activity and layout needs to be added to be able
to do the manual input for the searches in a different screen as displayed in the wireframe.
A string format method using regex needs to be implemented to simplify the way a user inputs multiple addresses.
The main purpose of this  would so that the user would only be required to separate the house numbers with a comma or something similar
followed by the street name and zip code without the need to retype the whole address every single time.
A method would also be needed to parse the address when it contains multiple houses in the same street
so that a batch search and routing could be executed.
An save an export feature (and the layouts related to them) so that the user can save searches and routes, and be able to export the 
list of addresses visited.


An import and export functionality needs to be added. The import to be able to read external files and
extract the addresses to make it far more efficient that having every user input every single address.


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

# Entity, DAO, and Database classes

### [House.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/model/entity/House.java)

### [Voter.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/model/entity/Voter.java)

### [HouseDao.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/model/dao/HouseDao.java)

### [VoterDao.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/model/dao/VoterDao.java)

### [HouseDatabase.java](https://github.com/Jortiz07/grassroots-easy-steps/blob/master/app/src/main/java/edu/cnm/deepdive/grassrootseasysteps/service/HouseDatabase.java)