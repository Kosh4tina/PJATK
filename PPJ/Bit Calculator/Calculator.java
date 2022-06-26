import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        System.out.println("Input 0 for both arguments to stop program");
        System.out.println();
        while(true) {
            System.out.print("Input 1 argument: ");
            int val1 = scanner.nextInt();

            System.out.print("Input 2 argument: ");
            int val2 = scanner.nextInt();

            if(val1 != 0 && val2 !=0) {
                System.out.print("Input operator: ");
                char operator = scanner.next().charAt(0);

                System.out.println("=======================");

                switch (operator) {
                    case '+':
                        System.out.println("Sum '+' ");
                        converter(val1);
                        converter(val2);
                        converter(sum(val1, val2));
                        break;

                    case '-':
                        System.out.println("Difference '-' ");
                        converter(val1);
                        converter(val2);
                        converter(diff(val1, val2));
                        break;

                    case '*':
                        System.out.println("Multiply '*' ");
                        converter(val1);
                        converter(val2);
                        converter(mul(val1, val2));
                        break;

                    case '/':
                        if (val2 != 0) {
                            System.out.println("Division '/' ");
                            converter(val1);
                            converter(val2);
                            converter(div(val1, val2));
                        } else {
                            System.out.println("Cannot be divided by 0");
                        }
                        break;
                    default:
                        System.out.println("Invalid operator");
                        break;
                }

                System.out.println("=======================");
            }
            else{
                System.out.println("Program stopped by user");
                System.out.println("=======================");
                break;
            }
        }
    }

    public static void converter(int val){
        if(val > 0) {
            int size = bitsCounter(val);
            int val2;
            System.out.print(val + " - ");
            while (--size >= 0) {
                if ((val & (1 << size)) == 0) {
                    val2 = 0;
                } else {
                    val2 = 1;
                }
                System.out.print(val2);
            }
            System.out.println();
        }else if(val == 0){
            System.out.println(val + " - " + 0);
        }else{                                          //The negative number converter does not work as I planned
            int tmp = diff(val, val << 1);
            int size = bitsCounter(tmp);
            int val2;
            System.out.print(val + " - (");
            while (--size >= 0) {
                if ((val & (1 << size)) == 0) {
                    val2 = 1;
                } else {
                    val2 = 0;
                }
                System.out.print(val2);
            }
            System.out.print("+1)");
            System.out.println();
        }
    }
    public static int bitsCounter(int n) {
        int result = 0;
        while (n > 0) {
            n >>= 1;
            result = sum(result,1);
        }
        return result;
    }

    public static int sum(int val1, int val2){
        int tmp1 = 1;
        int tmp2;
        int result;
        while(true){
            if(tmp1==0){
                result = val1;
                return result;
            }else {
                tmp1 = val1 & val2;
                tmp1 <<= 1;
                tmp2 = val1 ^ val2;
                val1 = tmp2;
                val2 = tmp1;
            }
        }
    }

    public static int diff(int val1, int val2){
        int tmp1 = 1;
        int tmp2;
        int tmp3 = ~val2;
        int result;
        while(true){
            if(tmp1==0){
                result = val1;
                result = sum(result,1);
                return result;
            }else {
                tmp1 = val1 & tmp3;
                tmp1 <<= 1;
                tmp2 = val1 ^ tmp3;
                val1 = tmp2;
                tmp3 = tmp1;
            }
        }
    }

    public static int mul(int val1, int val2){

        int result = 0;

        if(val1 >=0 && val2 >= 0) {
            while (val2 != 0) {
                if ((val2 & 1) == 1) {
                    result = sum(result, val1);
                }
                val2 >>= 1;
                val1 <<= 1;
            }
        }else if(val1 < 0 && val2 < 0){
            int tmp = ~val1;
            tmp = sum(tmp,1);
            int tmp2 = ~val2;
            tmp2 = sum(tmp2,1);

            while (tmp2 != 0) {
                if ((tmp2 & 1) == 1) {
                    result = sum(result, tmp);
                }
                tmp2 >>= 1;
                tmp <<= 1;
            }

        }else if(val1 < 0){
            int tmp = ~val1;
            tmp = sum(tmp,1);

            while (val2 != 0) {
                if ((val2 & 1) == 1) {
                    result = sum(result, tmp);
                }
                val2 >>= 1;
                tmp <<= 1;
            }
            result = diff(result, sum(result,result));
        }else if(val2 < 0){
            int tmp2 = ~val2;
            tmp2 = sum(tmp2,1);

            while (tmp2 != 0) {
                if ((tmp2 & 1) == 1) {
                    result = sum(result, val1);
                }
                tmp2 >>= 1;
                val1 <<= 1;
            }
            result = diff(result, sum(result,result));
        }

        return result;
    }

    public static int div(int val1, int val2) {
        int result = 0;

        if (val1 < 0 && val2 > 0) {

            while (val1 <= -val2) {
                result = diff(result,1);
                val1 = sum(val1,val2);
            }

        }else if (val2 < 0 && val1 > 0) {

            while (val1 >= -val2) {
                result = diff(result,1);
                val1 = sum(val1,val2);
            }

        } else if (val1 < 0 && val2 < 0){

            while (val1 <= val2) {
                result = sum(result,1);
                val1 = diff(val1,val2);
            }

        }else {

            while (val1 >= val2) {
                result = sum(result,1);
                val1 = diff(val1,val2);
            }

        }
        return result;
    }
}
