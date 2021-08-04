import utill.CalculatorImplException;

import java.util.*;

import static java.lang.String.valueOf;

public class Utill {

    private static String inputString;
    private static String userInput;

    public TypeOfLanguage definitionRomanArabic(String inputString) throws CalculatorImplException { // метод проверки вводимых символов: арабские цифры или римские

        String expression = inputString;

        if (expression.matches(".*[0-9].*")) { // условие проверки на арабские: сравниваем содержимое вх-й строки с рег-м выражением: 0-9
            return TypeOfLanguage.Arabian;  // вернем Arabian, если условие true
        }

        if (expression.matches(".*[I-X].*")) { // условие проверки на римские: сравниваем содержимое вх. строки с рег-м выражением: I-X
            return TypeOfLanguage.Roman; // вернем Roman, если условие true
        }
        return TypeOfLanguage.valueOf(expression);
    }


    public Map<String, Object> separationOperands(String inputString) throws CalculatorImplException { // метод,возвращающий Мар с операндом (+,-,*,/)

        Map<String, Object> operandMap = new HashMap<>(); // создаем объект operandMap

        if (inputString.contains("+")) {      // проверка условия: в вводимой строке содержится знак "+", если - да, то
            operandMap.put("Operator", "+"); // в объект operandMap  добавляем соответствующиую пару ключ-значение
        }

        if (inputString.contains("-")) {      // проверка условия: в вводимой строке содержится знак "-"
            operandMap.put("Operator", "-"); // в объект operandMap  добавляем соответствующиую пару ключ-значение
        }

        if (inputString.contains("*")) {      // проверка условия: в вводимой строке содержится знак "*"
            operandMap.put("Operator", "*"); // в объект operandMap  добавляем соответствующиую пару ключ-значение
        }

        if (inputString.contains("/")) {      // проверка условия: в вводимой строке содержится знак "/"
            operandMap.put("Operator", "/"); // в объект operandMap  добавляем соответствующиую пару ключ-значение
        }

        if (inputString.contains(":")) {
            throw new CalculatorImplException("Не правильно введен символ операции, используйте только +, -, *, /");
        }

        if (operandMap.isEmpty()) {
            throw new CalculatorImplException("Cтрока не является математической операцией");
        }

        if ((checkCountOfInput(inputString))) {
            throw new CalculatorImplException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        return addFirstAndSecondOperand(inputString, operandMap); //возвращаемся в метод addFirstAndSecondOperand
    }


    private Map<String, Object> addFirstAndSecondOperand(String inputString, Map<String, Object> map) throws CalculatorImplException { // метод, возвращающий Мар с операндами
        int variableOperator; // объявление переменных
        String firstOperand; // объявление переменных
        String secondOperand; // объявление переменных
        variableOperator = inputString.indexOf((String) map.get("Operator")); // методом indexOf находим в строке символ (+,-,*,/) и вернем его индекс

        firstOperand = inputString.substring(0, variableOperator); // Методом substring() возвращаем 1-й операнд(firstOperand) (подстроку входной строки) с учетом индекса символа.
        secondOperand = inputString.substring(variableOperator + 1); //Методом substring() возвращаем 2-й операнд(secondOperand) (подстроку входной строки) с учетом индекса символа

        if ((firstOperand.matches(".*[0-9].*") && secondOperand.matches(".*[I-X].*"))) {
            throw new CalculatorImplException("Калькулятор работает только с арабскими или римскими цифрами одновременно. Введите цифры от 1 до 10 или от I до X.");
        }

        if (secondOperand.matches(".*[0-9].*") && firstOperand.matches(".*[I-X].*")) {
            throw new CalculatorImplException("Калькулятор работает только с арабскими или римскими цифрами одновременно. Введите цифры от 1 до 10 или от I до X.");
        }

        map.put("FirstOperand", firstOperand); //методом put добавим в Мар пару ключ-значение
        map.put("SecondOperand", secondOperand); //методом put добавим в Мар пару ключ-значение

        return map;
    }

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>(); // мап для преобразования арабских в римские (для вывода конечного результата)

    static {
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public String transformNumberArabianToRoman(int result) throws CalculatorImplException { //преобразование арабских в римские (для вывода конечного результата)
        if (result <= 0) {
            throw new CalculatorImplException("В римской системе нет отрицательных чисел");
        }

        int l = map.floorKey(result);
        if (result == l) {
            return map.get(result);
        }
        return map.get(l) + transformNumberArabianToRoman(result - l);

    }

    private static int transformNumber(char inputChar) { // метод возвращает арабские цифры (на входе римские)
        switch (inputChar) {
            case 'C':
                return 100;
            case 'L':
                return 50;
            case 'X':
                return 10;
            case 'V':
                return 5;
            case 'I':
                return 1;
            default:
                return 0;
        }
    }

    public static int transformNumberRomanToArabian(String inputString) throws CalculatorImplException { // метод трансформации римских цифр в арабские (с помощью transformNumber())
        int result = 0;
        for (int i = 0; i < inputString.length() - 1; i++) {// зацикливаемся (кроме последнего элемента)
            if (transformNumber(inputString.charAt(i)) < transformNumber(inputString.charAt(i + 1))) { //charAt() возвращает символ, расположенный по указанному индексу строки. Индексы начинаются с нуля.
                result -= transformNumber(inputString.charAt(i));
            } else {
                result += transformNumber(inputString.charAt(i));
            }
        }
        result += transformNumber(inputString.charAt(inputString.length() - 1));
        return result;
    }

    private static boolean checkCountOfInput(String inputString) throws CalculatorImplException {
        int i = 0;
        int j = 0;
        char[] userInput = inputString.toUpperCase().toCharArray(); // создаем массив символов из входной строки
        char[] operator = {'+', '-', '*', '/'}; // создаем массив operator знаков операций - для сравнения
        int[] counts = new int[4]; // массив счета равен количеству символов операторов - 4

        for (i = 0; i < userInput.length; i++) { // цикл для входной строки

            for (j = 0; j < operator.length; j++) { //цикл перебора сравнения операторов

                if (userInput[i] == operator[j]) { // если i-й элемент строки равен j-му оператору
                    counts[j]++; // в переменную counts добавляется соответсвующий оператор
                }
            }
        }

       if (Arrays.stream(counts).sum() > 1) { // сумма всех операторов в строке
            return true;
        }
        return false;
    }
}







