#Cell Society Plan

Authors: Matthew Mosca (mjm141), Venkat Subramaniam (vms23), Yiqin Zhou (yz259)
Professor: Robert Duvall
Computer Science 308
09.18.17

###Introduction

In completing this project, our goal is to design a program that can run various simulations, all of which are in the form of a 2D grid. Each square of the grid will be called a “cell” from here on out, and every cell has its own “state.” This state could be anything depending on the kind of simulation that we are running, from just one within a binary framework to various temperatures of states. Each cell would also have the ability to change its state depending on its neighbor cells’ states, as per the rules of the simulation that is being run. This changing of the states is what enables this program to actually work as a simulation, as when all of the cells fluidly shift from one state to another, it allows the user, or the viewer as it may be, to actually see the bigger picture. Our goal is to create a program that will be able to run rich, interactive simulations that can help people understand different topics. 

In terms of design, the program will be flexible enough to be easily modified to handle additional simulations without extensive changes in many parts of the source code. Also, since there are many moving pieces that will be treated as separate classes, the program will be designed to smoothly integrate them all without any convoluted dependencies. Specifically, designing the cells to interact effectively with the code controlling the simulation will be a priority. In addition, it will be important to develop a straightforward UI for each simulation in order to allow the user to interact with the program easily.

For the primary architecture of the design, we plan to keep the UI package and all superclasses open. People outside the design group should be able to add additional UIs and modify the existing UIs as they extend the program to handle different simulations. Superclasses are made open so that anyone who would like to modify this project in the future can know their specifications and add more subclasses to facilitate customization. Subclasses themselves will be kept closed so that people outside the design team will not be able to edit them or damage their functionalities. 

###Overview

The program has several logical divisions, which basically will be used as the divisions between classes when writing the program. The primary classes in the program will be Main, Simulation, a Cell superclass, Cell subclasses, and State. Another piece of the program, somewhat separate from the rest, will be the User Interface, contained in a package.

Main will be a short class that starts and runs the program. It will keep track of the primary stage as an instance variable and will add the first scene to the stage. In terms of activity, it will call methods that will set up the user interface and run the simulation.

Simulation will keep track of its grid, grid size, and the scene on which the grid is displayed with instance variables. It will set up the grid, have a time step that causes the cells of the grid to change over time during the simulation, and a method that updates the values of the cells by calling methods in the appropriate Cell subclass (there will be a Cell subclass for each playable simulation). Main will call Simulation’s methods to initiate the simulation, and Simulation will interact with Cell because it holds a grid of Cell objects.

Cell will be a superclass representing a square in the grid of the program simulation, with subclasses corresponding to each simulation that the program can handle. Cell will keep track of its current state, position, size, and neighboring cells, and it will have methods to change its state and determine the cells in its neighborhood. The cell subclass for each simulation will have instance variables and methods specific to that simulation, since simulations can vary in the number of possible states and in the conditions that determine how and when the state of a cell changes. These subclasses will essentially handle the nuances of the changes that occur in the different simulations. The Cell subclasses will interact with Simulation because Simulation holds a grid of Cell objects and interacts with State because each Cell object always has a state, represented by a State object.

State represents the state of a cell and the visual representation of that state. State keeps track of an int, which is the numerical representation of the state, and an ImageView object, which is the visual representation.

The User Interface package will handle and incorporate the XML file and input entered by the user during the program. Main will call methods from this package to initialize the user interface, and classes in the package will interact with Cell and Simulation because values received by this class will be passed to Cell and Simulation methods to set the values (size, color, etc.) of the objects in the program. This package will also set up the actual appearance of the interface.

(See CellSociety_Divisions.png)

###User Interface

When one opens our program, they will immediately be met with a screen which asks them what kind of simulation they would like to run. After they select one of the simulations, the scene changes and they are taken to the simulation that they chose to use. The way that this happens is that when they select a particular simulation, the XML file which contains the default settings and information for that particular simulation will be read and processed so that it can be set up. Alternatively, this could also work in such a way that the user can create their own XML file with their own default settings, so long as our program supports the simulation that they would like to run.

Once this has been done and the user is looking at the simulation, there will be a series of buttons which allow the user to manipulate how the program runs: A play button, a pause button, a step button, and a reset button. Right next to these buttons, the user will have a series of sliders that will allow them to adjust the settings of the simulation, allowing it to run in different ways. For example, there will be a slider that adjusts how quickly the simulation runs, another that adjusts the size of the grid in which the simulation is running, and still more that are unique to each simulation. There will also be a button/drop-down menu that allows one to change simulations if they would like to do so. 

Whenever the program runs into an error, such as an empty file being read, or a file that does not follow the correct format for our program, the user will be alerted of such with a pop-up box which has an appropriate error message. When this happens, the user will usually have to close the program, as it cannot run if an error forces it to crash. We will ensure that the user will have no choice but to close the program, although they will also be given appropriate information as to how to address their issue. 

(See CellSociety_UI_Diagram.png)

###Design Details

To elaborate, Main will create the primary stage for the program and hold it as an instance variable. It will also create the first scene of the program and attach it to the stage. Then, it will call methods from the User Interface package to initiate the type of simulation and values for the simulation, and will call methods from Simulation to begin the simulation.

Simulation first needs to know the grid configuration, which is based on data received from the XML or input by the user. This information is used to set up the grid and cells, which will be represented by and stored as a 2D array of Cell objects. The class will also contain a step method, which will be repeated every specified time interval, and an update method, which will update the states of the cells. Inside step, we will call methods from Cell subclasses to perform simulation-specific functions.

