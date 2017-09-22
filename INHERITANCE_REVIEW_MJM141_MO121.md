Inheritance Review
09.21.17

Matthew Mosca (mjm141)
Partner: Matthew O'Boyle (mo121)

### Part 1
1.
What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?

In terms of front end vs. back end, the front end only receives a 2D array of cells with set states from the back end. Its job is just to represent these cells, and does not have access to the logic that changes their states. Therefore, much of the information contained and manipulated on the back end is hidden from the front end.

2.
What inheritance hierarchies are you intending to build within your area and what behavior are they based around?

In our program, we will have a Simulation superclass with subclasses and a Cell superclass with subclasses. The hierarchy essentially is designed to handle the different states and rules of each simulation. I am handling parts of both of these superclass/subclass groups.

3.
What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?

Once designed, the Simulation and Cell subclasses created for the four simulations will be closed to modification, and only will only exchange data with each other and the front end. The Cell and Simulation superclasses will facilitate extension (open for extension) because adding a new simulation for the program to handle will only require the addition of a Cell subclass and a Simulation subclass.

4.
Why do you think your design is good (also define what your measure of good is)?

I think a good design is one that is logical, simple, well-structured, and can be extended relatively easily and without modifying important code. I think our design is logical and simple because, essentially, the Cell subclasses just encapsulate a state, while the Simulation subclasses keep track of a grid of Cell objects and manipulate them according to conditions specified by the rules of the simulation. This design is well-structured because the inheritance hierarchy makes it easy to see the divisions between the code corresponding to the different simulations. The inheritance structure also makes it possible to extend the program easily, as new subclasses can simply be added to accommodate additional simulations. No existing code must be modified in order to do this.

### Part 2

1.
How is your area linked to/dependent on other areas of the project?

The Simulation classes are dependent on the Cell classes because every Simulation object holds an array of Cell objects and accesses and modifies the data of each of these cells. The front end classes also depend on the Simulation classes because the front end must display the information that is fed to it in the 2D array in Simulation objects representing the grid.

2.
Are these dependencies based on the other class's behavior or implementation?

These dependencies are based on the other class's behavior in that there is no way for the Simulation classes to execute their behavior without storing an array of Cell objects and there is no (good) way we have conceived of for the front end to represent the array of cells in the Simulation classes without referencing this array through some method. In other words, these dependencies can be cleaned up to make the interactions between these classes smoother, but they cannot be reasonably eliminated.

3.
How can you minimize these dependencies?

We can try to minimize the difficulties caused by these dependencies by including them as parameters rather than using them explicitly in many places throughout the code.

4.
Go over one pair of super/sub classes in detail to see if there is room for improvement. Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).

There isn't much room for improvement in terms of the general plan for the Cell superclass and subclasses because all the commonalities between the subclasses are included in the superclass. The superclass contains the common instance variables (int myState, Image myImage, ImageView myDisplay) and methods (getters and setters for these variables), while the subclasses contain variables and methods specific to the corresponding simulation.

### Part 3

1.
Come up with at least five use cases for your part (most likely these will be useful for both teams).

A. For the fire simulation, determine if a given tree cell not on the edge of the grid will catch fire.
B. For the fire simulation, determine if a given edge tree cell will catch fire.
C. Reset the grid to its initial state.
D. Pause the simulation.
E. Make the simulation move forward one step.

2.
What feature/design problem are you most excited to work on?

I am most excited to work on the logic of the update methods within the Simulation subclasses, which will determine whether the state of each cell should change based on the states of the surrounding cells. I think this part is exciting because it is the logic that truly drives the simulations.

3.
What feature/design problem are you most worried about working on?

I am most worried about designing the Simulation subclasses well enough so that it interacts smoothly with the front end, which will be designed by my teammate.