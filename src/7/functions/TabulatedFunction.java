package 7.functions;

import java.util.Iterator;

public interface TabulatedFunction extends Function, Iterable<FunctionPoint> {

    Iterator<FunctionPoint> iterator();

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
