# Projeto PageX - Simulador de Tradução de Páginas

![Logo do Projeto](./assets/logo.png)

## Descrição
O **PageX** é um simulador de tradução de páginas que permite configurar e executar diferentes simulações relacionadas ao gerenciamento de memória em sistemas operacionais. Através de uma interface de linha de comando (CLI), o usuário pode definir diversos parâmetros da máquina e do gerenciamento de memória, como o tamanho de página, o número de bits da máquina, entradas na TLB, e o algoritmo utilizado pela TLB e durante a execução das traduções poderá verificar trade-offs de cada configuração que fizer sobre a máquina.

## Requisitos
Este projeto requer **Java 19** ou superior para ser executado corretamente. Caso sua versão do Java seja anterior ou igual à 17, siga os passos abaixo para atualizar.

### Atualizando para Java 21

Se a versão do Java no seu sistema for anterior ou igual à 17, siga as instruções abaixo para atualizar para **Java 21**:

```bash
sudo add-apt-repository ppa:linuxuprising/java
sudo apt update
sudo apt install openjdk-21-jdk
sudo update-alternatives --config java
java -version
```

Após rodar esses comandos, sua versão do Java deve estar configurada para **Java 21**. Para verificar, use:

```bash
java -version
```

### Revertendo para uma versão anterior

Caso precise retornar para uma versão anterior do Java (como a 17), basta rodar o seguinte comando:

```bash
sudo update-alternatives --config java
```

Esse comando permite alternar entre as versões do Java já instaladas no sistema.

## Como Utilizar

### 1. Compilando o código

Antes de rodar o simulador, compile o projeto utilizando o **Makefile**:

```bash
make build
```

Esse comando irá compilar o código usando o **Gradle** e garantir que todos os arquivos estejam prontos para a execução.

### 2. Executando o simulador

Você pode executar o simulador de duas formas: com ou sem passar as configurações de simulação.

#### Execução sem configuração personalizada

Para rodar o simulador sem definir configurações iniciais, use o comando:

```bash
make run
```

Essa execução solicitará que você insira as informações da máquina e da TLB para poder utilizar o simulador.

#### Execução com configuração personalizada

Se você deseja passar uma configuração inicial personalizada, execute o comando abaixo, onde você pode definir parâmetros como o número de bits da máquina, o tamanho da página, o número de entradas na TLB, e o algoritmo da TLB:

```bash
make run -- --maquina 32 --page-size 4B --tlb-entry 8 --tlb-alg fifo
```

Caso queira que a sua máquina NÃO tenha a TLB, basta omitir as flags dela:

```bash
make run -- --maquina 32 --page-size 4B 
```

Os valores passados no exemplo são ilustrativos. Substitua-os pelos valores que você deseja testar na simulação.

### Flags Disponíveis

Aqui estão as descrições de cada uma das flags que você pode configurar ao rodar o simulador:

- **`--page-size`**: Define o tamanho da página de memória. Exemplo: `--page-size 4B` (onde o tamanho da página é 4 Bytes).
- **`--maquina`**: Define o número de bits da máquina (largura de endereços). Exemplo: `--maquina 32` (máquina com arquitetura de 32 bits).
- **`--tlb-entry`**: Define o número de entradas na TLB (Translation Lookaside Buffer), que armazena mapeamentos recentes de endereços virtuais para endereços físicos. Exemplo: `--tlb-entry 8` (opcional).
- **`--tlb-alg`**: Define o algoritmo de substituição da TLB. Exemplo: `--tlb-alg fifo` (opcional).
- **`--memoria-fisica`**: Define o tamanho da sua memória física. Exemplo `--memoria-fisica 16KB` (máquina com memória física de 16KB) (opcional).

### Exemplo Completo de Execução

```bash
make run -- --maquina 64 --page-size 8B --tlb-entry 16 --tlb-alg lru
```

Neste exemplo, o simulador será configurado para rodar em uma máquina de 64 bits, com páginas de 8 Bytes, 16 entradas na TLB, e utilizando o algoritmo de substituição **LRU (Least Recently Used)**.

---