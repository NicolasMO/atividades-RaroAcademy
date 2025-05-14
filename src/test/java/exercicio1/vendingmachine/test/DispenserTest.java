package exercicio1.vendingmachine.test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import exercicio1.vendingmachine.Dispenser;
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

public class DispenserTest {

   private Dispenser dispenser;
   
   @Before
   public void deveInicializarODispenser(){
       dispenser = new Dispenser();
       dispenser.definirEstoque(MoedaDeCincoCentavos.class, 1);
       dispenser.definirEstoque(MoedaDeDezCentavos.class, 10);
       dispenser.definirEstoque(MoedaDeVinteECincoCentavos.class, 10);
       dispenser.definirEstoque(MoedaDeCinquentaCentavos.class, 10);
       dispenser.definirEstoque(MoedaDeUmReal.class, 10);
       dispenser.definirEstoque(NotaDeUmReal.class, 2);
       dispenser.definirEstoque(NotaDeDoisReais.class, 10);
       dispenser.definirEstoque(NotaDeCincoReais.class, 10);
       dispenser.definirEstoque(NotaDeDezReais.class, 10);
       dispenser.definirEstoque(NotaDeVinteReais.class, 10);
       dispenser.definirEstoque(NotaDeCinquentaReais.class, 10);
       dispenser.definirEstoque(NotaDeCemReais.class, 10);
   }

   @Test
   public void deveEstarInicializado(){
       double total = dispenser.getSaldoTotal();
       assertEquals(1890.55, total, 0);
   }
   
   @Test public void deveIncrementarEstoque() {
	   int qntdMoedasEstoqueTotalEsperado = 15;
	   dispenser.incrementarEstoque(MoedaDeDezCentavos.class, 5);
	   assertEquals(qntdMoedasEstoqueTotalEsperado, dispenser.getQuantidade(MoedaDeDezCentavos.class));
   }
   
   @Test 
   public void deveDecrementarEstoque() {
	   int qntdMoedasEstoqueTotalEsperado = 5;
	   dispenser.decrementarEstoque(MoedaDeDezCentavos.class, 5);
	   assertEquals(qntdMoedasEstoqueTotalEsperado, dispenser.getQuantidade(MoedaDeDezCentavos.class));
   }
   
   @Test
   public void deveDarZeroDeTroco() {
	   Dinheiro[] troco = dispenser.trocoPara(200, 200);
	   assertArrayEquals(new Dinheiro[0], troco);
   }
   
   @Test
   public void naoDeveTerTroco() {
	   Dinheiro[] troco = dispenser.trocoPara(20000, 123.40);
	   assertNull(troco);
   }
   
   @Test
   public void deveCalcularTrocoSimples() {
	   dispenser.zerarEstoque();
	   dispenser.definirEstoque(MoedaDeCinquentaCentavos.class, 1);
	   dispenser.definirEstoque(MoedaDeVinteECincoCentavos.class, 2);
	   
	   Dinheiro[] troco = dispenser.trocoPara(2.00, 1.00);
	   
	   // Deve existir pelo menos uma unidade de troco
	   assertTrue(troco.length > 0);
	   
	   // Deve verificar em ordem decrescente de valor
	   for (int i = 1; i < troco.length; i++) {
		   assertTrue(troco[i - 1].valor() >= troco[i].valor());
	   }
	   
	   // Soma do dinheiro deve ser exatamente R$ 1.00
	   double total = 0.0;
	   double totalEsperado = 1.0;
	   
	   for (Dinheiro dinheiro : troco) {
		   total += dinheiro.valor() * dinheiro.getQuantidade();
	   }
	   
	   assertEquals(totalEsperado, total, 0.0001);
   }
   
   
	// 1. Troco viável usando apenas uma nota
	@Test
	public void deveDarTrocoEmUmaNota() {
	    dispenser.zerarEstoque();
	    dispenser.definirEstoque(NotaDeVinteReais.class, 3);
	    Dinheiro[] troco = dispenser.trocoPara(50.00, 30.00);
	
	    assertEquals(1, troco.length);
	    assertEquals(20.00, troco[0].valor(), 0.0001);
	}
	
	// 2. Troco fracionado com notas e moedas diversas (R$ 1,85)
	@Test
	public void deveDarTrocoComMoedasDiversas() {
	    dispenser.zerarEstoque();
	    dispenser.definirEstoque(MoedaDeUmReal.class, 2);
	    dispenser.definirEstoque(MoedaDeCinquentaCentavos.class, 2);
	    dispenser.definirEstoque(MoedaDeVinteECincoCentavos.class, 2);
	    dispenser.definirEstoque(MoedaDeDezCentavos.class, 2);
	
	    Dinheiro[] troco = dispenser.trocoPara(10.00, 8.15);
	
	    double total = 0.0;
	    for (Dinheiro d : troco) {
	    	total += d.valor() * d.getQuantidade();
	    };
	    assertEquals(1.85, total, 0.0001);
	
	    for (int i = 1; i < troco.length; i++) {
	        assertTrue(troco[i - 1].valor() >= troco[i].valor());
	    }
	}
	
