package exercicio1.vendingmachine.models.dinheiro;

import exercicio1.vendingmachine.models.ValorMonetario;

public abstract class Dinheiro implements ValorMonetario {
	private int quantidade = 0;
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
	    return getClass().getSimpleName() + " (valor: R$ " + valor() + ", qtd: " + getQuantidade() + ")";
	}
}
