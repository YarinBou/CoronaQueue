/*
   File Name : CoronaQueue.java
   */
package corona;


public class CoronaQueue {

    Person[] data;        // the data array.
    int[] next;           // the next index array.
    int[] prev;           // the prev index array.
    int[] ref;            // the reference array.
    int size;             // the current number of subjects in the queue.
    int head;             // the index of the lists 'head'. -1 if the list is empty.
    int tail;             // the index of the lists 'tail'. -1 if the list is empty.
    int free;             // the index of the first 'free' element.

    /**
     * Creates an empty data structure with the given capacity.
     * The capacity dictates how many different subjects may be put into the data structure.
     * Moreover, the capacity gives an upper bound to the ID number of a Person to be put in the data structure.
     */
    public CoronaQueue(int capacity) {
        this.data = new Person[capacity];
        this.next = new int[capacity];
        this.prev = new int[capacity];
        this.ref = new int[capacity + 1];
        this.size = 0;
        this.head = -1;
        this.tail = -1;
        this.free = 0;
        this.prev[0] = -1;
        // this part restart the arrays without the last index.
        for (int i = 0; i < capacity - 1; i++) {
            this.next[i] = i + 1;
            this.prev[i + 1] = i;
            this.ref[i] = -1;
        }
        // this part restart the last index.
        this.next[capacity - 1] = -1;
        this.prev[capacity - 1] = capacity - 2;
        this.ref[capacity - 1] = -1;
        this.ref[capacity] = -1;

    }

    /**
     * Returns the size of the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return this.size;
    }

    /**
     * Inserts a given Person into the queue.
     * Inesertion should be done at the tail of the queue.
     * If the given person is already in the queue this function should do nothing.
     * Throws an illegal state exception if the queue is full.
     *
     * @param p - the Task to be inserted.
     * @throws IllegalStateException - if queue is full.
     */
    public void enqueue(Person p) {
        if (this.free == -1) {
            throw new IllegalStateException("i'm sorry the queue is full Bro");
        }
        if (this.ref[p.id] == -1) {
            if (this.tail == -1) {
                this.prev[free] = -1;
                this.head = this.free;
                this.tail = this.head;
            } else {
                this.next[this.tail] = this.free;
                this.prev[this.next[this.tail]] = this.tail;
                this.tail = this.free;
            }
            this.free = this.next[free];
            this.next[tail] = -1;
            this.data[this.tail] = p;
            this.ref[p.id] = tail;
            this.size++;
        }
    }


    /**
     * Removes and returns a Person from the queue.
     * The person removed is the one which sits at the head of the queue.
     * If the queue is empty returns null.
     */
    public Person dequeue() {
        if (this.size == 0) {
            return null;
        }
        Person tempPerson = this.data[this.head];
        if (this.free == -1) {
            this.free = this.head;
            this.prev[this.head] = -1;
            this.head = this.next[this.head];
            this.next[this.free] = -1;
        } else {
            this.prev[this.free] = this.head;
            this.head = this.next[this.head];
            this.next[this.prev[this.free]] = this.free;
            this.free = this.prev[this.free];
            this.prev[this.free] = -1;
        }
        if (this.head == -1) {
            this.tail = -1;
        }
        this.ref[tempPerson.id] = -1;
        this.size--;
        return tempPerson;
    }

    /**
     * Removes a Person from (possibly) the middle of the queue.
     * <p>
     * Does nothing if the Person is not already in the queue.
     * Recall that you are not allowed to traverse all elements in the queue. In particular no loops or recursion.
     * Think about all the different edge cases and the variables which need to be updated.
     * Make sure you understand the role of the reference array for this function.
     *
     * @param p - the Person to remove
     */
    public void remove(Person p) {
        int personPlace = this.ref[p.id];
        if (personPlace != -1) {
            if (personPlace == this.head) {
                dequeue();
                return;
            } else {
                if (this.free == -1) {
                    if (personPlace == this.tail) {
                        this.tail = this.prev[this.tail];
                    }
                    this.free = personPlace;
                    this.next[this.prev[this.free]] = this.next[this.free];
                    this.prev[this.next[this.free]] = this.prev[this.free];
                    this.next[this.free] = -1;
                    this.prev[this.free] = -1;
                } else {
                    this.next[this.prev[personPlace]] = this.next[personPlace];
                    if (personPlace == this.tail) {
                        this.tail = this.prev[this.tail];
                    } else {
                        this.prev[this.next[personPlace]] = this.prev[personPlace];
                    }
                    this.prev[personPlace] = -1;
                    this.next[personPlace] = this.free;
                    this.prev[this.free] = personPlace;
                    this.free = personPlace;
                }
            }
            this.ref[p.id] = -1;
            this.size--;
        }
    }

    /*
     * The following functions may be used for debugging your code.
     */
    private void debugNext() {
        for (int i = 0; i < next.length; i++) {
            System.out.println(next[i]);
        }
        System.out.println();
    }

    private void debugPrev() {
        for (int i = 0; i < prev.length; i++) {
            System.out.println(prev[i]);
        }
        System.out.println();
    }

    private void debugData() {
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
        System.out.println();
    }

    private void debugRef() {
        for (int i = 0; i < ref.length; i++) {
            System.out.println(ref[i]);
        }
        System.out.println();
    }

    /*
     * Test code; output should be:
		Aaron, ID number: 1
		Baron, ID number: 2
		Cauron, ID number: 3
		Dareon, ID number: 4
		Aaron, ID number: 1
		Baron, ID number: 2
		Aaron, ID number: 1
		
		Baron, ID number: 2
		Cauron, ID number: 3
		
		Aaron, ID number: 1
		Dareon, ID number: 4
		
		Aaron, ID number: 1
		Cauron, ID number: 3
     */
    public static void main(String[] args) {
        CoronaQueue demo = new CoronaQueue(4);
        Person a = new Person(1, "Aaron");
        Person b = new Person(2, "Baron");
        Person c = new Person(3, "Cauron");
        Person d = new Person(4, "Dareon");
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        demo.enqueue(a);
        System.out.println(demo.dequeue());
        demo.enqueue(b);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        demo.enqueue(a);
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(a);
        System.out.println(demo.dequeue());
        demo.remove(d);
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(b);
        demo.remove(c);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(b);
        demo.remove(d);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());


    }
}
