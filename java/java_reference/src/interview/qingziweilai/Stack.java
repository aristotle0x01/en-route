package interview.qingziweilai;

// 2.
class Stack {
    private int[] table;
    private volatile int top;
    private final int MAX = 100;
    // 0: idle, 1: occupied
    private volatile int processing;

    public Stack() {
        this.table = new int[MAX];
        this.top = 0;
        this.processing = 0;
    }

    public boolean push(int v) {
        if (this.top >= MAX) {
            return false;
        }

        if (testAndSet(new int[]{this.processing}, 1, 0)) {
            if (this.top >= MAX) {
                this.processing = 0;
                return false;
            }

            this.table[this.top++] = v;
            this.processing = 0;
            return true;
        }

        return false;
    }

    public int pop() {
        if (this.top <= 0) {
            throw new RuntimeException("empty");
        }

        if (testAndSet(new int[]{this.processing}, 1, 0)) {
            if (this.top <= 0) {
                this.processing = 0;
                throw new RuntimeException("empty");
            }

            int v = this.table[--this.top];
            this.processing = 0;
            return v;
        }

        throw new RuntimeException("contention");
    }

    boolean testAndSet(int[] v0, int target, int before) {
        return true;
    }
}

// 4.













}