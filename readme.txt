= README

== Introduction

This is solution for Parking Lot problem given for Gojek Hiring test. Solution has written using Java 8 and managed using Maven. This project come with executable Maven wrapper, you can delegate normal maven execution to this wrapper by mvnw clean install, mvnw test, etc (First time mvnw execution will download Maven binary from central Maven repo). As stated in rule, no external dependencies for runtime except for testing (TestNG, Mockito and Hamcrest library).

There are two main package in this project, parking.space and parking.command. First package contains all objects represent parking space management (e.g. parking.space.Space, parking.space.Slot, parking.space.SlotRegistry, parking.space.AllocationStrategy). The second package is containing components for dispatching (see parking.command.core.CommandDispatcher) parking related command, i.e. translate raw command input (java.lang.String type) into executable commands (parking.command.core.Command type) using parking.command.core.CommandTranslator and parking.command.core.SmartCommandTranslator, execute translated command and handle parking space exception thrown, format result of command execution (parking.command.core.Result type) using parking.command.core.ResultFormatter and parking.command.core.ResultFormatter.

Please note, thread synchronization in parking space management (especially in slot allocation by parking.space.AllocationStrategy) was not enforced for simplicity ;-)

== Running

Before running, this project, make sure JDK 8 installed on target machine.

There are two required mechanism to run this project (as stated in test rule), i.e. run in interactive mode and not.

=== Interactive Mode

=== Non Interactive Mode

== Improvement

There is several point which could be improved, including :
0. Refactor parking.space package so that contract or interface and implementation to improve readability.
1. Create abstract implementation of parking.command.CommandTranslator to extract common feature, e.g. splitting and validating raw command input.
2. Synchronize thread in parking.space.AllocationStrategy implementation (currently only parking.space.NearestAllocationStrategy) for com.parking.Slot allocation inside current parking.space.Space.
