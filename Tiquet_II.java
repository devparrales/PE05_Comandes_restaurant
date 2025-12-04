import java.util.Scanner;

public class Tiquet_II {

    private static String comandaItems = "";
    private static double totalSenseIVA = -1; 
    private static String nomClient = "";

    private static final Scanner input = new Scanner(System.in);

    private static double demanarDouble(String missatge) {
        while (true) {
            try {
                System.out.print(missatge);
                String valor = input.nextLine().replace(",", ".");
                double num = Double.parseDouble(valor);
                if (num <= 0) {
                    System.out.println("El valor ha de ser positiu.");
                    continue;
                }
                return num;
            } catch (Exception e) {
                System.out.println("Introdueix un número vàlid.");
            }
        }
    }

    private static int demanarInt(String missatge) {
        while (true) {
            try {
                System.out.print(missatge);
                int num = Integer.parseInt(input.nextLine());
                if (num <= 0) {
                    System.out.println("El valor ha de ser positiu.");
                    continue;
                }
                return num;
            } catch (Exception e) {
                System.out.println("Introdueix un número enter vàlid.");
            }
        }
    }

    private static String formatDouble(double num) {
        return String.format("%.2f €", num);
    }

    private static Producte afegirProducte() {
        System.out.print("> Introdueix el producte: ");
        String nom = input.nextLine();

        double preuUnitari = demanarDouble("> Preu unitari (€): ");
        int quantitat = demanarInt("> Quantitat: ");
        double subtotal = preuUnitari * quantitat;

        String linia = String.format("%-15s%-12d%-13s%s\n",
                nom,
                quantitat,
                formatDouble(preuUnitari),
                formatDouble(subtotal)
        );

        return new Producte(linia, subtotal);
    }

    private static void crearNovaComanda() {
        System.out.println("______________________________________");
        System.out.println("============  NOVA COMANDA ============");
        System.out.println("______________________________________");

        System.out.print("> Introdueix el nom del client: ");
        nomClient = input.nextLine();

        comandaItems = "";
        totalSenseIVA = 0;

        while (true) {
            Producte p = afegirProducte();
            comandaItems += p.linia;
            totalSenseIVA += p.subtotal;

            System.out.print("> Vols afegir un altre producte? (s/n): ");
            if (!input.nextLine().trim().equalsIgnoreCase("s")) break;
        }

        System.out.println("S’està generant el tiquet…");
        mostrarTiquet("TIQUET");
        System.out.println("Comanda enregistrada correctament.");
    }

    private static void actualitzarComanda() {
        if (totalSenseIVA < 0) {
            System.out.println("No hi ha cap comanda enregistrada.");
            return;
        }

        while (true) {
            Producte p = afegirProducte();
            comandaItems += p.linia;
            totalSenseIVA += p.subtotal;

            System.out.print("> Vols afegir més productes? (s/n): ");
            if (!input.nextLine().trim().equalsIgnoreCase("s")) break;
        }

        System.out.println("S’està actualitzant la comanda…");
        mostrarTiquet("TIQUET ACTUALITZAT");
        System.out.println("Comanda actualitzada correctament.");
    }

    private static void mostrarTiquet(String titol) {
        if (totalSenseIVA < 0) {
            System.out.println("No hi ha cap comanda enregistrada.");
            return;
        }

        double iva = totalSenseIVA * 0.10;
        double totalFinal = totalSenseIVA + iva;

        System.out.println("______________________________________");
        System.out.println("============== " + titol + " ==============");
        System.out.println("______________________________________");

        System.out.println("Nom del client: " + nomClient);
        System.out.println("--------------------------------------");
        System.out.printf("%-15s%-12s%-13s%s\n", "Producte", "Quantitat", "Preu", "Subtotal");
        System.out.println("--------------------------------------");
        System.out.print(comandaItems);
        System.out.println("--------------------------------------");

        System.out.println("Total sense IVA:   " + formatDouble(totalSenseIVA));
        System.out.println("IVA (10%):          " + formatDouble(iva));
        System.out.println("TOTAL A PAGAR:      " + formatDouble(totalFinal));
        System.out.println("______________________________________");
    }

    private static class Producte {
        String linia;
        double subtotal;

        Producte(String linia, double subtotal) {
            this.linia = linia;
            this.subtotal = subtotal;
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("============ RESTAURANT ============");
            System.out.println("1. Crear nova comanda");
            System.out.println("2. Actualitzar comanda");
            System.out.println("3. Mostrar tiquet");
            System.out.println("4. Sortir");
            System.out.print("> Selecciona una opció: ");

            String opcio = input.nextLine();

            switch (opcio) {
                case "1" -> crearNovaComanda();
                case "2" -> actualitzarComanda();
                case "3" -> mostrarTiquet("TIQUET");
                case "4" -> {
                    System.out.println("Fins aviat!");
                    return;
                }
                default -> System.out.println("Opció no vàlida.");
            }
        }
    }
}