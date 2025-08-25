public class Account
{
    private String name; //variável de instância
    private double balance; 
    private int num;
    private double limit;

    public Account(String name, double balance, int num, double limit)
    {
        this.name = name;
        if (balance > 0.0){
            this.balance = balance;
        }
        else
        {
            this.balance = 0.0;
        }
        this.num = num;
        this.limit = limit;
            
    }

    public void deposit(double depositAmount)
    {
        if (depositAmount > 0.0)
            balance += depositAmount;
    }

    public double getBalance() //retorna saldo
    {
        return balance;
    }

    public void setName(String name) //define nome
    {
        this.name = name;
    }

    public String getName() //retorna nome
    {
        return name;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public int getNum()
    {
        return num;
    }

    public double getLimit()
    {
        return limit;
    }

}