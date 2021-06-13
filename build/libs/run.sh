#!/bin/bash
numeProgram="TotalPopulationByYear"
hadoop fs -rm -r /census/output
hadoop jar $numeProgram-1.0-SNAPSHOT.jar $numeProgram

