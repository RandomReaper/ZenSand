# ZenSand [![Build Status](https://travis-ci.org/RandomReaper/ZenSand.svg?branch=master)](https://travis-ci.org/RandomReaper/ZenSand)
## A Bot drawing in sand

The goal is to create a decorative box with a ball drawing in sand.

## Inspirations
* Idea : [hackaday](http://blog.hackaday.com)
* Design : [Bruce Shapiro's Sisyphus](http://www.taomc.com/sisyphus/)
* Mechanics : The [Plotclock](https://wiki.fablab-nuernberg.de/w/Ding:Plotclock) and various [Five-bar parallel robots](https://www.google.com/search?q=Five-bar+robots)

## Design choice
Use some standard hobby servo to move the robotic arms.

##Part #1 The simulator
![Simulator](doc/img/sim.png)
Simulator based on [jbox2d](http://www.jbox2d.org/), currently in development, will be used to choose and tune design choice like arm length and servo placement.

### Give it a try
```bash
git clone https://github.com/RandomReaper/ZenSand.git
cd ZenSand
mvn install exec:java
```
Use the A and B sliders to move the arms.

## Links
* [DEVELOPMENT OF A FIVE-BAR PARALLEL ROBOT WITH LARGE WORKSPACE](http://etsmtl.ca/Professeurs/ibonev/documents/pdf/DETC2010.pdf)