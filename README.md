# jeternity2

Java project created around 2009 playing around with some ideas for the Eternity2 puzzle.

## Introduction

This was my first encounter with the Eternity2
puzzle [https://en.wikipedia.org/wiki/Eternity_II_puzzle](https://en.wikipedia.org/wiki/Eternity_II_puzzle)
Essentially the puzzle is about placing tiles on a 16 by 16 grid in such a way that all edges to the tiles match.
There is also the criteria that one of the tiles must occupy a defined location on the grid.
All tiles may be rotated.

This puzzle has become a sort of goto puzzle when getting to know a new programming language.
It has become a bit of an off and on obsession over the following years, the monitory prize has long
since disappeared, not that I ever thought of being able to win it but there is something attractive in
messing around with different ideas.

This Java implementation from 2009 played around with combining bigger "tiles" from smaller tiles
and seeing where that idea lead too.

It was also my first encounter with No SQL databases, in this case [db4o](https://en.wikipedia.org/wiki/Db4o).
There was also an intention of having an implementation based
on [Berkley DB](https://www.oracle.com/database/technologies/related/berkeleydb-downloads.html)

No SQL also became a bit of an obsession, ending up being used in other professional projects.

This shows the value of these toy hobby projects, they give freedom to explore ideas that then feedback
into the real world.

## Build and test

In the unlikely event that this code will interest anyone.

It can be built using maven, it targets JDK1.8, and there are known vulnerabilities in the db4o library
(Through the version of Log4j that it uses).

Hope that is enough warning.

## Contribute

I would be amazed if anyone was interested in contributing...
but if anyone were to feel so inclined I'm open to pull requests. 

