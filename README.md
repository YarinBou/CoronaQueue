# CoronaQueue
Person
The Person class represents a subject, waiting in line to get the vaccine. Person objects are characterized by an
ID number and a string describing their name. ID numbers are positive integers starting from 1 and are unique.
Throughout the program you may assume that no two Person objects will have the same ID number.
CoronaQueue
The CoronaQueue class is the data structure itself. The constructor of CoronaQueue takes as input an integer
which represents the maximal capacity of the data structure. Imagine this to be the size of the population. The
capacity is also the maximal ID number of a person which may be inserted into the data structure. In your code,
you may assume that all tasks will have a serial number ranging from 1, up to the capacity (inclusive).
The queue is implemented as doubly-linked lists represented by arrays. New subjects are inserted at the tail of
the list and subjects are dequeued from the head of the list. The data structure is composed of 4 arrays. The first
three arrays maintain the list. The first array contains the data, the second array contains the index of the next
element and the third array contains the index of the last element.
