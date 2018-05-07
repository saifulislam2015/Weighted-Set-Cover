import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class LP_1305073 {
    public int n;
    public int m;
    public Vector<Integer> size;
    public Vector<Double> weight;
    public Vector<Double> sl;
    public int[] frequency;
    public ArrayList<Vector<Integer>> subsets;
    public double freq;
    public double min;

    public LP_1305073(int n,int m,Vector<Integer> s,Vector<Double> w,ArrayList<Vector<Integer>> subsets){
        this.n = n;
        this.m = m;
        this.size = s;
        this.weight = w;
        this.subsets = subsets;
        sl = new Vector<Double>(m);
        frequency = new int[n];
        for(int i=0;i<n;i++) frequency[i]=0;

        freq = 0;
    }

    public LinearConstraint MakeEqn(int i){
        double [] constcoeff = new double[m];
        LinearConstraint eqn;

        for(int j=0;j<m;j++){
            Vector<Integer> subset = subsets.get(j);
            if(subset.contains(i)) constcoeff[j] = 1;
            //System.out.print(constcoeff[j]+" ");
        }
        //System.out.println();
        eqn = new LinearConstraint(constcoeff, Relationship.GEQ,1);
        return eqn;
    }

    public int maxfreq(){
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                Vector<Integer> v = subsets.get(j);
                if(v.contains(i)) frequency[i]++;
            }
        }
        Arrays.sort(frequency);
        return frequency[n-1];
    }

    public double[] SimpleRounding(double [] x){
        double [] C = new double[m];
        Random rand = new Random();
        for(int i=0;i<x.length;i++){
            boolean val = rand.nextInt((int)(1/x[i]))==0;
            if(val==true) C[i] = 1;
            else C[i] = 0;
        }
        return C;
    }

    public double[] UnionSets(double[] a,double[] b){
        double[] C = new double[m];
        for(int i=0;i<a.length;i++){
            if(a[i]==1 || b[i]==1) C[i] = 1;
            else C[i] = 0;
        }
        return C;
    }

    public double minCost(){
        double [] coefficients = new double[m];
        ArrayList<LinearConstraint> constraints = new ArrayList<>();
        PointValuePair soln = null;
        SimplexSolver simplex = null;
        LinearConstraintSet lcs = null;

        try{
            for(int i=0;i<m;i++) coefficients[i] = weight.get(i);
            LinearObjectiveFunction obj = new LinearObjectiveFunction(coefficients,0);
            for(int i=0;i<n;i++) constraints.add(MakeEqn(i));
            simplex = new SimplexSolver();
            lcs = new LinearConstraintSet(constraints);
            soln = simplex.optimize(new MaxIter(50),obj,lcs, GoalType.MINIMIZE,new NonNegativeConstraint(true));


        }catch (NoFeasibleSolutionException e){
            e.getStackTrace();
        }

        double [] x = soln.getPoint();
        //for(int i=0;i<x.length;i++) System.out.print(x[i]+" ");

        double cost = 0;
        System.out.println();
        /*freq = maxfreq();
        for(int i=0;i<m;i++){
            if(x[i]>=1/freq){
                sl.add(i,1.0);
                cost+=weight.get(i);
            }
            else sl.add(i,0.0);
        }
        return cost;*/

        double t = 2* Math.ceil(Math.log(m));
        int T = (int) t;
        double[] arr = new double[m];

        for(int i=0;i<T;i++){
            double[] a = SimpleRounding(x);
            arr = UnionSets(a,arr);
        }

        //for(int i=0;i<x.length;i++) System.out.print(arr[i]+" ");
        for(int i=0;i<m;i++){
            if(arr[i]==1){
                sl.add(i,1.0);
                cost+=weight.get(i);
            }
            else sl.add(i,0.0);
        }
        return cost;
    }

    public void setCover(){
        min = minCost();

        System.out.println("-----------LP SOLUTION--------------"+"("+n+")");
        System.out.println("Min Cost: "+ min);
        System.out.println("Subsets: ");
        for(int i=0;i<m;i++){
            if(sl.get(i)==1) System.out.print(i+" ");
        }
        System.out.println();
    }


}
