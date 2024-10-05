build:
	@echo "Compilando o projeto com Gradle..."
	./gradlew build

run:
	@echo "Executando o projeto com Gradle..."
	@if [ "$(filter-out $@,$(MAKECMDGOALS))" ]; then \
		./gradlew run --console=plain --args="$(filter-out $@,$(MAKECMDGOALS))"; \
	else \
		./gradlew run --console=plain; \
	fi

%:
	@: