package Java;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Calculator {

    private Scanner scanner;
    public Calculator() {
        scanner = new Scanner(System.in);
        readExpression();

    }

    private void readExpression() {
        System.out.print("Input your expression: ");
        String expression = scanner.nextLine().trim();
        while (!expression.equals("quit")){
            try {
                System.out.println( " = " +count(expression));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.print("Input your expression: ");

            expression = scanner.nextLine().trim();
        }
    }

    private String count(String expression) throws Exception {
        // For a + b format
        boolean isArabic = false;
        boolean isRome = false;
        int a = 0;
        int b = 0;
        String [] partsOfExpression = expression.split(" ");
        int result = 0;

        int operands = 0;
        for (char c: expression.toCharArray()) {
            if (c > 41 && c < 48)
                operands++;
            if (c >47 && c < 58)
                isArabic = true;
            if (c > 65)
                isRome = true;
        }
        if (isArabic && isRome)
            throw new Exception("Римские и араюские цифры не дружат");
        if (operands != 1)
            throw new Exception("Неверный формат выражения");





        // For a+b format
        if (partsOfExpression.length < 2){
            char [] expressionBySings = expression.toCharArray();
            int positionInsideExpression = 0;
            partsOfExpression = new String[3];
            for (char i: expressionBySings) {
                String sing = "";
                if (partsOfExpression[positionInsideExpression] != null)
                 sing = partsOfExpression[positionInsideExpression].trim();
                if (i > 41 && i < 48)
                    positionInsideExpression++;
                if (sing.equals(("+")) || sing.equals(("-")) ||
                        sing.equals(("*")) || sing.equals(("/")))
                    positionInsideExpression++;
                if (partsOfExpression[positionInsideExpression] != null)
                    partsOfExpression[positionInsideExpression] += String.valueOf(i);
                else
                    partsOfExpression[positionInsideExpression] = String.valueOf(i);
            }
            if (positionInsideExpression != 2)
                throw new Exception("Неверный формат выражения");
        }
        if (isArabic) {
            a = Integer.parseInt(partsOfExpression[0]);
            b = Integer.parseInt(partsOfExpression[2]);
        }
        if (isRome){
            for (int i = 0; i < partsOfExpression.length; i+=2) {
                int romeToArabic = 0;
                if (partsOfExpression[i].charAt(0) == 'V')
                        romeToArabic = 5 + partsOfExpression[i].length() - 1;
                if (partsOfExpression[i].charAt(0) == 'X')
                    romeToArabic = 10 + partsOfExpression[i].length() - 1;
                if (partsOfExpression[i].charAt(0) == 'I'){
                    boolean isOneOnly = true;
                    for (char c:partsOfExpression[i].toCharArray()) {
                        if (c == 'X'){
                            isOneOnly = false;
                            romeToArabic = 10 - (partsOfExpression[i].length() - 1);
                        }
                        if (c == 'V'){
                            isOneOnly = false;
                            romeToArabic = 5 - (partsOfExpression[i].length() - 1);
                        }
                    }
                    if (isOneOnly)
                        romeToArabic = partsOfExpression[i].length();
                }


                if (i == 0)
                    a = romeToArabic;
                else
                    b = romeToArabic;
            }

        }

        if (a > 10 || b > 10)
            throw new Exception("Один из операндов больше 10");

        switch (partsOfExpression[1]){
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            case "/" -> result = a / b;
        }

        if (isRome && result < 1)
            throw new Exception("Для римских цифр только положительный результат");
        String romeResult = "";
        // Processing result to rome format
        if (isRome){
            romeResult = "";
            int l1 = result / 50;
            int lr = result % 50;
            int d1 = lr / 10;
            int unit = lr % 10;

            if (l1 == 2)
                romeResult = "C";
            if (l1 == 1 && lr > 39)
                romeResult = "XC";
            if (l1 == 1){
                romeResult = "L";
                for (int i = 0; i < d1; i++) {
                    romeResult += "X";
                }
            }
            if (l1 == 0 && lr > 39)
                romeResult = "XL";
            if (l1 == 0 && lr < 40){
                for (int i = 0; i < d1; i++) {
                    romeResult += "X";
                }
            }
            if (unit == 9)
                romeResult += "IX";
            if (unit < 4)
                for (int i = 0; i < unit; i++)
                    romeResult+="I";
            if (unit == 4)
                romeResult+="IV";
            if (unit > 4 && unit < 9){
                romeResult += "V";
                for (int i = 0; i < unit-5; i++) {
                    romeResult += "I";
                }
            }
        }

        if (isRome)
            return romeResult;
        return String.valueOf(result);
    }

}
