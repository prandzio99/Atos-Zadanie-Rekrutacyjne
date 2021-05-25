/**
 * Autor: Paweł Randzio
 * Zadanie rekrutacyjne do Atos Polska
 * Prosty kalkulator do konwersji walut pobierający dane z pliku XML
 */

import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {
    
    /**
     * Funkcja pomocnicza wybierająca zawartość pliku XML
     * i zwracająca go w postaci HashMap<String, Double>
     * w parach (waluta, kurs)
     */
    public static HashMap<String, Double> getCurrencies(String fileName) {
        
        // inicjalizacja HashMap do zwrócenia wyniku funkcji
        HashMap<String, Double> result = new HashMap<>();

        // inicjalizacja DBF do parsowania pliku XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // blok w którym może wystąpić błąd, np. niepoprawne wczytanie pliku
        try {
            
            // zabezpieczenie odczytywania pliku XML
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // inicjalizacja DB do parsowania pliku XML
            DocumentBuilder db = dbf.newDocumentBuilder();

            // parsowanie wskazanego pliku XML do przechowania w zmiennej 'doc'
            Document doc = db.parse(new File(fileName));

            // normalizacja
            doc.getDocumentElement().normalize();

            // utworzenie listy elementów o tagu "Cube"
            NodeList list = doc.getElementsByTagName("Cube");

            // iteracja od 3. elementu listy, ponieważ dwa pierwsze
            // nie zawierają atrybutów 'currency' oraz 'rate'
            for (int i = 2; i < list.getLength(); i++) {
                
                // pobranie i-tego node'a z listy
                Node node = list.item(i);
                
                // jeśli node jest typu 'ELEMENT_NODE'
                if (node.getNodeType() == Node.ELEMENT_NODE) {
        
                    // konwersja node do elementu
                    Element element = (Element) node;

                    // pobranie atrybutu 'currency'
                    String currency = element.getAttribute("currency");
            
                    // pobranie atrybuty 'rate'
                    Double conversionRate = Double.parseDouble(element.getAttribute("rate"));

                    // zapisanie pary atrybutów w HashMap
                    result.put(currency, conversionRate);
                }
            }
        
        // w razie błędu odczytu pliku XML
        } catch (Exception e) {
            
            // pokaż co poszło nie tak
            e.printStackTrace();
        }
        
        // zwróć HashMap z zawartością pliku XML
        return result;
    }

    /**
     * Funkcja pomocnicza, zastosowanie wyłącznie stylistyczne,
     * zaokrągla liczby zmiennoprzecinkowe do wskazanej ilości
     * miejsc po przecinku
     */
    public static Double round(Double value, int places) {
        
        // jeśli podana zostanie błędna liczba miejsc po przecinku rzuć wyjątek
        if (places < 0) throw new IllegalArgumentException();

        // utwórz zmienną typu BigDecimal do formatowania
        BigDecimal bd = new BigDecimal(value.toString());
        
        // formatuj
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        
        // zwróć sformatowaną liczbę
        return bd.doubleValue();
    }

    public static void main(String[] args) {
        
        // instancja klasy konwertera walut, inicjalizowana z HashMapą walut i kursów
        Converter currencyConverter = new Converter(getCurrencies("eurofxref-daily.xml"));
        
        // scanner do pobierania danych od użytkownika
        Scanner scanner = new Scanner(System.in);

        // zmienna do przechowywania wskazanej waluty
        String chosenCurrency;
        
        // zmienna do przechowywania wskazanej kwoty
        Double givenAmount;

        System.out.println("Currency Converter\n");
        
        System.out.println("Choose Currency you would like to convert TO: ");
        
        // pokaż dostępne waluty
        currencyConverter.showCurrencies();
        
        // wskazanie waluty przez użytkownika
        chosenCurrency = scanner.nextLine();
        
        // jeśli wskazana waluta jest dostępna
        if (currencyConverter.currencyList.containsKey(chosenCurrency)) {
        
            System.out.println("\nType in the amount you would like to convert (in EUR):");
        
            // wskazanie kwoty przez użytkownika
            givenAmount = scanner.nextDouble();
        
            System.out.println("\nThe given amount converts to: ");
        
            // wyświetlenie kwoty po przeliczeniu na wskazaną walutę
            System.out.print(round(currencyConverter.getConversion(givenAmount, chosenCurrency), 2));
        
            System.out.print(" "+chosenCurrency+"\n\n");
        } else {
            
            // w przypadku wskazania niedostępnej waluty program kończy działanie
            // informując użytkownika o niepoprawnym wskazaniu
            System.out.println("\nSorry, the currency you chose is unavailable in this converter.\nTry again.");
        }
    }
}