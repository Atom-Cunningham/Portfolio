#CSC 252 summer 19
#asm4
#Adam Cunningham
#netId: Laser

#this program has two functions
#the first function, countLetters, counts each instance of each english letter in a string
#it prints the count of each letter
#the second function encodes a string, given a key

#this program uses the stack to instantiate arrays

.text

.globl countLetters
.globl subsCipher

countLetters:
	#countletters reads a string from a testcase, and iterates over it
	#it counts each letter, and stores that information on the stack
	#it prints the string
	# it then prints out for the user the count of each letter
	#and the count of all non letters,
	#t0 = a0 = char *str
	
	#void countLetters(char *str)
	#{
		#int letters[26]; // this function must fill these with zeroes
		#int other = 0;
		#printf("----------------\n%s\n----------------\n", str);
		#char *cur = str;
		#while (*cur != ’\0’)
		#{
			#if (*cur >= ’a’ && *cur <= ’z’)
				#letters[*cur-’a’]++;
			#else if (*cur >= ’A’ && *cur <= ’Z’)
				#letters[*cur-’A’]++;
			#else
				#other++;
				#cur++;
		#}
		#for (int i=0; i<26; i++)
			#printf("%c: %d\n", ’a’+i, letters[i]);
			#printf("<other>: %d\n", other);
		#}
	
	#standard prologue
	addiu 	$sp, $sp, -24
	sw    	$fp, 0($sp)
	sw    	$ra, 4($sp)
	addiu	$fp, $sp, 20
	
	#int letters[26]; // this function must fill these with zeroes
	addiu	$sp, $sp, -104			#allocate 26 words of memory onto the stack
	#	j = 0
	#	i = 26
	# while i > 0
	# letters[j] = 0
	#i--
	#j+=1 (4 bytes)

	add	$t7, $zero, $sp			#t7 = curr sp (&letters[0])
	addi	$t2, $zero, 26			#i = 26
populateLoop:					#fill int array with 0s
	beq	$t2, $zero, populateLoop_End	#while i > 0
	
	sw	$zero, 0($t7)			#letters[j] = 0
	
	addiu	$t2, $t2, -1			#i--
	addi	$t7, $t7, 4			#j++ (&letters[j]++)
	j	populateLoop
populateLoop_End:
	add	$t0, $zero, $a0			#t0 = a0 = char &str
	#printf("----------------\n%s\n----------------\n", str);
.data
dash:		.asciiz "----------------\n"
.text
	addi	$v0, $zero, 4			#print dash
	la	$a0, dash
	syscall
	
	add	$a0, $zero, $t0			#print str
	syscall
	
	addi	$v0, $zero, 11			#print '\n'
	addi	$a0, $zero, 0xA
	syscall
	
	addi	$v0, $zero, 4			#print dash
	la	$a0, dash
	syscall				
	
	add	$t2, $zero, $zero		#t2 = other = 0
countLettersLoop:
	#while (*cur != ’\0’)
	#{
		#if (*cur >= ’a’ && *cur <= ’z’)
			#letters[*cur-’a’]++;
		#else if (*cur >= ’A’ && *cur <= ’Z’)
			#letters[*cur-’A’]++;
		#else
			#other++;
			#cur++;
	#}
	add	$t7, $zero, $sp			#t7 = curr sp (&letters[0])
	lb	$t1, 0($t0)			#t1 =   str[i] = cur
	beq	$t1, $zero,countLettersLoop_End #while (str[i] != ’\0’)
	
	#if (*cur >= ’a’ && *cur <= ’z’)
		#letters[*cur-’a’]++;
	slti	$t3, $t1, 0x61			#t3 = cur < a
	bne	$t3, $zero, checkUpper		#if   cur < a goto checkUpper
	

	addi	$t4, $zero, 0x7a		#t4 = z
	slt	$t3, $t4, $t1			#t3 = z	< cur	
	bne	$t3, $zero, checkUpper		#if   z	< cur  goto checkUpper
	
	#letters[*cur-’a’]++;
	addi	$t3, $t1, -0x61			#t3 = *cur - 'a'
	sll	$t3, $t3, 2			#convert from bytes to words (x4) to get index of int[]
	
	add	$t7, $t7, $t3			#t7 = &letters[*cur - 'a']
	lw	$t3, 0($t7)			#t3 = current count of letter
	addi	$t3, $t3, 1			#     current count of letter ++
	sw	$t3, 0($t7)			#letters[*cur - 'a'] = current count of letter
	
	
	
	j	letterIncr_End

