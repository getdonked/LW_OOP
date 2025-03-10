package 4.functions.basic;


/**
 * Класс Exp
 * Объекты, данного класса вычисляют значение экспоненты
 * Данный класс, расширяет ранее описанный общий класс ContFunctions, который реализует интерфейс Function
 * */
public class Exp extends ContFunctions{

    /**
     * Переопределим метод получения значения функции
     * */
    public double getFunctionValue(double x)
    {
        return Math.exp(x);
    }

}
