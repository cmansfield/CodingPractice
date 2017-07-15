
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;


public class Main {

    public static class Sort {

        public static int basicOp = 0;


        public static void resetBasicCount() { basicOp = 0; }

        public static void bubbleSort(int[] data) {
            int swap;

            for(int j=0; j < data.length; ++j) {
                for (int i = 0; i < data.length - 1; ++i) {

                    if (data[i] > data[i + 1]) {

                        swap = data[i];
                        data[i] = data[i + 1];
                        data[i + 1] = swap;
                    }

                    ++basicOp;
                }
            }
        }

        public static void selectionSort(int[] data) {
            int n = data.length - 1;
            int maxIndex = 0;
            int swap;


            for(; n >= 0; --n) {
                maxIndex = 0;

                for(int i=1; i <= n; ++i) {

                    if(data[maxIndex] < data[i]) maxIndex = i;
                    ++basicOp;
                }

                swap = data[maxIndex];
                data[maxIndex] = data[n];
                data[n] = swap;
            }
        }
    }

    public static void display(int[] data) {

        for(int i: data) { System.out.print(i + " "); };
        System.out.println();
    }

    public static void main(String[] args) {
        int[] data = {7, 1, 4, 8, 2, 3, 5, 9, 0};
        int[] mutableData = new int[data.length];
        Map<String, Consumer<int[]>> sortingAlgorithms = new HashMap<String, Consumer<int[]>>();
        sortingAlgorithms.put("Bubble Sort", Sort::bubbleSort);
        sortingAlgorithms.put("Selection Sort", Sort::selectionSort);


        for(Map.Entry sort: sortingAlgorithms.entrySet()) {

            System.arraycopy(data, 0, mutableData, 0, data.length);

            System.out.println(sort.getKey());
            display(mutableData);
            ((Consumer<int[]>)sort.getValue()).accept(mutableData);
            display(mutableData);
            System.out.println("Basic Operation Count: " + Sort.basicOp);
            Sort.resetBasicCount();
            System.out.println();
        }

    }
}
