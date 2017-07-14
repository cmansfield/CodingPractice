

public class Main {

    public static class Sort {

        public static int basicOp = 0;


        public static void resetBasicCount() { basicOp = 0; }

        public static void bubbleSort(int[] data) {
            
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

        display(data);
        Sort.selectionSort(data);
        display(data);
        System.out.print("Basic Operation Count: " + Sort.basicOp);
    }
}
