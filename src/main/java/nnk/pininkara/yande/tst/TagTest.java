// 2022/1/20 14:05

package nnk.pininkara.yande.tst;

import java.util.concurrent.locks.ReentrantLock;

public class TagTest {
    public static void main(String[] args) {
        Add add = new Add();
        for (int i = 0; i < 100; i++) {
            new Thread(add).start();
        }
    }

    static class Add extends Thread {
        private ReentrantLock lock = new ReentrantLock();
        int sum = 0;

        @Override
        public void run() {
            lock.lock();
            sum++;
            System.out.println(sum);
            lock.unlock();
        }
    }
}


