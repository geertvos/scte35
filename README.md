# SCETE 35 Decoder

[![License](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/nfl/scte35/blob/master/LICENSE)

Java library that handles decoding strings that follow the SCTE 35 Standard: https://en.wikipedia.org/wiki/SCTE-35. The result is an Plain Old Java Object of type `SpliceInfoSection`

This library was forked from https://github.com/nfl/scte35. 

## Goals
* Provide a simple interface that can be instatiated or injected
* Fully define model
* Move to maven
* Add unit tests

## Integration
This library is made available via maven repository. Alternatively it can be downloaded and included directly into a private maven repository or directly in the libs folder of the target app.

### Create or inject instance:
```java
Scte35Decoder scte35Decoder = new Scte35Decoder(false);
SpliceInfoSection spliceInfoSection = scte35Decoder.decode35(byte[] scte35data);
//Use values from model: spliceInfoSection
```