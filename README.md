# DummyCastle - Cryptography for dummies.
  
<img align="middle" src="https://github.com/polincdev/DummyCastle/blob/master/img/dummycastle.png" data-canonical-src="https://github.com/polincdev/DummyCastle/blob/master/img/dummycastle.png" width="200" height="150" />

## What is DummyCastle?

It's a quick and simple solution for Java and Dart developers who want to apply any level of security to their applications and they are not
interested in learning to use available libraries.

## Who is this solution for?
DummyCastle should be used whenever there is a need to protect information that is not essential. For example, to protect:
- API keys
- chats
- messages
- settings
- generally small amount of data

## Who is this solution not for?

DummyCastle should not be used whenever there is a need to secure high-value information. For example, to protect:
- money - ecoins
- secret information
- illegal information
- generally large amounts of data

## Features

The library provides the following features:
- symmetric encryption
- asymmetric encryption
- random generation
- hashing(digesting)
- shuffling
- obfuscation

Everything is preconfigured and there is no need to have any kind knowledge about cryptography other than this short manual  

## Getting started

One simple object is enough to handle all the operations and methods.

```java
import pl.polinc.dummycastle.DummyCastle;
  
DummyCastle dummyCastle = new DummyCastle();
 
String password="Password";
dummyCastle.genSymmKeyWith(password);
String encrypted = dummyCastle.encryptSymmWith(plainText).getResult();
```

The DummyCastle class is designed to be a container for a value which is being operated on.
That's why as a rule of thumb, a new object should be created for every value which you want to encrypt or carry out any other operation.
It is not thread safe in any way. Object's memory footprint is very small though. Most of the methods are static, so the only memory weight is a result of the processed value. 
In order to help to work on a value, the method chaining is possible. For example if you want to generate a random value and encrypt it, you can do it with only one line of code:

```java
String randomInt = dummyCastle.randomNumWith(8).genSymmKeyWith("Password").encryptSymm().getResult();
```

### Encoding

DummyCastle keeps the processed value as an array of bytes. It may be retrieved from the object using couple of methods.
In general the output from the library is encoded using HEX encoding. The reason behind it is to prevent any platform specific problems related to encoding. 
The result might be retrieved using getResult() or toString() methods. 

```java
String randomIntDecoded = dummyCastle.randomNumWith(8).getResult();
```

The raw output might be retrieved as a string using getResultDecoded() or as an array of bytes using getResultDecodedRaw():

```java
String randomIntPlain = dummyCastle.randomNumWith(8).getResultDecoded();
```

Alternatively, you may use the method fromStringEncoded() which accept encoded text and then decode it using getResultDecoded():

```java
String plainText = dummyCastle.fromStringEncoded(decrypted).getResultDecoded();
```

of using decodeWith() method:

```java
String plainText=dummyCastle.decodeWith(decrypted).toStringDecoded();
```

### Error handling

The library tries to be as unobtrusive as possible, that's why it handles all its exceptions internally. 
Thanks to that there is no risk of NullPointerException, but it is attained at the cost of verbosity of error handling. 
So if the exception occurs the value gets purged and limited to an empty string, but not nullified.
In order to detect causes of potential errors two methods are provided: isError() and getException().
The isError() method, as one may suspect, returns boolean value which indicate that at any point of calling methods of the library an exceptions has been thrown.
This means that errors don't get reset by next operations. This poses a risk of calling chained methods which in sequence may work of erratic value.
The second method returns the actual exceptions which may be used to retrieve information concerning the issue. 

```java
//Any kind of operations. We assume it will throw an internal exception.
String plainText=dummyCastle.decodeWith(decrypted).toStringDecoded();
//If an exception occures, at this point the plainText == "". 
//By checking the flag and printing the exceptions stack we may get the necesarry information
if(dummyCastle.isError())
    System.out.println(dummyCastle.getException());
```

## Usage

### Symmetric encryption

Symmetric encryption is a type of encryption where only one key (a secret key) is used to both encrypt and decrypt electronic information. 
The requirement that both parties have access to the secret key is one of the main drawbacks of symmetric-key encryption, in comparison to public-key encryption (also known as asymmetric-key encryption).

