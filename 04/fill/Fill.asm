// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.


//screen memory map (RAM[16384]-RAM[24576]) sind 8192

//Loop:
//if(RAM[0x6000] == 0) {
//    white;
//}
//
//  for(int i = 8192; *Screen+i <= 24576) {
//      RAM[Screen+i] = -1;
//  }
//
//  white:
//    for(int i = 8192; *Screen+i <= 24576) {
//      RAM[Screen+i] = 0;
//  }

//@8192
//D=A


(LOOP)
    @i
    M=0
    @24576 //Keyboard Adresse
    D=M
    @BLACK
    D;JNE

    @WHITE
    0;JMP

(BLACK)
    @24576
    D=A
    @SCREEN
    D=D-A
    @i
    D=D-M
    @LOOP
    D;JEQ

    @SCREEN
    D=A
    @i
    A=D+M
    M=-1
    @i
    M=M+1
    @BLACK
    0;JMP

(WHITE)
    @24576
    D=A
    @SCREEN
    D=D-A
    @i
    D=D-M
    @LOOP
    D;JEQ

    @SCREEN
    D=A
    @i
    A=D+M
    M=0
    @i
    M=M+1

    @WHITE
    0;JMP