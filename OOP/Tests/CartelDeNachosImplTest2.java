package OOP.Tests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.Profesor;
import OOP.Provided.CasaDeBurrito;

import OOP.Provided.Profesor.*;
import OOP.Provided.CasaDeBurrito.*;
import OOP.Provided.CartelDeNachos.*;

import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.ProfesorImpl;
import OOP.Solution.CasaDeBurritoImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.Assert.*;

/**
 * Created by Omer on 4/22/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // For ascending order to the tests

public class CartelDeNachosImplTest2 {
	
	private static CasaDeBurrito dornishBurgers, mcFreys, starkBurger, theWallBurger, lannisterBurger, baratheonBurger, bravosiChicken; // wat
	
	private static Profesor jon;
	private static Profesor arya;
	private static Profesor daenerys;
	private static Profesor theHound;
	private static Profesor jorah;
	private static Profesor daario;
	private static Profesor bran;
	private static Profesor theThreeEyedRaven;
	private static Profesor hodor;
	private static Profesor meera;
	
	private static Set<String> menu1, menu2, menu3, menu4, menu5, menu6, menuE;
	
	static {
		menu1 = new HashSet<>();
		menu1.add("Dornish Fries");
		menu1.add("Ale");
		menu1.add("Holy Burger");
		menu1.add("Extra Holy Burger");
		
		menu2 = new HashSet<>();
		menu2.add("Hot Wine");
		menu2.add("Mutton Burger");
		menu2.add("Salted Pork");
		menu2.add("Dornish Fries");
		menu2.add("Chopped Stark");
		
		menu3 = new HashSet<>();
		menu3.add("Beer");
		menu3.add("Mereneese Lamb Burger");
		menu3.add("Dornish Fries");
		
		menu4 = new HashSet<>(); // Jon knows nothing, keep it a secret
		menu4.add("Dornish Fries");
		menu4.add("Hot Dragon Burger");
		menu4.add("Fire Ale");
		menu4.add("Bloodburger");
		menu4.add("McDrogo");
		
		menu5 = new HashSet<>();
		menu5.add("Incested Fries");
		menu5.add("Dwarf Wine");
		menu5.add("Golden-Handed Burger");
		menu5.add("The Three Children");
		menu5.add("Valonqar's Hands");
		menu5.add("Light of The Burger - now with CHEESE");
		
		menu6 = new HashSet<>();
		menu6.add("Dornish Chicken");
		menu6.add("Chicken Ale");
		menu6.add("Salted Chicken");
		menu6.add("Extra Holy Chicken");
		menu6.add("Boiled Chicken");
		menu6.add("Chicken Chicken");
		menu6.add("SPECIAL : Chicken Attack");
		
		menuE = new HashSet<>(); // No Baratheons left :(
	}
	
	private static CartelDeNachos network;
	
	private void clearNetwork() {
		network = new CartelDeNachosImpl();
	}
	
	@Before
	public void setup() {
		clearNetwork();
	}
	
	private void registerBastards() throws ProfesorAlreadyInSystemException {
		jon = network.joinCartel(1, "Jon Snow");
		arya = network.joinCartel(2, "A girl has no name");
		daenerys = network.joinCartel(3, "Daenerys Stormborn of the House Targaryen, First of Her Name, the Unburnt, Queen of the Andals and the First Men, Khaleesi of the Great Grass Sea, Breaker of Chains, and Mother of Dragons");
		theHound = network.joinCartel(4, "Sandor Clegane");
		jorah = network.joinCartel(5, "Jorah Mormont");
		daario = network.joinCartel(6, "Daario Naharis");
		bran = network.joinCartel(7, "Bran Stark");
		theThreeEyedRaven = network.joinCartel(8, "The Three Eyed Raven");
		hodor = network.joinCartel(9, "Hodor Hodor");
		meera = network.joinCartel(10, "Meera Reed");
	}
	
	@Test
	public void test1_joinCartel() throws Exception {
		
		try {
			registerBastards();
		} catch (ProfesorAlreadyInSystemException e) {
			fail("Failed while adding new CasaDeBurritos!");
		}
		
		try {
			Profesor azorAhay = network.joinCartel(1, "Jon Snow"); // Hmm
			fail("Successfully added duplicate students!");
		} catch (ProfesorAlreadyInSystemException e) {
		}
		
		
		System.out.println("test1_joinCartel - V");
	}
	
	private void registerCasaDeBurritos() throws CasaDeBurritoAlreadyInSystemException {
		dornishBurgers = network.addCasaDeBurrito(1, "Dornish Burgers", 100, menu1);
		mcFreys = network.addCasaDeBurrito(2, "McFreys", 100, menu2);
		starkBurger = network.addCasaDeBurrito(3, "Starkburger", 400, menu3);
		theWallBurger = network.addCasaDeBurrito(4, "Burgers Beyond The Wall", 800, menu4);
		lannisterBurger = network.addCasaDeBurrito(5, "The Onion Knights", 0, menu5); // Technion = King's Landing, just because
		baratheonBurger = network.addCasaDeBurrito(6, "For The Night is Dark and Full of Burgers™", 70, menuE);
		bravosiChicken = network.addCasaDeBurrito(7, "BFC - Bravosi Fried Chicken", 800, menu6);
	}
	
	@Test
	public void test2_addCasaDeBurrito() throws Exception {
		try {
			registerCasaDeBurritos();
		} catch (CasaDeBurritoAlreadyInSystemException e) {
			fail("Failed while adding new CasaDeBurritos!");
		}
		
		try {
			registerCasaDeBurritos(); // Should throw
			fail("Successfully added duplicate CasaDeBurritos into the network!");
		} catch (CasaDeBurritoAlreadyInSystemException e) { /* Empty as Cersei's heart */ }
		
		System.out.println("test2_addCasaDeBurritos - V");
	}
	
	@Test
	public void test3_registeredProfesores() throws Exception {
		Profesor[] s1 = new Profesor[]{};
		assertArrayEquals(s1, network.registeredProfesores().stream().sorted().toArray());
		
		registerBastards();
		Profesor[] s2 = new Profesor[]{jon, arya, daenerys, theHound, jorah, daario, bran, theThreeEyedRaven, hodor, meera};
		assertArrayEquals(s2, network.registeredProfesores().stream().sorted().toArray());
		
		System.out.println("test3_registeredProfesores - V");
	}
	
	@Test
	public void test4_registeredCasasDeBurrito() throws Exception {
		
		CasaDeBurrito[] r1 = new CasaDeBurrito[]{};
		assertArrayEquals(r1, network.registeredCasasDeBurrito().stream().sorted().toArray());
		
		registerCasaDeBurritos();
		CasaDeBurrito[] r2 = new CasaDeBurrito[]{dornishBurgers, mcFreys, starkBurger, theWallBurger, lannisterBurger, baratheonBurger, bravosiChicken};
		assertArrayEquals(r2, network.registeredCasasDeBurrito().stream().sorted().toArray());
		
		System.out.println("test4_registeredCasasDeBurrito - V");
	}
	
	@Test
	public void test5_getProfesor() throws Exception {
		
		registerBastards();
		registerCasaDeBurritos();
		
		assertEquals(jon, network.getProfesor(1));
		
		try {
			network.getProfesor(-1);
			network.getProfesor(19);
		} catch (ProfesorNotInSystemException e) {
		
		}
		
		Profesor cersei = network.joinCartel(20, "Cersei Lannister");
		assertEquals(cersei, network.getProfesor(20));
		
		try {
			network.getCasaDeBurrito(20); // Just making sure a new student is added to the students collection only
			fail();
		} catch (CasaDeBurritoNotInSystemException e) {
			//Good
		}
		
		System.out.println("test5_getProfesor - V");
	}
	
	@Test
	public void test6_getCasaDeBurrito() throws Exception {
		
		registerBastards();
		registerCasaDeBurritos();
		
		assertNotEquals(jon, network.getCasaDeBurrito(1)); // Jon Snow can't be a CasaDeBurrito
		assertEquals(starkBurger, network.getCasaDeBurrito(3));
		
		try {
			network.getCasaDeBurrito(-1);
			network.getCasaDeBurrito(19);
		} catch (CasaDeBurritoNotInSystemException e) {
		
		}
		
		CasaDeBurrito reekOmmended = network.addCasaDeBurrito(20, "ReekOmmended - 100% more organs!", 340, menuE); // You really don't want to know what is the menu...
		assertEquals(reekOmmended, network.getCasaDeBurrito(20));
		
		try {
			network.getProfesor(20); // Just making sure a new CasaDeBurrito is added to the CasaDeBurritos collection only
			fail();
		} catch (ProfesorNotInSystemException e) {
			//Good
		}
		
		System.out.println("test6_getCasaDeBurrito - V");
	}
	
	private void makeFriends() throws Exception {
		network.addConnection(jon, arya);
		network.addConnection(jon, bran);
		network.addConnection(arya, theHound);
		network.addConnection(arya, bran);
		network.addConnection(bran, theThreeEyedRaven);
		network.addConnection(bran, hodor);
		network.addConnection(bran, meera);
		network.addConnection(meera, theThreeEyedRaven);
		network.addConnection(meera, hodor);
		network.addConnection(daenerys, daario);
		network.addConnection(daenerys, jorah); // Only friends
	}
	
	@Test
	public void test7_addConnection() throws Exception {
		
		registerBastards();
		registerCasaDeBurritos();
		
		try {
			Profesor tyrion = new ProfesorImpl(35, "Tyrion-Badass-Lannister");
			network.addConnection(daenerys, tyrion);
			network.addConnection(tyrion, tyrion); // StudentNotInSystem has higher priority
			fail();
		} catch (ProfesorNotInSystemException e) {
		}
		
		try {
			Profesor azorAhay = new ProfesorImpl(1, "Jon Snow");
			network.addConnection(jon, azorAhay);
			fail();
		} catch (SameProfesorException e) {
		}
		
		try {
			try {
				network.addConnection(arya, jon); // Successfully make Arya and Jon friends
			} catch (Exception e) {
				fail();
			}
			network.addConnection(arya, jon);
			network.addConnection(jon, arya); // Friendship is symmetric, Arya is Jon's friend and Jon is Arya's friend
			fail();
		} catch (ConnectionAlreadyExistsException e) {
		
		}
		
		clearNetwork();
		registerBastards();
		
		try {
			makeFriends();
		} catch (Exception e) { // Nothing should go wrong
			fail();
		}
		
		System.out.println("test7_addConnection - V");
	}

	@Test
	public void test8_favoritesByRating() throws Exception {
		registerBastards();
		registerCasaDeBurritos();
		makeFriends();
		
		Profesor mycah = new ProfesorImpl(25, "Alive Mycah");
		try {
			network.favoritesByRating(mycah);
			fail();
		} catch (ProfesorNotInSystemException e) {
		
		}
		
		mycah = network.joinCartel(25, "Dead Mycah");
		network.addConnection(mycah, arya);
		network.addConnection(mycah, theHound);
		
		CasaDeBurrito[] CasaDeBurritoArray1 = new CasaDeBurrito[]{};
		assertArrayEquals(CasaDeBurritoArray1, network.favoritesByRating(mycah).toArray()); // Mycah is only friend to arya and the hound
		
		bravosiChicken.rate(theHound, 5)
				.rate(arya, 3);
		theHound.favorite(bravosiChicken);
		arya.favorite(bravosiChicken);
		CasaDeBurritoArray1 = new CasaDeBurrito[]{bravosiChicken};
		// assertArrayEquals(CasaDeBurritoArray1, network.favoritesByRating(mycah).toArray()); // Duplicate copies should be removed
		
		clearNetwork();
		registerCasaDeBurritos();
		registerBastards();
		makeFriends();
		
		bravosiChicken.rate(theHound, 5)
				.rate(arya, 3);
		
		starkBurger.rate(theHound, 1);
		mcFreys.rate(theHound, 1);
		dornishBurgers.rate(theHound, 1);
		theWallBurger.rate(theHound, 1);
		
		theHound.favorite(bravosiChicken)
				.favorite(starkBurger)
				.favorite(mcFreys)
				.favorite(dornishBurgers)
				.favorite(theWallBurger);
		
		arya.favorite(bravosiChicken);
		
		CasaDeBurrito[] CasaDeBurritoArray2 = new CasaDeBurrito[]{bravosiChicken, dornishBurgers, mcFreys, starkBurger, theWallBurger};
		
		assertArrayEquals(CasaDeBurritoArray2, network.favoritesByRating(arya).toArray());
		assertArrayEquals(CasaDeBurritoArray1, network.favoritesByRating(theHound).toArray()); // Should contain only arya's favorites
		
		starkBurger.rate(jon, 5); // 5 out of 5, would come again
		jon.favorite(starkBurger); // The feelings :'(
		
		CasaDeBurrito[] CasaDeBurritoArray3 = new CasaDeBurrito[]{starkBurger, bravosiChicken, dornishBurgers, mcFreys, theWallBurger}; // Jon comes before the hound (jon.id < theHound.id)
		assertArrayEquals(CasaDeBurritoArray3, network.favoritesByRating(arya).toArray()); // Now Jon has some favorites
		
		theWallBurger.rate(bran, 4);
		bran.favorite(theWallBurger);
		
		CasaDeBurrito[] CasaDeBurritoArray4 = new CasaDeBurrito[]{starkBurger, bravosiChicken};
		assertArrayEquals(CasaDeBurritoArray4, network.favoritesByRating(bran).toArray()); // Only friend's favorites
		
		
		bravosiChicken.rate(daenerys, 2) // Too much oil and slaves
				.rate(daario, 5)
				.rate(jorah, 2); // Maybe now she will notice me
		daenerys.favorite(bravosiChicken);
		
		dornishBurgers.rate(daario, 1); // Sand burger? nah
		
		lannisterBurger.rate(jorah, 5); // Sneaky spy
		jorah.favorite(bravosiChicken).favorite(lannisterBurger);
		daario.favorite(bravosiChicken).favorite(dornishBurgers);
		
		CasaDeBurrito[] CasaDeBurritoArray5 = new CasaDeBurrito[]{lannisterBurger, bravosiChicken, dornishBurgers};
		assertArrayEquals(CasaDeBurritoArray5, network.favoritesByRating(daenerys).toArray());
		
		System.out.println("test8_favoritesByRating - V");
	}

	@Test
	public void test9_0_favoritesByDist() throws Exception {
		registerBastards();
		registerCasaDeBurritos();
		makeFriends();
		
		Profesor mycah = new ProfesorImpl(25, "Alive Mycah");
		try {
			network.favoritesByDist(mycah);
			fail();
		} catch (ProfesorNotInSystemException e) {
		
		}
		
		mycah = network.joinCartel(25, "Dead Mycah");
		network.addConnection(mycah, arya);
		network.addConnection(mycah, theHound);
		
		CasaDeBurrito[] CasaDeBurritoArray1 = new CasaDeBurrito[]{};
		assertArrayEquals(CasaDeBurritoArray1, network.favoritesByDist(mycah).toArray()); // Mycah is only friend to arya and the hound
		
		bravosiChicken.rate(theHound, 5)
				.rate(arya, 3);
		theHound.favorite(bravosiChicken);
		arya.favorite(bravosiChicken);
		CasaDeBurritoArray1 = new CasaDeBurrito[]{bravosiChicken};
		// assertArrayEquals(CasaDeBurritoArray1, network.favoritesByDist(mycah).toArray()); // Duplicate copies are removed
		
		clearNetwork();
		registerCasaDeBurritos();
		registerBastards();
		makeFriends();
		
		bravosiChicken.rate(theHound, 5)
				.rate(arya, 3);
		
		starkBurger.rate(theHound, 1);
		mcFreys.rate(theHound, 1);
		dornishBurgers.rate(theHound, 1);
		theWallBurger.rate(theHound, 1);
		
		theHound.favorite(bravosiChicken)
				.favorite(starkBurger)
				.favorite(mcFreys)
				.favorite(dornishBurgers)
				.favorite(theWallBurger);
		
		arya.favorite(bravosiChicken);
		
		CasaDeBurrito[] CasaDeBurritoArray2 = new CasaDeBurrito[]{dornishBurgers, mcFreys, starkBurger, bravosiChicken, theWallBurger};
		
		assertArrayEquals(CasaDeBurritoArray2, network.favoritesByDist(arya).toArray());
		assertArrayEquals(CasaDeBurritoArray1, network.favoritesByDist(theHound).toArray()); // Should contain only arya's favorites
		
		starkBurger.rate(jon, 5); // 5 out of 5, would come again
		jon.favorite(starkBurger); // The feelings :'(
		
		CasaDeBurrito[] CasaDeBurritoArray3 = new CasaDeBurrito[]{starkBurger, dornishBurgers, mcFreys, bravosiChicken, theWallBurger}; // Jon comes before the hound (jon.id < theHound.id)
		assertArrayEquals(CasaDeBurritoArray3, network.favoritesByDist(arya).toArray()); // Now Jon has some favorites
		
		theWallBurger.rate(bran, 4);
		bran.favorite(theWallBurger);
		
		CasaDeBurrito[] CasaDeBurritoArray4 = new CasaDeBurrito[]{starkBurger, bravosiChicken};
		assertArrayEquals(CasaDeBurritoArray4, network.favoritesByDist(bran).toArray()); // Only friend's favorites
		
		
		bravosiChicken.rate(daenerys, 2) // Too much oil and slaves
				.rate(daario, 5)
				.rate(jorah, 2); // Maybe now she will notice me
		daenerys.favorite(bravosiChicken);
		
		dornishBurgers.rate(daario, 1); // Sand burger? nah
		
		lannisterBurger.rate(jorah, 5); // Sneaky spy
		jorah.favorite(bravosiChicken).favorite(lannisterBurger);
		daario.favorite(bravosiChicken).favorite(dornishBurgers);
		
		CasaDeBurrito[] CasaDeBurritoArray5 = new CasaDeBurrito[]{lannisterBurger, bravosiChicken, dornishBurgers};
		assertArrayEquals(CasaDeBurritoArray5, network.favoritesByDist(daenerys).toArray());
		
		System.out.println("test9_0_favoritesByDist - V");
	}

	@Test
	public void test9_1_getRecommendation() throws Exception {
		
		registerBastards();
		registerCasaDeBurritos();
		makeFriends();
		
		network.addConnection(theHound, jon); // wat
		
		dornishBurgers.rate(daario, 4);
		mcFreys.rate(arya, 1);
		theWallBurger.rate(jon, 4).rate(meera, 3).rate(bran, 5).rate(hodor, 1);
		starkBurger.rate(jon, 5).rate(arya, 4).rate(bran, 4).rate(hodor, 3);
		lannisterBurger.rate(jorah, 3);
		bravosiChicken.rate(theHound, 5).rate(arya, 3);
		
		daario.favorite(dornishBurgers);
		arya.favorite(mcFreys).favorite(starkBurger);
		jon.favorite(theWallBurger).favorite(starkBurger);
		meera.favorite(theWallBurger);
		bran.favorite(theWallBurger).favorite(starkBurger);
		hodor.favorite(theWallBurger).favorite(starkBurger);
		jorah.favorite(lannisterBurger);
		theHound.favorite(bravosiChicken);
		
		try{
			Profesor azorAhay = new ProfesorImpl(30, "Jon Snow");
			network.getRecommendation(azorAhay, dornishBurgers, 100);
			fail("Should throw! azorAhay not in system!");
		}
		catch(ProfesorNotInSystemException e){
		
		}
		
		try{
			CasaDeBurrito khalBurger = new CasaDeBurritoImpl(30, "Khal Burger", 1200 , menuE);
			network.getRecommendation(jon, khalBurger, 100);
			fail("Should throw! khalBurger not in system!");
		}
		catch(CasaDeBurritoNotInSystemException e){
		
		}
		
		try{
			network.getRecommendation(jon, starkBurger, -1);
			fail("t is negative!");
		}
		catch(ImpossibleConnectionException e){
		
		}
		
		try{
			assertTrue(network.getRecommendation(jon, starkBurger, 0));
			assertTrue(network.getRecommendation(jon, bravosiChicken, 1)); // Jon has two ways to get to bravosiChicken - straight from The Hound or via Arya
			assertFalse(network.getRecommendation(arya, theWallBurger, 0));
			assertTrue(network.getRecommendation(arya, theWallBurger, 1));
			assertFalse(network.getRecommendation(arya, lannisterBurger, Integer.MAX_VALUE));
			assertFalse(network.getRecommendation(daenerys, dornishBurgers, 0));
			assertTrue(network.getRecommendation(daenerys, dornishBurgers, 1));
			
			assertTrue(network.getRecommendation(theHound, theWallBurger, 1));
			assertTrue(network.getRecommendation(theHound, theWallBurger, 2));
			
			CasaDeBurrito hodorHodors = network.addCasaDeBurrito(30, "Hodor Hodor Hodor's Hodors", 10000, menuE);
			hodorHodors.rate(hodor, 5);
			hodor.favorite(hodorHodors);
			
			assertTrue(network.getRecommendation(arya, hodorHodors, 2));
			assertFalse(network.getRecommendation(arya, hodorHodors, 1));
			
		}
		catch(Exception e){
			String s = e.getMessage();
			fail("Something is messed up - input is ok, but " + e.getMessage() + " is thrown");
		}
		
		System.out.println("test9_1_getRecommendation - V");
	}
	
	@Test
	public void test9_2_toString() throws Exception {
		String empty = "Registered profesores: .\nRegistered casas de burrito: .\nProfesores:\nEnd profesores.";
		assertEquals(empty, network.toString());
		
		String profesoresOnly = "Registered profesores: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10.\nRegistered casas de burrito: .\nProfesores:\n1 -> [].\n2 -> [].\n3 -> [].\n4 -> [].\n5 -> [].\n6 -> [].\n7 -> [].\n8 -> [].\n9 -> [].\n10 -> [].\nEnd profesores.";
		registerBastards();
		assertEquals(profesoresOnly, network.toString());
		
		
		String CasaDeBurritosOnly = "Registered profesores: .\nRegistered casas de burrito: 1, 2, 3, 4, 5, 6, 7.\nProfesores:\nEnd profesores.";
		clearNetwork();
		registerCasaDeBurritos();
		assertEquals(CasaDeBurritosOnly, network.toString());
		
		String noFriendsYet = "Registered profesores: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10.\nRegistered casas de burrito: 1, 2, 3, 4, 5, 6, 7.\nProfesores:\n1 -> [].\n2 -> [].\n3 -> [].\n4 -> [].\n5 -> [].\n6 -> [].\n7 -> [].\n8 -> [].\n9 -> [].\n10 -> [].\nEnd profesores.";
		registerBastards();
		assertEquals(noFriendsYet, network.toString());
		
		String allTheThings = "Registered profesores: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10.\nRegistered casas de burrito: 1, 2, 3, 4, 5, 6, 7.\nProfesores:\n1 -> [2, 7].\n2 -> [1, 4, 7].\n3 -> [5, 6].\n4 -> [2].\n5 -> [3].\n6 -> [3].\n7 -> [1, 2, 8, 9, 10].\n8 -> [7, 10].\n9 -> [7, 10].\n10 -> [7, 8, 9].\nEnd profesores.";
		makeFriends();
		assertEquals(allTheThings, network.toString());
		
		System.out.println("test9_2_toString - V");
		
	}
	
	@AfterClass
	public static void endCredits(){
		System.out.println("---SUCCESS, and remember, Burgers are coming---");
	}
	
}