Basic
```java
String encrypted = dummyCastle.encryptSymmWith(plainText).getResult();
String decrypted = dummyCastle.decryptSymmWith(encrypted).getResult();
String decodedResult = dummyCastle.decodeWith(decrypted).toStringDecoded();
```
Advanced
```java
//Prepare key
String password="Password";
dummyCastle.genSymmKeyWith(password);
//Encrypt and get the result as a safe HEX encoded string
String encrypted = dummyCastle.encryptSymmWith(plainText).getResult();
//If a raw encrypted bytes are required you may get it from the same object
byte[] encryptedDecoded = dummyCastle.getResultDecodedRaw();

//Decryption in general should be carried out using the HEX encoded string. The resulting plain text is still HEX decoded.
 String decrypted = dummyCastle.decryptSymmWith(encrypted).getResult();
 //The input for decryption may be provided through a separate method.
 dummyCastle.fromStringEncoded(encrypted);
 //Now you can decrypt the data as they are kept inside. 
String decrypted = dummyCastle.decryptSymm().getResult();
```

### Asymmetric encryption

Public-key cryptography or asymmetric cryptography, is a cryptographic system that uses pairs of keys. 
Each pair consists of a public key (which may be known to others) and a private key (which may not be known by anyone except the owner). 
The generation of such key pairs depends on cryptographic algorithms which are based on mathematical problems termed one-way functions. 
Effective security requires keeping the private key private. The public key can be openly distributed without compromising security.

Basic
```java
CryptAsymmKeysPair cryptAsymmKeysPair = dummyCastle.genAsymmKeys();
CryptAsymmPublicKey cryptAsymmPublicKey = cryptAsymmKeysPair.getCryptAsymmPublicKey();
CryptAsymmPrivateKey cryptAsymmPrivateKey = cryptAsymmKeysPair.getCryptAsymmPrivateKey();

String encrypted = dummyCastle.encryptAsymmWith(plainText, cryptAsymmPublicKey).getResult();
String decrypted = dummyCastle.decryptAsymmWith( encrypted , cryptAsymmPrivateKey).getResult();
String decodedResult=dummyCastle.decodeWith(decrypted).toStringDecoded();
```

Advanced
```java
//Generate a key pair
CryptAsymmKeysPair cryptAsymmKeysPair = dummyCastle.genAsymmKeys();
//Retrieve private key for decrypting
CryptAsymmPrivateKey cryptAsymmPrivateKey = cryptAsymmKeysPair.getCryptAsymmPrivateKey();
//Retrieve the public key  
 CryptAsymmPublicKey cryptAsymmPublicKey = cryptAsymmKeysPair.getCryptAsymmPublicKey();
//Turn the key into a string for publishing and publish it somewhere on the web
String pub = cryptAsymmPublicKey.toString();
//Encrypt with the public key
encrypted = dummyCastle.encryptAsymmWith(plainText, cryptAsymmPublicKey).getResult();
//Get published key from the web and turn it into an object
cryptAsymmPrivateKey = dummyCastle.genAsymmPublicKeyWith(pub);
//Decrypt data using the private key
decrypted = dummyCastle.decryptAsymmWith(encrypted, cryptAsymmPrivateKey).getResult();
```

### Random generation

DummyCastle makes it possible to generate random numeric and alphanumeric values.
Both are always represented as strings, the numeric value can be parsed into a long though. The result is always HEX encoded.

Basic
```java
String randomInt1 = dummyCastle.randomNumWith(8).getResultDecoded();
String randomStr1 = dummyCastle.randomStrWith(8).getResultDecoded();
String randomInt2 = dummyCastle.randomDeterministicNumWith(someRand1, 8).getResult();
```

Advanced
```java
//Generates 6 characters in a deterministic manner but starts the generation from the '2' character.
//This method is a counterpart of substring method but without generating the whole 8 characters.
randomStr2 = dummyCastle.randomDeterministicStrFromWith(someRand1, 6, 2).getResult();
```

### Hashing

