
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<String>ranges=new ArrayList<String>();
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        scan.nextLine();
        ArrayList<String>inputs=new ArrayList<String>();
        for(int i=0;i<n;i++)
        {
             inputs.add(scan.nextLine());
        }
        for (int i=0;i<n;i++)
        {
            getRanges(inputs.get(i));
        }
        System.out.println(ranges);
    }
    public static void getRanges(String input)
    {
        String[] dates=input.split(" ");
        if(!isSignedUp(dates))
        {
            ranges.add("No range");
        }
        else
        {

            LocalDate[] range = allowableRange(dates);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            //System.out.println("Start "+range[0]+" End "+range[1]);
            String startDate = range[0].format(formatter);
            String endDate = range[1].format(formatter);
            ranges.add(startDate+" to "+endDate);
        }

    }
    public static LocalDate[] allowableRange(String dates[])
    {
        LocalDate[] range=new LocalDate[2];
        LocalDate signup=getLocalDateFormat(dates[0]);
        LocalDate currentDate=getLocalDateFormat(dates[1]);
        LocalDate anniversary1=signup.withYear(currentDate.getYear());
        LocalDate anniversary2=signup.withYear(currentDate.getYear()-1);
        LocalDate anniversary3=signup.withYear(currentDate.getYear()+1);
        LocalDate anniversary;
        long days1=ChronoUnit.DAYS.between(currentDate,anniversary1);
        long days2=ChronoUnit.DAYS.between(anniversary2,currentDate);
        long days3=ChronoUnit.DAYS.between(currentDate,anniversary3);
        if(days1<days2 && days1<days3)
        {
            anniversary=anniversary1;
        }
        else if(days2<days1 && days2<days3)
        {
            anniversary=anniversary2;
        }
        else
        {
            anniversary=anniversary3;
        }
            range[0]=anniversary.minusDays(30);
            LocalDate endDate=anniversary.plusDays(30);
            range[1]=currentDate.isAfter(endDate)?endDate:currentDate;

        return range;
    }
    public static LocalDate getLocalDateFormat(String date)
    {
        String[] details=date.split("-");
        int day=Integer.parseInt(details[0]);
        int month=Integer.parseInt(details[1]);
        int year=Integer.parseInt(details[2]);
        LocalDate localDate= LocalDate.of(year,month,day);
        return localDate;
    }
    public static boolean isSignedUp(String dates[])
    {
        LocalDate signup=getLocalDateFormat(dates[0]);
        LocalDate currentDate=getLocalDateFormat(dates[1]);
       return signup.isBefore(currentDate);
    }

}
