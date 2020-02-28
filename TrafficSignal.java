
/**
 * Write a description of class TrafficSignal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class TrafficSignal
{
    public static void main(String args[])
    {
        //Object Declaration
        TrafficSignal obj = new TrafficSignal();
        Scanner sc = new Scanner(System.in);
        
        //variable declaration
        int maxcol=6; 
        int lst, m, app_phs[], choice, no;
        int sum_at[], entry_tempo[], sum_at_tempo[], entry[];
        double hor, lam_inc, tm_stp, n, cs, rt, ts, cum_delay;
        
        //Array Instantiation
        app_phs = new int[maxcol];
        entry= new int[maxcol];
        sum_at= new int[maxcol];
        sum_at_tempo= new int[maxcol];
        entry_tempo= new int[maxcol];
        //Initialization
        cs =0;
        rt =0;
        no=1;
        ts=0;
        cum_delay=0;
        
            for (int i=0; i<maxcol; i++)
        {
            app_phs[i]=0;
            entry[i]=0;
            sum_at[i]=0;
            sum_at_tempo[i]=0;
            entry_tempo[i]=0;
        }
        
        //User Defined Variables Input
        System.out.println("Welcome to Traffic Optimization System");
        System.out.println("This program will find the optimum signal cycle for your given input");
        System.out.println("Enter assumed horizon length(signal-cycle length)");
        hor = sc.nextDouble();
        System.out.println("Enter the minimum discernible lambda increment");
        lam_inc = sc.nextDouble();
        System.out.println("Enter the lost time (all-red time");
        lst=sc.nextInt();
        System.out.println("Enter the re-iteration time period");
        tm_stp= sc.nextDouble();
        System.out.println("Enter the number of approaches to the intersection (lesser than 6)");
        m=sc.nextInt();
        System.out.println("Enter the number of phases to be used for the cycle");
        n= sc.nextInt();
        System.out.println("The assumed cycle length would be adjusted for the lost time to "+ (hor + (n*lst))+  " seconds");
        
        for(int c6=0; c6<m; c6++)
        {
            System.out.println("Enter the phase being assigned to the approach "+ (c6+1));
            app_phs[c6] = sc.nextInt();
        }
        System.out.println("Enter 1 to input the arrival times manually OR Enter 2 to input the arrival times from arr_time.txt file");
        choice= sc.nextInt();
        
        //user defined phase-to-phase process condition
        while(no==1)
        {
            for(
        }
        
    }
}
