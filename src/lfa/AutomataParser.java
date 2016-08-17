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

    // Pega o fecho lambda 
    public void getLambda(Map<String, String> transicoes, ArrayList<String> fecho, String state) {
        if (!fecho.contains(state)) {
            if (state.isEmpty()) {
                fecho.add(state);
            } else {
                fecho.add(state);
                String[] t = state.split(",");
                for (String a : t) {
                    getLambda(transicoes, fecho, doMove(transicoes, a, "."));
                }
            }
        }
    }

    // formata a saida do lambda
    public ArrayList<String> formatLambda(ArrayList<String> fecho) {
        ArrayList<String> l2 = new ArrayList<>();

        fecho.stream().forEach((state) -> {
            for (String e : state.split(",")) {
                if (!l2.contains(e)) {
//                    e = e.replaceAll("q", "");
                    l2.add(e);
                }
            }
        });

        return l2;

    }

    // formata a saida do lambda
    public CopyOnWriteArrayList<String> formatFecho(CopyOnWriteArrayList<String> fecho) {
        CopyOnWriteArrayList<String> l2 = new CopyOnWriteArrayList<>();

        fecho.stream().forEach((state) -> {
            for (String e : state.split(",")) {
                if (!l2.contains(e)) {
//                    e = e.replaceAll("q", "");
                    l2.add(e);
                }
            }
        });

        return l2;

    }

    // retorna o fecho lambda como um hashmap
    public Map<String, ArrayList<String>> fechoLambda(Map<String, String> transicoes, String states) {

        // formata os estados
        String[] estados = formateStates(states);

        Map<String, ArrayList<String>> fechoHash = new HashMap<>();

        System.out.println("");
        // para cada estado
        for (String estado : estados) {

            // crie uma lista contendo o fecho lambda
            ArrayList<String> fecho = new ArrayList<>();

            // pega o fecho lambda
            getLambda(transicoes, fecho, estado);

            // format lambda
            fecho = formatLambda(fecho);

            // ordena a lista
            fecho.sort((String estado1, String estado2) -> estado1.compareTo(estado2));

            System.out.printf("Fecho para: " + estado + "-> ");
            for (String el : fecho) {
                System.out.printf(el + " ");
            }
            System.out.println("");

            // salva o fecho relativo ao 
            fechoHash.put(estado, fecho);
        }

        return fechoHash;

    }

    // formata uma string de estados para um cojunto de estados respectivo
    public String[] formateStates(String states) {

        // formata o conjunto de estados
        states = states.replaceAll(" ", "");
        states = states.replaceAll("\\{", "");
        states = states.replaceAll("\\}", "");

        // pega o conjunto de estados
        String[] estados = states.split(",");
        return estados;
    }

    // formata uma string de estados para um cojunto de estados respectivo
    public String[] formateAlfabeto(String alfabeto) {

        // formata o conjunto de estados
        alfabeto = alfabeto.replaceAll(" ", "");
        alfabeto = alfabeto.replaceAll("\\{", "");
        alfabeto = alfabeto.replaceAll("\\}", "");

        // pega o conjunto de estados
        String[] words = alfabeto.split(",");
        return words;
    }

    public void getMoveFecho(Map<String, ArrayList<String>> hashLambda, Map<String, String> transicoes, String est, String word, CopyOnWriteArrayList<String> result) {

        if (est.isEmpty()) {
            result.clear();
        } else {

            String mov = doMove(transicoes, est, word);

            // pega os estados
            String[] moves = mov.split(",");

            // para cada transicao pegamos o fecho lambda
            for (String move : moves) {

                // fecho do estado
                ArrayList<String> fechEst = hashLambda.get(move);

                if (fechEst != null) {
                    for (String fech : fechEst) {
                        result.add(fech);
                    }
                }

            }
        }

    }

    // converte para AFND
    public void converteAFND(Map<String, String> transicoes, Map<String, ArrayList<String>> hashLambda, String states, String alfabeto) {

        // pega o alfabeto formatado
        String[] words = formateAlfabeto(alfabeto);

        // pega os estados do automato
        String[] estados = formateStates(states);

        for (String estado : estados) {

            // pega o fecho do estado
            ArrayList<String> fechoEstado = hashLambda.get(estado);

            // pega o resultado 
            CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();

            // array de marcacoes para pegar os fechos lambda
            ArrayList<String> marca = new ArrayList<>();

            // para cada palavra
            for (String word : words) {

                // para cada estado no fecho marcamos o estado no array de marcacao
                for (String est : fechoEstado) {
                    marca.add(est);
                }

                // para cada estado marcado verificamos a sua transicao para a palavra
                for (String est : marca) {
                    getMoveFecho(hashLambda, transicoes, est, word, result);
                }

                result = formatFecho(result);

                System.out.println("Estado:" + estado);
                System.out.printf("Transicao:" + word);
                System.out.println(result);
                System.out.println("");

            }

        }

    }

    // realiza movimentos
    public void mount(String[] funcoes, String estados, String alfabeto) {

        // mapa das transicoes
        Map<String, String> transicoes = new HashMap<>();

        // mapeia os estados para as transicoes e simbolos
        for (String funcao : funcoes) {
            String[] result = funcao.split("->");
            result[1] = result[1].replaceAll("\\{", "");
            result[1] = result[1].replaceAll("\\}", "");
            transicoes.put(result[0], result[1]);
        }

        // pega a tabela do lambda
        getTableLambda(estados, transicoes, alfabeto);

        //pega o fecho do lambda
        Map<String, ArrayList<String>> hashLambda = fechoLambda(transicoes, estados);

        // converte para AFND
        converteAFND(transicoes, hashLambda, estados, alfabeto);

//        // tabela do AFND
//        getTableAFND(alfabeto);
//
//        // tabela do AFD
//        getTableAFD();
    }

}