	// 3. Troco impossível devido a centavos não representáveis
	@Test
	public void deveRetornarNullQuandoTrocoDecimalImpossivel() {
	    dispenser.zerarEstoque(); // estoque não importa, não há moeda de 0,02 ou 0,05
	    Dinheiro[] troco = dispenser.trocoPara(5.00, 4.03);
	    assertNull(troco);
	}
	
	// 4. Troco misto em notas e moedas (R$ 36,60)
	@Test
	public void deveDarTrocoUsandoNotasEMoedas() {
	    dispenser.zerarEstoque();
	    dispenser.definirEstoque(NotaDeVinteReais.class, 2);
	    dispenser.definirEstoque(NotaDeDezReais.class, 2);
	    dispenser.definirEstoque(NotaDeCincoReais.class, 5);
	    dispenser.definirEstoque(MoedaDeUmReal.class, 5);
	    dispenser.definirEstoque(MoedaDeCinquentaCentavos.class, 3);
	    dispenser.definirEstoque(MoedaDeDezCentavos.class, 2);
	
	    Dinheiro[] troco = dispenser.trocoPara(100.00, 63.40);
	    double total = 0.0;
	    for (Dinheiro d : troco) {
	    	total += d.valor() * d.getQuantidade();
	    };
	    assertEquals(36.60, total, 0.0001);
	}
	
	// 5. Estoque limitado – troco deve falhar por falta de moedas suficientes
	@Test
	public void deveRetornarNullQuandoEstoqueInsuficiente() {
	    dispenser.zerarEstoque();
	    dispenser.definirEstoque(MoedaDeUmReal.class, 1); // apenas R$ 1 disponível
	    Dinheiro[] troco = dispenser.trocoPara(10.00, 8.00); // troco de R$ 2
	    assertNull(troco); // não há moedas ou notas suficientes para R$ 2
	}

	@Test
	public void deveRetornarTrocoComNotasDeMaiorValorPrimeiro() {
	    dispenser.zerarEstoque();
	    dispenser.definirEstoque(NotaDeDoisReais.class, 2);
	    dispenser.definirEstoque(MoedaDeUmReal.class, 5);
	    dispenser.definirEstoque(MoedaDeCinquentaCentavos.class, 2);

	    Dinheiro[] troco = dispenser.trocoPara(10.00, 3.00);

	    double total = 0.0;
	    for (Dinheiro d : troco) {
	        total += d.valor() * d.getQuantidade();
	    }

	    assertEquals(7.00, total, 0.0001);
	}

	@Test
	public void deveRetornarOTrocoExato(){
	    dispenser.zerarEstoque();
	    dispenser.definirEstoque(NotaDeDoisReais.class, 4);
	    dispenser.definirEstoque(MoedaDeCinquentaCentavos.class, 3);
	    dispenser.definirEstoque(MoedaDeVinteECincoCentavos.class, 1);
	    dispenser.definirEstoque(MoedaDeDezCentavos.class, 3);

	    Dinheiro[] troco = dispenser.trocoPara(20.00, 14.20);
	    double total = 0.0;
	    for (Dinheiro d : troco) {
	    	total += d.valor() * d.getQuantidade();
	    };
	    assertEquals(5.80, total, 0.0001);
	}
	
	@Test
	public void deveRetornarTrocoExatoComNotasEMoedas() {
	    dispenser.zerarEstoque();
	    dispenser.definirEstoque(NotaDeDoisReais.class, 2);
	    dispenser.definirEstoque(MoedaDeCinquentaCentavos.class, 0);
	    dispenser.definirEstoque(MoedaDeDezCentavos.class, 2);
	    dispenser.definirEstoque(MoedaDeCincoCentavos.class, 20);

	    Dinheiro[] troco = dispenser.trocoPara(10.00, 5.00);

	    double total = 0.0;
	    for (Dinheiro d : troco) {
	        total += d.valor() * d.getQuantidade();
	    }

	    assertEquals(5.00, total, 0.0001);
	}

	@Test
	public void deveRetornarArrayVazioQuandoNaoHaTroco() {
	    dispenser.zerarEstoque();
	    dispenser.definirEstoque(NotaDeDoisReais.class, 2);

	    Dinheiro[] troco = dispenser.trocoPara(5.00, 5.00);

	    assertNotNull(troco);
	    assertEquals(0, troco.length);
	}
}
