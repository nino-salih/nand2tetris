//BOOTSTRAP CODE
@256
D=A
@SP
M=D
@Sys.init$ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
D=M
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(Sys.init$ret.0)
//FUNCTION Sys.init 0
(Sys.init)
//PUSH constant 4000
@4000
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP pointer 0
@R3
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH constant 5000
@5000
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP pointer 1
@R4
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//CALL Sys.main 0
@Sys.main$ret.1
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
D=M
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.main
0;JMP
(Sys.main$ret.1)
//POP temp 1
@R6
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//Label LOOP
(NestedCall$LOOP)
//GOTO LOOP
@NestedCall$LOOP
0;JMP
//FUNCTION Sys.main 5
(Sys.main)
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 4001
@4001
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP pointer 0
@R3
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH constant 5001
@5001
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP pointer 1
@R4
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH constant 200
@200
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP local 1
@LCL
D=M
@1
A=D+A
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH constant 40
@40
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP local 2
@LCL
D=M
@2
A=D+A
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP local 3
@LCL
D=M
@3
A=D+A
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH constant 123
@123
D=A
@SP
A=M
M=D
@SP
M=M+1
//CALL Sys.add12 1
@Sys.add12$ret.2
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
D=M
@6
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.add12
0;JMP
(Sys.add12$ret.2)
//POP temp 0
@R5
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH local 0
@LCL
D=M
@0
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
//PUSH local 1
@LCL
D=M
@1
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
//PUSH local 2
@LCL
D=M
@2
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
//PUSH local 3
@LCL
D=M
@3
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
//PUSH local 4
@LCL
D=M
@4
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
@SP
A=M
M=D+M
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
@SP
A=M
M=D+M
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
@SP
A=M
M=D+M
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
@SP
A=M
M=D+M
@SP
M=M+1
//RETURN 
@LCL
D=M
@R14
M=D
@5
A=D-A
D=M
@R15
M=D
@ARG
D=M
@0
A=D+A
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
@ARG
D=M+1
@SP
M=D
@R14
AM=M-1
D=M
@THAT
M=D
@R14
AM=M-1
D=M
@THIS
M=D
@R14
AM=M-1
D=M
@ARG
M=D
@R14
AM=M-1
D=M
@LCL
M=D
@R15
A=M
0;JMP
//FUNCTION Sys.add12 0
(Sys.add12)
//PUSH constant 4002
@4002
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP pointer 0
@R3
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH constant 5002
@5002
D=A
@SP
A=M
M=D
@SP
M=M+1
//POP pointer 1
@R4
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
//PUSH argument 0
@ARG
D=M
@0
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 12
@12
D=A
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
@SP
A=M
M=D+M
@SP
M=M+1
//RETURN 
@LCL
D=M
@R14
M=D
@5
A=D-A
D=M
@R15
M=D
@ARG
D=M
@0
A=D+A
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
@ARG
D=M+1
@SP
M=D
@R14
AM=M-1
D=M
@THAT
M=D
@R14
AM=M-1
D=M
@THIS
M=D
@R14
AM=M-1
D=M
@ARG
M=D
@R14
AM=M-1
D=M
@LCL
M=D
@R15
A=M
0;JMP
(END$LABEL)
@END$LABEL
0;JMP