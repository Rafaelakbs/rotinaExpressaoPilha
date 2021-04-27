import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String expressao = scanner.nextLine();
//        String expressao = "(4+(1*3-(2/5)))"; //Equacao de teste

        TrataExpressao trata = new TrataExpressao(expressao);
        trata.trataExpressao();

        scanner.close();
    }

}
