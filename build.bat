@echo off
echo Compiling
call nxjc %1.java
echo Linking
call NXJLINK -o %1.nxj %1