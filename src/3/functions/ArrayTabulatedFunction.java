package 3.functions;

/**
 * Класс ArrayTabulatedFunction.
 * Объект, данного класса описывает табулированную функцию
 * */
public class ArrayTabulatedFunction {

    /**
     * Опишем атрибуты класса ArrayTabulatedFunction
     * FunctionPoint[] points - описывает массив точек FunctionPoint
     * pointsCount - описывает количество точек в массиве
     * expandSize - шаг приращения массива точек
     * */
    private FunctionPoint[] points;
    private int pointsCount;
    private static final int expandSize=100;

    /**
     * Опишем конструкторы для работы с классом ArrayTabulatedFunction
     * */

    /**
     * Конструктор ArrayTabulatedFunction(double leftX, double rightX, int pointsCount)
     * Создаёт объект табулированной функции по заданным левой и правой границе области определения.
     * А также количеству точек для табулирования (значения функции в точках при этом следует считать равными 0)
     * */
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount){
        // определим количество точек функции
        this.pointsCount=pointsCount;
        // проверим, что количество точек функции более 1
        if(pointsCount<2){
            throw new IllegalArgumentException("Количество точек функции ("+pointsCount+") должно быть более 1!");
        }
        // проверим корректность задания области определения
        if(Double.compare(leftX, rightX) >= 0){
            throw new IllegalArgumentException("Правая граница области определения ("+rightX+") должна быть больше, чем левая("+leftX+")");
        }
        // определим шаг приращения по оси X
        double step=(rightX-leftX)/(pointsCount-1);
        // создадим массив точек FunctionPoint
        points=new FunctionPoint[pointsCount+expandSize];
        // заполним его на длину pointsCount
        for(int i=0;i<pointsCount-1;i++){
            points[i]=new FunctionPoint(leftX,0);
            leftX+=step;
        }
        // заполним последнее значение
        points[pointsCount-1]=new FunctionPoint(rightX,0);
    }

    /**
     * Конструктор ArrayTabulatedFunction(double leftX, double rightX, double[] values)
     * Данный конструктор аналогичен предыдущему конструктору.
     * Но вместо количества точек получает значения функции в виде массива.
     * */
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        // определим количество точек в массиве
        this.pointsCount=values.length;
        // проверим, что количество точек функции более 1
        if(pointsCount<2){
            throw new IllegalArgumentException("Количество точек функции ("+pointsCount+") должно быть более 1!");
        }
        // проверим корректность задания области определения
        if(Double.compare(leftX, rightX) >= 0){
            throw new IllegalArgumentException("Правая граница области определения ("+rightX+") должна быть больше, чем левая("+leftX+")");
        }
        // определим шаг приращения по оси X
        double step=(rightX-leftX)/(pointsCount-1);
        // создадим массив точек FunctionPoint
        points=new FunctionPoint[pointsCount+100];
        // заполним его на длину pointsCount
        for(int i=0;i<pointsCount-1;i++){
            points[i]=new FunctionPoint(leftX,values[i]);
            leftX+=step;
        }
        // заполним последнее значение
        points[pointsCount-1]=new FunctionPoint(rightX,values[pointsCount-1]);
    }

    /**
     * Метод getPointsCount() возвращает количество точек.
     * */
    public int getPointsCount()
    {
        return pointsCount;
    }

    /**
     * Метод getLeftDomainBorder() возвращает значение левой границы области определения табулированной функции.
     * */
    public double getLeftDomainBorder()
    {
        return points[0].getX();
    }

    /**
     * Метод getRightDomainBorder() возвращает значение правой границы области определения табулированной функции.
     * */
    public double getRightDomainBorder()
    {
        return points[pointsCount-1].getX();
    }

    /**
     * Метод getFunctionValue(double x) возвращает значение функции в точке x, если эта точка лежит в области определения функции.
     * В противном случае метод возвратит значение неопределённости (NaN из класса Double)
     * */
    public double getFunctionValue(double value) {
        // Проверяем, что точка лежит внутри области определения
        if(Double.compare(value, getLeftDomainBorder()) >= 0 || Double.compare(value, getRightDomainBorder())<=0){
            // В цикле ищем необходимый индекс
            for(int i=0;i<pointsCount-1;i++){
                // проверяем, что точка лежит между соседними точками
                if(Double.compare(value, points[i].getX()) >= 0 && Double.compare(value, points[i+1].getX())<=0){
                    // если все получилось считаем значение
                    return FunctionPoint.getValueOnLine(points[i],points[i+1],value);
                }
            }
        }
        // если что-то пошло не так возвращаем NaN
        return Double.NaN;
    }

    /**
     * Метод FunctionPoint getPoint(int index) возвращает ссылку на объект, описывающий точку, имеющую указанный номер.
     * */
    public FunctionPoint getPoint(int index){
        // проверяем что индекс указан верно
        if(index<0 || index>=pointsCount){
            throw  new FunctionPointIndexOutOfBoundsException("Такого элемента не существует!",index);
        }
        return points[index];
    }

    /**
     * Метод getPointX(int index) возвращает значение абсциссы точки с указанным номером
     * */
    public double getPointX(int index) {
        // проверяем что индекс указан верно
        if(index<0 || index>=pointsCount){
            throw  new FunctionPointIndexOutOfBoundsException("Такого элемента не существует!",index);
        }
        return points[index].getX();
    }

    /**
     * Метод getPointY(int index) возвращает значение ординаты точки с указанным номером.
     * */
    public double getPointY(int index){
        // проверяем что индекс указан верно
        if(index<0 || index>=pointsCount){
            throw  new FunctionPointIndexOutOfBoundsException("Такого элемента не существует!",index);
        }
        return points[index].getY();
    }

    /**
     * Метод void setPoint(int index, FunctionPoint point) заменяет указанную точку табулированной функции на заданную.
     * Для функции, определяемой точками {(0; 0), (1; 1), (2; 4)}, точку с индексом 1 нельзя заменить точкой (-1; 5).
     * Однако точку с индексом 0 уже можно заменить.
     * */
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        // проверяем что индекс лежит внутри области определения
        if(index<0 || index>=pointsCount){
            throw  new FunctionPointIndexOutOfBoundsException("Такого элемента не существует!",index);
        }
        // проверяем что индекс лежит не на левой границе области определения
        if(index!=0){
            if(Double.compare(point.getX(), points[index-1].getX()) <= 0){
                throw new InappropriateFunctionPointException("Выход за границу области определения слева ("+points[index-1].getX()+")",point.getX());
            }
        }
        // проверяем что индекс лежит не на правой границе области определения
        if(index!=pointsCount){
            if(Double.compare(point.getX(), points[index+1].getX()) >= 0){
                throw new InappropriateFunctionPointException("Выход за границу области определения справа("+points[index+1].getX()+")",point.getX());
            }
        }
        points[index]=point;
    }

    /**
     * Метод setPointX(int index, double x) изменяет значение абсциссы точки с указанным номером
     */
    public void setPointX(int index, double value) throws InappropriateFunctionPointException {
        // проверяем что индекс лежит внутри области определения
        if(index<0 || index>=pointsCount){
            throw  new FunctionPointIndexOutOfBoundsException("Такого элемента не существует!",index);
        }
        // проверяем что индекс лежит не на левой границе области определения
        if(index!=0){
            if(Double.compare(value, points[index-1].getX()) <= 0){
                throw new InappropriateFunctionPointException("Выход за границу области определения слева ("+points[index-1].getX()+")",value);
            }
        }
        // проверяем что индекс лежит не на правой границе области определения
        if(index!=pointsCount){
            if(Double.compare(value, points[index+1].getX())>=0){
                throw new InappropriateFunctionPointException("Выход за границу области определения справа ("+points[index-1].getX()+")",value);
            }
        }
        points[index].setX(value);
    }

    /**
     * Метод setPointY(int index, double y) изменяет значение ординаты точки с указанным номером
     * */
    public void setPointY(int index, double value){
        // проверяем что индекс указан верно
        if(index<0 || index>=pointsCount){
            throw  new FunctionPointIndexOutOfBoundsException("Такого элемента не существует!",index);
        }
        points[index].setY(value);
    }

    /**
     * Метод deletePoint(int index) удаляет заданную точку табулированной функции.
     * */
    public void deletePoint(int index){
        // проверяем что в массиве достаточно точке для выполнения операции удаления
        if(pointsCount<3){
            throw new IllegalStateException("Табличная функция должна иметь не менее 2 точек!");
        }
        // проверяем что индекс указано
        if(index<0 || index>=pointsCount){
            throw  new FunctionPointIndexOutOfBoundsException("Такого элемента не существует!",index);
        }
        System.arraycopy(points,index+1,points,index,pointsCount-index+1);
        pointsCount--;
    }

    /**
     * Метод addPoint(FunctionPoint point) добавляет новую точку табулированной функции.
     * */
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        // проверяем лежит ли добавляемая точка левее левой границ области определения
        if(Double.compare(point.getX(), getLeftDomainBorder()) == -1)
        {
            // копируем весь массив на один элемент вправо
            System.arraycopy(points, 0, points, 1, pointsCount++);
            // нулевой элемент новая точка
            points[0]=point;
            // после добавления выходим
            return;
        }
        // проверяем лежит ли добавляемая точка правее правой границы области определения
        if(Double.compare(point.getX(), getRightDomainBorder()) == 1) {

            points[pointsCount++] = point;
            return;
        }
        for(int i=1;i<pointsCount;i++){
            if(Double.compare(point.getX(), points[i].getX()) == 0)
            {
                throw new InappropriateFunctionPointException("Добавляемая точка уже существует ",point.getX());
            }
            if(Double.compare(point.getX(), points[i].getX()) == -1){
                System.arraycopy(points,i,points,i+1,(pointsCount++)-i);
                points[i]=point;
                return;
            }
        }
    }

    public void printFunction(ArrayTabulatedFunction func){
        System.out.println("(  X ; Y  )");
        System.out.println("---------------------------------------------");
        for (int i = 0; i < func.getPointsCount(); i++) {
            System.out.println(func.getPoint(i).toString());
        }
        System.out.println("---------------------------------------------");
    }
}
