package SO.pag39;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Banco {
    public static void main(String[] args) {
        Random random = new Random();
        Semaphore semaphore = new Semaphore(1);
        Semaphore semaphore1 = new Semaphore(1);
        Thread thread;

        for (int i=1; i<=20; i++){
            thread = new ContaTransacaoThread(semaphore, semaphore1, i, random.nextDouble()*5000, random.nextDouble()*5000 );
            thread.start();
        }

    }
}
