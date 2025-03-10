package 5.functions;

public interface TabulatedFunction extends Function{

    int getPointsCount();

    FunctionPoint getPoint(int index);

    double getPointX(int index);

    double getPointY(int index);

    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    void setPointX(int index, double x) throws InappropriateFunctionPointException;

    void setPointY(int index, double y);

    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    void deletePoint(int index);
}
