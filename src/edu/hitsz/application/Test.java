package edu.hitsz.application;

public class Test {
    public double func(double x) {
        return -Math.exp(-x / 1000000) + 2;
    }

    public static void main(String[] args) {
        Test t = new Test();
        for (int i = 0; i < 10000000; i = i + 600) {
            System.out.println(t.func((double) i));
        }
    }
}
