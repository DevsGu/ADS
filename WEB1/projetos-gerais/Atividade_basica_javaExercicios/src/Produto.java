public class Produto {
    
    public String produto;
    public String nome ;
    public double preco;
    public int quantidade;
    public double totalEstoque ;


    public Produto( String nome , String produto , double preco ,int  quantidade){
        this.nome = nome;
        this.produto = produto;
        this.preco = preco;
        this.quantidade =quantidade;
        this.totalEstoque = preco*quantidade;
    }


    public void ExtratoProduto(){
        System.out.println("nome ="+ nome );
        System.out.println("Produto ="+ produto );
        System.out.println("Preço ="+ preco );
        System.out.println("Quantidade ="+ quantidade );
        System.out.println("Total Estoque =" + totalEstoque);
    }

}
