package calculaa;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private static final Map<Character, Integer> ROMAN_VALUES = new HashMap<>();

    static {
        ROMAN_VALUES.put('I', 1);
        ROMAN_VALUES.put('V', 5);
        ROMAN_VALUES.put('X', 10);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the expression: ");
            String expression = scanner.nextLine();

            Object result = calculate(expression);
            if (result instanceof Integer) {
                System.out.println("Result: " + result);
            } else {
                System.out.println("Result: " + result);
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Object calculate(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid input format");
        }

        String num1Str = parts[0];
        char operator = parts[1].charAt(0);
        String num2Str = parts[2];

        boolean isRoman = isRoman(num1Str) && isRoman(num2Str);

        int num1 = isRoman ? romanToArabic(num1Str) : Integer.parseInt(num1Str);
        int num2 = isRoman ? romanToArabic(num2Str) : Integer.parseInt(num2Str);

        validateInput(num1, num2);

        int result = switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };

        if (isRoman) {
            if (result <= 0) {
                throw new RuntimeException("Result of Roman calculation must be positive");
            }
            return arabicToRoman(result);
        }

        return result;
    }

    private static void validateInput(int num1, int num2) {
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Numbers must be between 1 and 10 inclusive");
        }
    }

    private static boolean isRoman(String str) {
        return str.matches("[IVX]+");
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = ROMAN_VALUES.get(roman.charAt(i));

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }

            prevValue = value;
        }

        return result;
    }

    private static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new RuntimeException("Roman numerals cannot represent zero or negative numbers");
        }

        StringBuilder result = new StringBuilder();

        for (Map.Entry<Character, Integer> entry : ROMAN_VALUES.entrySet()) {
            char romanChar = entry.getKey();
            int value = entry.getValue();

            while (number >= value) {
                result.append(romanChar);
                number -= value;
            }
        }

        return result.toString();
    }
}