Hashing maps data of any length into a fixed/determined length hash value. 
The library enables you to hash into a numeric(long) or string(alphanumeric) value.
Both are always represented as strings, the numeric hash value can be parsed into a long though. 
The default length of alphanumeric value is 8. The result is always HEX encoded.

Basic
```java
String hashed1  = dummyCastle.hashToNumWith(plainText1).getResult();
String hashed2  = dummyCastle.hashToStrWith(plainText1).getResult();
```

Advanced
```java
//Hash data provided externally
String hashed1 = dummyCastle.hashToStrWith(plainText1).getResult();
//Provide data from an external source
String dummyCastle.fromStringDecoded(plainText1);
//Hash the data internally
String hashed2 = dummyCastle.hashToStr().getResult();
```

### Shuffling

Shuffling arranges bytes in a random or deterministic order. 
The deterministic shuffling needs a seed data which will put the data in the same order every time it gets called. 
  
Simple
```java
String someRand1="aAbBcC";
String shuffled1 = dummyCastle.shuffleWith(plainText).getResultDecoded();
String shuffled2 = dummyCastle.shuffleDeterministicWith(plainText, someRand1).getResultDecoded();
```

Advanced
```java
dummyCastle.fromStringDecoded(plainText);
String shuffled1 = dummyCastle.shuffle().getResultDecoded();
```

### Obfuscation

Obfuscation is the process of creating data that is difficult to decompile, read and understand in order to protect intellectual property or other secrets, and to prevent an attacker from reverse engineering a proprietary software program.

Simple
```java
String obfuscated = dummyCastle.obfuscateWith(plainText).getResult();
String unobfuscated = dummyCastle.unobfuscateWith(obfuscated).getResult();
```

Advanced
```java
dummyCastle.fromStringDecoded(plainText);
String obfuscated = dummyCastle.obfuscate().getResult();
String unobfuscated = dummyCastle.unobfuscateWith(obfuscated).getResultDecoded();
        
```
 
##Clean up - optional

The cleaning up is not required. What it does is resetting the main value to an empty string, setting the error flag to false and resetting the exception.

```java
dummyCastle.reset();
```

## Test

The below tests were prepared without a use of any kind of testing framework in order to make them Java and Dart versions compatible. 

Given the following global variables:

```java
String password = "Password";
String plainText = "TestPlainText";
String plainText1 = "TestPlainText1";
String plainText2 = "TestPlainText2";
String encrypted = "";
String decrypted = "";
String hashed1 = "";
String hashed2 = "";
String decodedResult = "";
String someRand1 = "aAbBcC";
String someRand2 = "xXyYzZ";
String obfuscated = "";
String unobfuscated = "";
String shuffled1 = "";
String shuffled2 = "";
String randomInt1 = "";
String randomInt2 = "";
String randomStr1 = "";
String randomStr2 = "";
boolean verbose = true;
```

and the main library object:

```java
DummyCastle dummyCastle = new DummyCastle();
```

as well as a helper method:

```java
String getOpErrorStatus(DummyCastle dummyCastle) {
		return " [Error=" + dummyCastle.isError() + " " + dummyCastle.getException() + "]";
	}
```

we can call a set of testing methods:

```java
/* SYMMETRIC ENCRYPTION */
testSymmEncryption();

/* ASYMMETRIC ENCRYPTION */
testAsymmEncryption();

/* HASHING */
testHashing();

/* SHUFFLING */
testShuffling();

/* OBFUSCATION */
testObfuscation();

/* RANDOM */
testRandomGeneration();
```

where testSymmEncryption() is:

