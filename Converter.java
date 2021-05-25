/**
 * Autor: Paweł Randzio
 * Zadanie rekrutacyjne do Atos Polska
 * Klasa kalkulatora-konwertera
 */

import java.util.*;


public class Converter {

    // HashMap walut i kursów konwertera
    HashMap<String, Double> currencyList;
  
    // pusty konstruktor
    public Converter() {}
  
    // konstruktor z gotową HashMapą
    public Converter(HashMap<String, Double> currencyList) {
        this.currencyList = currencyList;
    }

    // metoda nadająca konwerterowi HashMapę walut i kursów
    public void setCurrencyList(HashMap<String, Double> currencyList) {
        this.currencyList = currencyList;
    }
  
    // metoda zwracająca przeliczenie wskazanej kwoty na wskazaną walutę
    public Double getConversion(Double amount, String currency) {
        return amount * this.currencyList.get(currency);
    }
  
    // metoda drukująca zestaw dostępnych walut
    public void showCurrencies() {
        int formatter = 0;
        for (String currency : this.currencyList.keySet()) {
            if (formatter == 5) {
                System.out.println();
                formatter = 0;
            }
            System.out.print(currency);
            System.out.print(" | ");
            formatter++;
        }
        System.out.println("\n");
    }
}
