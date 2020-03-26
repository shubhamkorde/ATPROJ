
/**
 * Write a description of class TrafficSignal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
import java.lang.Math.*;
import java.text.DecimalFormat;
public class TrafficSignal
{
    public static void main(String args[])
    {
        //Object Declaration
        TrafficSignal obj = new TrafficSignal();
        Scanner sc = new Scanner(System.in);

        //variable declaration
        int maxcol=6;
        int maxrow=60;
        int lst, count , m, app_phs[], choice, no, arr, c6, count3, iterate, count_iter, tt, tabu_ind, leap, position;
        int sum_at[], entry_tempo[], sum_at_tempo[], entry[];
        double cs, hor, lam_inc, tm_stp, n, cyclestart, rt, ts, cum_delay, arrival_time[][], lam[][], lambda[][], ki[][], k[],diff_sum[],tabu_char[];
        double lam_sol[], pe, veh_count[], prop_lam[], at[][], lam_combo[][] ;
        double temp1, temp2[];
        //Array Instantiation
        app_phs = new int[maxcol];
        entry= new int[maxcol];
        sum_at= new int[maxcol];
        sum_at_tempo= new int[maxcol];
        entry_tempo= new int[maxcol];
        arrival_time= new double[maxrow][maxcol];
        lam= new double[maxrow][maxcol];
        lambda=new double[maxrow][maxcol];
        ki= new double[maxrow][maxcol];
        lam_sol= new double[maxcol];
        k= new double[maxcol];
        diff_sum= new double[maxrow];
        tabu_char= new double[maxcol];
        at = new double[maxrow][maxcol];
        prop_lam = new double[maxcol];
        veh_count= new double[maxcol];
        lam_combo= new double[maxrow][maxcol];
        temp2= new double[maxcol];
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
        System.out.println("Enter 1 for test 2 for checking");
        int a;
        a= sc.nextInt();    

        if(a==1)
        {
            hor= 20;
            lam_inc=1;
            lst=1;
            tm_stp=2;
            m=4;
            n=4;
        }

        else 
        {
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

            for(c6=0; c6<m; c6++)
            {
                System.out.println("Enter the phase being assigned to the approach "+ (c6+1));
                app_phs[c6] = sc.nextInt();
            }

            System.out.println("Enter 1 to input the arrival times manually OR Enter 2 to input the arrival times from arr_time.txt file");
            choice= sc.nextInt();
        }


        //user defined phase-to-phase process condition
        //while(no==1)
        //{
        for(int j=0;j<maxrow;j++)
        {
            for(int i=0;i<maxcol;i++)
            {
                arrival_time[j][i]=0;
                lam[j][i]=0;
                lambda[j][i]=0;
                ki[j][i]=0;
            }
        }

        for(int i=0;i<maxcol;i++)
        {
            lam_sol[i]=0;
        }

        pe=cs+hor;

        //automatic inphase process conditioning
        //while(rt<pe)
        //{
        arr=0;
        c6=0;
        iterate=0;
        rt=rt+ts;

                
        System.out.println("\n The cycle start time is now "+ cs + " and the nominal cycle end time is"+ (cs+hor)+"\n");

        app_phs[0]=1;
        app_phs[1]=3;
        app_phs[2]=2;
        app_phs[3]=4;

        choice=3;

        arrival_time[0][0]=2;
        arrival_time[1][0]=3;
        arrival_time[2][0]=4;
        arrival_time[3][0]=5;
        arrival_time[0][1]=12;
        arrival_time[4][1]=4;
        arrival_time[1][1]=17;
        arrival_time[2][1]=13;
        arrival_time[3][1]=10;
        arrival_time[0][2]=12;
        arrival_time[1][2]=15;
        arrival_time[2][2]=1;
        arrival_time[3][2]=7;
        arrival_time[4][2]=6;
        arrival_time[5][2]=2;
        arrival_time[1][3]=3;
        arrival_time[0][3]=6;
        arrival_time[2][3]=8;

        arr=6;

        ki[0][0]=app_phs[0];
        ki[1][0]=app_phs[0];
        ki[2][0]=app_phs[0];
        ki[3][0]=app_phs[0];
        ki[0][1]=app_phs[1];
        ki[1][1]=app_phs[1];
        ki[2][1]=app_phs[1];
        ki[3][1]=app_phs[1];
        ki[4][1]=app_phs[1];
        ki[0][2]=app_phs[2];
        ki[1][2]=app_phs[2];
        ki[2][2]=app_phs[2];
        ki[3][2]=app_phs[2];
        ki[4][2]=app_phs[2];
        ki[5][2]=app_phs[2];
        ki[0][3]=app_phs[3];
        ki[1][3]=app_phs[3];
        ki[2][3]=app_phs[3];

        arr=6;
        //Arrival time setup
        if(choice==1)
        {
            System.out.println("Enter the arrival times within the range of cycle start and end times");
            while(c6<m)
            {
                System.out.println("For approach "+ (c6+1) + " :\n");
                System.out.println("How many vehicles are arriving");
                int j= sc.nextInt();
                System.out.println("Enter the arrival time for each vehicle");
                for( count=0;count<j;count++)
                {
                    System.out.println("Vehicle No:-" + (count+1) );
                    arrival_time[count][c6]= sc.nextDouble();
                    ki[count][c6]=app_phs[c6];
                }
                c6++;
                if(j>arr)
                {
                    arr=j;
                }
            }
            System.out.println();
        }

        if(choice==3)
        {

        }

        if(choice==2)
        {

        }

        //Setting up lambda depending on vehicle arrival times.

        for(int j=0; j<arr; j++)
        {
            for(int i=0;i<m;i++)
            {
                if(arrival_time[j][i]>0)
                {
                    lam[j][i]=(arrival_time[j][i]-cs)/(hor+(n*lst));
                }
            }
        }

        //Lambda solution space 
        int count2 = 0;
        int lam_max = 0;
        for ( count = 1; count <= n; count++)
        {
            for (int j = 0; j < arr; j++)
            {
                for (int i = 0; i < m; i++)
                {
                    if (lam[j][i] > 0)
                    {
                        if (ki[j][i] == count)
                        {
                            lambda[count2][count] = lam[j][i];
                        }

                        if (ki[j][i] == count)
                        {
                            count2++;
                        }

                        if (count2 > lam_max)
                        {
                            lam_max = count2;
                        }
                    }

                }
            }
            count2 = 0;
        }

        //display of independent variables for confirmation
        System.out.println("The arrival time matrix for this cycle is ");
        for(int j=0; j<arr; j++)
        {
            for(int i=0; i<m; i++)
            {
                System.out.print(String.format("%.2f", arrival_time[j][i])+ "  ");
            }
            System.out.println();
        }

        System.out.println("The phase of each vehicle is according to the following matrix ");
        for(int j=0; j<arr; j++)
        {
            for(int i=0; i<m; i++)
            {
                System.out.print(String.format("%.2f", ki[j][i])+ "  ");
            }
            System.out.println();
        }

        System.out.println("The lambda matrix to be used is as follows ");
        for(int j=0; j<arr; j++)
        {
            for(int i=0; i<m; i++)
            {
                System.out.print(String.format("%.2f", lambda[j][i])+ "  ");
            }
            System.out.println();
        }

        //solution selection for proportional heuristic
        //ON HOLD for now
        
        //Brute Force Method for selection of initial solution
        int index[];
        index = new int[(int)n+1];
        
        for (int i = 0; i <= n; i++)
        {
            index[i] = 0;
        }
        double NC = Math.pow((lam_max), n);
        System.out.println(NC);
        System.out.println(lam_max);
        
        for ( count = 1; count <= NC; count++ )
        {
            if ( count == 1 )
            {
                for ( count2 = 1; count2 <= n; count2++ )
                {
                    index[count] = 0;
                }
            }
            else 
            {
                position = (int)n;
                while ( index[position] == lam_max )
                {
                    position--;
                }
                index[position]++;
                for ( count3 = position+1; count3 <= n; count3++ )
                {
                    index[count3] = 0;
                }
            }
            for ( count2 = 0; count2 <= n ; count2++)
            {
            lam_sol[count2] = lambda[index[count2]][count2];
            }
        }
        
        
        System.out.println("The Initial Solution is as follows");
        for(int i=0; i<=n; i++)
        {
            System.out.println(lam_sol[i]);
        }
        
        System.out.println("\n index matrix");
        for(int i=0; i<=n; i++)
        {
            System.out.println(index[i]);
        }
        //}
        //}
        
    }
}