# cellsociety

### CompSci 308 Cell Society Project
Authors: Matthew Mosca, Venkat Subramaniam, and Yiqin Zhou

Start date: 09.14.17

End date: 10.02.17

Approximate hours spent: 100 hours (total for all three of us)

### Group member roles:
Matthew: Matthew worked primarily on the back end, handling the design, logic, and functionality of the simulations. He also designed the NeighborFinder classes in the util package that assist the simulations.

Yiqin: Yiqin worked primarily on preparing the XML files and connecting them to the simulations, and also contributed to the back end of the Segregation and Wa-Tor simulations.

Venkat: Venkat handled the majority of the work for the front end.

The load of work ended up being distributed quite evenly between the three of us.

### Program implementation:

The program runs five simulations: Fire, Game of Life, Segregation, Wa-Tor, and Rock, Paper, Scissors. The program is run from class Main.

* Error checking for incorrect file data
      * When no simulation type (simulation title) is given, it will use the default simulation type, but the screen will show an error message saying that "No simulation is given"
      
* Initial configuration set up
      * When "type" inside XML is 1, it will randomly generate locations based on a total number of locations to occupy
    * When type is 2, it will use a fixed arrangement, specified inside SimDisplay
    *In Style.xml, when gridEdge is Y, the grid edge is toroidal. When gridEdge is N, the grid edge is normal

### Known bugs:
* When the window appears, the buttons do not show up, but as soon as the window size is adjusted even slightly, the buttons appear and function correctly.
* The slider that adjusts speed works well, but the other sliders are buggy. 

### Impressions
We enjoyed this project. We found it interesting, and seeing the result of a properly functioning design was quite rewarding. However, it was very difficult to complete all the required tasks in the amount of time we were given.