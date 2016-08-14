/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lfa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pvsousalima
 */
public class AutomataParser {

    // remove a ultima ocorrencia de alguma substring
    public String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1) {
            return string;
        }
        return string.substring(0, index) + replacement + string.substring(index + substring.length());
    }

    // obtem os estados de um automato definido
    public String[] getStates(ArrayList<String> automato) {

        System.out.println("");

        // remove a primeira e a ultima ocorrencia do conjunto
        automato.set(2, automato.get(2).replaceFirst("\\{", ""));
        automato.set(2, replaceLast(automato.get(2), "}", ""));

        // pega os estados
        String[] estados = automato.get(2).split("(?<=[\\}]),");

        return estados;
    }

    // obtem os estados de um automato definido
    public void getTableLambda(String estados, Map<String, String> transicoes, String alfabeto) {

        ArrayList<String> alfa = new ArrayList<>(Arrays.asList(alfabeto.split("[,}{]")));
        alfa.add(".");

        System.out.printf("AFND−λ");
        for (String letra : alfa) {
            if (letra.equals(".")) {
                System.out.format("%6s", "λ");
            } else {
                System.out.format("%5s  ", letra);
            }
        }

        for (String estado : estados.split("[,}{]")) {
            System.out.printf(estado);
            for (String letra : alfa) {
                String walk = doMove(transicoes, estado, letra);
                System.out.format("%8s", walk);
            }
            System.out.println("");
        }

    }

    // mapeia o movimento para o retorno e pega o destino do movimento
    public String doMove(Map<String, String> transicoes, String state, String word) {
        String destination = "";
        for (Entry<String, String> e : transicoes.entrySet()) {
            if (e.getKey().contains("(" + state + "," + word + ")")) {
                destination = transicoes.get(e.getKey());
            }
        }
        return destination;
    }

    // realiza movimentos
    public void move(String[] funcoes, String estados, String alfabeto) {

        // mapa das transicoes
        Map<String, String> transicoes = new HashMap<>();

        // mapeia os estados para as transicoes e simbolos
        for (String funcao : funcoes) {
            String[] result = funcao.split("->");
            transicoes.put(result[0], result[1]);
        }

        // pega a tabela do lambda
        getTableLambda(estados, transicoes, alfabeto);
    }

}
