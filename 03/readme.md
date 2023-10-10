# Table of Content
- [Table of Content](#table-of-content)
- [Additional information](#additional-information)
- [Orginal Task](#orginal-task)
  - [Project 3](#project-3)
  - [Build the following chips:](#build-the-following-chips)
  - [Structure of the Project Folder](#structure-of-the-project-folder)


# Additional information

Only the HDL files are important, the rest of the files are for testing.

# Orginal Task 

## Project 3
The computer's main memory, also called Random Access Memory, or RAM, is an addressable
sequence of registers, each designed to hold an n-bit value. In this project you will gradually build a
RAM unit. This involves two main issues: 

(i) using gate logic to store bits persistently, over time,

 and 

(ii) using gate logic to locate ("address") the memory register on which we wish to operate.

## Build the following chips:
 - DFF (given)
 - Bit
 - Register
 - RAM8
 - RAM64
 - RAM512
 - RAM4K
 - RAM16K
 - PC
  
The only building blocks that you can use are some of the chips listed in Projects 1 and 2 and the
chips that you will gradually build on top of them in this project. The DFF chip is considered
primitive and thus there is no need to build it.

## Structure of the Project Folder
When constructing RAM chips from lower-level RAM chip-parts, we recommend using built-in
versions of the latter. Otherwise, the simulator will recursively generate many memory-resident
software objects, one for each of the many chip parts that make up a typical RAM unit, all the way
down to the individual register and bit levels. This may cause the simulator to run slowly, or, worse,
run out of the memory of the host computer on which the simulator is executing.

To avert this problem, we've partitioned the RAM chips built in this project into two sub-folders.
The RAM8.hdl and RAM64.hdl files are stored in projects/03/a, and the files of the other,
higher-level RAM chips are stored in projects/03/b. This partition is done for one purpose only:
when evaluating the RAM chips stored in the b folder, the simulator will resort to using builtin
implementations of the RAM64 chip-parts, simply because RAM64.hdl is not to be found in the b
folder.

---

Section from [Project 3: Sequential Logic](https://drive.google.com/open?id=1ArUW8mkh4Kax-2TXGRpjPWuHf70u6_TJ&authuser=schocken%40gmail.com&usp=drive_fs)