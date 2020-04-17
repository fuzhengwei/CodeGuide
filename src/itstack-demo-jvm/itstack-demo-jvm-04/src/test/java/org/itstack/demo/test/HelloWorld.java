package org.itstack.demo.test;

public class HelloWorld {
    
    public static void main(String[] args) {
        System.out.println(circumference(1.6f));
    }

    public static float circumference(float r){
        float pi = 3.14f;
        float area = 2 * pi * r;
        return area;
    }

}
