/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lfa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

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

    public void getTableAFND(String alfabeto) {

        System.out.println("\n");
        ArrayList<String> alfa = new ArrayList<>(Arrays.asList(alfabeto.split("[,}{]")));

        System.out.printf("AFND");
        for (String letra : alfa) {
            System.out.format("%6s  ", letra);
        }
    }

    public void getTableAFD() {

    }

//    public ArrayList<String> recursiva(Map<String, String> transicoes, String word, String state, ArrayList<String> fecho) {
//        String move = doMove(transicoes, state, word);
//        if (fecho.indexOf(move) == -1) {
//            fecho.add(move);
//            state = move;
//        } else {
//            return fecho;
//        }
//        return recursiva(transicoes, word, state, fecho);
//    }
    public void fechoLambda(Map<String, String> transicoes, String states) {

        // lambda
        String word = ".";

        states = states.replaceAll(" ", "");
        states = states.replaceAll("\\{", "");
        states = states.replaceAll("\\}", "");

        // pega o conjunto de estados
        String[] estados = states.split(",");

        // para cada estado
        for (String estado : estados) {

            // crie uma lista contendo o fecho lambda
            CopyOnWriteArrayList<String> fecho = new CopyOnWriteArrayList<>();

            // adicione o proprio estado
            fecho.add(estado);

            // fecho = recursiva(transicoes, word, doMove(transicoes, estado, word), fecho);
            while (!doMove(transicoes, estado, word).isEmpty()) {
                String estate = doMove(transicoes, estado, word);
                String[] possibleMoves = estate.split(",");

                if (possibleMoves.length == 1) {
                    if (fecho.indexOf(estate) == -1) {
                        fecho.add(estate);
                        estado = estate;
                    } else {
                        break;
                    }
                } else {

                    // computar a cadeia de cada estado
                    for (String element : possibleMoves) {
                       
                    }

                }

                System.out.println("");
                System.out.printf("fecho para " + fecho.get(0) + "-> ");
                for (String string : fecho) {
                    System.out.printf(string + " ");
                }
                System.out.println("");
            }

        }}

    

    public void converteAFND() {

    }

    // realiza movimentos
    public void mount(String[] funcoes, String estados, String alfabeto) {

        // mapa das transicoes
        Map<String, String> transicoes = new HashMap<>();

        // mapeia os estados para as transicoes e simbolos
        for (String funcao : funcoes) {
            String[] result = funcao.split("->");
            //        String estate = "q2";

            result[1] = result[1].replaceAll("\\{", "");
            result[1] = result[1].replaceAll("\\}", "");

            transicoes.put(result[0], result[1]);
        }

        // pega a tabela do lambda
        getTableLambda(estados, transicoes, alfabeto);

        //pega o fecho do lambda
        fechoLambda(transicoes, estados);

        // tabela do AFND
        getTableAFND(alfabeto);

        // tabela do AFD
        getTableAFD();

    }

}
