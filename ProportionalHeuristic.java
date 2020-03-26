
/**
 * Write a description of class ProportionalHeuristic here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ProportionalHeuristic
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class ProportionalHeuristic
     */
    public ProportionalHeuristic()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        int index[];
        index = new int[(int)n+1];

        for (int i = 0; i <= n; i++)
        {
            index[i] = 0;
        }
        double NC = Math.pow((lam_max+1), n);
        System.out.println(NC);
        for (int i = 0; i < maxrow; i++)
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

            for (int i=0; i<=n; i++)
            {
                veh_count[i] = 0;
                prop_lam[i]=0;
            }

            for (int j=0; j<=lam_max; j++)
            {
                for (int i=0; i<=n; i++)
                {
                    if (lambda[j][i] > 0)
                    {
                        veh_count[i]++;
                        total_veh++;
                    }
                }
            }

            for (int i = 0; i <= n; i++)
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
        }
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
        //leap = 0;
    }
}
