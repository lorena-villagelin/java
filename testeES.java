import java.io.InputStreamReader;
import java.io.BufferedReader;

public class testeES{
    public static void main(String[] args) throws Exception {

        //Associa o stream reader ao teclado
        InputStreamReader stream = new InputStreamReader(System.in);

        //associa o bufferedReader ao stream reader
        BufferedReader in = new BufferedReader(stream);

        //apresenta uma mensagem
        System.out.print("Informe uma string: ");

        //lê uma linha
        String teste = in.readLine();

        //apresenta string
        System.out.println("String lida: " + teste);

        System.out.print("Informe um inteiro: ");

        //lê linha
        int numero = Integer.parseInt(in.readLine());

        System.out.println("Inteiro lido: " + numero);
    }
}