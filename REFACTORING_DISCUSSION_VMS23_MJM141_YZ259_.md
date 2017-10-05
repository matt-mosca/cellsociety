* We delete all our redundant resources.
     * We deleted CellSociety class and we deleted some main classes
     because they are set up for testing purpose.
     * We deleted State class and images because we changed our structure
     along the way.  

* We changed all the magic words to constant. 

* We also refactored longest methods such as chooseScene. The method is 
long because it has four if statements. 

* We are planning to make findNeighbor class because there are several ways
to find neighbors and it would be repetitive if we write findNeighbor in every
Simulation subclass.