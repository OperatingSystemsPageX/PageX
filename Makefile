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

help:
	@echo "Uso: make run -- [OPCOES]"
	@echo ""
	@echo "Opcoes:"
	@echo "  --page-size <tamanho>           Tamanho da página em Bytes (ex: 4096)"
	@echo "  --maquina <bits>             Número de bits da máquina (ex: 32 ou 64)"
	@echo "  --tlb-entry <numero>         Número de entradas da TLB (default: 10)"
	@echo "  --tlb-alg <algoritmo>        Algoritmo de substituição da TLB (default: lru)"
	@echo "  --memoria-fisica <tamanho>      Tamanho da memória física em Bytes"
	@echo "  --memoria-alg <algoritmo>    Algoritmo de substituição de páginas da Memória Física (default: fifo)"
	@echo ""
	@echo "Exemplos:"
	@echo "  make run -- --maquina 16 --page-size 1024"
	@echo "  make run -- --maquina 32 --page-size 2048 --tlb-entry 16 --tlb-alg lru"
	@echo "  make run -- --maquina 64 --page-size 4096 --tlb-entry 32 --tlb-alg fifo --memoria-fisica 4KB --memoria-alg lru"
	@echo ""
	@echo "Execute o programa com as opções especificadas"

%:
	@: