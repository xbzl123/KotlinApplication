package com.example.kotlinapplication;

class Demo1 {
    public static void main(String[] args) {
        // Java
        String input = "##place##holder##";
        String result = input.replaceFirst("##", "").replaceAll("##$", "");
        System.out.println(result);
    }
}
