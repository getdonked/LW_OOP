package 7;

import 7.functions.*;
import 7.functions.basic.*;

import java.util.Iterator;

public class Laba_work_7 {

    public static void main(String[] args) {
        /**
         * Проверка работы итератора
         * */
        double[] arr = {1., 2., 3., 4., 5., 6., 7., 8., 9., 10.};
        System.out.println("#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#");
        System.out.println("TASK 1");
        TabulatedFunction f1 = new ArrayTabulatedFunction(1, 10,arr);
        for(FunctionPoint point : f1) {
            System.out.println(point);
        }
        TabulatedFunction f2 = new LinkedListTabulatedFunction(1, 10,arr);
        for(FunctionPoint point : f2) {
            System.out.println(point);
        }
        System.out.println("#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#");
        System.out.println("TASK 2");
        Function f = new Cos();
        TabulatedFunction tf;
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        System.out.println("#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#");
        System.out.println("TASK 3");
        TabulatedFunction g;
        g = TabulatedFunctions.createTabulatedFunction( 0.0, 10.0, 3, ArrayTabulatedFunction.class);
        System.out.println(g.getClass());
        System.out.println(g);
        g = TabulatedFunctions.createTabulatedFunction(0, 10, new double[] {0, 10},ArrayTabulatedFunction.class);
        System.out.println(g.getClass());
        System.out.println(g);
        FunctionPoint[] points={new FunctionPoint(0, 0), new FunctionPoint(10, 10), new FunctionPoint(11,3)};
        g = TabulatedFunctions.createTabulatedFunction(points, LinkedListTabulatedFunction.class);
        System.out.println(g.getClass());
        System.out.println(g);
        g = TabulatedFunctions.tabulate(new Sin(), 0, Math.PI, 11,LinkedListTabulatedFunction.class);
        System.out.println(g.getClass());
        System.out.println(g);


    }

    
}
