
### CellSociety

# CompSci 308 Cell Society Project

* The high-level design goal of our project is to keep the interaction between back-end and front-end at minimal. We achieve this
through a carefully designed structure: Back-end provides a 2D Cell array to the front-end. The 2D array will be updated when function update() is called.
* Adding new features are not so hard in our project as we made the design less dependent among different classes.
    * Adding a new simulation requires addition of a new Simulation subclass and a new Cell subclass. Moreover, a new button needs to be added inside SimDisplay Class in the front-end.
    * Adding a style requires addition of a method inside StyleUI and calling of this method in relevant area.
    * Adding a new initial configuration requires a bit more, a new constructor in every subclass of simulation and alteration in SimDisplay class.
    * Adding a new way of NeighborFinder only requires addition of a new NeighborFinder subclass. 
    * Adding specific visualization requirement only requires addition of new method in SimDisplay.  
* Cell superclass and Simulation superclass so that the structure can be most flexible. However, this will increase the number of classes. 
* Assumptions: 
    * For the extension of setting up the initial grid using a fixed configuration, the fixed configuration is not read from a XML file. Rather, it is specified directly inside code. Users can choose between randomly set up initial configuration or set up according to fixed configuration by changing the "type" parameter inside each simulation's XML file.
    * For the extension of showing two simulations side by side, we assume that the second simulation will be in a different window.  
