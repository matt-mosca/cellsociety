# cellsociety

### CompSci 308 Cell Society Project
By Matthew Mosca, Venkat Subramaniam, and Yiqin Zhou

The program runs five simulations: Fire, Game of Life, Segregation, Wa-Tor, and Rock, Paper, Scissors

* Error checking for incorrect file data
      * When no simulation type (simulation title) is given, it will use the default simulation type, but the screen will show an error message saying that "No simulation is given"
      
* Initial configuration set up
      * When "type" inside XML is 1, it will randomly generate locations based on a total number of locations to occupy
    * When type is 2, it will use a fixed arrangement, specified inside SimDisplay
    *In Style.xml, when gridEdge is Y, the grid edge is toroidal. When gridEdge is N, the grid edge is normal


   