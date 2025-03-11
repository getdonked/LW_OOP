package 7.functions;

/**
 * В пакете functions опишите базовый интерфейс фабрик табулированных функций TabulatedFunctionFactory
 * */
public interface TabulatedFunctionFactory {
    /**
     * Интерфейс должен объявлять три перегруженных метода TabulatedFunction createTabulatedFunction()
     * Параметры функций, должны соответствовать параметрам конструкторов классов табулированных функций.
     * */
    TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount);
    TabulatedFunction createTabulatedFunction(double leftX, double rightX, double values[]);
    TabulatedFunction createTabulatedFunction(FunctionPoint[] points);
}
