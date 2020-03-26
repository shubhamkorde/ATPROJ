/**
 * Write a description of class TrafficSignal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
import java.lang.Math.*;
import java.text.DecimalFormat;
public class TrafficSignal2
{
    //variable declaration
    static int maxcol=6;
    static int maxrow=1800;
    static int count2 = 0;
    static int lam_max = 0;
    static int i, j, lst, count , m, app_phs[], choice, sim_end, no, arr, c6, count3, iterate, count_iter, tt, tabu_ind, leap, position, prop_rank, iter_opt, phase;
    static int sum_at[], cntr_temp[], sum_at_tempo[], cntr[];
    static double cs, hor, lam_inc, tm_stp, n, cyclestart, rt, ts, cum_delay, arrival_time[][], lam[][], lambda[][], ki[][], k[],diff_sum[],tabu_char[];
    static double lam_sol[], pe, veh_count[], prop_lam[], at[][], lam_combo[][], stop_del[][], q_del[][];
    static double temp1, temp2[], tabu_index[],lam_out[];
    static int xdummy[][], ydummy[][];
    static double total_del, total_stop_del, exp_del, cum_del, co_del, delay;
    static void arrayinst()
    {
        app_phs= new int[maxcol];
        cntr= new int[maxcol];
        sum_at= new int[maxcol];
        sum_at_tempo= new int[maxcol];
        cntr_temp= new int[maxcol];
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
        tabu_index = new double[maxcol];
        xdummy = new int[maxrow][maxcol];
        ydummy = new int[maxrow][maxcol];
        stop_del = new double[maxrow][maxcol];
        q_del= new double[maxrow][maxcol];
        lam_out= new double[maxcol];
    }

    static void initialize()
    {
        cs =0;
        rt =0;
        sim_end=3600;
        phase=1;
        no=1;
        ts=0;
        tt=4;
        cum_delay=0;
        for ( i=0; i<maxcol; i++)  
        {
            app_phs[i]=0;
            cntr[i]=0;
            sum_at[i]=0;                
            sum_at_tempo[i]=0;              
            cntr_temp[i]=0;            
        }                    
    }

    static void show()
    {
        System.out.println("The arrival time matrix for this cycle is ");
        for(j=0; j<arr; j++)
        {
            for(i=0; i<m; i++)
            {
                System.out.print(String.format("%.2f", arrival_time[j][i])+ "  ");
            }
            System.out.println();
        }

        System.out.println("The phase of each vehicle is according to the following matrix ");
        for(j=0; j<arr; j++)
        {
            for(i=0; i<m; i++)
            {
                System.out.print(String.format("%.2f", ki[j][i])+ "  ");
            }
            System.out.println();
        }

        System.out.println("The lambda matrix to be used is as follows ");
        for(j=0; j<arr; j++)
        {
            for(i=0; i<m; i++)
            {
                System.out.print(String.format("%.2f", lambda[j][i])+ "  ");
            }
            System.out.println();
        }
    }

    static void phaseprep()
    {
        for(j=0;j<maxrow;j++)
        {
            for(i=0;i<maxcol;i++)
            {
                arrival_time[j][i]=0;
                lam[j][i]=0;
                lambda[j][i]=0;
                ki[j][i]=0;
            }
        }

        for(i=0;i<maxcol;i++)
        {
            lam_sol[i]=0;
        }

        pe=cs+hor;

    }

    static void setlambda()
    {

        for(j=0; j<arr; j++)
        {
            for(i=0;i<m;i++)
            {
                if(arrival_time[j][i]>0)
                {
                    lam[j][i]=(arrival_time[j][i]-cs)/(hor+(n*lst));
                }
            }
        }
    }

    static void setlambdasol()
    {

        for ( count = 1; count <= n; count++)
        {
            for (j = 0; j < arr; j++)
            {
                for (i = 0; i < m; i++)
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
    }

    static void propheuselect()
    {

        int index[];
        index = new int[(int)n+1];

        for (i = 0; i <= n; i++)
        {
            index[i] = 0;
        }
        double NC = Math.pow(lam_max, n);
        System.out.println(NC);
        for (i = 0; i < maxrow; i++)
        {
            diff_sum[i] = 0;
        }
        count_iter = 0;

        for ( count = 1; count <= NC; count++ )
        {
            if ( count == 1 )
            {
                for ( count2 = 1; count2 <= n; count2++ )
                {
                    index[count2] = 0;
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
                for (count3 = position+1; count3 <= n; count3++ )
                {
                    index[count3] = 0;
                }
            }

            double total_veh=0;

            for (i=0; i<=n; i++)
            {
                veh_count[i] = 0;
                prop_lam[i]=0;
            }

            for (j=0; j<=lam_max; j++)
            {
                for (i=0; i<=n; i++)
                {
                    if (lambda[j][i] > 0)
                    {
                        veh_count[i]++;
                        total_veh++;
                    }
                }
            }

            for (i = 0; i <= n; i++)
            {
                prop_lam[i] = (veh_count[i])/total_veh;
            }

            for ( count2 = 0; count2 <= n ; count2++)
            {
                lam_sol[count2] = lambda[index[count2]][count2];
            }

            int flag = 1;
            for (int count6 = 1; count6 <= n; count6++)
            {
                if (lam_sol[count6] < lam_sol[(count6 - 1)])
                    flag = 0;
            }
            if (flag == 1)
            {
                for (int count6 = 0; count6 < n; count6++)
                {
                    //System.out.println(count_iter + "  " + count6 );
                    diff_sum[count_iter] += Math.abs(lam_sol[count6] - prop_lam[count6]);
                    lam_combo[count_iter][count6] = lam_sol[count6];
                }
                count_iter++;
                
            }

            //System.out.println("The value of count-iter is "+ count_iter);
        }
        
        System.out.println("\n\n\n");
        for (count=0; count<count_iter; count++)
        {
            if (diff_sum[count] > diff_sum[count+1])
            {
                temp1 = diff_sum[count];
                for (count2=0; count2<=n; count2++)
                {
                    temp2[count2] = lam_combo[count][count2];
                }
                diff_sum[count]=diff_sum[count+1];
                for (count2=0; count2<=n; count2++)
                {
                    lam_combo[count][count2] = lam_combo[count+1][count2];
                }
                diff_sum[count+1]=temp1;
                for (count2=0; count2<=n; count2++)
                {
                    lam_combo[count+1][count2] = temp2[count2];
                }
            }
        }
        if (count_iter < 500)
        {
            count3 = (count_iter - 1);
        }
        else 
        {
            count3 = 500;
        }
        leap = 0;
    }

    static void hardcode()
    {
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
        arrival_time[2][2]=6;
        arrival_time[3][2]=7;
        arrival_time[4][2]=6;
        arrival_time[5][2]=2;
        arrival_time[1][3]=3;
        arrival_time[0][3]=6;
        arrival_time[2][3]=9;

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
    }

    static void metasearch()
    {
        for (count=0; count<count3; count++)
        {
            if (leap==(count3-1))
            {
                leap=0;
            }         
            else 
            {
                leap++;
            }

            for (count2 = 1; count2 <= n ; count2++)
            {
                lam_sol[count2] = lam_combo[leap][count2];
            }
            if (count > 0)
            {
                for (j=0;j<=tt;j++)
                {
                    if(lam_sol[(int)(tabu_index[j])] == tabu_char[j])
                    {
                        leap++;
                    }
                    for (count2 = 1; count2 <= n ; count2++)
                    {
                        lam_sol[count2] = lam_combo[leap][count2];
                    }
                }
            }
        }

        System.out.println("Meta Search 1 Soln");
        for(i=0; i<=n; i++)
        {
            System.out.println(lam_sol[i]);
        }

    }

    static void assigndummy()
    {

        for (j = 0; j < maxrow; j++)
        {
            for (i = 0; i < maxcol; i++)
            {
                if ((int)arrival_time[j][i] == 0)
                {
                    xdummy[j][i] = 0;
                    ydummy[j][i] = 0;
                }
                else
                {
                    if (arrival_time[j][i] <= (cs + ((hor+(n*lst)) * lam_sol[((int)ki[j][i]) - 1])))
                    {
                        xdummy[j][i] = 1;
                    }
                    else 
                    {
                        xdummy[j][i] = 0;
                    }
                    if (arrival_time[j][i] <= (cs + ((hor+(n*lst)) * lam_sol[((int)ki[j][i] - 1)])))
                    {
                        ydummy[j][i] = 0;
                    }
                    else if (arrival_time[j][i] > (cs + ((hor+(n*lst)) * lam_sol[((int)ki[j][i])])))
                    {
                        ydummy[j][i] = 0;
                    }
                    else 
                    {
                        ydummy[j][i] = 1;
                    }
                }
            }
        }

        System.out.print("The X Dummy Variables are ");
        for (j = 0; j < 6; j++)
        {
            for (i = 0; i < maxcol; i++)
            {
                System.out.print(xdummy[j][i]+"  ");
            }
            System.out.println();
        }

        System.out.print("\n\nThe Y Dummy Variables are ");
        for (j = 0; j < 6; j++)
        {
            for (i = 0; i < maxcol; i++)
            {
                System.out.print(ydummy[j][i]+"  ");
            }
            System.out.println();
        }
    }

    static void calcdelay()
    {
        for (j = 0; j < arr; j++)
        {
            for (i = 0; i < m; i++)
            {
                if(ki[j][i]!=0)
                {
                    stop_del[j][i] = (1 - ydummy[j][i]) * ((cs + (xdummy[j][i]*lam_sol[((int)ki[j][i] - 1)]*(hor+(n*lst))) + ((1 -
                                    xdummy[j][i])*(hor+(n*lst))) - arrival_time[j][i]));
                    q_del[j][i] = (1 - ydummy[j][i]) * ((2*xdummy[j][i]) +
                        xdummy[j+1][i] + xdummy[j+2][i]);
                    total_stop_del += (stop_del[j][i] + q_del[j][i]);
                }

            }
        }
        for (j=0; j<=(n-3); j++)
        {
            co_del += (cntr[j] * (cs + (lam_sol[j+1]*hor))) - sum_at[j] + (2 * cntr[j]);
        }
        total_del = total_stop_del + co_del;

        for(i=0; i<=n; i++)
        {
            System.out.println(lam_sol[i]*hor);
        }
        System.out.println("\n");
        System.out.println("Total Delay is "+ total_del);
        System.out.println("Total Stop Delay is "+ total_stop_del);
        System.out.println("Total Co-Delay is "+ co_del);
    }

    public static void main(String args[])
    {
        //Scanner Object Declaration
        TrafficSignal obj = new TrafficSignal();
        Scanner sc = new Scanner(System.in); 
        //================================

        //Array Instantiation
        arrayinst();

        //Initialization
        initialize();  

        //==================================
        //User Defined Variables Input
        System.out.println("Enter 1 for test-data  2 for checking");
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
        //=====================================================================

        //user defined phase-to-phase process condition
        while(no==1) //this is for repeating the process till processing time exists
        {
            //Setting the variable to zero as phase preparation
            phaseprep();
            //===================================================
            //automatic inphase process conditioning
            while(rt<pe)
            {
                arr=0;
                c6=0;
                iterate=0;
                rt=rt+ts;

                System.out.println("\n The cycle start time is now "+ cs + " and the nominal cycle end time is"+ (cs+hor)+"\n");
                //Hard Coding Sample Input for testing purpose
                hardcode();
                //===========================
                //Arrival time setup for actual purpose 
                if(choice==1)
                {
                    System.out.println("Enter the arrival times within the range of cycle start and end times");
                    while(c6<m)
                    {
                        System.out.println("For approach "+ (c6+1) + " :\n");
                        System.out.println("How many vehicles are arriving");
                        j= sc.nextInt();
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
                    //when testing the choice would be 3
                }

                if(choice==2)
                {
                    //to take input from external document 
                    //PENDING
                }
                //=====================================================
                //Setting up lambda depending on vehicle arrival times.
                setlambda();
                //=====================================================
                //Lambda solution space
                setlambdasol();

                //display of independent variables for confirmation
                show();
                //solution selection for proportional heuristic
                propheuselect();

                //Brute Force Method for selection of initial solution
                //not done

                //searching for solution metaheuristically
                metasearch();        

                //assignment of dummy variables for calculation of delay-time
                assigndummy();

                total_del = 0;
                total_stop_del = 0;
                co_del = 0;

                //delay calculations

                calcdelay();

                //delay calculations for each lambda solution set
                System.out.println("The Lambdas are :");
                for (int count6 = 0; count6 <= n; count6++)
                {
                    System.out.print(String.format("%.2f", lam_sol[count6])+ "  ");
                }
                System.out.println();
                System.out.println("The proportional rank is :");
                prop_rank = 0;
                for (int count6 = 0; count6 <= n; count6++)
                {
                    prop_rank += Math.abs(lam_sol[count6] - prop_lam[count6]);
                    System.out.println( prop_rank );
                }

                System.out.println("\n\nThe stopped delays are ");
                for (j = 0; j < 6; j++)
                {
                    for (i = 0; i < maxcol; i++)
                    {
                        System.out.print(String.format("%.2f", stop_del[j][i])+ "  ");
                    }
                    System.out.println();
                }

                //Optimal solution storage
                if (total_del < delay)
                {
                    delay = total_del;
                    iter_opt = (count+1);
                    for (i = 0; i <= n; i++)
                    {
                        lam_out[i] = lam_sol[i];
                    }
                    exp_del = 0;
                    for (i = 0; i<=(n-3); i++)
                    {
                        cntr_temp[i] = 0;
                        sum_at_tempo[i]=0;
                    }
                    for (j = 0; j < arr; j++)
                    {
                        for (i = 0; i < m; i++)
                        {
                            if (xdummy[j][i] == 1)
                            {
                                if (ki[j][i] == 2)
                                {
                                    exp_del += stop_del[j][i];
                                }
                                if (ki[j][i] >= 3)
                                {
                                    if (arrival_time[j][i] < (lam_sol[1]*(hor+(n*lst))))
                                    {
                                        cntr_temp[((int)ki[j][i]-3)]++;
                                        sum_at_tempo[((int)ki[j][i]-3)] +=arrival_time[j][i];
                                    }
                                }
                            }
                        }
                    }
                }

                //Tabu Tenure Reduction
                for (j=0; j<=tt; j++)
                {
                    if (j==tt)
                    {
                        tabu_char[j]=0;
                        tabu_index[j]=0;
                    }
                    else
                    {
                        tabu_char[j]=tabu_char[j+1];
                        tabu_index[j]=tabu_index[j+1];
                    }   
                }

                //Tabu Status Placement
                if (total_del > delay)
                {
                    tabu_char[0]=lam_sol[tabu_ind];
                    tabu_index[0]=tabu_ind;
                    if (tabu_ind == 1)
                    {
                        tabu_ind = (int)n;
                    }   
                    else 
                    {
                        tabu_ind--;
                    }
                }

                //Updation of Delays
                exp_del += (cntr[1] * (cs + (lam_sol[1]*hor))) - sum_at[1];
                for (i=0; i<=(n-3); i++)
                {
                    cntr[i] = cntr_temp[i];
                    sum_at[i] = sum_at_tempo[i];
                    if (i==(n-3))
                    {
                        cntr_temp[i]=0;
                        sum_at_tempo[i]=0;
                    }
                    else
                    {
                        cntr_temp[i]=cntr_temp[i+1];
                        sum_at_tempo[i]=sum_at_tempo[i+1];
                    }
                }

                System.out.println("Current Run-Time is "+ rt + " seconds");
                System.out.println("Minimum stopped delay time is "+ delay + " seconds");
                System.out.println("The minimum occurs at the iteration "+iter_opt);
                for(i=0;i<=n;i++)
                {
                    System.out.println(lam_out[i]);
                }

                System.out.println(phase);
                System.out.println(lam_out[1]*(hor +(n*lst)));
                System.out.println("Experienced stopped delay time is "+ exp_del + " seconds");

                //=======================================================
                pe = cs + (lam_out[1] * hor);
                no=1;
                cs = cs + (lam_out[1] * (hor+(n*lst))) + lst;

                if (phase == n)
                {
                    phase = 1;
                }
                else 
                {
                    phase = phase + 1;
                }
                for (int count6 = 0; count6 <= n; count6++)
                {
                    k[count6] = (k[count6] - 1);
                    if (k[count6] == 0)
                    {
                        k[count6] = n;
                    }
                }
                if (pe >= sim_end)
                {
                    no = 2;
                    System.out.println("The simulation end time has been reached!\n");
                }
                //
            }
        }
    }
}