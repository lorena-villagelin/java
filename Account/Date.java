package Account;

public class Date 
{
    private int month;
    private int day;
    private int year;

    public Date(int day, int month, int year)
    {
        if(month <= 12)
        {
            this.month = month;
        }
        else
        {
            throw new IllegalArgumentException("Não existe esse mês: " + month);
        }
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
        {
            if (day <= 31)
            {
                this.day = day;
            }
            else
            {
                throw new IllegalArgumentException("Não existe esse dia: " + day);
            }
        }
            else
            {
                if (day <= 30)
                {
                    this.day = day;
                }
                else 
                {
                    throw new IllegalArgumentException("Não existe esse dia: " + day);
                }
            }
        this.year = year;
        if (year%4 == 0 && year%100 != 0 || year%400 == 0)
        {
            System.out.printf("O ano %d é bissexto!%n", year);
        }
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    public int getYear()
    {
        return year;
    }
}
