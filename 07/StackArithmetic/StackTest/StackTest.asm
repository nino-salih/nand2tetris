//PUSH constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT1
D;JEQ
@SP
A=M-1
M=0
(JUMP_SEGMENT1)
//PUSH constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT2
D;JEQ
@SP
A=M-1
M=0
(JUMP_SEGMENT2)
//PUSH constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT3
D;JEQ
@SP
A=M-1
M=0
(JUMP_SEGMENT3)
//PUSH constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT4
D;JLT
@SP
A=M-1
M=0
(JUMP_SEGMENT4)
//PUSH constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT5
D;JLT
@SP
A=M-1
M=0
(JUMP_SEGMENT5)
//PUSH constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT6
D;JLT
@SP
A=M-1
M=0
(JUMP_SEGMENT6)
//PUSH constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT7
D;JGT
@SP
A=M-1
M=0
(JUMP_SEGMENT7)
//PUSH constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT8
D;JGT
@SP
A=M-1
M=0
(JUMP_SEGMENT8)
//PUSH constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
D=M
@SP
A=M-1
D=M-D
M=-1
@JUMP_SEGMENT9
D;JGT
@SP
A=M-1
M=0
(JUMP_SEGMENT9)
//PUSH constant 57
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 31
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
//PUSH constant 53
@53
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
//PUSH constant 112
@112
D=A
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
M=M-1
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@SP
M=M+1
//neg
@SP
M=M-1
@SP
A=M
M=-M
@SP
M=M+1
//and
@SP
M=M-1
A=M
D=M
@SP
M=M-1
@SP
A=M
M=D&M
@SP
M=M+1
//PUSH constant 82
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
//or
@SP
M=M-1
A=M
D=M
@SP
M=M-1
@SP
A=M
M=D|M
@SP
M=M+1
//not
@SP
M=M-1
@SP
A=M
M=!M
@SP
M=M+1
(END_LABEL)
@END_LABEL
0;JMP