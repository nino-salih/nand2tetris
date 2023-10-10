# Table of Content
- [Table of Content](#table-of-content)
- [Additional information](#additional-information)
- [Orginal Task](#orginal-task)
  - [Project 7](#project-7)
  - [Objective](#objective)





# Additional information

The written VM Translator I is in the folder [VMTranslator](./VMTranslator).

For the finished `VMTranslator` goto [VMTranslator](../08/VMTranslator).

It's written in Java and uses Gradle as the build tool.

If you're not familiar with Gradle, you can run `./gradlew build` to get a `jar`.

# Orginal Task 

## Project 7
In this project you will build the first half of the first half of a typical two-tier compiler. Specifically,
the Jack compiler generates VM code for a virtual machine, much like a Java compiler generates
Bytecode. The VM code is then translated further into machine language, using a program named
VM translator. In this project you will develop a basic version of this translator, and in the next
project you will complete it. This VM Translator is sometimes referred to as the compiler’s
backend.


Basically, you have to write a program that reads and parses VM commands, one command at a
time, and generates Hack instructions that execute the command’s semantics on the Hack
computer. For example, how should the VM translator handle an input like "push constant 7"?
Answer: it should output a sequence of Hack assembly instructions that implement this stack
operation on the host RAM. Code generation – coming up with a sequence of Hack instructions
that realize each one of the VM commands – is the very essence of this project.

## Objective
Build a basic VM translator that implements the arithmetic-logical and push/pop commands of the
VM language. 

For the purpose of this project, This version of the VM translator assumes that the
source VM code is error-free. Error checking, reporting and handling can be added to later versions
of the VM translator, but are not part of this project.

---

Section from [Project 7: VM I: Stack Arithmetic](https://drive.google.com/open?id=1DN5Gpjw6uJZuSvGBdXzwm-SHcBEn0PE-&authuser=schocken%40gmail.com&usp=drive_fs)