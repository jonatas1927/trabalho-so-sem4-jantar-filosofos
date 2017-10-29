package jantarfilosofos;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Filosofo implements Runnable {
	private String nome;
	private boolean[] talheres; // talheres na mesa, sendo que false indica nao
								// // usada, e true indica usada
	private int direita; // talher da direita
	private int esquerda; // talher da esquerda
	private int iteracoes; // quantas vezes o filosofo vai pensar e comer
	private Random rand;
	private static Lock lock = new ReentrantLock(true);

	public Filosofo(boolean[] talheres, int ordem, int iteracoes) {
		this.talheres = talheres;
		this.esquerda = ordem;
		this.direita = (ordem + 1) % talheres.length;
		this.iteracoes = iteracoes;
		this.nome = "Filósofo " + (ordem + 1);
		this.rand = new Random();
	}

	@Override
	public void run() {
		try {
			while (iteracoes >= 0) {
				System.out.println(nome + " começa pensar...");

				Thread.sleep(rand.nextInt(101) + 1);

				System.out.println(nome
						+ " para de pensar e tenta pegar os talheres "
						+ esquerda + " e " + direita);

				lock.lock();
				if (talheres[esquerda] || talheres[direita])
					System.out.println(nome + " precisa esperar.");
				lock.unlock();

				while (true) {
					lock.lock();
					if ((talheres[esquerda] || talheres[direita]) == false)
						break;

					lock.unlock();
					Thread.sleep(5);
				}

				// Ocupa os talheres
				talheres[esquerda] = true;
				talheres[direita] = true;

				System.out.println(nome + " começa comer.");
				lock.unlock();
				Thread.sleep(rand.nextInt(51) + 1);

				lock.lock();
				System.out.println(nome
						+ " para de comer e larga os talheres que possuía.");
				talheres[esquerda] = false;
				talheres[direita] = false;
				lock.unlock();

				iteracoes--;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(nome + " terminou seu jantar.");
	}

}
