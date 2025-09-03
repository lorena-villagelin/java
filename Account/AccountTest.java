import java.util.Scanner;

public class AccountTest
{
    public static void main(String[] args)
    {
        Account account1 = new Account(); //sem parametros
        Account account2 = new Account("Jhon Blue", 8765, 2000.00); //com parametros

        System.out.printf("Account1 -> name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account1.getName(), account1.getBalance(), account1.getNum(), account1.getLimit());
        System.out.printf("Account2 -> name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account2.getName(), account2.getBalance(), account2.getNum(), account2.getLimit());

        Scanner input = new Scanner(System.in);

        System.out.print("Deposit for account1: ");
        double depositAmount = input.nextDouble();
        System.out.printf("%nAdding: %.2f%n%n", depositAmount);
        account1.deposit(depositAmount);

        System.out.printf("name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account1.getName(), account1.getBalance(), account1.getNum(), account1.getLimit());
        System.out.printf("name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account2.getName(), account2.getBalance(), account2.getNum(), account2.getLimit());
        
        System.out.print("Deposit for account2: ");
        depositAmount = input.nextDouble();
        System.out.printf("%nAdding: %.2f%n%n", depositAmount);
        account2.deposit(depositAmount);
        
        System.out.printf("name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account1.getName(), account1.getBalance(), account1.getNum(), account1.getLimit());
        System.out.printf("name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account2.getName(), account2.getBalance(), account2.getNum(), account2.getLimit());

        System.out.print("Withdraw for account1:");
        double WDamount = input.nextDouble();
        account1.withdraw(WDamount);

        System.out.print("Withdraw for account2: ");
        WDamount = input.nextDouble();
        account2.withdraw(WDamount);

        System.out.print("Old password Account1: ");
        String oldpass = input.next();

        System.out.print("New password for Account1: ");
        String newpass = input.next();

        account1.changePassword(oldpass, newpass);
        
        input.close();


    }
}