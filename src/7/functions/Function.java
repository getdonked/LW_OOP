package lab_work_7.functions;

import java.io.Serializable;

public interface Function extends Serializable {
    double getLeftDomainBorder();
    double getRightDomainBorder();
    double getFunctionValue(double x);
}