checkUpper:
	#else if (*cur >= ’A’ && *cur <= ’Z’)
		#letters[*cur-’A’]++;
	slti	$t3, $t1, 0x41			#t3 = cur < 'A'
	bne	$t3, $zero, checkOther		#if   cur < 'A' goto checkOther
	
	addi	$t4, $zero, 0x5A		#t4 = 'Z'
	slt	$t3, $t4, $t1			#t3 = 'Z' < cur	
	bne	$t3, $zero, checkOther		#if   'Z' >=cur  goto checkOther
	
	#letters[*cur-’A’]++;
	addi	$t3, $t1, -0x41			#t3 = *cur - 'a'
	sll	$t3, $t3, 2			#convert from bytes to words (x4) to get index of int[]
	
	add	$t7, $t7, $t3			#t7 = &letters[*cur - 'a']
	lw	$t3, 0($t7)			#t3 = current count of letter
	addi	$t3, $t3, 1			#     current count of letter ++
	sw	$t3, 0($t7)			#letters[*cur - 'a'] = current count of letter

	j	letterIncr_End

checkOther:
	addi	$t2, $t2, 1
	
	j	letterIncr_End

letterIncr_End:
	addi	$t0, $t0, 1			#curr++
	j	countLettersLoop
	
countLettersLoop_End:
	
countLettersPrint:
	add	$t7, $zero, $sp			#t7 = curr sp (&letters[0])
	add	$t3, $zero, $zero		#t3 = i = 0
printLoop:
.data
spacer:		.asciiz ": "
otherMsg:	.asciiz "<other>: "
.text
	slti	$t5, $t3, 26			#t5 =  i < 26
	beq	$t5, $zero, printLoop_End	#while i < 26
	
	addi	$v0, $zero, 11			#print curr letter
	addi	$a0, $t3, 0x61			#a0 = curr letter =  i + 'a'
	syscall

	addi	$v0, $zero, 4			#print spacer
	la	$a0, spacer
	syscall
	
	addi	$v0, $zero, 1			#print letter count letterCount[i]
	lw	$a0, 0($t7)
	syscall
	
	addi	$v0, $zero, 11			#print '\n'
	addi	$a0, $zero, 0xA
	syscall

	
	addi	$t7, $t7, 4			#&letters[i]++
	addi	$t3, $t3, 1			#i++
	j printLoop
printLoop_End:

countLettersPrint_End:
	addi	$v0, $zero, 4			#print label otherMsg
	la	$a0, otherMsg
	syscall
	
	addi	$v0, $zero, 1			#print other count
	add 	$a0, $zero, $t2
	syscall
	
	addi	$v0, $zero, 11			#print '\n'
	addi	$a0, $zero, 0xA
	syscall

	addiu	$sp, $sp, 104			#remove 26 words of memory from the stack
	
	# standard epilogue
	lw    	$ra, 4($sp)
	lw     	$fp, 0($sp)
	addiu  	$sp, $sp, 24
	jr     	$ra
	
subsCipher:
	#subs Cipher encodes a message
	#it takes two parameters, the message to be encoded, and a map
	#it creates a new string to be filled with the encoded message
	#the value of a letter is the index of the encoded version of itself in the map
	#the index of that letter in the new string should be written to as the encoded letter
	#this prints the encoded string for the user
	
	#t0 = len
	#t1 = roundUp len
	#t7 used to preserve $a0
	#t7 used to preserve $a1
	
	#void subsCipher(char *str, char *map)
	#{
		#// NOTE: len is one more than the length of the string; it includes
		#// an extra character for the null terminator.
		#int len = strlen(str)+1;
		#int len_roundUp = (len+3) & ~0x3;
		#char dup[len_roundUp]; // not legal in C, typically.
		#for (int i=0; i<len-1; i++)
			#dup[i] = map[(int)str[i]];
			#dup[len-1] = ’\0’;
		#printSubstitutedString(dup);
	#}
	
	#standard prologue
	addiu 	$sp, $sp, -24
	sw    	$fp, 0($sp)
	sw    	$ra, 4($sp)
	addiu	$fp, $sp, 20
	
	
	add	$t7, $zero, $a0			#t7 = str
	add	$t8, $zero, $a1			#t8 = map
	
	#// NOTE: len is one more than the length of the string; it includes
	#// an extra character for the null terminator.
	#int len = strlen(str)+1;
	addi	$sp, $sp, -8
	sw	$t7, 4($sp)
	sw	$t8, 0($sp)
	jal	strlen				#strlen(str), $v0 = strlen($a0)
	lw	$t8, 0($sp)
	lw	$t7, 4($sp)
	addi	$sp, $sp, 8
	
	add	$t0, $zero, $v0			#t0 = len
	addi	$t0, $t0, 1			#len = len +1
	
	#int len_roundUp = (len+3) & ~0x3;
	addi	$t1, $t0, 3			#t1 = len+3
	andi	$t1, $t1, 0xFFFFFFFC		#len_roundUp = (len+3) & ~0x3

	#char dup[len_roundUp];
	sub	$sp, $sp, $t1			#allocate stack space for char[len_roundUp]
	
	#for (int i=0; i<len-1; i++)
	#	dup[i] = map[(int)str[i]];
	#dup[len-1] = ’\0’;
	addi	$t3, $t0, -1			#t3 = i-1
	add	$t2, $zero, $zero		#t2 = i = 0
