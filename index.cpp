#include <iostream>

using namespace std;

int main()
{
    //Variable Initialization
    const int maxcol = 6;
    int lst, m, app_phs[maxcol], choice;
    int sum_at[maxcol], entry_tempo[maxcol], sum_at_tempo[maxcol], entry[maxcol];
    double hor, lam_inc, tm_stp, n, cs, rt, ts, cum_delay;

    //Initialization
    cs=0;
    rt=0;
    no=1;
    ts=0;
    cum_delay=0;
    for (int i=0; i<maxcol; i++)
    {
        app_phs[i]=0;
        entry[i]=0;
        sum_at[i]=0;
        sum_at_tempo[i]=0;
        entry_tempo[i];
    }



    //User Defined Variables Input
    cout<<"Welcome to Traffic Optimization System"<<endl;
    cout<<"This program will find the optimum signal cycle for your given input"<<endl;
    cout<<"Enter assumed horizon length(signal-cycle length)"<<endl;
    cin>>hor;
    cout<<"Enter the minimum discernible lambda increment "<<endl;
    cin>>lam_inc;
    cout<<"Enter the lost time (all-red time)";
    cin>>lst;
    cout<<"Enter the re-iteration time period"<<endl;
    cin>>tm_stp;
    cout<<"Enter the number of approaches to the intersection (lesser than 6)"<<endl;
    cin>>m;
    cout<<"Enter the number of phases to be used for the cycle"<<endl;
    cin>>n;
    cout<<"The assumed cycle length would be adjusted for the lost time to "<< hor + (n*lst)<< "seconds"<<endl;

    for(int c6=0; c6<m;c6++)
    {
        cout<<"Enter the phase being assigned to approach "<< c6+1 <<endl;
        cin>> app_phs[c6];
    }  
    cout<<"Enter 1 to input the arrival times manually OR Enter 2 to input the arrival times from arr_time.txt file"<<endl;
    cin>>choice;

    //User Defined phase to phase process condition
    while()
    //Inputting the arrival times
    if(choice==1)
    {

    }
    return 0;
}
