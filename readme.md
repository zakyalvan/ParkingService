# README

## Introduction

This is solution for Parking Lot problem given for Gojek Hiring coding test. Solution has written using Java 8 and managed using [Maven](https://maven.apache.org/). This project come with executable Maven wrapper, you can delegate normal maven execution to this wrapper by ```mvnw clean install```, ```mvnw test```, etc. Please note, first time ```mvnw``` execution will download Maven binary from central Maven repo. As stated in rule, no external dependencies for runtime except for testing ([TestNG](http://testng.org/doc/), [Mockito](http://site.mockito.org/) and [Hamcrest](http://hamcrest.org/)).

There are two main package in this project, ```parking.space``` and ```parking.command```. First package contains all objects represent parking space management (e.g. ```parking.space.Space```, ```parking.space.Slot```, ```parking.space.SlotRegistry```, ```parking.space.AllocationStrategy```). The second package is containing components used in command dispatching, with ```parking.command.core.CommandDispatcher``` as main interface. Other type support for parsing raw command input (```parking.command.core.InputParser```), translate parsed input ```parking.command.core.Input``` into executable commands ```parking.command.core.Command``` using ```parking.command.core.CommandTranslator``` and ```parking.command.core.SmartCommandTranslator```, execute translated command and handle parking space exception thrown, format result of command execution (```parking.command.core.Result``` type) using ```parking.command.core.ResultFormatter```.

## Running

Before running, this project, make sure JDK 8 installed on target machine, otherwise the code wont compiled.

There are two required mechanism to run this project (as stated in test rule), i.e. run in interactive mode and not.

### Interactive Mode

To run in interactive mode, execute ```./parking_lot``` command from project base directory. In background, it will check whether jar already built, if not built first (using ```./mvnw clean install``` command) then execute normal ```java -jar ParkingService.jar``` command.

In interactive mode, to close the running program, type in ```EXIT``` (case ignored) instead of brute force ```Ctrl+C``` ;)

### Non Interactive Mode

To run in non interactive mode, execute ```./parking_lot file_inputs.txt``` command from project base directory. Same with interactive mode, in background, it will check whether jar already built, if not built first (using ```./mvnw clean install command```) then execute normal ```java -jar ParkingService.jar file_inputs.txt``` command.

## Improvement

There is several point which could be improved, including :

1. Refactor ```parking.space``` package so that contract or interface and implementation readablity improved (Cleaner code).
2. Create abstract implementation of ```parking.command.core.CommandTranslator``` to extract common feature, e.g. splitting and validating raw command input.
3. Synchronize thread in ```parking.space.AllocationStrategy``` implementation (currently only ```parking.space.NearestAllocationStrategy```) for ```com.parking.Slot``` allocation inside current ```parking.space.Space```.
