# Table of Content
- [Table of Content](#table-of-content)
- [Additional information](#additional-information)
- [Orginal Task](#orginal-task)
  - [Project 6](#project-6)
  - [Objective](#objective)





# Additional information

The written assembler is in the folder [assembler](./assembler).

It's written in Java and uses Gradle as the build tool.

If you're not familiar with Gradle, you can run `./gradlew build` to get a `jar`.

# Orginal Task 

## Project 6
Low-level programs written in symbolic machine language are called assembly programs.

Programmers rarely write programs directly in machine language. Rather, programmers who
develop high-performance programs (e.g. system software, mission-critical apps, and software for
embedded systems) often inspect the assembly code generated by compilers. 

They do so in order
to understand how their high-level code is actually deployed to the hardware, and how that code
can be optimized for gaining better performance. 

One of the players in this process is the program
that translates code written in a symbolic machine language into code written in binary machine
language. This program is called an assembler.

## Objective
Develop an assembler that translates programs written in the Hack assembly language into Hack
binary code. 
This version of the assembler assumes that the source assembly code is valid. 

Error checking, reporting and handling can be added to later versions of the assembler, but are not part
of this project.

---

Section from [Project 6: Assembler](https://drive.google.com/open?id=1CITliwTJzq19ibBF5EeuNBZ3MJ01dKoI&authuser=schocken%40gmail.com&usp=drive_fs)