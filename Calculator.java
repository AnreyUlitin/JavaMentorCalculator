import utill.CalculatorImplException;

import java.util.Map;

public interface Calculator {
    String calculated (Map<String,Object> map, TypeOfLanguage type) throws CalculatorImplException;

}

