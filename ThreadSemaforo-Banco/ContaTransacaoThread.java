package SO.pag39;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ContaTransacaoThread extends Thread{

    private Semaphore saqueSemaphore;
    private Semaphore depositoSemaphore;

    private Integer numCta;
    private Double saldo;
    private Double vlr;
    private Random random;

    public ContaTransacaoThread(Semaphore saqueSemaphore, Semaphore depositoSemaphore, Integer numCta, Double saldo, Double vlr) {
        this.saqueSemaphore = saqueSemaphore;
        this.depositoSemaphore = depositoSemaphore;
        this.numCta = numCta;
        this.saldo = saldo;
        this.vlr = vlr;
        this.random = new Random();
    }

    @Override
    public void run() {
        transacionar();
    }

    private void transacionar() {
        if (random.nextBoolean())
            depositar();
        else
            sacar();
    }

    private void sacar() {
        if (vlr > saldo) {
            System.err.println(String.format("Erro na conta #%s: Você não tem saldo suficiente" +
                    "\nValor: %.2f "+
                    "\nSaldo: %.2f \n", numCta, vlr, saldo));
            return;
        }
        try {
            saqueSemaphore.acquire();
            saldo-=vlr;
            System.out.println(String.format("Conta #%s R$%.2f Sacado com sucesso. Saldo atual: R$%.2f \n",numCta, vlr, saldo));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            saqueSemaphore.release();
        }

    }

    private void depositar() {
        try {
            depositoSemaphore.acquire();
            saldo+=vlr;
            System.out.println(String.format("Conta #%s R$%.2f Depositado com sucesso. Saldo atual: R$%.2f \n",numCta, vlr, saldo));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            depositoSemaphore.release();
        }
    }
}
