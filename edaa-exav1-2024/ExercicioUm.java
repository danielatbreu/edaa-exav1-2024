import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Country {
    String name;
    String confederation;
    double population;
    double tvAudience;
    double gdpWeightedShare;

    public Country(String name, String confederation, double population, double tvAudience, double gdpWeightedShare) {
        this.name = name;
        this.confederation = confederation;
        this.population = population;
        this.tvAudience = tvAudience;
        this.gdpWeightedShare = gdpWeightedShare;
    }
}

public class ExercicioUm {
    public static void main(String[] args) {
        Country[] countries = lerArquivoCSV("fifa_countries_audience.csv");

        Scanner scanner = new Scanner(System.in);

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    ordenarPorNome(countries);
                    exibirPaises(countries);
                    break;
                case 2:
                    ordenarPorAudienciaTV(countries);
                    exibirPaises(countries);
                    break;
                case 3:
                    System.out.println("Encerrando o programa. Obrigado por usar!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, digite novamente.");
            }
        } while (opcao != 3);
    }

    public static Country[] lerArquivoCSV(String nomeArquivo) {
        int numLinhas = contarLinhas(nomeArquivo);
        Country[] countries = new Country[numLinhas - 1];

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String line;
            br.readLine();
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0].trim();
                String confederation = data[1].trim();
                double population = Double.parseDouble(data[2].trim().replace(".", "").replace(",", "."));
                double tvAudience = Double.parseDouble(data[3].trim().replace(".", "").replace(",", "."));
                double gdpWeightedShare = Double.parseDouble(data[4].trim().replace(".", "").replace(",", "."));
                countries[index++] = new Country(name, confederation, population, tvAudience, gdpWeightedShare);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de formato ao converter número: " + e.getMessage());
        }

        return countries;
    }

    public static int contarLinhas(String nomeArquivo) {
        int linhas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            while (br.readLine() != null) {
                linhas++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao contar as linhas do arquivo: " + e.getMessage());
        }
        return linhas;
    }

    public static void exibirMenu() {
        System.out.println("\nDigite a opção desejada:");
        System.out.println("[1] Ordenar por Ordem Alfabética");
        System.out.println("[2] Ordenar por audiência TV");
        System.out.println("[3] Encerrar");
        System.out.println("--------------------------");
    }

    public static void ordenarPorNome(Country[] countries) {
        for (int i = 0; i < countries.length - 1; i++) {
            for (int j = 0; j < countries.length - i - 1; j++) {
                if (countries[j].name.compareTo(countries[j + 1].name) > 0) {
                    Country temp = countries[j];
                    countries[j] = countries[j + 1];
                    countries[j + 1] = temp;
                }
            }
        }
    }

    public static void ordenarPorAudienciaTV(Country[] countries) {
        for (int i = 0; i < countries.length - 1; i++) {
            for (int j = 0; j < countries.length - i - 1; j++) {
                if (countries[j].tvAudience > countries[j + 1].tvAudience) {
                    Country temp = countries[j];
                    countries[j] = countries[j + 1];
                    countries[j + 1] = temp;
                }
            }
        }
    }

    public static void exibirPaises(Country[] countries) {
        for (Country country : countries) {
            System.out.println(country.name + ": " + country.tvAudience + "%");
        }
    }
}
