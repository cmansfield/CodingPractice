
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;


public class Main {


    public static void main(String[] args) {
//        Integer[] data = {7, 1, 4, 8, 2, 3, 5, 9, 0};
//        Integer[] mutableData = new Integer[data.length];
//        Display<Integer> display = new Display<>();
//        Sort<Integer> sorter = new Sort<>();

        Double[] data = {2.5, 1.0, 4.6, 7.3, 0.5, 3.2, 5.5};
        Double[] mutableData = new Double[data.length];
        Sort<Double> sorter = new Sort<>();

        Map<String, Function<Double[],Integer>> sortingAlgorithms = new HashMap<>();
        sortingAlgorithms.put("Bubble Sort", sorter::bubbleSort);
        sortingAlgorithms.put("Selection Sort", sorter::selectionSort);
        sortingAlgorithms.put("Heap Sort", sorter::heapSort);
        sortingAlgorithms.put("Quick Sort", sorter::quickSort);


        sortingAlgorithms.forEach((key, value) -> {

            System.arraycopy(data, 0, mutableData, 0, data.length);

            System.out.println(key);
            sorter.display(mutableData);
            final int basicOp = value.apply(mutableData);
            sorter.display(mutableData);
            System.out.println("Basic Operation Count: " + basicOp);
            System.out.println();
        });
    }
}