```java
void testSymmEncryption() {
System.out.println("***Test symmetric encryption***");
//
dummyCastle.genSymmKeyWith(password);
if (verbose)
System.out.println("Test: Symmetric key generation=" + dummyCastle.getSymmKey().toString()
        + getOpErrorStatus(dummyCastle));
//
System.out.println("*Test symmetric encryption externally*");
encrypted = dummyCastle.encryptSymmWith(plainText).getResult();
if (verbose)
System.out.println("Test: Symmetric encryption=" + encrypted + getOpErrorStatus(dummyCastle));
decrypted = dummyCastle.decryptSymmWith(encrypted).getResult();
if (verbose)
System.out.println("Test: Symmetric decryption=" + decrypted + getOpErrorStatus(dummyCastle));
decodedResult = dummyCastle.decodeWith(decrypted).toStringDecoded();
System.out.println("Test: Symmetric result=" + (decodedResult.equals(plainText) ? "PASSED" : "FAILED")
    + getOpErrorStatus(dummyCastle));

//
dummyCastle.reset();
//
dummyCastle.genSymmKeyWith(password);
if (verbose)
System.out.println("Test: Symmetric key generation=" + dummyCastle.getSymmKey().toString()
        + getOpErrorStatus(dummyCastle));
//
System.out.println("*Test symmetric encryption internally*");
dummyCastle.fromStringDecoded(plainText);
encrypted = dummyCastle.encryptSymm().getResult();
if (verbose)
System.out.println("Test: Symmetric encryption=" + encrypted + getOpErrorStatus(dummyCastle));
decrypted = dummyCastle.decryptSymm().getResult();
if (verbose)
System.out.println("Test: Symmetric decryption=" + decrypted + getOpErrorStatus(dummyCastle));
decodedResult = dummyCastle.decodeWith(decrypted).toStringDecoded();
System.out.println("Test: Symmetric result=" + ((decodedResult.equals(plainText)) ? "PASSED" : "FAILED")
    + getOpErrorStatus(dummyCastle));
dummyCastle.reset();
 }
```
and testAsymmEncryption() is:

```java
void testAsymmEncryption() {
System.out.println("***Test asymmetric encryption***");
CryptAsymmKeysPair cryptAsymmKeysPair = dummyCastle.genAsymmKeys();
CryptAsymmPublicKey cryptAsymmPublicKey = cryptAsymmKeysPair.getCryptAsymmPublicKey();
CryptAsymmPrivateKey cryptAsymmPrivateKey = cryptAsymmKeysPair.getCryptAsymmPrivateKey();

System.out.println("*Test asymmetric encryption externally*");
encrypted = dummyCastle.encryptAsymmWith(plainText, cryptAsymmPublicKey).getResult();
if (verbose)
System.out.println("Test: Asymmetric encryption=" + encrypted + getOpErrorStatus(dummyCastle));
decrypted = dummyCastle.decryptAsymmWith(encrypted, cryptAsymmPrivateKey).getResult();
if (verbose)
System.out.println("Test: Asymmetric decryption=" + decrypted + getOpErrorStatus(dummyCastle));
decodedResult = dummyCastle.decodeWith(decrypted).toStringDecoded();
;
System.out.println("Test: Asymmetric result=" + (decodedResult.equals(plainText) ? "PASSED" : "FAILED")
+ getOpErrorStatus(dummyCastle));
dummyCastle.reset();

System.out.println("*Test asymmetric encryption from source*");
encrypted = "0000009a333835323632343536353337343134323232333835353032323831363039353832343031333939323839343836393334383439323230333833393938333230363530363336303034363538393736383237363931323933373331323835303938303134313731363932333435343734323137323637343136383132333435323633323438393032303232373430353438333231303534333238356b2937316a302f57140c163c11";
String pub = "3050648331079197714843623445805135130729850255102544140887224657375482563080876790127317106805275373497518298601036749587025082479587574370867084455170723=@=5046681414847334363823497680505582547008051696988249793270100134042430048998694849893001642372341723686739425690696038035592605596450556906661936335457107";
String priv = "2152374241593825319134227098618465774395685084445578076220700307631905644072705303010484875134566030047425527839702624538996411351348511657549884035109787=@=5046681414847334363823497680505582547008051696988249793270100134042430048998694849893001642372341723686739425690696038035592605596450556906661936335457107";
cryptAsymmPublicKey = dummyCastle.genAsymmPublicKeyWith(pub);
cryptAsymmPrivateKey = dummyCastle.genAsymmPrivateKeyWith(priv);

decrypted = dummyCastle.decryptAsymmWith(encrypted, cryptAsymmPrivateKey).getResult();
if (verbose)
System.out.println("Test: Asymmetric decryption=" + decrypted + getOpErrorStatus(dummyCastle));
decodedResult = dummyCastle.decodeWith(decrypted).toStringDecoded();
 
System.out.println("Test: Asymmetric descryption from source result="
+ (decodedResult.equals(plainText) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

System.out.println("*Test asymmetric encryption internally*");
dummyCastle.fromStringDecoded(plainText);
encrypted = dummyCastle.encryptAsymmWithKey(cryptAsymmPublicKey).getResult();
if (verbose)
System.out.println("Test: Symmetric encryption=" + encrypted + getOpErrorStatus(dummyCastle));
decrypted = dummyCastle.decryptAsymmWithKey(cryptAsymmPrivateKey).getResult();
if (verbose)
System.out.println("Test: Symmetric decryption=" + decrypted + getOpErrorStatus(dummyCastle));
decodedResult = dummyCastle.decodeWith(decrypted).toStringDecoded();
;
System.out.println("Test: Symmetric result=" + (decodedResult.equals(plainText) ? "PASSED" : "FAILED")
+ getOpErrorStatus(dummyCastle));
dummyCastle.reset();
}
```
and testHashing() is:

