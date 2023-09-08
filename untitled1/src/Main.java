import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Map<String, Integer> romanNumerals = new HashMap<>();
    private static Map<Integer, String> reverseRomanNumerals = new HashMap<>();

    static {
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);
        romanNumerals.put("XX", 20);
        romanNumerals.put("XXX", 30);
        romanNumerals.put("XL", 40);
        romanNumerals.put("L", 50);
        romanNumerals.put("LX", 60);
        romanNumerals.put("LXX", 70);
        romanNumerals.put("LXXX", 80);
        romanNumerals.put("XC", 90);
        romanNumerals.put("C", 100);

        reverseRomanNumerals.put(1, "I");
        reverseRomanNumerals.put(2, "II");
        reverseRomanNumerals.put(3, "III");
        reverseRomanNumerals.put(4, "IV");
        reverseRomanNumerals.put(5, "V");
        reverseRomanNumerals.put(6, "VI");
        reverseRomanNumerals.put(7, "VII");
        reverseRomanNumerals.put(8, "VIII");
        reverseRomanNumerals.put(9, "IX");
        reverseRomanNumerals.put(10, "X");
        reverseRomanNumerals.put(20, "XX");
        reverseRomanNumerals.put(30, "XXX");
        reverseRomanNumerals.put(40, "XL");
        reverseRomanNumerals.put(50, "L");
        reverseRomanNumerals.put(60, "LX");
        reverseRomanNumerals.put(70, "LXX");
        reverseRomanNumerals.put(80, "LXXX");
        reverseRomanNumerals.put(90, "XC");
        reverseRomanNumerals.put(100, "C");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine();

        try {
            String result = calculate(expression);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String expression) {
        String[] parts = expression.split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверное выражение");
        }

        String a = parts[0];
        String operator = parts[1];
        String b = parts[2];

        boolean isArabicNumerals = a.matches("\\d+") && b.matches("\\d+");
        boolean isRomanNumerals = romanNumerals.containsKey(a) && romanNumerals.containsKey(b);

        if (!isArabicNumerals && !isRomanNumerals) {
            throw new IllegalArgumentException("Неподходящие числа");
        }

        int operandA;
        int operandB;

        if (isArabicNumerals) {
            operandA = Integer.parseInt(a);
            operandB = Integer.parseInt(b);
        } else {
            operandA = romanNumerals.get(a);
            operandB = romanNumerals.get(b);
        }

        if (operandA < 1 || operandA > 100 || operandB < 1 || operandB > 100) {
            throw new IllegalArgumentException("Недопустимые числа");
        }

        int result;
        switch (operator) {
            case "+":
                result = operandA + operandB;
                break;
            case "-":
                result = operandA - operandB;
                break;
            case "*":
                result = operandA * operandB;
                break;
            case "/":
                result = operandA / operandB;
                break;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }

        if (isArabicNumerals) {
            return Integer.toString(result);
        } else {
            if (result < 1) {
                throw new IllegalArgumentException("Неверный результат");
            }

            return convertToRomanNumerals(result);
        }
    }

    private static String convertToRomanNumerals(int number) {
        StringBuilder romanNumeral = new StringBuilder();

        // Добавляем римские цифры для десятков
        while (number >= 100) {
            romanNumeral.append("C");
            number -= 100;
        }
        if (number >= 90) {
            romanNumeral.append("XC");
            number -= 90;
        }
        if (number >= 50) {
            romanNumeral.append("L");
            number -= 50;
        }
        if (number >= 40) {
            romanNumeral.append("XL");
            number -= 40;
        }

        // Добавляем римские цифры для единиц
        while (number >= 10) {
            romanNumeral.append("X");
            number -= 10;
        }
        if (number >= 9) {
            romanNumeral.append("IX");
            number -= 9;
        }
        if (number >= 5) {
            romanNumeral.append("V");
            number -= 5;
        }
        if (number >= 4) {
            romanNumeral.append("IV");
            number -= 4;
        }

        // Добавляем оставшиеся единицы
        while (number > 0) {
            romanNumeral.append("I");
            number--;
        }

        return romanNumeral.toString();
    }
}