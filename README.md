# Pitchcast

A distributed system to store and collect pitchers and thrown pitches in Baseball games.

## Some master branch numbers
[![Build Status](https://travis-ci.org/SeWaS/pitchcast.svg?branch=master)](https://travis-ci.org/SeWaS/pitchcast)
[![codecov](https://codecov.io/gh/SeWaS/pitchcast/branch/master/graph/badge.svg)](https://codecov.io/gh/SeWaS/pitchcast)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c4da3b36fdb54de8b34ecc852c7cfc83)](https://www.codacy.com/app/SeWaS/pitchcast?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SeWaS/pitchcast&amp;utm_campaign=Badge_Grade)

## What is all this good for
This repository has two objectives:
1. A simple demonstration project how to build a microservice system with spring-framework and demonstrate a full automated build process
2. A system that provides all baseball fans, players and coaches a possibility to save a pitcher's pitching behaviour and to compile numbers and stats.

## A word about technologies

- Spring 5
- SpringBoot 2
- Spring Cloud
- JUnit Jupiter
- Travis-CI
- CodeCov
- Codacy
- Kotlin for tests
- Java for production code

## How to use the Pitchcast 
tbd

## Asynchronous endpoint
The pithing-service has an asynchronous endpoint for adding new pitches. The endpoint can be called with ``POST`` method under ``/pitches/`` and is implemented in ``PitchesController.java`` using Spring's ``DeferredResult`` and ``CompletableFuture``.

## How to run all things
There is good news!! It's quite easy to start the whole system thanks to ``docker-compose``.
Simply run 
```
$ docker-compose up
```

and everything starts up. But please consider that there are some services and databases booting; it will take a while :)

## License
MIT
