package exercicio1.vendingmachine;

import java.util.Arrays;
import java.util.Objects;

import exercicio1.vendingmachine.models.dinheiro.Dinheiro;
import exercicio1.vendingmachine.models.dinheiro.MoedaDeCincoCentavos;
import exercicio1.vendingmachine.models.dinheiro.MoedaDeCinquentaCentavos;
import exercicio1.vendingmachine.models.dinheiro.MoedaDeDezCentavos;
import exercicio1.vendingmachine.models.dinheiro.MoedaDeUmReal;
import exercicio1.vendingmachine.models.dinheiro.MoedaDeVinteECincoCentavos;
import exercicio1.vendingmachine.models.dinheiro.NotaDeCemReais;
import exercicio1.vendingmachine.models.dinheiro.NotaDeCincoReais;
import exercicio1.vendingmachine.models.dinheiro.NotaDeCinquentaReais;
import exercicio1.vendingmachine.models.dinheiro.NotaDeDezReais;
import exercicio1.vendingmachine.models.dinheiro.NotaDeDoisReais;
import exercicio1.vendingmachine.models.dinheiro.NotaDeUmReal;
import exercicio1.vendingmachine.models.dinheiro.NotaDeVinteReais;

public class Dispenser {

	// Inventário inicial – uma instância de cada denominação
	private final Dinheiro[] estoque = new Dinheiro[] { 
			new MoedaDeCincoCentavos(), 
			new MoedaDeDezCentavos(),
			new MoedaDeVinteECincoCentavos(), 
			new MoedaDeCinquentaCentavos(), 
			new MoedaDeUmReal(), 
			new NotaDeUmReal(),
			new NotaDeDoisReais(), 
			new NotaDeCincoReais(), 
			new NotaDeDezReais(), 
			new NotaDeVinteReais(),
			new NotaDeCinquentaReais(), 
			new NotaDeCemReais(), 
		};

	// Zera todo o estoque
	public void zerarEstoque() {
		for (int i = 0; i < estoque.length; i++) {
			estoque[i].setQuantidade(0);
		}
	}

	// Define a quantidade fixa para um dinheiro
	public void definirEstoque(Class<? extends Dinheiro> dinheiro, int quantidade) {
		getDinheiro(dinheiro).setQuantidade(quantidade);
	}

	// Soma total do dinheiro no dispenser
	public double getSaldoTotal() {
		double total = 0;

		for (Dinheiro dinheiro : estoque) {
			total += dinheiro.getQuantidade() * dinheiro.valor();
		}
		return total;
	}

	public int getQuantidade(Class<? extends Dinheiro> tipoDinheiro) {
		Dinheiro dinheiro = getDinheiro(tipoDinheiro);
		return dinheiro.getQuantidade();
	}

	// Incrementa dinheiro no estoque
	public void incrementarEstoque(Class<? extends Dinheiro> tipoDinheiro, int quantidade) {
		Dinheiro dinheiro = getDinheiro(tipoDinheiro);
		dinheiro.setQuantidade(dinheiro.getQuantidade() + quantidade);
	}

	// Decrementa dinheiro no estoque
	public void decrementarEstoque(Class<? extends Dinheiro> tipoDinheiro, int quantidade) {
		Dinheiro dinheiro = getDinheiro(tipoDinheiro);
		dinheiro.setQuantidade(dinheiro.getQuantidade() - quantidade);
	}

	// Metodo de troco
	public Dinheiro[] trocoPara(double valorPago, double valorProduto) {

		double troco = Math.round((valorPago - valorProduto) * 100.0) / 100.0;

		// Sem troco pois saldo da máquina é menor que troco
		if (this.getSaldoTotal() < troco) {
			return null;
		}

		if (troco == 0.0) {
			return new Dinheiro[0];
		}

		// Faz uma cópia de estoque e ordena de forma decrescente
		Dinheiro[] copiaOrdenada = clonarEOrdenarEstoquePorValorDecrescente(estoque);
		Dinheiro[] usados = new Dinheiro[100];

		mostrarQuantidadeEmEstoque("Caixa atual: ");
		
		// Verifica se é possível dar troco
		boolean trocoEncontrado = tentarDarTroco(copiaOrdenada, troco, usados, 0);
		if(!trocoEncontrado) {
			mostrarQuantidadeEmEstoque("Caixa final: ");
			return null;
		}
		
		for (int i = 0; i < usados.length && usados[i] != null; i++) {
			this.decrementarEstoque(usados[i].getClass(), usados[i].getQuantidade());
		}

		mostrarQuantidadeEmEstoque("Caixa final: ");
		return Arrays.stream(usados).filter(Objects::nonNull).toArray(Dinheiro[]::new);
	}
	
