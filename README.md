# FederalReserveJavaChallenge
This is an application that will accept textDocuments and output the top 20 most used words in the text file with all words stemmed.

## Usage
Use the following command in command prompt  

```java -jar <Path to Jar> <Path to text File>```  

An Example    

```java -jar C:\Users\bkyou\Desktop\FederalReserveJavaChallenge.jar C:\Users\bkyou\Desktop\Text1.txt```

There is also TestStringManipulator class where you can input the file name if it exists on root of project and obtain results in console. 

##Assumptions  
* Manipulation will be run in the order given in requirements:
    1. Remove Stop Words
    2. Remove Non Alphabetical Characters
    3. Stem the words
* White Space/ Black Characters will be removed
* When two words have the same rate of use no decisioning will be made to determine placement

##Output
|Text1.txt|Text2.txt|
|---------|----------|
|he|said|
|us|i|
|govern|alic|
right|it|
peopl|on|
for|and|
law|littl|
state|the|
we|you|
power|look|
time|like|
refus|know|
establish|went|
declar|that|
among|thing|
form|go|
independ|queen|
legislatur|thought|
new|time|
legisl|sai|








