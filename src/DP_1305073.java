import java.util.ArrayList;
import java.util.Vector;

public class DP_1305073 {
    public int n;
    public int m;
    public Vector<Integer> size;
    public Vector<Double> weight;
    public Vector<Double> sl;
    public ArrayList<Vector<Integer>> subsets;
    public double[][] dp;
    public int[][] direction;
    public int[] masks;
    public double min = 0;

    public DP_1305073(int n,int m,Vector<Integer> size,Vector<Double> weight,ArrayList<Vector<Integer>> s){
        this.n = n;
        this.m = m;
        this.size = size;
        this.weight = weight;
        this.subsets = s;
        sl = new Vector<Double>(m);

        int col = (int) Math.pow(2,n);
        dp = new double[m][col];
        direction = new int[m][col];
        for(int i=0;i<m;i++){
            for(int j=0;j<col;j++){
                dp[i][j] = -1;
                direction[i][j] = -1;
            }
        }

        masks = new int[m];
        for(int i=0;i<m;i++) {
            Vector<Integer> set = subsets.get(i);
            int c = 0;

            for (int j = 0; j < size.get(i); j++) {
                c |= 1 << set.get(j);
            }
            masks[i] = c;
            //System.out.println(c);
        }
    }

    public double minCost(int index,int bitmask){
        //System.out.println(bitmask);
        if (index == m && bitmask != (1<<n)-1) return Double.MAX_VALUE;
        else if (index == m && bitmask == (1<<n)-1) return 0;
        else {
            double valChoose= minCost( index+1,bitmask | masks[index] ) + weight.get(index);
            double valNotChoose= minCost(  index+1,bitmask );
            double cost;
            //System.out.println("valc: "+valChoose+"valnc: "+valNotChoose);

            if(valChoose<valNotChoose){
                cost = valChoose;
                direction[index][bitmask] = 1;
                //System.out.println(index);
            }
            else {
                cost = valNotChoose;
                direction[index][bitmask] = 0;
            }
            dp[index][bitmask] = cost;
            //System.out.print(cost+" ");
            return cost;
        }
    }

    public void SolutionSets(){
        int bitmask = 0;

        for(int i=0;i<m;i++){
            if(direction[i][bitmask]==1){
                sl.add(i,1.0);
                bitmask |= masks[i];
                //System.out.println(bitmask);
            }
            else sl.add(i,0.0);
        }
        //for(int i=0;i<m;i++) System.out.print(sl.get(i)+" ");
        System.out.println();
    }

    public void SetCover(){
        min = minCost(0,0);
        SolutionSets();

        System.out.println("-----------DP SOLUTION--------------"+"("+n+")");
        if(min==Double.MAX_VALUE) System.out.println("No Solution Exists");
        else {
            System.out.println("Min Cost: "+ min );
            System.out.println("Subsets : ");

            for(int i=0;i<m;i++){
                if(sl.get(i)==1) System.out.print(i+" ");
            }
        }
        System.out.println();
    }


}
