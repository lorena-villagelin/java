import java.util.Scanner;

public class AccountTest
{
    public static void main(String[] args)
    {
        Account account1 = new Account("Jane Green", 50.00, 1234, 50000.00);
        Account account2 = new Account("Jhon Blue", -7.53, 8765, 2000.00);

        System.out.printf("name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account1.getName(), account1.getBalance(), account1.getNum(), account1.getLimit());
        System.out.printf("name: %s | balance: $%.2f | number: %d | limit: $%.2f%n", account2.getName(), account2.getBalance(), account2.getNum(), account2.getLimit());

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
        input.close();
    }
}