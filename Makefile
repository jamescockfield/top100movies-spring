SHELL := /bin/bash

run:
	source .env
	./gradlew bootRun

testUnit:
	SPRING_PROFILES_ACTIVE=testUnit ./gradlew test
