import utill.CalculatorImplException;
import java.util.Map;
import static java.lang.String.valueOf;


public class CalculatorImpl implements Calculator { // класс вычисилений реализует интерфейс Calculator

    @Override   //переопределим метод calculated из интерфейса Calculator

    public String calculated(Map<String, Object> map, TypeOfLanguage type) throws CalculatorImplException { //метод вычисления результата(на вход мапа и тип цифры: араб или рим
        String result = null;

        if (type.equals(TypeOfLanguage.Arabian)) {//условие: если строка содержит арабские цифры
            result = String.valueOf(calculatedOfArabian(Integer.parseInt(valueOf(map.get("FirstOperand"))), // переменные берем из мап
                    Integer.parseInt(valueOf(map.get("SecondOperand"))), valueOf(map.get("Operator")).charAt(0))); //в result в виде строки сохраним вычисленное в calculatedOfArabian() значение
        }
        if (type.equals(TypeOfLanguage.Roman)) { //условие: если строка содержит римские цифры
            Utill utill = new Utill();  //создадим объект utill

            int firstNumber = utill.transformNumberRomanToArabian(map.get("FirstOperand").toString()); // преобразуем 1-й операнд используя transformNumberRomanToArabian() и данные мап
            int secondNumber = utill.transformNumberRomanToArabian(map.get("SecondOperand").toString()); // преобразуем 2-й операнд используя transformNumberRomanToArabian() и данные мап

            result = String.valueOf(calculatedOfArabian(firstNumber, secondNumber, valueOf(map.get("Operator")).charAt(0))); // вычислим результат используя calculatedOfArabian()
            result = utill.transformNumberArabianToRoman(Integer.parseInt(result)); // используя transformNumberArabianToRoman() преобразуем результат из арабских цифр в римские

        }

        return String.valueOf(result); //вернем результат в виде строки
    }

    private int calculatedOfArabian(int firstNumber, int secondNumber, Character operator) throws CalculatorImplException { //метод работает с арабскими цифрами
        int result = 0;
        if (firstNumber < 1 || secondNumber > 10 && secondNumber < 1 || firstNumber > 10) {
            throw new CalculatorImplException("Неподходящее значение числа(ел), используйте числа от 1 до 10 включительно");
        }

        switch (operator) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                result = firstNumber / secondNumber;
                break;
            default:
                throw new CalculatorImplException("Не правильно введен символ операции, используйте только +, -, *, /");
        }

        return result;
    }
}








