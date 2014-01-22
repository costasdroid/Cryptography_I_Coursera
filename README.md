Cryptography I Coursera
=======================

The C++ - Java implementations to solve the coursera Cryptography I course

#Week 1
Given 10 English texts (encrypted and encoded in hex) a 11th hex test must be decrypted.

I am new to Java so the implementation will be not so fast (javadocs are coming)
While implementing encryption, I found out that there was a problem in Java with byte data type. There was no unsigned byte, only sighned ones :).
The result now is, given a pt (plain text) and a key, it outputs the ct (cipher text).

The next one, will solve the excercise!

#Week 3
Split a file into 1024Bytes blocks (except the last one). Consume the file as:

Hash the last block
Hash the previous block concatenated with the last hash key again and again until you reach the beginning
Output the last hash key
