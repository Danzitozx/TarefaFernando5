/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.package1.calculadora;

import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author okmen
 */
public class Calculadora {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe o tipo da notacao infixa, posfixa ou prefixa:");
        String tipo = entrada.nextLine();
        System.out.println("Digite a expressao:");
        String exp = entrada.nextLine().replace(" ", "");
        
        String infixa = "", posfixa = "", prefixa = "";
        int resultado = 0;
        
        if (tipo.equalsIgnoreCase("infixa")) {
            infixa = exp;
            posfixa = paraPosfixa(exp);
            prefixa = paraPrefixa(exp);
            resultado = calcularPosfixa(posfixa);
        } else if (tipo.equalsIgnoreCase("posfixa")) {
            posfixa = exp;
            infixa = posfixaParaInfixa(exp);
            prefixa = paraPrefixa(infixa);
            resultado = calcularPosfixa(posfixa);
        } else if (tipo.equalsIgnoreCase("prefixa")) {
            prefixa = exp;
            infixa = prefixaParaInfixa(exp);
            posfixa = paraPosfixa(infixa);
            resultado = calcularPrefixa(prefixa);
        }

        System.out.println("Resultado: " + resultado);
        System.out.println("Infixa: " + infixa);
        System.out.println("Pos fixa: " + posfixa);
        System.out.println("Pre fixa: " + prefixa);
    }

    static String paraPosfixa(String e) {
        Stack<Character> ops = new Stack<>();
        StringBuilder saida = new StringBuilder();
        for (char c : e.toCharArray()) {
            if (Character.isDigit(c)) saida.append(c);
            else if (c == '(') ops.push(c);
            else if (c == ')') {
                while (ops.peek() != '(') saida.append(ops.pop());
                ops.pop();
            } else {
                while (!ops.isEmpty() && prioridade(ops.peek()) >= prioridade(c)) saida.append(ops.pop());
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) saida.append(ops.pop());
        return saida.toString();
    }

    static String paraPrefixa(String e) {
        StringBuilder rev = new StringBuilder(e).reverse();
        for (int i = 0; i < rev.length(); i++) {
            if (rev.charAt(i) == '(') rev.setCharAt(i, ')');
            else if (rev.charAt(i) == ')') rev.setCharAt(i, '(');
        }
        String pos = paraPosfixa(rev.toString());
        return new StringBuilder(pos).reverse().toString();
    }

    static int calcularPosfixa(String exp) {
        Stack<Integer> st = new Stack<>();
        for (char c : exp.toCharArray()) {
            if (Character.isDigit(c)) st.push(c - '0');
            else {
                int b = st.pop(), a = st.pop();
                if (c == '+') st.push(a + b);
                else if (c == '-') st.push(a - b);
                else if (c == '*') st.push(a * b);
                else if (c == '/') st.push(a / b);
            }
        }
        return st.pop();
    }

    static int calcularPrefixa(String exp) {
        Stack<Integer> st = new Stack<>();
        for (int i = exp.length() - 1; i >= 0; i--) {
            char c = exp.charAt(i);
            if (Character.isDigit(c)) st.push(c - '0');
            else {
                int a = st.pop(), b = st.pop();
                if (c == '+') st.push(a + b);
                else if (c == '-') st.push(a - b);
                else if (c == '*') st.push(a * b);
                else if (c == '/') st.push(a / b);
            }
        }
        return st.pop();
    }

    static String posfixaParaInfixa(String exp) {
        Stack<String> s = new Stack<>();
        for (char c : exp.toCharArray()) {
            if (Character.isDigit(c)) s.push(c + "");
            else {
                String b = s.pop(), a = s.pop();
                s.push("(" + a + c + b + ")");
            }
        }
        return s.pop();
    }

    static String prefixaParaInfixa(String exp) {
        Stack<String> s = new Stack<>();
        for (int i = exp.length() - 1; i >= 0; i--) {
            char c = exp.charAt(i);
            if (Character.isDigit(c)) s.push(c + "");
            else {
                String a = s.pop(), b = s.pop();
                s.push("(" + a + c + b + ")");
            }
        }
        return s.pop();
    }

    static int prioridade(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }
}

    

