package jantarfilosofos;

public class Main {

public static void main(String[] args) {
	boolean[] talheres = new boolean[5];
	Filosofo[] filosofos = new Filosofo[talheres.length];

for (int o = 0; o < filosofos.length; o++){
	filosofos[o] = new Filosofo(talheres, o, 100);
	new Thread(filosofos[o]).start();
}

}

}
