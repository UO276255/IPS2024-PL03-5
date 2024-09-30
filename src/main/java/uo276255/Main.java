package uo276255;

import uo276255.util.Database;

public class Main {

    public static void main(String[] args) {
        System.out.println("Inicializando la base de datos en memoria...");
        new Database();
       
        System.out.println("Base de datos inicializada con éxito.");
            }
}

