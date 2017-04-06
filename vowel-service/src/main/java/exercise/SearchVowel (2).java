package com.buscape.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchVowel implements Stream {

	// deve buscar a primeira vogal após uma consoante, que tem um antecessor uma vogal, onde a vogal antecessora não se repita no restante
	
	private String word;
	
	public SearchVowel() {	}
	
	public SearchVowel(String world) {
		this.setWord(world);
	}
	
	/**
	 * retorna proximo caracter a ser processado
	 */
	@Override
	public char getNext() {
		return this.getWord().charAt(1);
	}

	/**
	 * retorna se ainda possui caracteres para processamento
	 */
	@Override
	public boolean hasNext() {
		return this.getWord().isEmpty();
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
	public static void main(String[] args) {
		Map<Character, Integer> teste = new HashMap<>();
		teste.put('a', 0);
		teste.put('e', 1);
		
		Set<Character> set = teste.keySet();
		Collection<Integer> lista = teste.values();
		System.out.println(lista.toArray()[0]);
		System.err.println(set.toArray()[0]);
		
		
		
		String testWord = "aaadeweVbneyUttOoni"; // deve retornar o U
		
		String aux = testWord.toLowerCase();
		SearchVowel searchVowel = new SearchVowel(aux); 
		
		// estrutura 2 = mapa
		// estrutura 1 = lista (?)
		
		// leitura da palavra enquanto houver letras
			// verifica se é vogal 
				// grava na esrtutura 1
			// caso seja consoante 
				// pega o próximo caracter
				// verifica se é vogal e se é uma nova (diferente das já gravadas anteriormente na estrutura 1)
				// caso positivo
					// grava na esrtutura 2
				// caso seja repitida 
					// remove a mesma da estrutura 2  
		// vai para o proximo
		// no fim pega o "primeiro" registro (ou seja o "mais velho") da estrutura 2
			
		
		Map<Character, Integer> map = new HashMap<>();
		List<Character> list = new ArrayList<>();
		List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');
		Character charAux = null;
		Character charNext = null;
		int count = 0;
		
		while(searchVowel.hasNext()) {
			charAux = aux.charAt(0);
			
			if(vowels.contains(charAux)) {
				list.add(charAux);
				charAux = ' ';
			} else {
				charNext = searchVowel.getNext();
				if(vowels.contains(charNext)) {
					if (!list.contains(charNext)) {
						map.put(charNext, count);
						count++;
					} else {
						map.remove(charNext);
					}
				}
				charNext = ' ';
			}
			
			aux = aux.substring(1);
			searchVowel.setWord(aux);
		}
		
		
		System.out.println("ORIGINAL:" + testWord);
		// varrer no mapa e retornar o de menor valor
		map.keySet();
		map.values();
		
		
		
//		Character result = null;
//		String aux = testWord.toLowerCase();
//		
//		int vowel = 0;
//		int a = 0;
//		int e = 0;
//		int i = 0;
//		int o = 0;
//		int u =0;
//		int consonant = 0;
//		
//		List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');
//		Character letra;
////		List<Character> charList = new ArrayList<>();
//		
//		// loop varrendo em quanto houver next no AUX
//		while(searchVowel.hasNext()) {
//			letra = aux.charAt(0);
//			
//			if(vowels.contains(letra)) {
//				vowel++;
//				if(letra.equals('a')) a++;
//				if(letra.equals('e')) e++;
//				if(letra.equals('i')) i++;
//				if(letra.equals('o')) o++;
//				if(letra.equals('u')) u++;
//			}
//			
//			
//			
//			// caso não econtrado, fazer substring retirando o primeiro caracter
//			aux = aux.substring(1, aux.length());
//			searchVowel = new SearchVowel(aux);
//		}
//		
//		System.out.println("ENCONTRADO: " + searchVowel.serachFirstInCondition(testWord));
//		System.out.println("ENCONTRADO: " + result);
		
	}
	
}
