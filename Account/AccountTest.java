import java.util.Scanner;

public class AccountTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Account account1 = new Account(); //sem parametros
        Account account2 = new Account("Jhon Blue", 8765, 2000.00); //com parametros

        System.out.printf("Account1 -> name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account1.getName(), account1.getBalance(), account1.getNum(), account1.getLimit());
        System.out.printf("Account2 -> name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account2.getName(), account2.getBalance(), account2.getNum(), account2.getLimit());

        Scanner input = new Scanner(System.in);

        int  opcli=-1, opman=-1, op=-1;

        while(op!=0)
        {
            System.out.println("MENU CONTAS");
            System.out.println("1 - Gerente");
            System.out.println("2 - Cliente");
            System.out.println("Opção: ");
            op = input.nextInt();

            switch(op)
            {
                case 1:

                case 2:
                    while(opcli != 0)
                    {
                        System.out.println("\n--- MENU ---");
                        System.out.println("1 - Depositar");
                        System.out.println("2 - Sacar");
                        System.out.println("3 - Ver saldo");
                        System.out.println("4 - Trocar senha");
                        System.out.println("0 - Sair");
                        System.out.print("Escolha uma opção: ");
                        opcli = input.nextInt();

                        switch(opcli) {
                            case 1:
                                System.out.print("Escolha a conta (1 ou 2): ");
                                int contaDep = input.nextInt();
                                System.out.print("Valor do depósito: ");
                                double dep = input.nextDouble();
                                if (contaDep == 1) account1.deposit(dep);
                                else account2.deposit(dep);
                                break;
                    
                            case 2:
                                System.out.print("Escolha a conta (1 ou 2): ");
                                int contaSaq = input.nextInt();
                                System.out.print("Valor do saque: ");
                                double saq = input.nextDouble();
                                if (contaSaq == 1) account1.withdraw(saq);
                                else account2.withdraw(saq);
                                break;
                    
                            case 3:
                                System.out.printf("Account1 -> saldo: $%.2f (limite: %.2f)%n", account1.getBalance(), account1.getLimit());
                                System.out.printf("Account2 -> saldo: $%.2f (limite: %.2f)%n", account2.getBalance(), account2.getLimit());
                                break;
                    
                            case 4:
                                System.out.print("Escolha a conta (1 ou 2): ");
                                int contaSenha = input.nextInt();
                                System.out.print("Senha antiga: ");
                                String oldpass = input.next();
                                System.out.print("Nova senha: ");
                                String newpass = input.next();
                            
                                if (contaSenha == 1) account1.changePassword(oldpass, newpass);
                                else account2.changePassword(oldpass, newpass);
                                break;
                    
                            case 0:
                                System.out.println("Saindo...");
                                break;
                    
                            default:
                                System.out.println("Opção inválida!");
                        }
                    }
            

            case 0:
                System.out.println("Saindo...");
                break;
                    
            default:
                System.out.println("Opção inválida!");

        }
        



        input.close();
    }
    }
}
