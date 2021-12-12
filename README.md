# DummyCastle - Cryptography for dummies.
  
<img align="middle" src="https://github.com/polincdev/DummyCastle/blob/master/img/dummycastle.png" data-canonical-src="https://github.com/polincdev/DummyCastle/blob/master/img/dummycastle.png" width="200" height="150" />

## What is DummyCastle?

It's a quick and simple solution for developers who want to apply any level of security to their applications and they are not
interested in learning to use available libraries.

## Who is this solution for?
DummyCastle should be used wherever there is a need to protect information that is not essential. For example, to protect:
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

## Getting started

One simple object is enough to handle all the operations and methods.

```java
import pl.polinc.dummycastle.DummyCastle;
 
### The clss

DummyCastle dummyCastle = new DummyCastle();
 
String password="Password";
dummyCastle.genSymmKeyWith(password);
String encrypted = dummyCastle.encryptSymmWith(plainText).getResult();
```

The DummyCastle class is designed to be a container for a value which is being operated on.
That's why as a rule of thumb a new object should be created for every value which you want to encrypt or carry out any other operation on it.
It is not thread safe in any way. Object's memory footprint is very small thou. Most of methods are static, so the only memory weight is a result of the very value. 
In order to help working on a value, the method chaining is possible. For example if you want do generate a random value and encrypt it, you can do it with one line of code:

```java
String randomInt = dummyCastle.randomNumWith(8).encryptSymm().getResult();
```

### Encoding

DummyCastle keeps the processed value as an array of bytes. It may be retrieved from the object using couple of methods.
In general the output form the library is encoded using HEX encoding. The reason behind it is to prevent any platform specific problems related to encoding. 
The result might be retrieved using getResult() or toString() methods. 

```java
String randomIntDecoded = dummyCastle.randomNumWith(8).getResult();
```

The raw output might be retrieved as a string using getResultDecoded() or as an array of bytes using getResultDecodedRaw().

```java
String randomIntPlain = dummyCastle.randomNumWith(8).getResultDecoded();
```

Alternatively, you may use method fromStringEncoded() which accept encoded text and decode it using getResultDecoded():

```java
String plainText = dummyCastle.fromStringEncoded(decrypted).getResultDecoded();
```

of using decodeWith() method

```java
String plainText=dummyCastle.decodeWith(decrypted).toStringDecoded();
```

### Error handling



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
//If a raw encrypted bytes are required you mey get it from the same object
byte[] encryptedDecoded = dummyCastle.getResultDecodedRaw();

//Decryption in general should be carried out using the HEX encoded string. The resulting plain text is still HEX decoded.
 String decrypted = dummyCastle.decryptSymmWith(encrypted).getResult();
 //The input for decryption may be provided through a separate method.
 dummyCastle.fromStringEncoded(encrypted);
 //Now you can decode the data as they are kept inside. 
String decrypted = dummyCastle.decryptSymm().getResult();
```

### Asymmetric encryption

Public-key cryptography or asymmetric cryptography, is a cryptographic system that uses pairs of keys. 
Each pair consists of a public key (which may be known to others) and a private key (which may not be known by anyone except the owner). 
The generation of such key pairs depends on cryptographic algorithms which are based on mathematical problems termed one-way functions. 
Effective security requires keeping the private key private; the public key can be openly distributed without compromising security.

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
//Generate key pair
CryptAsymmKeysPair cryptAsymmKeysPair = dummyCastle.genAsymmKeys();
//Retrieve private key for decrypting
CryptAsymmPrivateKey cryptAsymmPrivateKey = cryptAsymmKeysPair.getCryptAsymmPrivateKey();
//Retrieve public key  
// CryptAsymmPublicKey cryptAsymmPublicKey = cryptAsymmKeysPair.getCryptAsymmPublicKey();


cryptAsymmPrivateKey = dummyCastle.genAsymmPrivateKeyWith(priv);

```
//Random generation

```java
String randomInt1 = dummyCastle.randomNumWith(8).getResultDecoded();
String randomStr1 = dummyCastle.randomStrWith(8).getResultDecoded();
String randomInt2 = dummyCastle.randomDeterministicNumWith(someRand1, 8).getResult();
```

//Hashing

```java
String hashed1  = dummyCastle.hashToNumWith(plainText1).getResult();
String hashed2  = dummyCastle.hashToStrWith(plainText1).getResult();
```

//Shuffling

```java
String someRand1="aAbBcC";
String shuffled1 = dummyCastle.shuffleWith(plainText).getResultDecoded();
String shuffled2 = dummyCastle.shuffleDeterministicWith(plainText, someRand1).getResultDecoded();
```

//Obfuscation

```java
String obfuscated = dummyCastle.obfuscateWith(plainText).getResult();
String unobfuscated = dummyCastle.unobfuscateWith(obfuscated).getResult();
```

//Clean up - optional
```java
dummyCastle.reset();
```

## Additional information

This library has an official port fo Dart/Flutter version which can be found here:
https://github.com/polincdev/DummyCastle

The Dart/FLutter version is available here:
https://github.com/polincdev/DummyCastle4Dart

The versions remain synced.