subsCipherLoop:					#for (int i=0; i<len-1; i++)
	slt	$t4, $t2, $t3			#t4 = i < (len-1)
	beq	$t4, $zero, subsCipherLoop_End	#if !(i <  len-1) goto End
	
	#dup[i] = map[(int)str[i]];
	add	$t9, $t7, $t2			#t9 = &str[i]
	lb	$t4, 0($t9)			#t4 =  str[i]
	
	add	$t9, $t8, $t4			#t9 = &map[(int)str[i]]
	lb	$t4, 0($t9)			#t4 =  map[(int)str[i]]
	
	add	$t9, $zero, $sp			#t9 = &dup[0]
	add	$t9, $t9, $t2			#t9 = &dup[i]
	
	sb	$t4, 0($t9)			#dup[i] = map[(int)str[i]] (store the encoded character
	
	addi	$t2, $t2, 1			#i++
	j	subsCipherLoop
subsCipherLoop_End:
	#dup[len-1] = ’\0’;
	add	$t9, $sp, $t3			#t9 = &dup[len-1]
	addi	$t0, $zero, 0x00		#t0 = \0
	
	sb	$t0, 0($t9)			#dup[len-1] = \0
	
	add	$a0, $zero, $sp
	jal	printSubstitutedString
	
	add	$sp, $sp, $t1			#remove space allocated for char[len_roundUp]
	# standard epilogue
	lw    	$ra, 4($sp)
	lw     	$fp, 0($sp)
	addiu  	$sp, $sp, 24
	jr     	$ra
	
strlen:
	#strlen takes as a parameter a &string of unknown length
	#it counts the number of (non-null) characters in the string
	#and returns the count
	
	#standard prologue
	addiu 	$sp, $sp, -24
	sw    	$fp, 0($sp)
	sw    	$ra, 4($sp)
	addiu	$fp, $sp, 20

	add	$t0, $zero, $a0
	
	
	#strlen(string)
	#i = 0
	#while string[i] != null
		#i++
	#return i
	
	add	$t1, $zero, $zero		#i = 0
strlenLoop:
	lb	$t2, 0($t0)			#t2 = str[i]
	beq	$t2, $zero, strlenLoop_End	#while string[i] != null

	addi	$t0, $t0, 1			#$t0 = &str[i]++
	addi	$t1, $t1, 1			#i++
	j	strlenLoop
strlenLoop_End:
	add	$v0, $zero, $t1			#return i
	
	# standard epilogue
	lw    	$ra, 4($sp)
	lw     	$fp, 0($sp)
	addiu  	$sp, $sp, 24
	jr     	$ra
	
printSubstitutedString:
	#standard prologue
	addiu 	$sp, $sp, -24
	sw    	$fp, 0($sp)
	sw    	$ra, 4($sp)
	addiu	$fp, $sp, 20
.data
codeMsg:	.asciiz	"pSS(dup): dup="
.text
	add	$t0, $zero, $a0			#t0 = str to print
	
	addi	$v0, $zero, 4			#print codeMsg
	la	$a0, codeMsg
	syscall
	
	add	$a0, $zero, $t0			#print str (encoded)
	syscall
	
	addi	$v0, $zero, 11			#print '\n'
	addi	$a0, $zero, '\n'		
	syscall
	
	# standard epilogue
	lw    	$ra, 4($sp)
	lw     	$fp, 0($sp)
	addiu  	$sp, $sp, 24
	jr     	$ra
	
#forcing push