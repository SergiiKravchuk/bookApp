package com.company;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Scanner;

import static java.lang.Math.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

//    private static double h = 0.2;
//    private static double size = 2/h;
    private static double h = 0.1;
    private static double size = 2/h;

    private static double[] k = new double[4];
    private static int n = (int)floor(size);

    private static double[] err = new double[n];
    private static int count = 0;


    public static void main(String[] args) {
        EilerSimple();
        EilerCorected();
        //EilerModify();
        RK();
        // write your code here
    }
    private static double function(double x, double y){

//        return 2*x + x*y;
        return 0.5 * (x-1) * pow(Math.E, x) - x*y;
    }


    private static void EilerSimple(){
        double yZero = 2;
//        double yZero = 0.1;
        double[] y = new double[n];
        double x = 0;

        int rank = 1;

        for (int i = 0; i < n; i++){
            y[i]=yZero + h * function(x, yZero);
            yZero = y[i];
            errorCalc(yZero, x, rank);

            x += h;

        }
        System.out.println("EilerSimple");
        show(y);


    }


    private static void EilerCorected() {
        double yZero = 2;
//        double yZero = 0.1;
        double[] y = new double[n];
        double x = 0;
        int rank = 2;

        for (int i = 0; i < n; i++) {
            y[i] = yZero + h/2 *( function(x, yZero) + function(x + h, yZero + h * function(x, yZero)));
            yZero = y[i];

            errorCalcCor(yZero, x, rank);

            x += h;
        }
        System.out.println("Eiler corrected");
        show(y);
    }

    private static void EilerReverse(){
        double yZero = 0.1;
        double[] y = new double[n];
        double x = 0;
        double c;
        int rank = 1;

        for (int i = 0; i < n; i++) {
            //y[i] = (yZero - 0,9*x*h + h)/(1-cos()); ????according to ur function
            yZero = y[i];

            x += h;
        }
    }

    private static void EilerModify(){
        double yZero = 2;
//        double yZero = 0.1;
        double[] y = new double[n];
        double x = 0;
        int rank = 2;

        for (int i = 0; i < n; i++) {
            y[i] = yZero + h*function(x + h/2, yZero + (h/2) * function(x, yZero));
            yZero = y[i];

            errorCalcMod(yZero, x, rank);

            x += h;
        }
        System.out.println("Eiler mod");
        show(y);

    }


    private static void errorCalc(double y, double x, int rank){

        double y1, y2, yTmp;
        y1= y + h * function(x, y);
        yTmp = y + (h/2) * function(x, y);
        //y2= yTmp + h * function(x + h, yTmp);
        y2= yTmp + h/2 * function(x + h/2, yTmp);
//        System.out.println("Test " + "y h " + y1 + " y h/2 " + y2);

        //double c = (y2 - y1)/ (pow(h/2, 2) - pow(h, 2));
        double c = (y2 - y1)/ (pow(h, 2) - pow(h/2, 2));

        err[count] = c*pow(h,rank+1);
//        System.out.println("count 1" + count);
        count ++;
//        System.out.println("coynt 2" + count);

        if(count == n ){
            count = 0;
            error();

        }

    }
    private static void errorCalcMod(double y, double x, int rank){

        double y1, y2, yTmp;
        y1= y + h * function(x, y);
        yTmp = y + (h/2) * function(x, y);
        y2= yTmp + h/2 * function(x + h/2, yTmp);

        y1 = y + h*function(x + h/2, y + (h/2) * function(x, y));
        double h1 = h/2;
        yTmp = y + h1*function(x + h1/2, y + (h1/2) * function(x, y));
        y2 = yTmp + h1*function(x + h1/2 + h1, yTmp + (h1/2) * function(x + h1, yTmp));


        double c = (y2 - y1)/ (pow(h, 2) - pow(h/2, 2));

        err[count] = c*pow(h,rank+1);
        count ++;

        if(count == n ){
            count = 0;
            error();

        }

    }

    private static void errorCalcCor(double y, double x, int rank){

        double y1, y2, yTmp;

        y1 = y + h/2 *( function(x, y) + function(x + h, y + h * function(x, y)));
        double h1 = h/2;
        yTmp = y + h1/2 *( function(x, y) + function(x + h1, y + h1 * function(x, y)));
        y2 = yTmp + h1/2 *( function(x + h1, yTmp) + function(x + 2*h1, yTmp + h1 * function(x + h1, yTmp)));
//        System.out.println("Test " + "y h " + y1 + " y h/2 " + y2);

        double c = (y2 - y1)/ (pow(h, 2) - pow(h/2, 2));

        err[count] = c*pow(h,rank+1);
//        System.out.println("count 1" + count);
        count ++;
//        System.out.println("coynt 2" + count);

        if(count == n ){
            count = 0;
            error();

        }

    }

    private static void error(){
        double error = 0;
        for(int i = 0; i < n; i ++){

            error += err[i];
            err[i] = 0;
        }

        System.out.println("Total error " + error);
    }

    private static void show(double[] y){
        for (int i = 0; i < n; i++){
            System.out.println("y[" + i + "] = " + y[i]);
        }
    }
//    private static double coefficientC(double x, double y){
//        double M0, M1, M2;
//        double c;
//        M0 = abs(function(x, y));
//        M1 = abs(derivative(x, 0, h, 0));
//        M2 = abs(derivative(0, y, 0, h));
//        c = (M1 + M0 * M2)/2;
//
//        return c;
//
//    }
//    private static double derivative(double x, double y, double hx, double hy){
//        double dev = (function(x + hx, y + hy) - 2 * function(x,y) + function(x - hx, y - hy));
//        return dev;
//    }

    private static void RK(){
//        double yZero = 0.1;/
        double yZero = 2;
        double[] y = new double[n];
        double x = 0;

        int rank = 4;

        for (int i = 0; i < n; i++){
            y[i]=yZero + h * (k[0] + 2 * k[1] + 2 * k[2] + k[3])/6;
            yZero = y[i];


            coefficientK(x, yZero);
            errorCalc(yZero, x, rank);

            x += h;
        }
        System.out.println("RK method");
        show(y);
    }

    private static void coefficientK(double x, double y){
        //double[] k = new double[4];

        k[0] = function(x, y);
        k[1] = function(x + h/2, y + h*k[0]/2);
        k[2] = function(x + h/2, y + h*k[1]/2);
        k[3] = function(x + h, y + h*k[2]);

    }



}