	private boolean tentarDarTroco(Dinheiro[] estoque, double troco, Dinheiro[] usados, int usadosIndex) {
	    if (Math.abs(troco) < 0.0001) return true;

	    for (int i = 0; i < estoque.length; i++) {
	        Dinheiro dinheiro = estoque[i];
	        if (dinheiro.getQuantidade() == 0 || dinheiro.valor() > troco) {
	        	continue;
	        }

	        int maxUsoPossivel = (int) Math.min(dinheiro.getQuantidade(), Math.floor(troco / dinheiro.valor()));
	   	        
	        for (int qtd = maxUsoPossivel; qtd >= 1; qtd--) {
	            double novoTroco = Math.round((troco - qtd * dinheiro.valor()) * 100.0) / 100.0;
	            
	            dinheiro.setQuantidade(dinheiro.getQuantidade() - qtd);
	            Dinheiro usado = novaInstancia(dinheiro);
	            usado.setQuantidade(qtd);
	            usados[usadosIndex] = usado;

	            if (tentarDarTroco(estoque, novoTroco, usados, usadosIndex + 1)) {
	            	return true;
	            }

	            dinheiro.setQuantidade(dinheiro.getQuantidade() + qtd);
	            usados[usadosIndex] = null;
	        }
	    }

	    return false;
	}


	private void mostrarQuantidadeEmEstoque(String caixa) {
		System.out.println(caixa);
		System.out.println("Nota de 100.00: " + this.getQuantidade(NotaDeCemReais.class));
		System.out.println("Nota de 50.00: " + this.getQuantidade(NotaDeCinquentaReais.class));
		System.out.println("Nota de 20.00: " + this.getQuantidade(NotaDeVinteReais.class));
		System.out.println("Nota de 10.00: " + this.getQuantidade(NotaDeDezReais.class));
		System.out.println("Nota de 05.00: " + this.getQuantidade(NotaDeCincoReais.class));
		System.out.println("Nota de 02.00: " + this.getQuantidade(NotaDeDoisReais.class));
		System.out.println("Nota de 01.00: " + this.getQuantidade(NotaDeUmReal.class));
		System.out.println("Moeda de 1.00: " + this.getQuantidade(MoedaDeUmReal.class));
		System.out.println("Moeda de 0.50: " + this.getQuantidade(MoedaDeCinquentaCentavos.class));
		System.out.println("Moeda de 0.25: " + this.getQuantidade(MoedaDeVinteECincoCentavos.class));
		System.out.println("Moeda de 0.10: " + this.getQuantidade(MoedaDeDezCentavos.class));
		System.out.println("Moeda de 0.05: " + this.getQuantidade(MoedaDeCincoCentavos.class));
	}

	private Dinheiro[] clonarEOrdenarEstoquePorValorDecrescente(Dinheiro[] estoque) {
		// Clonar estoque
		Dinheiro[] copia = new Dinheiro[estoque.length];
		for (int i = 0; i < estoque.length; i++) {
			Dinheiro d = novaInstancia(estoque[i]);
			d.setQuantidade(estoque[i].getQuantidade());
			copia[i] = d;
		}

		// Ordenar por valor decrescente
		for (int i = 0; i < copia.length - 1; i++) {
			for (int j = i + 1; j < copia.length; j++) {
				if (copia[j].valor() > copia[i].valor()) {
					Dinheiro tmp = copia[i];
					copia[i] = copia[j];
					copia[j] = tmp;
				}
			}
		}

		return copia;
	}

	private Dinheiro novaInstancia(Dinheiro dinheiro) {
		if (dinheiro instanceof MoedaDeCincoCentavos) return new MoedaDeCincoCentavos();
		if (dinheiro instanceof MoedaDeDezCentavos) return new MoedaDeDezCentavos();
		if (dinheiro instanceof MoedaDeVinteECincoCentavos) return new MoedaDeVinteECincoCentavos();
		if (dinheiro instanceof MoedaDeCinquentaCentavos) return new MoedaDeCinquentaCentavos();
		if (dinheiro instanceof MoedaDeUmReal) return new MoedaDeUmReal();
		if (dinheiro instanceof NotaDeUmReal) return new NotaDeUmReal();
		if (dinheiro instanceof NotaDeDoisReais) return new NotaDeDoisReais();
		if (dinheiro instanceof NotaDeCincoReais) return new NotaDeCincoReais();
		if (dinheiro instanceof NotaDeDezReais) return new NotaDeDezReais();
		if (dinheiro instanceof NotaDeVinteReais) return new NotaDeVinteReais();
		if (dinheiro instanceof NotaDeCinquentaReais) return new NotaDeCinquentaReais();
		if (dinheiro instanceof NotaDeCemReais) return new NotaDeCemReais();

		throw new IllegalArgumentException("Tipo de dinheiro desconhecido");
	}

	// Retorna a instancia do dinheiro em estoque
	private Dinheiro getDinheiro(Class<? extends Dinheiro> dinheiro) {
		for (Dinheiro dinheiroRegistrado : estoque) {
			if (dinheiroRegistrado.getClass() == dinheiro) {
				return dinheiroRegistrado;
			}
		}
		throw new IllegalArgumentException("Tipo de dinheiro não registrado: " + dinheiro.getSimpleName());
	}

}
