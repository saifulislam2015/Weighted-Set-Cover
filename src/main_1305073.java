import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class main_1305073 {
    public static void main(String[] args) throws IOException{
        int cases=0,n=0,m=0;

        //File file = new File("1305073_input.txt");
        File file = new File("1305073_input.txt");
        Scanner input = new Scanner(file);

        /*File file1 = new File("1305073_output_LP.csv");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
        PrintWriter pw = new PrintWriter(bw);

        File file2 = new File("1305073_output_DP.csv");
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(file2));
        PrintWriter pw2 = new PrintWriter(bw2);

        File file3 = new File("1305073_Approximate.csv");
        BufferedWriter bw3 = new BufferedWriter(new FileWriter(file3));
        PrintWriter pw3 = new PrintWriter(bw3);*/

        if(input.hasNextInt()) {
            cases = input.nextInt();
        }

        for(int i=0;i<cases;i++){
            if(input.hasNext()) n = input.nextInt();
            if(input.hasNext()) m = input.nextInt();

            Vector<Double> weight = new Vector<>();
            Vector<Integer> size = new Vector<>();
            ArrayList<Vector<Integer>> subsets = new ArrayList<>();

            for(int j=0;j<m;j++){
                if(input.hasNext()) weight.add(input.nextDouble());
                if(input.hasNext()) size.add(input.nextInt());

                Vector<Integer> subset = new Vector<>();

                for(int k=0;k<size.get(j);k++) {
                    if(input.hasNextInt()) subset.add(input.nextInt());
                }
                subsets.add(subset);
            }
            /*for(int k=0;k<subsets.size();k++){
                System.out.println("Subset "+ k + " has a weight of "+ weight.get(k));
                System.out.println("Elements of subset "+k +":");
                for(int l=0;l<size.get(k);l++){
                    System.out.print(subsets.get(k).get(l)+" ");
                }
                System.out.println();
            }*/

            //long start = System.nanoTime();
            LP_1305073 LP = new LP_1305073(n,m,size,weight,subsets);
            LP.setCover();
            //long end = System.nanoTime();
            //pw.print(n+","+ (end-start)/1000000+"\n");

            //long start2 = System.nanoTime();
            //System.out.println("DP");
            DP_1305073 DP = new DP_1305073(n,m,size,weight,subsets);
            DP.SetCover();
            //long end2 = System.nanoTime();
            //pw2.print(n+","+(end2-start2)/1000000+"\n");

            //pw3.print(n+","+LP.min/DP.min+"\n");


        }
        /*bw.close();
        bw2.close();
        bw3.close();*/
    }
}
