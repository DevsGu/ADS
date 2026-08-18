public class Aluno {

    public String nome;
    public double nota;

    public double Aprovado(double nota1 , double nota2){
            this.nota =  (nota1 + nota2) / 2; 

        if(nota >= 7){
                System.out.println("Aluno Aprovado");
        }else{
                System.out.println("Aluno Reprovado");
        }
        return this.nota;
    }

    public String conceito(String nome){
        if(this.nota >= 9){
            System.out.println("Aluno:" + nome + " nota: "+ this.nota);
        }else if(this.nota >= 7 && this.nota <=8.9){
            System.out.println("A nota do "+ nome + " está entre 7 e 8.9");
        }else{
            System.out.println("A nota do "+ nome + " está menor que 7");
        }
        return "nome:" + nome + "nota:"+this.nota;
    }



}
