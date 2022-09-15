include .env

run:
	export
	./gradlew bootRun

testUnit:
	SPRING_PROFILES_ACTIVE=testUnit ./gradlew test $(if $(method), --tests $(method)) $(if $(debug), --debug-jvm)

testIntegration:
	export
	SPRING_PROFILES_ACTIVE=testIntegration ./gradlew integrationTest
