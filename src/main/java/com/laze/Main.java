package com.laze;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var publisher = new Publisher();

        publisher.startFlux()
                .subscribe(System.out::println);
        publisher.startMono()
                .subscribe(System.out::println);
        publisher.startMono2()
                .subscribe(System.out::println);
    }
}