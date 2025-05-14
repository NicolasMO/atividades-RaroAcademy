package exercicio1.vendingmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exercicio1.vendingmachine.models.dinheiro.Dinheiro;
import exercicio1.vendingmachine.models.dinheiro.Moeda;
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
		for (Dinheiro dinheiro : estoque) {
			dinheiro.setQuantidade(0);
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

		double troco = arredondarParaDuasCasas(valorPago - valorProduto);

		// Sem troco pois saldo da máquina é menor que troco
		if (this.getSaldoTotal() < troco) {
			return null;
		}

		if (troco == 0.0) {
			return new Dinheiro[0];
		}

		// Faz uma cópia de estoque e ordena de forma decrescente
		Dinheiro[] copiaOrdenada = clonarEOrdenarEstoquePorValorDecrescente(estoque);
		List<Dinheiro> usados = new ArrayList<>();

		mostrarQuantidadeEmEstoque("Caixa atual: ");
		
		// Verifica se é possível dar troco
		boolean trocoEncontrado = tentarDarTroco(copiaOrdenada, troco, usados, 0);
		
		if(!trocoEncontrado) {
			mostrarQuantidadeEmEstoque("Caixa final: ");
			return null;
		}
		
		for (Dinheiro dinheiro: usados) {
			this.decrementarEstoque(dinheiro.getClass(), dinheiro.getQuantidade());
		}

		mostrarQuantidadeEmEstoque("Caixa final: ");
		mostrarDinheiroUsadoParaTroco(usados.toArray(new Dinheiro[0]));
		
		return usados.toArray(new Dinheiro[0]);
	}
	
	private boolean tentarDarTroco(Dinheiro[] estoque, double troco, List<Dinheiro> usados, int inicio) {
	    if (Math.abs(troco) < 0.0001) {
	    	return true;
	    }

	    for (int i = 0; i < estoque.length; i++) {
	        Dinheiro dinheiro = estoque[i];
	        if (dinheiro.getQuantidade() == 0 || dinheiro.valor() > troco) {
	        	continue;
	        }

	        int maxUsoPossivel = (int) Math.min(dinheiro.getQuantidade(), Math.floor(troco / dinheiro.valor()));
	   	        
	        for (int qtd = maxUsoPossivel; qtd >= 1; qtd--) {
	            double novoTroco = arredondarParaDuasCasas(troco - qtd * dinheiro.valor());
	            
	            dinheiro.setQuantidade(dinheiro.getQuantidade() - qtd);
	            Dinheiro usado = novaInstancia(dinheiro);
	            usado.setQuantidade(qtd);
	            usados.add(usado);

	            if (tentarDarTroco(estoque, novoTroco, usados, i)) {
	            	return true;
	            }

	            dinheiro.setQuantidade(dinheiro.getQuantidade() + qtd);
	            usados.remove(usados.size() - 1);
	        }
	    }

	    return false;
	}


	private void mostrarQuantidadeEmEstoque(String titulo) {
		System.out.println(titulo);

	    List<Class<? extends Dinheiro>> tiposDeDinheiro = Arrays.asList(
	        NotaDeCemReais.class,
	        NotaDeCinquentaReais.class,
	        NotaDeVinteReais.class,
	        NotaDeDezReais.class,
	        NotaDeCincoReais.class,
	        NotaDeDoisReais.class,
	        NotaDeUmReal.class,
	        MoedaDeUmReal.class,
	        MoedaDeCinquentaCentavos.class,
	        MoedaDeVinteECincoCentavos.class,
	        MoedaDeDezCentavos.class,
	        MoedaDeCincoCentavos.class
	    );

	    for (Class<? extends Dinheiro> tipo : tiposDeDinheiro) {
	        int qtd = this.getQuantidade(tipo);
	        try {
	            Dinheiro instancia = tipo.getDeclaredConstructor().newInstance();
	            System.out.printf("%s de %.2f: %d%n", instancia instanceof Moeda ? "Moeda" : "Nota", instancia.valor(), qtd);
	        } catch (Exception e) {
	            System.out.println("Não foi possível instanciar valor");
	        }
	    }
	}
	
	public void mostrarDinheiroUsadoParaTroco(Dinheiro[] usados) {
		System.out.println("Troco fornecido:");
	    Map<Double, Integer> agrupado = new TreeMap<>(Collections.reverseOrder());

	    for (Dinheiro d : usados) {
	        if (d == null) continue;
	        agrupado.merge(d.valor(), d.getQuantidade(), Integer::sum);
	    }

	    for (Map.Entry<Double, Integer> entry : agrupado.entrySet()) {
	        System.out.printf("Valor %.2f: %d unidades%n", entry.getKey(), entry.getValue());
	    }
	}

	private Dinheiro[] clonarEOrdenarEstoquePorValorDecrescente(Dinheiro[] estoque) {
		// Clonar estoque e ordenar em decrescente
		return Arrays.stream(estoque).map(original -> {
			Dinheiro clone = novaInstancia(original);
			clone.setQuantidade(original.getQuantidade());
			return clone;
		}).sorted(Comparator.comparingDouble(Dinheiro::valor).reversed())
		  .toArray(Dinheiro[]::new);		
	}

	private Dinheiro novaInstancia(Dinheiro dinheiro) {
		try {
			return dinheiro.getClass().getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("Tipo de dinheiro desconhecido");
		}
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
	
	private double arredondarParaDuasCasas(double valor) {
		return Math.round(valor * 100.0) / 100.0;
	}

}
