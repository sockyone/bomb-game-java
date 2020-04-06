package sample.Tools;

public class SpecifiedQueue {
    private int[] array;
    private int head;
    private int tail;
    private int n;

    //note : this queue will full and you will lose your data
    //and I didn't catch that exception because it is not really necessary for my home work in school

    public SpecifiedQueue(int n) {
        if (n>10000) {
            System.out.println("You should use another queue which more effective with big number of element. " +
                    "This one just for my graph to find out the way in my game.");
            System.out.println("This queue will not be created. Remove it if not you will get some error.");
        }
        else {
            this.n = n;
            array = new int[n];
            head = 0;
            tail = 0;
        }
    }

    public boolean isEmpty() {
        return head==tail;
    }

    public void enqueue(int e) {
        array[tail++%n] = e;
    }

    public int dequeue() {
        return array[head++%n];
    }

}