The Cell superclass will have instance variables such as currentState, xCoordinate, yCoordinate, neighborCells, and size. It will have a method to change its own state and a method to determine/identify its neighboring cells. It also will have a method that determines whether the state should be changed, and to what, which will depend on the simulation that is running. This means that this method will be overwritten in the Cell subclasses. The Cell subclasses will have methods specific to each simulation. The Cell subclass for a given simulation will have methods that check if conditions are met that require the state of the cell to be changed based on the rules of the simulation. It may also have certain instance variables specific to the simulation and will specify different states possible in the simulation.

State is a simple class, no more complicated than as described in the Overview section above. It makes sense to have this State class because it will be helpful to have a single type of object that can handle the various states possible in all the different simulations.

The User Interface will not be contained within a class, but rather within a package. Within this package, there will be an inheritance hierarchy where the superclass is that of a “general” UI, while all of the subclasses will be those of specific UIs for each simulation. Some of the methods that they share would include play(), pause(), step(), reset(), and switchSim(). In addition to all of these classes which correspond to the UIs, there will also be smaller classes which help define the error messages that the user gets if there is an error in the program, and which allow the reading/parsing of the XML files. Additional classes here would be related to the sliders that are used, which could also potentially be based upon their own inheritance hierarchy. 
 
####Use cases:

Use Case 1: Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors).

Once the simulation is running, Simulation checks each cell at each time step. When the cell is a middle cell, it checks the states of its neighbors using a method in Cell that determines its neighbors, and uses a method in Simulation to count the cells in the neighborhood with a given state (in this case live cells). Another method in Simulation checks if the number yielded from this method is greater or less than the acceptable standard set forth by the rules of the simulation, and if this is the case, changes the state of the cell to dead by calling a method from Cell.

Use Case 2: Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing).

The process for this case is quite similar to the process for the above case. There is no difference between the process required for an edge cell and that required for a middle cell, since the method that determines the cells in the neighborhood handles this distinction. The counting method in Simulation checks the number of live cells in the neighborhood and another compares it to a number specified by the simulation’s rules. If the numbers are the same, the program changes the state of the cell to live with a method in Cell.

Use Case 3: Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically.

To move to the next generation, we need to call the changeState method inside Cell class for every cell initialized inside Simulation class. After we update all cells to their next state, we will reload the scene.

Use Case 4: Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire.

To set a simulation parameter, we need to read input from the XML file. The probCatch would be one instance variable of Cell class. So we only need to read the input and make the input a parameter for the constructor of each cell.

Use Case 5: Switch simulations: use the GUI to change the current simulation from Game of Life to Wator.

The user clicks a button in the scene which leads to a drop down menu asking which simulation the user would like to switch to. When the user clicks on a particular simulation, the animation is paused. The method switchSim would be called. When this method is called, it sets up a new simulation with default parameters by reading about that particular simulation from its designated XML file, or by calling the Simulation class and creating a new Simulation which uses Cells and states which correspond to the new simulation. Once this new Simulation object is created, it is attached to the stage, and the user is given the same option of choosing what settings they would like. This sets the parameters for the new simulation. When the user clicks the “play” button, the simulation starts working because the animation was unpaused. 

###Design Considerations

The design issue we spent the most time discussing was how to incorporate the four required simulations into the class structure of our program. The first design proposed was to make a superclass for Simulation with four subclasses, one for each type of Simulation, and a single class for all Cells. This appears to be a good and logical design at first because we have four simulations to handle, and it therefore makes sense to make four Simulation subclasses. It is also straightforward for anyone who would like to add another simulation to our project later. However, this design would make the Cell class too general, and would require the Simulation subclasses to act upon the Cell to change its values, making the Cell quite passive. The second potential design was to make a Simulation superclass with four subclasses, as well as a Cell superclass with four subclasses. This is the most flexible design because it allows each Simulation subclass to have qualities of each simulation, and also lets the Cell subclasses retain some activity. However, every time someone wanted to add another simulation to the repertoire of the program, they would need to add both a Simulation subclass and a Cell subclass. The third design proposed was to have one class for Simulation class and a Cell superclass with four subclasses, one for each simulation. We eventually reached the conclusion that this design would be the best among the three because simulations are actually differentiated by their cells, and with this design only one class would need to be added to accommodate another simulation. We can implement all rules within the cell class, refactor the common structure out and put it inside one simulation class. We actually did also consider one more design, which was to have a single Simulation class and a single Cell class, with multiple classes acting as intermediaries, or junctions, one for each simulation. This design is conceptually satisfying because it maintains the integrity of the Simulation framework and Cell concepts as unfragmented classes, but we could not figure out the best way to implement this idea. However, this design is a possibility we will keep in mind as we begin to code in case we run into design difficulties and need another solution.

Another issue that we discussed at length is the UI design. We are not sure about which parameters should go inside XML file and which parameters should be interactive for users to change during the program. Finally, we decided that we should set default parameters inside XML file, but that nearly all parameters should be customizable later. This would make our project more interactive and flexible.

Our design has dependencies between the Cell subclasses and the Simulation class, as cells are components of a simulation. It is inevitable for them to be tied together. Similarly, there is dependency between Cell classes and the State class. 

###Team Responsibilities

We plan to work on the back end and the front end simultaneously because they can be separated and combined together easily. Specifically, Venkat is going to finish the UI design and design relating to handling the XML file. Yiqin and Matt will focus more on back end. Yiqin and Matt will take two Cell subclasses each, coming up with Cell subclass outlines. Then our team will meet again to refactor similar methods out into the Cell superclass. Then Yiqin will be responsible for writing Simulation, two specific Cell classes, and the Cell superclass, while Matt will be responsible for another two specific Cell subclasses and the State class. Venkat will finish Main after all other classes are written.

