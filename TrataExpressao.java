public class TrataExpressao {
    private String [] expressao;

    public TrataExpressao(String linha){
        //tira os espacos
        linha = linha.replaceAll("\\s", "");

        //separando a equacao, em um array.
        this.expressao = linha.split("");
    }

    public boolean trataExpressao(){
        Pilha numeros = new Pilha(); //empilha numeros, desempilha quando calcula e empilha o calculado
        Pilha operador = new Pilha(); //empilha os operadores/parenteses e desempilha quando usa o operador/parenteses

        //variaveis de controle para validar se a expressao esta valida
        int qtdParentesesAbertos = 0, qtdParentesesFechados = 0;
        boolean indicUltimoCaractereOperador = false, indicUltimoCaractereParentesesFechado = false;
        boolean calcula = false;   // verifica necessidade de calcular.
        boolean concatena = false; // verifica necessidade de juntar os numeros.
    
        //passa por cada elemento para validar a expressao
        for(int i = 0; i < expressao.length; i++) {
            //se o elemento for um parenteses aberto, soma controle de parentese aberto
            //limpa controle de operadores e parentese fechado
            //empilha operador
            if(expressao[i].equals("(")) {
                qtdParentesesAbertos++;
                indicUltimoCaractereOperador = false;
                indicUltimoCaractereParentesesFechado = false;
                operador.add(expressao[i]);
    
            //se o elemento for um parenteses fechado, soma controle de parentese fechado
            //limpa conrole de operadores e indica parenteses fechado
            //empilha operador
            //calcula expressao dentro do parenteses.
            } else if(expressao[i].equals(")")) {
                qtdParentesesFechados++;
                indicUltimoCaractereOperador = false;
                indicUltimoCaractereParentesesFechado = true;
                operador.add(expressao[i]);
                calculaFechaParenteses(numeros,operador);
    
            //se o elemento não for numerico, assume que eh um operador e arruma os indicadores
            //empilha o operador
            //agenda o calculo se necessario
            } else if(expressao[i].matches("\\D")) {

                //se o indicador de operadores estiver ligado, quer dizer que tem operador a mais.
                if(indicUltimoCaractereOperador) {
                    System.out.println("Equacao invalida por operador ["+expressao[i]+"] sobrando");
                    return false;
                }
                //arruma os indicadores
                indicUltimoCaractereOperador = true;
                indicUltimoCaractereParentesesFechado = false;
    
                operador.add(expressao[i]);
    
                if( operador.exibeUltimoValor().equals("*") || operador.exibeUltimoValor().equals("/") ){
                    calcula = true;
                    concatena = false;
                    continue;
                }
    
            //se entrar aqui, quer dizer que o elemento eh um numero
            } else {
                //se a flag abaixo estiver true, quer dizer que antes era um parenteses fechado e a equacao eh invalida
                if(indicUltimoCaractereParentesesFechado) {
                    System.out.println("Equacao invalida posicao do numero "+ expressao[i] +" errada");
                    return false; 
                }
                indicUltimoCaractereOperador = false;
                indicUltimoCaractereParentesesFechado = false;
    
                if(concatena){
                    numeros.add(numeros.remove()+expressao[i]);
                }
                else{
                    addPilha(numeros, expressao[i], operador, calcula);
                }
                
                calcula = false;
                concatena = true;
                continue;
    
            }
            concatena = false;
            calcula = false;
        }
        //se a qtd de parenteses abertos for diferente da qtd de parenteses fechado, entao a equacao eh invalida
        if(qtdParentesesAbertos != qtdParentesesFechados){
            System.out.println("Equacao invalida quantidade de parenteses errado");
            return false; 
        }

        //trata caso a expressao fique sem parenteses.
        if(numeros.size() > 1) {
            int qtd = numeros.size();
            for(int i = 1; i < qtd; i++){
                calculaEEmpilha(numeros, operador);
            }
        }
    
        System.out.println("Equacao valida");
        System.out.println("Resultado da Equacao: "+ numeros.exibeUltimoValor());
        return true;
    
        }
    
        private static void calculaFechaParenteses(Pilha numeros, Pilha operador) {
    
            if( operador.exibeUltimoValor().equals(")") ) {
                operador.remove();
    
                while(!operador.exibeUltimoValor().equals("(")){
                    calculaEEmpilha(numeros, operador);
                }
                operador.remove();
            }
        }
    
        private static void calculaEEmpilha(Pilha numeros, Pilha operador) {
            double b = Double.valueOf(numeros.remove());
            double a = Double.valueOf(numeros.remove());
            switch(operador.exibeUltimoValor()){
                case "+":
                    a = a + b;
                    break;
                case "-":
                    a = a - b;
                break;
                case "*":
                    a = a * b;
                break;
                case "/":
                    a = a / b;
                break;
                default:break;
            }
            numeros.add(String.valueOf(a));
            operador.remove();
        }
    
        private static String addPilha(Pilha numeros, String aux, Pilha operador, boolean calcula) {
            if(aux.isEmpty())
            return "";
    
            if(Integer.valueOf(aux) !=  0){
                numeros.add(aux);
                aux = "";
            }
    
            if(calcula){
                calculaEEmpilha(numeros,operador);
            }
            return aux;
        }
       
}
