package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {

		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}

	@Test
		// S3: on n’imprime pas le ticket si le montant inséré est insuffisant
	void pasImprimerPasmontant(){
		machine.insertMoney(PRICE-1);
		assertFalse( machine.printTicket(), "impossible d'imprimer le ticket car montant insuffisant");
	}

	@Test
		// S4: on imprime le ticket si le montant inséré est suffisant
	void imprimerTicketMontantOk(){
		machine.insertMoney(PRICE);
		assertEquals(true, machine.printTicket(), "Impression du ticket");
	}

	@Test
		// S5: Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void modifTicketBalance(){
		machine.insertMoney(60);
		machine.printTicket();
		assertEquals(60-PRICE,machine.getBalance(), "mise à jour de la balance");
	}

	@Test
		// S6: le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void MAJMontantImpressionTicket(){
		machine.insertMoney(PRICE);
		assertEquals(machine.getTotal(), 0, "Mise à jour du montant collecté");
		machine.printTicket();
		assertEquals(machine.getTotal(), PRICE, "Mise à jour du montant collect");
	}

	@Test
		// S7 : refund() rend correctement la monnaie
	void refundMonnaie(){
		machine.insertMoney(50);
		machine.printTicket();
		assertEquals(50-machine.getPrice(), machine.refund(), "La machine rend correctement la monnaie");
	}

	@Test
		// S8 : refund() remet la balance à zéro
	void refundAZero(){
		machine.refund();
		assertEquals(0, machine.getBalance(),"La balance doit etre remise à 0");
	}

	@Test
		// S9 : on ne peut pas insérer un montant négatif
	void MontantNegatifImpossible(){
		try {
			machine.insertMoney(-10);
			fail("impossible, exception");
		}
		catch(IllegalArgumentException e){}
		}


	@Test
		// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void TicketnegatifImpossible(){
		assertThrows(IllegalArgumentException.class, () -> {new TicketMachine(-1);}, "");
	}
}