import utill.CalculatorImplException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); //считываем входную строку
        String userInput = new String();

        try {
            userInput = bufferedReader.readLine().toUpperCase().replaceAll("\\s+",""); //выравниваем символы по высоте и удаляем пробелы
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Input: ");
        System.out.println(userInput);
        Utill utill = new Utill();
        TypeOfLanguage typeOfLanguage = utill.definitionRomanArabic(userInput); //сохраним в переменной typeOfLanguage результат метода проверки вводимых символов

        Map<String, Object> map = utill.separationOperands(userInput);
        map.put("TypeOfLanguage", typeOfLanguage.name());

        Calculator calculator = new CalculatorImpl();
        System.out.println("Output: ");
        System.out.println(calculator.calculated(map, typeOfLanguage));
    }
}
