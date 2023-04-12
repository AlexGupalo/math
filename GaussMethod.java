import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;

public class GaussMethod {

    private double [][] original; // исходная матрица СЛАУ
    private double [][] copy; // матрица СЛАУ
    private int n, m; // размеры матрицы СЛАУ


    private void create(int k, int l) { // метод для создания матрицы заданного размера
        original = new double[k][];
        for(int i = 0; i < k; i++)
            original[i] = new double[l];
    }

    public void copyMatrix() { // создание копии матрицы
        copy = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                copy[i][j] = original[i][j];
            }
        }
    }

    public void init(String s) throws FileNotFoundException { // метод для инициализации матрицы из файла
        File file = new File(s);
        Scanner scan = new Scanner(file);
        Pattern pat = Pattern.compile("[\\s\\t]+");
        String str = scan.nextLine();
        String[] sn = pat.split(str.trim());

        n = Integer.parseInt(sn[0]);
        m = Integer.parseInt(sn[1]);

        create(n, m);

        for (int i = 0; i < n; i++) { // цикл заполнения матрицы значениями из файла
            str = scan.nextLine();
            sn = pat.split(str.trim());

            for (int j = 0; j < m; j++) {
                original[i][j] = Double.parseDouble(sn[j]);
            }
        }

        scan.close(); // закрытие сканера

        copyMatrix(); // создание копии матрицы

        solve(); // решение СЛАУ методом Гаусса
    }

    public void solve() { // метод для решения СЛАУ методом Гаусса
        int i, j, k;
        double t;

        for (i = 0; i < n; i++) { // цикл для приведения матрицы к верхнетреугольному виду
            for (j = i + 1; j < n; j++) {
                t = copy[j][i] / copy[i][i];
                for (k = 0; k < m; k++)
                    copy[j][k] -= t * copy[i][k];
            }
        }

        for (i = 0; i < n; i++) { // цикл для проверки на вырожденность системы по
            if (copy[i][i] == 0) {
                System.out.println("система вырождена");
                return;
            }
        }

        for (i = n - 1; i >= 0; i--) {// цикл для обратного хода метода Гаусса
            if (copy[i][i] == 0) {
                if (copy[i][m - 1] == 0) {
                    System.out.println("бесконечно");
                } else {
                    System.out.println("решений нет");
                }
                return;
            }

            t = copy[i][m - 1];

            for (j = m - 2; j > i; j--)
                t -= copy[i][j] * copy[j][m - 1];
            copy[i][m - 1] = t / copy[i][i];
        }
        print();
    }


    public void print() {
        System.out.println("Оригинальная матрица:");
        printOriginal();
        System.out.println("Полученная матрица:");
        printTriangulated();

        System.out.println("Результат:");
        for (int i = 0; i < n; i++) {
            System.out.printf("%3s", " ");
            System.out.printf("x[" + i +  "] = %6E", + copy[i][m - 1]);
        }


    }

    public void printOriginal() { //оригинальная матрица
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++)
                System.out.printf("%15.6E", original[i][j]);
            System.out.println();
        }
        System.out.println();
    }

    public void printTriangulated() { //треугольная матрица
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++)
                System.out.printf("%15.6E", copy[i][j]);
            System.out.println();
        }
        System.out.println();
    }

}