```java
void testHashing() {

System.out.println("***Test hashing***");
System.out.println("*Test hashing to number*");
hashed1 = dummyCastle.hashToNumWith(plainText1).getResult();
if (verbose)
System.out.println("Test: Hashing to number 1=" + hashed1 + getOpErrorStatus(dummyCastle));
hashed2 = dummyCastle.hashToNumWith(plainText1).getResult();
if (verbose)
System.out.println("Test: Hashing to number 2=" + hashed1 + getOpErrorStatus(dummyCastle));
System.out.println("Test: Hashing to number idepotency result="
+ ((hashed1.equals(hashed2)) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

hashed2 = dummyCastle.hashToNumWith(plainText2).getResult();
if (verbose)
System.out.println("Test: Hashing to number 3=" + hashed2 + getOpErrorStatus(dummyCastle));
System.out.println("Test: Hashing to number duplication result="
+ ((!hashed1.equals(hashed2)) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

System.out.println("*Test hashing to string*");
hashed1 = dummyCastle.hashToStrWith(plainText1).getResult();
if (verbose)
System.out.println("Test: Hashing to string 1=" + hashed1 + getOpErrorStatus(dummyCastle));
hashed2 = dummyCastle.hashToStrWith(plainText1).getResult();
if (verbose)
System.out.println("Test: Hashing to string 2=" + hashed1 + getOpErrorStatus(dummyCastle));
System.out.println("Test: Hashing to string idepotency result="
+ ((hashed1.equals(hashed2)) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

hashed2 = dummyCastle.hashToStrWith(plainText2).getResult();
if (verbose)
System.out.println("Test: Hashing to string 3=" + hashed2 + getOpErrorStatus(dummyCastle));
System.out.println("Test: Hashing to string duplication result="
+ ((!hashed1.equals(hashed2)) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

System.out.println("*Test hashing coherence*");
hashed1 = dummyCastle.hashToStrWith(plainText1).getResult();
if (verbose)
System.out.println("Test: Hashing to string 4=" + hashed1 + getOpErrorStatus(dummyCastle));
System.out.println("Test: Hashing to string coherence result="
+ ((hashed1.equals("091c6c3057c6665f")) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

System.out.println("*Test hashing internally*");
hashed1 = dummyCastle.hashToStrWith(plainText1).getResult();
dummyCastle.fromStringDecoded(plainText1);
hashed2 = dummyCastle.hashToStr().getResult();
System.out.println("Test: Hashing to string internal result="
+ ((hashed1.equals(hashed2)) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

dummyCastle.reset();
}

```

and testShuffling() is:

```java
void testShuffling() {
System.out.println("***Test shuffling***");
shuffled1 = dummyCastle.shuffleWith(plainText).getResultDecoded();
System.out.println("Test: Shuffling random result=" + ((shuffled1 != plainText) ? "PASSED" : "FAILED")
    + getOpErrorStatus(dummyCastle));
shuffled1 = dummyCastle.shuffleDeterministicWith(plainText, someRand1).getResultDecoded();
System.out.println("Test: Shuffling deterministic result 1=" + ((shuffled1 != plainText) ? "PASSED" : "FAILED")
    + getOpErrorStatus(dummyCastle));
shuffled2 = dummyCastle.shuffleDeterministicWith(plainText, someRand1).getResultDecoded();
System.out.println("Test: Shuffling deterministic result 2="
    + ((shuffled1.equals(shuffled2)) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));
System.out.println("Test: Shuffling coherence result="
    + ((shuffled1.equals("teTTixtlPensa")) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

//
System.out.println("*Test Shuffling internally*");
dummyCastle.fromStringDecoded(plainText);
shuffled1 = dummyCastle.shuffle().getResultDecoded();
if (verbose)
System.out.println("Test: Shuffling random result=" + shuffled1 + getOpErrorStatus(dummyCastle));
System.out.println("Test: Shuffling internally result=" + ((!shuffled1.equals(plainText)) ? "PASSED" : "FAILED")
    + getOpErrorStatus(dummyCastle));
dummyCastle.reset();

}
```


and testObfuscation() is:

```java
void testObfuscation() {
System.out.println("*** Test obfuscation***");
obfuscated = dummyCastle.obfuscateWith(plainText).getResult();
if (verbose)
    System.out.println("Test: obfuscation=" + obfuscated + getOpErrorStatus(dummyCastle));
unobfuscated = dummyCastle.unobfuscateWith(obfuscated).getResult();
if (verbose)
    System.out.println("Test unobfuscation=" + unobfuscated + getOpErrorStatus(dummyCastle));
System.out.println("Test: Obfuscation result=" + ((dummyCastle.toStringDecoded().equals(plainText)) ? "PASSED" : "FAILED")
                + getOpErrorStatus(dummyCastle));
dummyCastle.reset();
}
```

and testRandomGeneration() is:

```java
void testRandomGeneration() {
System.out.println("*** Test random generation***");
randomInt1 = dummyCastle.randomNumWith(8).getResultDecoded();
if (verbose)
System.out.println("Test: random number=" + randomInt1 + getOpErrorStatus(dummyCastle));
randomStr1 = dummyCastle.randomStrWith(8).getResultDecoded();
if (verbose)
System.out.println("Test: random string=" + randomStr1 + " " + getOpErrorStatus(dummyCastle));
System.out.println("Test: random result=" + ((randomInt1.length() == 8) ? "PASSED" : "FAILED").toString()
+ getOpErrorStatus(dummyCastle));

System.out.println("* Test deterministic generation*");
randomInt1 = dummyCastle.randomDeterministicNumWith(someRand1, 8).getResult();
if (verbose)
System.out.println("Test: deterministic number=" + randomInt1 + " " + getOpErrorStatus(dummyCastle));
randomInt2 = dummyCastle.randomDeterministicNumWith(someRand1, 8).getResult();
if (verbose)
System.out.println("Test: deterministic number=" + randomInt1 + getOpErrorStatus(dummyCastle));
System.out.println("Test: random deterministic result="
+ ((randomInt1.equals(randomInt2)) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));
System.out.println("Test: random deterministic coherence result="
+ ((randomInt1.equals("3637353832333233")) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

randomStr1 = dummyCastle.randomDeterministicStrWith(someRand1, 8).getResult();
if (verbose)
System.out.println("Test: deterministic number=" + randomStr1 + getOpErrorStatus(dummyCastle));
randomStr2 = dummyCastle.randomDeterministicStrFromWith(someRand1, 6, 2).getResult();
if (verbose)
System.out.println("Test: deterministic number from=" + randomStr2 + getOpErrorStatus(dummyCastle));
System.out.println("Test: random deterministic number from result="
+ ((randomStr1.endsWith(randomStr2)) ? "PASSED" : "FAILED") + getOpErrorStatus(dummyCastle));

dummyCastle.reset();

}

```

## Additional information

This library has an official port fo Dart/Flutter version which can be found here:
https://github.com/polincdev/DummyCastle

The Dart/FLutter version is available here:
https://github.com/polincdev/DummyCastle4Dart

The versions remain synced.