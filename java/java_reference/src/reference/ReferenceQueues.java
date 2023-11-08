package reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceQueues {

    public static void main1(String[] args) throws InterruptedException {
        SavePoint savePoint = new SavePoint("Random"); // a strong object

        ReferenceQueue<SavePoint> savepointQ = new ReferenceQueue<SavePoint>();// the ReferenceQueue
        WeakReference<SavePoint> savePointWRefernce = new WeakReference<SavePoint>(savePoint, savepointQ);

        System.out.println("SavePoint created as a weak ref " + savePointWRefernce);
        Runtime.getRuntime().gc();
        System.out.println("Any weak references in Q ? " + (savepointQ.poll() != null));
        savePoint = null; // the only strong reference has been removed. The heap
        // object is now only weakly reachable

        System.out.println("Now to call gc...");
        Runtime.getRuntime().gc(); // the object will be cleared here - finalize will be called.

        System.out.println("Any weak references in Q ? " + (savepointQ.remove() != null));
        System.out.println("Does the weak reference still hold the heap object ? " + (savePointWRefernce.get() != null));
        System.out.println("Is the weak reference added to the ReferenceQ  ? " + (savePointWRefernce.isEnqueued()));
    }

    public static void main(String[] args) throws InterruptedException {
        SavePoint savePoint = new SavePoint("Random"); // a strong object

        ReferenceQueue<SavePoint> savepointQ = new ReferenceQueue<SavePoint>();// the ReferenceQueue
        WeakReference<SavePoint> savePointWRefernce = new WeakReference<SavePoint>(savePoint, savepointQ);

        System.out.println("Any weak references in Q ? " + (savepointQ.poll() != null));
        savePoint = null;

        System.out.println("Now to call gc...");
        Runtime.getRuntime().gc(); // the object will be cleared here - finalize Swill be called.
        Reference<? extends SavePoint> reCreatedSavePoint = savepointQ.remove();
        System.out.println("Any weak references in Q ? " + (reCreatedSavePoint != null));
        System.out.println("Is this same as original weak reference ? " + (reCreatedSavePoint == savePointWRefernce));
        System.out.println(" and heap object is " + reCreatedSavePoint.get());
    }

    private static class SavePoint{
        private String b;
        public SavePoint(String a){
            b = a;
        }
    }
}
