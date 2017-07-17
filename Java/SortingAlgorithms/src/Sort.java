
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;


public class Sort<T extends Comparable<? super T>> {

    private void swap(T[] data, int a, int b) {
        T temp = data[a];
        data[a] = data[b];
        data[b] = temp;
    }

    public int bubbleSort(T[] data) {
        int basicOp = 0;
        T swap;

        for (int j = 0; j < data.length; ++j) {
            for (int i = 0; i < data.length - 1; ++i) {

                if (data[i].compareTo(data[i + 1]) > 0) {

                    swap(data, i, i + 1);
                }

                ++basicOp;
            }
        }

        return basicOp;
    }

    public int selectionSort(T[] data) {
        int n = data.length - 1;
        int maxIndex = 0;
        int basicOp = 0;
        T swap;


        for (; n >= 0; --n) {
            maxIndex = 0;

            for (int i = 1; i <= n; ++i) {

                if (data[maxIndex].compareTo(data[i]) < 0) maxIndex = i;
                ++basicOp;
            }

            swap = data[maxIndex];
            data[maxIndex] = data[n];
            data[n] = swap;
        }

        return basicOp;
    }

    public int heapSort(T[] data) {
        int basicOp = 0;

        BiConsumer<Integer,Integer>[] heapify = new BiConsumer[1];
        heapify[0] = (idx, max) -> {
            int largest = idx;
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;

            if(left < max && data[left].compareTo(data[idx]) > 0)
                largest = left;
            if(right < max && data[right].compareTo(data[largest]) > 0)
                largest = right;
            if(largest != idx) {
                swap(data, idx, largest);
                heapify[0].accept(largest, max);
            }
        };


        for(int i=data.length/2 - 1; i >= 0; --i) {

            heapify[0].accept(i, data.length);
            ++basicOp;
        }

        for(int i=data.length - 1; i >= 1; --i) {

            swap(data, 0, i);
            heapify[0].accept(0, i);
            ++basicOp;
        }

        return basicOp;
    }

    public int quickSort(T[] data) {

        int basicOp = 0;

        BiConsumer<Integer,Integer> swap = (a, b) -> {
            T d = data[a];
            data[a] = data[b];
            data[b] = d;
        };

        BiFunction<Integer,Integer,Integer> partition = (left, right) -> {
            int pivot = left, divider = pivot + 1;

            if(left.equals(right)) return right;

            while(data[divider].compareTo(data[pivot]) < 0 && divider < right) {
                ++divider;
                //++basicOp;
            }

            for(int i=divider; i <= right; ++i) {

                if(data[i].compareTo(data[pivot]) < 0) {
                    swap.accept(i, divider);
                    ++divider;
                }

                //++basicOp;
            }

            --divider;

            if(data[divider].compareTo(data[pivot]) < 0) {
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

        return -1;
    }

    public void display(T[] data) {

        for(T i: data) { System.out.print(i + " "); };
        System.out.println();
    }
}
