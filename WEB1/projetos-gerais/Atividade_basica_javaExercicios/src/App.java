import javax.print.event.PrintEvent;
import java.util.Scanner;

public class App {
    public static void main(String[] args)  {
        
    /*
        Exercicio 01

        int idade = 18;
            if(idade>=18){
        System.out.println("É maior de idade");
            }else{
        System.out.println("é menor de idade");
    }
    */

    /*
        Exercicio 02
        Produto p1 = new Produto("Batman", "Brinquedo" ,5.0 , 5);
        p1.ExtratoProduto();

    */

    /*
    int fat = 1;

    for(int i = 1; i <= 5 ; i++){
        fat *= i;
        System.out.println("Valor do Fatorial :" +  fat);
    }

    System.out.println("Definição do valor total Fat :" + fat);
     */

    /*
    Aluno l1 = new Aluno();
    Aluno l2 = new Aluno();

    l1.Aprovado(7, 9);
    l1.conceito("Gu");

    l2.Aprovado(2,9);
    l2.conceito("jonas");
     */


    /*
    Exercicio 05
    int maior = 0;
    int menor = 0;


    for (int i = 1; i <= 10; i ++){
        if(maior < i){
            maior = i;
        }
        if(menor > i){
            menor = i;
        }
    }

        System.out.println("Maior Valor:" + maior);
        System.out.println("Menor Valor:"+ menor);
     */

    /*
    ContaBancaria b1 = new ContaBancaria();
    b1.depositar(100);
    b1.depositar(1000);
    b1.sacar(999);
    
    System.out.println(b1.saldoAtual());
     */


    /*
    Scanner scan = new Scanner(System.in);

    System.out.println("Escolha uma das Opções abaixo:\n1- Cadastrar\n2-Excluir\n3-Consultar\n4-Atualizar");




    int op = scan.nextInt();

    switch(op){
        case 1:
            System.out.println("Cadastrar");
            break;
        case 2:
            System.out.println("Excluir");
            break;
        case 3:
            System.out.println("Consultar");
            break;
        case 4:
            System.out.println("Atualizar");
            break;
        default:
            System.out.println("Operação invalida");
            break;


    }
     */
    
    /*
    Retangulo r1 = new Retangulo();
    r1.area(5 , 6);
    r1.perimetro(6 , 1);
    System.out.println(r1.area(5 , 6));
    System.out.println(r1.perimetro(6 , 1));
     */

        /*
            Exercicio 09

        Scanner scan = new Scanner(System.in);

        int inicio, fim;
        int contador = 0;

        System.out.print("Digite o início do intervalo: ");
        inicio = scan.nextInt();

        System.out.print("Digite o final do intervalo: ");
        fim = scan.nextInt();

        int i = inicio;

        while (i <= fim) {

            if (i % 2 != 0) {
                System.out.println("Número ímpar: " + i);
                contador++;
            }

            i++;
        }

        System.out.println("Total de números ímpares: " + contador);
    */
    /*
    Scanner scan = new Scanner(System.in);

        Calculadora c1 = new Calculadora();

        System.out.println("Escolha uma das operações abaixo:");
        System.out.println("1 - Soma");
        System.out.println("2 - Subtração");
        System.out.println("3 - Divisão");
        System.out.println("4 - Multiplicação");

        int op = scan.nextInt();

        System.out.print("Digite o primeiro número: ");
        int n1 = scan.nextInt();

        System.out.print("Digite o segundo número: ");
        int n2 = scan.nextInt();

        switch (op) {

            case 1:
                System.out.println("Soma: " + c1.somar(n1, n2));
                break;

            case 2:
                System.out.println("Subtração: " + c1.subtrair(n1, n2));
                break;

            case 3:
                System.out.println("Divisão: " + c1.dividir(n1, n2));
                break;

            case 4:
                System.out.println("Multiplicação: " + c1.multiplicar(n1, n2));
                break;

            default:
                System.out.println("Operação inválida");
        }
     */
    } 
}
