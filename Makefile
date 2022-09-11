SHELL := /bin/bash

run:
	source .env
	./gradlew bootRun

testUnit:
	SPRING_PROFILES_ACTIVE=testUnit ./gradlew test

testIntegration:
	source .env
	SPRING_PROFILES_ACTIVE=testIntegration ./gradlew integrationTest
