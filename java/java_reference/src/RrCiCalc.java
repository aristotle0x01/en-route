
// this is to calc Relative Risk confidence interval
// ref: How to Calculate a Confidence Interval for Relative Risk: https://www.statology.org/relative-risk-confidence-interval/
//      https://archive.ph/kcHbA
public class RrCiCalc {
    public static void main(String[] args) {
        double rr = 15.4586901;
        int[] arrT = calc_arr(267, 0.258499002);
        int[] arrC = calc_arr(100, 0.016721921);
        int a = arrT[0];
        int b = arrT[1];
        int c = arrC[0];
        int d = arrC[1];
//        double rr = calc_rr(a, b, c, d);
        double[] r_ci = calc_rr_ci(rr, a, b, c, d);

        System.out.println("[" + r_ci[0] + ","  + r_ci[1] + "]");
    }

    private static int[] calc_arr(int num, double ratio){
        int arr[] = new int[2];
        double total = Math.round(num*1.0/ratio);
        arr[0] = num;
        arr[1] = (int)(total-num);
        return arr;
    }

    private static double[] calc_rr_ci(double rr, int a, int b, int c, int d){
        double arr[] = new double[2];
        double inner = 1.0/a + 1.0/c - 1.0/(a+b) - 1.0/(c+d);
        double sqrt = Math.sqrt(inner);
        double zsqrt = 1.96*sqrt;
        double lnrr = Math.log(rr);
        arr[0] = Math.exp(lnrr-zsqrt);
        arr[1] = Math.exp(lnrr+zsqrt);
        return arr;
    }

    private static double calc_rr(int a, int b, int c, int d){
        return (a*1.0/(a+b)) / (c*1.0/(c+d)) ;
    }
}
