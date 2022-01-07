# A Confusing Train Ride
![image](https://user-images.githubusercontent.com/68981504/148496930-66ce3964-60f7-44be-ab30-45911e0b360d.png)

Due to a significant increase in its student population, especially in its Machine Learning depart- ment, the single direct train going to Hogwarts has been replaced by a network of 15 stations distributed over three lines. However, this has revealed to not exactly be an improvement. Just like the staircases within the castle, the stations get bored and, to fight this boredom, rearrange themselves every 2 hours. However, luckily for you, the stations have not yet started switching lines; they always remain on the same line, but the order of the stations within a line gets shuffled.

Implemented this shuffling train network and simulate the trip of an unfortunate traveler. You will be given Java templates to complete. Follow the instructions for each class closely.

Here are a few things you should know about the network:

• Traveling between two stations takes an hour.

• All stations are unique and only exist on one line.

• Transfer is possible between specific stations of different lines, which are indicated by having the same name but followed by a unique letter, identifying a distinct platform. Those remain two distinct stations, but that offer the possibility of traveling from one to the other. Transfer is always two-way; if it is possible to transfer from station A to B, then transfer is possible from station B to A.

• If transferring is possible, the passenger is forced to transfer, unless the passenger would be transferring to a station he just transferred from. for example, if I station from station London - A to London - B, your next step is not to transfer from London - B to London - A.

• Shuffling occurs every two hours. During a shuffling event, the order of the stations within a line changes. The left and right terminals might change. The transfer stations are part of the shuffling, but they stations they transfer to remain unaffected.

• Assume there is only one traveler on the network at a time, and that every line has a single train which is magically waiting for the traveler at the appropriate transfer station.
