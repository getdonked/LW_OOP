package 7.functions;

import java.io.*;


/**
 * Класс TabulatedFunctions
 * Данный класс, содержит вспомогательные статические методы для работы с табулированными функциями
 * */


public class TabulatedFunctions {

    private TabulatedFunctions() {
    }

    /**
     * В классе TabulatedFunctions объявите приватное статическое поле типа TabulatedFunctionFactory и проинициализируйте его объектом одного из описанных классов фабрик.
     * */
    private static TabulatedFunctionFactory tabulatedFunctionFactory=new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    /**
     * Объявите метод setTabulatedFunctionFactory(), позволяющий заменить объект фабрики.
     * */
    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory tabulatedFunctionFactory) {
        TabulatedFunctions.tabulatedFunctionFactory = tabulatedFunctionFactory;
    }

    /**
     * В классе TabulatedFunctions опишите три перегруженных метода TabulatedFunction createTabulatedFunction()
     * Классы возвращают объекты табулированных функций, созданные с помощью текущей фабрики.
     * Параметры методов должны соответствовать параметрам методов фабрики
     * */
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount){
        return tabulatedFunctionFactory.createTabulatedFunction(leftX,rightX,pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double values[]){
        return tabulatedFunctionFactory.createTabulatedFunction(leftX,rightX,values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points){
        return tabulatedFunctionFactory.createTabulatedFunction(points);
    }


    /**
     * В классе TabulatedFunctions добавьте ещё три перегруженных версии метода createTabulatedFunction().
     * Параметры метода должны повторять параметры трёх аналогичных методов, основанных на использовании фабрики.
     * Также эти методы должны получать ссылку типа Class на описание класса, объект которого требуется создать.
     * Эти методы должны быть доступны для передачи только ссылки на классы, реализующие интерфейс TabulatedFunction.
     * */
    public static <Type extends TabulatedFunction> TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount, Class<Type> type){
        /**
         * Если в ходе выполнения рефлексивных операций возникло исключение (не найден конструктор и т.д.), оно должно быть отловлено.
         * Вместо него должно быть выброшено исключение IllegalArgumentException, причём в его конструктор должно быть передано отловленное исключение из рефлексии.
         * Это позволит в случае возникновения ошибок определить реальную причину ошибки.
         * */
        try{
            return type.getConstructor(double.class, double.class, int.class).newInstance(leftX, rightX, pointsCount);
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public static <Type extends TabulatedFunction> TabulatedFunction createTabulatedFunction(double leftX, double rightX, double values[], Class<Type> type){
        try{
            return type.getConstructor(double.class, double.class, double[].class).newInstance(leftX, rightX, values);
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public static <Type extends TabulatedFunction> TabulatedFunction createTabulatedFunction(FunctionPoint[] points, Class<Type> type) {
        try {
            return type.getConstructor(FunctionPoint[].class).newInstance((Object)points);
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * В классе TabulatedFunctions перегрузите методы, создающие объекты табулированных функций, добавив версии, принимающие также ссылку типа Class на описание класса, объект которого требуется создать.
     * Сделайте так, чтобы в эти методы можно было передать только ссылки на классы, реализующие интерфейс TabulatedFunction.
     * */
    public static <Type extends TabulatedFunction> TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount, Class<Type> type) {
        if (Double.compare(leftX, function.getLeftDomainBorder()) == -1) {
            throw new IllegalArgumentException("Левая граница(" + leftX + ") выходит за границу области определения функции(" + function.getLeftDomainBorder() + ")");
        }
        if (Double.compare(rightX, function.getRightDomainBorder()) == 1) {
            throw new IllegalArgumentException("Правая граница(" + rightX + ") выходит за границу области определения функции(" + function.getRightDomainBorder() + ")");
        }
        double[] values = new double[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        double currentX=leftX;
        for (int i = 0; i < pointsCount; i++) {
            values[i] = function.getFunctionValue(currentX);
            currentX += step;
        }
        values[pointsCount - 1] = function.getFunctionValue(rightX);
        return createTabulatedFunction(leftX, rightX, values, type);
    }

    /**
     * Метод public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount)
     * Получает функцию и возвращает её табулированный аналог на заданном отрезке с заданным количеством точек
     * */
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        // Проверка границ области определения
        if (Double.compare(leftX, function.getLeftDomainBorder()) == -1) {
            throw new IllegalArgumentException("Левая граница(" + leftX + ") выходит за границу области определения функции(" + function.getLeftDomainBorder() + ")");
        }
        if (Double.compare(rightX, function.getRightDomainBorder()) == 1) {
            throw new IllegalArgumentException("Правая граница(" + rightX + ") выходит за границу области определения функции(" + function.getRightDomainBorder() + ")");
        }
        // Определили массив точек
        double[] values = new double[pointsCount];
        // Определили шаг приращения функции
        double step = (rightX - leftX) / (pointsCount - 1);
        double currentX=leftX;
        // Заполнили массив функции
        for (int i = 0; i < pointsCount; i++) {
            values[i] = function.getFunctionValue(currentX);
            currentX += step;
        }
        values[pointsCount - 1] = function.getFunctionValue(rightX);
        // вернули функцию
        return createTabulatedFunction(leftX, rightX, values);
    }

    /**
     * outputTabulatedFunction(TabulatedFunction function, OutputStream out)
     * Метод вывода табулированной функции в байтовый поток
     * Метод в указанный поток выводит значения, по которым потом можно будет восстановить табулированную функцию,
     * а именно количество точек в ней и значения координат точек.
     * */
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        // Создали байтовый поток для записи данных
        DataOutputStream dataOut = new DataOutputStream(out);
        // Записали количество точек функции
        dataOut.writeInt(function.getPointsCount());
        // Записали левую границу области определения функции
        dataOut.writeDouble(function.getLeftDomainBorder());
        // Записали правую границу области определения функции
        dataOut.writeDouble(function.getRightDomainBorder());
        // Записали значения функции
        for (int i = 0; i < function.getPointsCount(); i++) {
            dataOut.writeDouble(function.getPointY(i));
        }
    }

    /**
     * inputTabulatedFunction(InputStream in)
     * Метод ввода табулированной функции из байтового потока
     * Метод считывает из указанного потока данные о табулированной функции, создает и настраивает её объект, а так же возвращает его из метода
     * */
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        // Создали байтовый поток для чтения данных
        DataInputStream dataIn=new DataInputStream(in);
        // Считали количество точек функции
        int pointsCount=dataIn.readInt();
        // Считали количество левую границу области определения
        double leftX=dataIn.readDouble();
        // считали правую границу области определения
        double rightX=dataIn.readDouble();
        // Создали массив для чтения точек функции
        double[] values=new double[pointsCount];
        // считали точки функции
        for(int i=0;i<pointsCount;i++){
            values[i]=dataIn.readDouble();
        }
        // вернули функцию
        return new ArrayTabulatedFunction(leftX,rightX,values);
    }

    /**
     * writeTabulatedFunction(TabulatedFunction function, Writer out)
     * Метод записи табулированной функции в символьный поток
     * Метод в указанный поток выводит значения, по которым потом можно будет восстановить табулированную функцию:
     * а именно количество точек в ней и значения координат точек.
     * */
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out){
        // Создади символьный поток вывода
        PrintWriter printOut=new PrintWriter(out);
        // Записали количество точек функции
        printOut.print(function.getPointsCount()+" ");
        // записали левую границу
        printOut.print(function.getLeftDomainBorder()+" ");
        // записали правую границу
        printOut.print(function.getRightDomainBorder());
        // записали точки функции
        for(int i=0;i<function.getPointsCount();i++){
            printOut.print(" "+function.getPointY(i));
        }
    }

    /**
     * readTabulatedFunction(Reader in)
     * Метод чтения табулированной функции из символьного потока
     * Метод считывает из указанного потока данные о табулированной функции, создает и настраивает её объект, а так же возвращает его из метода.
     * */
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException{
        // создадим поток токенов
        StreamTokenizer tokenizer=new StreamTokenizer(in);
        // Анализируем очередной токен из потока токенов
        tokenizer.nextToken();
        // Пытаемся преобразовать токен в число, для получения количества точек функции
        int pointsCount=(int)tokenizer.nval;
        // Анализируем очередной токен из потока токенов
        tokenizer.nextToken();
        // Пытаемся преобразовать токен в число, для получения левой границы области определения
        double leftX=tokenizer.nval;
        // Анализируем очередной токен из потока токенов
        tokenizer.nextToken();
        // Пытаемся преобразовать токен в число, для получения правой границы области определения
        double rightX=tokenizer.nval;
        // создаем массив точек функции
        double[] values=new double[pointsCount];
        // получаем точки функции
        for(int i=0;i<pointsCount;i++){
            // Анализируем очередной токен из потока токенов
            tokenizer.nextToken();
            // Пытаемся преобразовать токен в число, для получения значений функции
            values[i]=tokenizer.nval;
        }
        // Возвращаем функцию
        return new ArrayTabulatedFunction(leftX,rightX,values);
    }
}
