# User Manual
# JobCompare6300

**Author**: Team 109


## Introduction

This simple app leverages an Affine Cipher (i.e., E(x) = (αx + β) % 26) to encrypt messages. It will take a string that contains at least one letter (defined as [a-zA-Z]) and encrypt the letter(s).

## Getting Started

### Dependencies
* Pixel 2 API 28

## Main Features

* Add a current job
* Add job offer(s)
* Adjust comparison settings
* Compare selected jobs

### Input Fields
* `Plaintext:` - the string to be encrypted. String must not be empty and must contain at least one valid letter (i.e., [a-zA-Z])
* `Alpha Key:` - the alpha value. Value must be an integer, coprime of 26, and (0, 26) i.e., between 0 and 26 non-inclusive
* `Beta Key:` - the beta value. Value must be an integer, >= 1, and < 26

### Output Fields
* `Ciphertext:` - the encrypted message

### Instructions
1. Input valid values for `Plaintext:`, `Alpha Key:`, and `Beta Key:`.
1. Click the `ENCIPHER` button. You will now see your encrypted message! This button can be re-clicked for new inputs.

Note: Error messages will be thrown if any of the input fields contain invalid values.

## Acknowledgments

* Hat tip to the lectures and piazza discussions
