public class Account
{
    private String name; //variável de instância
    private double balance; 
    private int num;
    private double limit;
    private String senha;

    public Account() //sem parametros
    {
        this.name = "";
        this.balance = 0.0;
        this.num = 0;
        this.senha = "0000";
    }

    public Account(String name, int num, double limit) //com parametros
    {
        this.name = name;
        this.num = num;
        this.limit = limit;
        this.balance = 0.0;
        this.senha = "0000";
    }

    public void deposit(double depositAmount)
    {
         if(balance+depositAmount > limit)
        {
            System.out.printf("O valor adicionado ultrapassa o limite da conta!!");
        }
        else
        {
            balance = balance + depositAmount;
        }
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

    public void withdraw(double WDamount) {
        if (WDamount > balance + limit)
        {
            System.out.println("Saldo insuficiente!");
        } else
        {
            balance -= WDamount;
        }
    }

    public void changePassword(String oldpass, String newpass)
    {
        if(this.senha.equals(oldpass))
        {
            this.senha = newpass;
            System.out.println("Senha alterada!");
        }
        else
        {
            System.out.println("Senha incorreta, imposível alterar!");
        }

    }

}
