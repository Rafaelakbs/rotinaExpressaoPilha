public class Pilha {

    public String[] pilha;
    public int indice;

    public Pilha() {
        this.indice = -1;
        this.pilha = new String[100];
    }
    public void add(String valor) {
        if (this.indice < this.pilha.length - 1) {
            this.pilha[++indice] = valor;
        }
    }

    public boolean isEmpty() {
        if (this.indice == -1) {
            return true;
        }
        return false;
    }

    public String remove() {
        if (isEmpty()) {
            return null;
        }
        return this.pilha[this.indice--];
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return this.indice + 1;
    }

    public String exibeUltimoValor() {
        if (isEmpty()) {
            return null;
        }
        return this.pilha[this.indice];
    }

    
}