# Instructions  

Create 3 classes all in different files. </br>

**A Person class:** </br>

Has a name and age which must be provided in the constructor</br>

Getters and Setters for all instance variables.</br>

**A Parent class:**</br>

Parent must extend Person</br>

Has a constructor that includes the name and age inherited from Person, and a spouse field (parent object).</br>
Has a secondary Constructor that is the same as person.</br>

Has a spouse field that holds their husband/wife(also a parent object). This will default to null.</br>

Has a children field that holds all their children. Starts as an empty array of Childs.</br>

Setters and getters for all fields. (setName, getName, setAge, getAge, setSpouse, getSpouse, setChildren, getChildren)</br>

an addChild(Child child) method that adds a child to the children field.</br>

**A Child class:**</br>

Child must extend Person</br>

Has a constructor that includes the name and age inherited from Person, and two parent fields (parent object).</br>

Has a parent1 and a parent2 field that holds one parent in each.</br>

Has a siblings field that holds all their siblings (children objects). Starts as an empty array of Childs.</br>

Setter and Getters for most fields. (setName, getName, setAge, getAge, getParent1, getParent2, setSiblings, getSiblings). No setters for parents</br>

an addSibling(Child child) method that adds another child to the sibling array.</br>
