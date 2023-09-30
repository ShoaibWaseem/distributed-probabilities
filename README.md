# Probability Distribution

## What is the problem?
The purpose of this repository is to investigate how to process 2 arrays where one contains a value to return and the other the probability of this value being select when against a random float.

## Assumptions/Constraints

- Both arrays must remain as global variables and be used.
- `Random.nextFloat()` can be used which will return a value of 0 and 1
  - But I can not provide an argument to increase max value
- Can not use any java libraries (apart from the ones I create)
- Can use any library to test

## Further Solutions
A second class has been created which differs from the Assumptions and Constraints that was placed. 
This solution utilises collections to create an ordered map which can then be used as an entryset to check all probabilities. 
This solution also allows the addition of probabilities to be greater than 1.

## Things I could improve
- Calling both classes through an interface which implements next num would let me clean up the code.