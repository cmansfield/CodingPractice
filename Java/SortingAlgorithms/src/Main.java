
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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

        public static void heapSort(int[] data) {

            BiConsumer<Integer,Integer> swap = (a, b) -> {
                int d = data[a];
                data[a] = data[b];
                data[b] = d;
            };

            BiConsumer<Integer,Integer>[] heapify = new BiConsumer[1];
            heapify[0] = (idx, max) -> {
                int largest = idx;
                int left = 2 * idx + 1;
                int right = 2 * idx + 2;

                if(left < max && data[left] > data[idx])
                    largest = left;
                if(right < max && data[right] > data[largest])
                    largest = right;
                if(largest != idx) {
                    swap.accept(idx, largest);
                    heapify[0].accept(largest, max);
                }

                ++basicOp;
            };


            for(int i=data.length/2 - 1; i >= 0; --i) {

                heapify[0].accept(i, data.length);
            }

            for(int i=data.length - 1; i >= 1; --i) {

                swap.accept(0, i);
                heapify[0].accept(0, i);
            }
        }

        public static void quickSort(int[] data) {

            BiConsumer<Integer,Integer> swap = (a, b) -> {
                int d = data[a];
                data[a] = data[b];
                data[b] = d;
            };

            BiFunction<Integer,Integer,Integer> partition = (left, right) -> {
                int pivot = left, divider = pivot + 1;

                if(left.equals(right)) return right;

                while(data[divider] < data[pivot] && divider < right) {
                    ++divider;
                    ++basicOp;
                }

                for(int i=divider; i <= right; ++i) {

                    if(data[i] < data[pivot]) {
                        swap.accept(i, divider);
                        ++divider;
                    }

                    ++basicOp;
                }

                --divider;

                if(data[divider] < data[pivot]) {
                    swap.accept(pivot, divider);
                }

                return divider;
            };

            BiConsumer<Integer,Integer>[] qsort = new BiConsumer[1];
            qsort[0] = (left, right) -> {

                if(left < right) {
                    int pivotIndex = partition.apply(left, right);
                    qsort[0].accept(left, pivotIndex - 1);
                    qsort[0].accept(pivotIndex + 1, right);
                }
            };


            qsort[0].accept(0, data.length - 1);
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
        sortingAlgorithms.put("Heap Sort", Sort::heapSort);
        sortingAlgorithms.put("Quick Sort", Sort::quickSort);


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
