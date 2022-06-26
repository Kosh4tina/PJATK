import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

import static java.lang.StrictMath.sqrt;

public class Main {
    public static void Welcome(){
        System.out.println("Welcome to the program!");
        System.out.println("1 method can solve from (0-71) element and 2 method can solve more");
        System.out.println("Just choose method and input 'n' to start it");
        System.out.println("-----------------------");
        System.out.println("Program was created by s19575");
        System.out.println("-----------------------");
    }

    public static BigInteger[][] matrixMultiplication(BigInteger[][] a, BigInteger[][] b) {
        // [a00 * b00 + a01 * b10, a00 * b01 + a01 * b11]
        // [a10 * b00 + a11 * b10, a10 * b01 + a11 * b11]
        return new BigInteger[][]{
                {a[0][0].multiply(b[0][0]).add(a[0][1].multiply(b[1][0])), a[0][0].multiply(b[0][1]).add(a[0][1].multiply(b[1][1]))},
                {a[1][0].multiply(b[0][0]).add(a[1][1].multiply(b[1][0])), a[1][0].multiply(b[0][1]).add(a[1][1].multiply(b[1][1]))},
        };
    }

    public static BigInteger[][] matrixPowerFast(BigInteger[][] a, int n) {
        if (n == 0) {
            return new BigInteger[][]{
                    {BigInteger.ONE, BigInteger.ZERO},
                    {BigInteger.ZERO, BigInteger.ONE}
            };
        } else if (n % 2 == 0) {
            // a^(2k)=(a^2)^k
            return matrixPowerFast(matrixMultiplication(a, a), n / 2);
        } else {
            // a^(2k+1)=(a)*(a^2k)
            return matrixMultiplication(matrixPowerFast(a, n - 1), a);
        }
    }

    public static BigInteger FibMat(int n) {
        if (n == 0) {
            return BigInteger.ZERO;
        }

        BigInteger[][] a = {
                {BigInteger.ONE, BigInteger.ONE},
                {BigInteger.ONE, BigInteger.ZERO}
        };
        BigInteger[][] powerOfA = matrixPowerFast(a, n - 1);
        // nthFibonacci = powerOfA[0][0] * F_1 + powerOfA[0][0] * F_0 = powerOfA[0][0] * 1 + powerOfA[0][0] * 0
        BigInteger nthFibonacci = powerOfA[0][0];
        return nthFibonacci;
    }


    public static long FibBine(int n){
        double index = sqrt(5);
        double left = (1 + index) / 2;
        double right = (1 - index) / 2;

        long res = Math.round((Math.pow(left, n) - Math.pow(right, n)) / index);
        return  res;
    }

    public static void main(String[] args) {
        //Welcome();
        Scanner var = new Scanner(System.in);
        System.out.println("Enter n");
        //int n1 = var.nextInt();
        int n2 = var.nextInt();
        //System.out.println(FibBine(n1));
        System.out.println(FibMat(n2));
    }
}
