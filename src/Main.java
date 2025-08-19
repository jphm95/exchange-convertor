import com.alluracursos.exchangeconversor.api.WebServices;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    // Opciones del menú
    private enum Pair {
        MXN_TO_EUR("MXN", "EUR"),
        EUR_TO_MXN("EUR", "MXN"),
        USD_TO_EUR("USD", "EUR"),
        EUR_TO_USD("EUR", "USD"),
        MKD_TO_EUR("MKD", "EUR"),
        EUR_TO_MKD("EUR", "MKD");

        final String from;
        final String to;

        Pair(String from, String to) {
            this.from = from;
            this.to = to;
        }
    }

    public static void main(String[] args) {
        WebServices ws = new WebServices();
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Elige una opción: ");
            String opt = sc.nextLine().trim();

            if (opt.equals("0")) {
                System.out.println("¡Hasta luego!");
                break;
            }

            Pair pair = switch (opt) {
                case "1" -> Pair.MXN_TO_EUR;
                case "2" -> Pair.EUR_TO_MXN;
                case "3" -> Pair.USD_TO_EUR;
                case "4" -> Pair.EUR_TO_USD;
                case "5" -> Pair.MKD_TO_EUR;
                case "6" -> Pair.EUR_TO_MKD;
                default -> null;
            };

            if (pair == null) {
                System.out.println("Opción inválida.\n");
                continue;
            }

            BigDecimal amount = readAmount(sc, pair.from);
            if (amount == null) continue;

            try {
                BigDecimal result = ws.convert(amount, pair.from, pair.to);
                System.out.printf("%s %s = %s %s%n%n",
                        amount.setScale(2), pair.from, result.setScale(2), pair.to);
            } catch (Exception e) {
                System.out.println("Error al convertir: " + e.getMessage() + "\n");
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("===== Conversor de Monedas =====");
        System.out.println("1) MXN -> EUR");
        System.out.println("2) EUR -> MXN");
        System.out.println("3) USD -> EUR");
        System.out.println("4) EUR -> USD");
        System.out.println("5) MKD (dinar macedonio) -> EUR");
        System.out.println("6) EUR -> MKD (dinar macedonio)");
        System.out.println("0) Salir");
    }

    private static BigDecimal readAmount(Scanner sc, String currency) {
        System.out.print("Monto en " + currency + ": ");
        String line = sc.nextLine().trim().replace(",", ".");
        try {
            BigDecimal amt = new BigDecimal(line);
            if (amt.signum() < 0) {
                System.out.println("El monto debe ser positivo.\n");
                return null;
            }
            return amt;
        } catch (NumberFormatException ex) {
            System.out.println("Monto inválido.\n");
            return null;
        }
    }
}