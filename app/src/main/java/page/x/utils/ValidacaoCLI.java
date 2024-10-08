package page.x.utils;

public class ValidacaoCLI {

    private Long ceilBits;

    public ValidacaoCLI(Long ceilBits) {
        this.ceilBits = ceilBits;
    }

    public boolean verificaPage(Long pageSize, Long tamanhoMemoriaFisica) {
        if (pageSize.compareTo(tamanhoMemoriaFisica) > 0) {
            this.printInvalidPage();
            return false;
        }
        return true;
    }

    public boolean verificaBits(Long bits) {
        if (bits.compareTo(ceilBits) > 0 || bits.compareTo(-1l) < 0) {
            this.printInvalidBits();
            return false;
        }
        return true;
    }

    public Long validStringToLong(String input) {
        if (input == null)
            return null;
        Long valorNumerico = 0L;
        input.trim();
        String[] parts = input.split("(?<=\\d)(?=\\D)");

        if (parts.length == 2) {
            valorNumerico = Long.parseLong(parts[0].trim());
            String unidade = parts[1].trim().toUpperCase();
            if (unidade.equals("KB"))
                valorNumerico *= 1024;
            else if (!unidade.equals("B"))
                this.printInvalidByte();
        } else
            this.printInvalidInput();
        return valorNumerico;
    }

    public boolean rangeEnderecamento(Long enderecoVirtual, Long qtdBits) {
        Long range = this.calculaQtdBits(qtdBits);
        if (enderecoVirtual >= range) {
            System.out.println("O endereço está fora do range de endereçamento.\n" +
                    "Tente novamente com um valor até " + range);
            return false;
        }
        return true;
    }

    private Long calculaQtdBits(Long bits) {
        return (long) Math.pow(2, bits);
    }

    
    private void printInvalidInput() {
        System.out.println("Entrada inválida. Certifique-se de passar um valor numérico seguido de 'B' ou 'KB'.");
    }

    private void printInvalidByte() {
        System.out.println("Unidade inválida. Use 'B' para bytes ou 'KB' para kilobytes.");
    }

    private void printInvalidBits() {
        System.out.println("Entrada inválida. Certifique-se de passar um valor numérico maior que 0 e menor ou igual 64.");
    }

    private void printInvalidPage() {
        System.out.println("Impossível configurar uma página maior que a memória.");
    }
    
}
